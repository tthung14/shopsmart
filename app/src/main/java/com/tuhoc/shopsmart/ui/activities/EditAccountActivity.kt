package com.tuhoc.shopsmart.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.tuhoc.shopsmart.databinding.ActivityEditAccountBinding
import com.tuhoc.shopsmart.viewmodel.EditAccountViewModel

class EditAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditAccountBinding
    private lateinit var editAccountViewModel: EditAccountViewModel

    private var selectedImageUri: Uri? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editAccountViewModel = ViewModelProvider(this)[EditAccountViewModel::class.java]

        initView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initView() {
        val name = binding.edtName.text
        val newPass = binding.edtNewPassword.text
        val oldPass = binding.edtOldPassword.text

        binding.btnCamera.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        binding.btnSave.setOnClickListener {
            if (selectedImageUri != null) {
                editAccountViewModel.editPhoto(selectedImageUri!!) { message, check ->
                    if (check) {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            if (name.isNotEmpty()) {
                editAccountViewModel.editProfile(name.toString()) { message, check ->
                    if (check) {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            if (newPass.isNotEmpty() || oldPass.isNotEmpty()) {
                if (checkPasswordField()) {
                    editAccountViewModel.editPassword(oldPass.toString(), newPass.toString()) { message, check ->
                        if (check) {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding.btnExit.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            val uri: Uri = data?.data!!
            selectedImageUri = uri
            binding.imgImage.setImageURI(uri)
        }
    }

    private fun checkPasswordField(): Boolean {
        val newPass = binding.edtNewPassword.text.toString()
        val oldPass = binding.edtOldPassword.text.toString()
        val confirm = binding.edtConfirm.text.toString()

        if (newPass.length < 6 || oldPass.length < 6) {
            Toast.makeText(this, "Please enter the correct format", Toast.LENGTH_SHORT).show()
            return false
        }

        if (newPass != confirm) {
            Toast.makeText(this, "Reconfirm", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}