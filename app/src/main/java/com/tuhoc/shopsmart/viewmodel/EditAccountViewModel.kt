package com.tuhoc.shopsmart.viewmodel

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class EditAccountViewModel : ViewModel() {
    private val user = Firebase.auth.currentUser
    private val storageRef = FirebaseStorage.getInstance().reference

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDate(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy")
        return current.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun editPhoto(photoUri: Uri, onResult: (String, Boolean) -> Unit) {
        val imageRef: StorageReference = storageRef.child("images/${getCurrentDate()}.jpg")
        Log.d("TAG", "editPhoto: ${getCurrentDate()}")

        imageRef.putFile(photoUri)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {

                    val profileUpdates = userProfileChangeRequest {
                        this.photoUri = it
                    }

                    user!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onResult("Edit successful", true)
                        } else {
                            onResult("Edit failure", false)
                        }
                    }
                }.addOnFailureListener { e ->
                    onResult("Get Image failure: ${e.message}", false)
                }
            }
            .addOnFailureListener { e ->
                onResult("Image upload failure: ${e.message}", false)
            }
    }

    fun editProfile(name: String, onResult: (String, Boolean) -> Unit) {
        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }
        user!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult("Edit successful", true)
            } else {
                onResult("Edit failure", false)
            }
        }
    }

    fun editPassword(oldPass: String, newPass: String, onResult: (String, Boolean) -> Unit) {
        val credential = EmailAuthProvider.getCredential(user?.email.toString(), oldPass)
        user?.reauthenticate(credential)?.addOnCompleteListener { reauthTask ->
            if (reauthTask.isSuccessful) {
                user.updatePassword(newPass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onResult("Edit successful", true)
                    } else {
                        onResult("Edit failure", false)
                    }
                }
            } else {
                onResult("Old password failure", false)
            }
        }
    }
}