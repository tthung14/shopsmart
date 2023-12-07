package com.tuhoc.shopsmart.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginViewModel : ViewModel() {
    private val firebaseAuth: FirebaseAuth = Firebase.auth
    fun logIn(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, "Login Successful")
                } else {
                    onResult(false, "Login False")
                }
            }
    }
}