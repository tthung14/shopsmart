package com.tuhoc.shopsmart.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tuhoc.shopsmart.data.pojo.Product
import com.tuhoc.shopsmart.utils.Constants

class DetailsViewModel: ViewModel() {
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance(Constants.DATABASE_URL)
    private val databaseReference: DatabaseReference = firebaseDatabase.reference
    private val user = Firebase.auth.currentUser

    fun isFavorites(product: Product, callback: (Boolean) -> Unit) {
        user?.let { currentUser ->
            val favoritesRef = databaseReference.child(currentUser.uid).child("favorites").child(product.id.toString())
            favoritesRef.get().addOnSuccessListener {
                callback(it.exists())
            }
        }
    }

    fun addToFavorites(product: Product, onResult: (Boolean) -> Unit) {
        user?.let {
            val uid = it.uid
            val favoritesReference = databaseReference.child(uid).child("favorites")
            favoritesReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!snapshot.hasChild(product.id.toString())) {
                        // Tên không trùng, thêm vào danh sách yêu thích
                        favoritesReference.child(product.id.toString()).setValue(product)
                        onResult(true)
                    } else {
                        // Tên đã tồn tại trong danh sách yêu thích, xoá nó đi
                        favoritesReference.child(product.id.toString()).removeValue()
                        onResult(false)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Xử lý lỗi nếu có
                }
            })
        }
    }

    fun addToCart(product: Product, quantity: Int) {
        user?.let {
            val uid = it.uid
            val cartReference = databaseReference.child(uid).child("cart")
            cartReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!snapshot.hasChild(product.id.toString())) {
                        // không tồn tại
                        cartReference.child(product.id.toString()).child("product").setValue(product)
                        cartReference.child(product.id.toString()).child("quantity").setValue(quantity)
                    } else {
                        // tồn tại
                        val oldQuantity = snapshot.child(product.id.toString()).child("quantity").getValue(Int::class.java)?:0
                        val newQuantity = oldQuantity + quantity
                        val updateQuantity = mapOf<String, Any>(
                            "quantity" to  newQuantity
                        )
                        cartReference.child(product.id.toString()).updateChildren(updateQuantity)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Xử lý lỗi nếu có
                }
            })
        }
    }
}