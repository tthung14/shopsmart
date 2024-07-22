package com.tuhoc.shopsmart.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.tuhoc.shopsmart.databinding.ActivityLoginBinding
import com.tuhoc.shopsmart.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        initView()
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null ) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            user.reload()
            finish()
        }
    }

    private fun initView() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            val regEmail = ".*@gmail\\.com$".toRegex()
            val regPassword = ".{6,}$".toRegex()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                binding.progressBar.visibility = View.VISIBLE
                loginViewModel.logIn(email, password) { success, message ->
                    binding.progressBar.visibility = View.GONE
                    if (regEmail.matches(email) && regPassword.matches(password)) {
                        if (success) {
                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Please enter the correct format", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this@LoginActivity, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
            finish()
        }

        binding.tvForgot.setOnClickListener {
            val email = binding.edtEmail.text.toString()

            val regEmail = ".*@gmail\\.com$".toRegex()

            if (email.isNotEmpty()) {
                binding.progressBar.visibility = View.VISIBLE
                loginViewModel.forgetPassword(email) { success, message ->
                    binding.progressBar.visibility = View.GONE
                    if (regEmail.matches(email)) {
                        if (success) {
                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Please enter the correct format", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this@LoginActivity, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            }
        }
    }
}