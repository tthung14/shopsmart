package com.tuhoc.shopsmart.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignupViewModel : ViewModel() {
    private val firebaseAuth: FirebaseAuth = Firebase.auth

    fun signUp(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, "Signup Successful")
                } else {
                    onResult(false, "User already exists")
                }
            }
    }
}