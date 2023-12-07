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
import com.tuhoc.shopsmart.data.pojo.Order
import com.tuhoc.shopsmart.utils.Constants

class OrdersViewModel: ViewModel() {
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance(Constants.DATABASE_URL)
    private val databaseReference: DatabaseReference = firebaseDatabase.reference
    private val user = Firebase.auth.currentUser
    val orderList = MutableLiveData<List<Order>>()

    fun getOrder() {
        user?.let {
            val uid = it.uid
            val cartReference = databaseReference.child(uid).child("order")
            cartReference.addValueEventListener (object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val orders = mutableListOf<Order>()
                    for (orderSnapshot in snapshot.children) {
                        val order = orderSnapshot.getValue(Order::class.java)
                        order?.let { item ->
                            orders.add(item)
                        }
                    }
                    orderList.value = orders
                }

                override fun onCancelled(error: DatabaseError) {
                    // Xử lý lỗi nếu có
                }
            })
        }
    }

}