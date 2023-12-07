package com.tuhoc.shopsmart.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tuhoc.shopsmart.data.pojo.Cart
import com.tuhoc.shopsmart.utils.Constants
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CartViewModel : ViewModel() {
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance(Constants.DATABASE_URL)
    private val databaseReference: DatabaseReference = firebaseDatabase.reference
    private val user = Firebase.auth.currentUser
    val cartList = MutableLiveData<List<Cart>>()
    val itemsToOrder = mutableListOf<Cart>()
    fun getCart() {
        user?.let {
            val uid = it.uid
            val cartReference = databaseReference.child(uid).child("cart")
            cartReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val carts = mutableListOf<Cart>()
                    for (cartSnapshot in snapshot.children) {
                        val cart = cartSnapshot.getValue(Cart::class.java)
                        cart?.let { item ->
                            carts.add(item)
                        }
                    }
                    cartList.value = carts
                }

                override fun onCancelled(error: DatabaseError) {
                    // Xử lý lỗi nếu có
                }
            })
        }
    }

    fun deleteCart(cart: Cart) {
        user?.let {
            val uid = it.uid
            val favoritesReference = databaseReference.child(uid).child("cart")
            favoritesReference.child(cart.product?.id.toString()).removeValue()
        }
    }

    fun decreaseQuantity(cart: Cart) {
        user?.let {
            val uid = it.uid
            val cartReference = databaseReference.child(uid).child("cart")

            cartReference.child(cart.product?.id.toString())
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val oldQuantity = snapshot.child("quantity").getValue(Int::class.java) ?: 0
                        val newQuantity = oldQuantity - 1

                        if (newQuantity > 0) {
                            val updateQuantity = mapOf<String, Any>(
                                "quantity" to newQuantity
                            )
                            cartReference.child(cart.product?.id.toString())
                                .updateChildren(updateQuantity)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }
    }

    fun increaseQuantity(cart: Cart) {
        user?.let {
            val uid = it.uid
            val cartReference = databaseReference.child(uid).child("cart")

            cartReference.child(cart.product?.id.toString())
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val oldQuantity = snapshot.child("quantity").getValue(Int::class.java) ?: 0
                        val newQuantity = oldQuantity + 1

                        val updateQuantity = mapOf<String, Any>(
                            "quantity" to newQuantity
                        )
                        cartReference.child(cart.product?.id.toString())
                            .updateChildren(updateQuantity)
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }
    }

    fun onCheckboxClick(cart: Cart, isChecked: Boolean) {
        if (isChecked) {
            itemsToOrder.add(cart)
        } else {
            itemsToOrder.remove(cart)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addToOrder(itemsToOrder: MutableList<Cart>) {
        user?.let {
            val uid = it.uid
            val orderReference = databaseReference.child(uid).child("order")
            val cartReference = databaseReference.child(uid).child("cart")

            for (cart in itemsToOrder) {
                val id = databaseReference.push().key
                orderReference.child(id!!).child("product").setValue(cart.product)
                orderReference.child(id).child("quantity").setValue(cart.quantity)
                orderReference.child(id).child("date").setValue(getCurrentDate())
                cartReference.child(cart.product?.id.toString()).removeValue()
            }
            itemsToOrder.clear()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDate(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return current.format(formatter)
    }

    fun totalCost(itemsToOrder: MutableList<Cart>): Int {
        var total = 0
        for (i in itemsToOrder) {
            val productPrice = i.product?.price ?: 0
            val quantity = i.quantity ?: 0
            total += quantity * productPrice
        }
        return total
    }
}