package com.tuhoc.shopsmart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tuhoc.shopsmart.data.pojo.User
import com.tuhoc.shopsmart.utils.Constants
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance(Constants.DATABASE_URL).reference.child("users")

    fun signUp(name: String, email: String, password: String, onResult: (Boolean, String) -> Unit) {
        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    val id = databaseReference.push().key
                    val user = User(id, name, email, password)
                    viewModelScope.launch {
                        databaseReference.child(id!!).setValue(user)
                        onResult(true, "Signup Successful")
                    }
                } else {
                    onResult(false, "User already exists")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onResult(false, "Database Error: ${error.message}")
            }
        })
    }
}