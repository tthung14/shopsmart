package com.tuhoc.shopsmart.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tuhoc.shopsmart.data.pojo.User
import com.tuhoc.shopsmart.utils.Constants

class LoginViewModel : ViewModel() {
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance(Constants.DATABASE_URL).reference.child("users")

    fun logIn(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)

                        if (user != null && user.password == password) {
                            onResult(true, "Login Successful")
                            return
                        }
                    }
                } else {
                    onResult(false, "Login False")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onResult(false, "Database Error: ${error.message}")
            }
        })
    }
}