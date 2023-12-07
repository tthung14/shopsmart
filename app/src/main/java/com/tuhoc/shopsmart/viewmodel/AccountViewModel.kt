package com.tuhoc.shopsmart.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tuhoc.shopsmart.utils.Constants

class AccountViewModel : ViewModel() {
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance(Constants.DATABASE_URL)
    private val databaseReference: DatabaseReference = firebaseDatabase.reference
    private val user = Firebase.auth.currentUser

    fun getUser(): FirebaseUser? {
        return user
    }

    fun orderQuantity(callback: (Int) -> Unit) {
        user?.let {
            val uid = it.uid
            val orderReference = databaseReference.child(uid).child("order")
            orderReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val numberOfNodes = snapshot.childrenCount.toInt()
                    callback(numberOfNodes)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }
}