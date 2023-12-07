package com.tuhoc.shopsmart.viewmodel

import androidx.lifecycle.MutableLiveData
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

class FavoritesViewModel :ViewModel() {
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance(Constants.DATABASE_URL)
    private val databaseReference: DatabaseReference = firebaseDatabase.reference
    private val user = Firebase.auth.currentUser
    val productList = MutableLiveData<List<Product>>()

    fun getProductsByFavorite() {
        user?.let {
            val uid = it.uid
            val favoritesReference = databaseReference.child(uid).child("favorites")
            favoritesReference.addValueEventListener (object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val products = mutableListOf<Product>()
                    for (productSnapshot in snapshot.children) {
                        // Lấy thông tin của sản phẩm từ DataSnapshot và thêm vào danh sách
                        val product = productSnapshot.getValue(Product::class.java)
                        product?.let { item ->
                            products.add(item)
                        }
                    }

                    productList.value = products
                }

                override fun onCancelled(error: DatabaseError) {
                    // Xử lý lỗi nếu có
                }
            })
        }
    }
}