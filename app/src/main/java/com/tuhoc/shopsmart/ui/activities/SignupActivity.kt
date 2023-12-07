package com.tuhoc.shopsmart.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tuhoc.shopsmart.databinding.ActivitySignupBinding
import com.tuhoc.shopsmart.viewmodel.SignupViewModel

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var signupViewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signupViewModel = ViewModelProvider(this)[SignupViewModel::class.java]

        initView()
    }

    private fun initView() {
        val chk = binding.cbAgree
        chk.isChecked = false
        binding.cbAgree.setOnCheckedChangeListener { _, isChecked ->
            chk.isChecked = isChecked
        }

        binding.btnSignup.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirm = binding.edtConfirm.text.toString()

            val regEmail = ".*@gmail\\.com$".toRegex()
            val regPassword = ".{6,}$".toRegex()

            if (email.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty()) {
                binding.progressBar.visibility = View.VISIBLE
                if (password == confirm) {
                    if (chk.isChecked) {
                        signupViewModel.signUp(email, password) { success, message ->
                            binding.progressBar.visibility = View.GONE
                            if (regEmail.matches(email) && regPassword.matches(password)) {
                                if (success) {
                                    Toast.makeText(this@SignupActivity, message, Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(this@SignupActivity, message, Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this@SignupActivity, "Please enter the correct format", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@SignupActivity, "Please click on your privacy policy", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@SignupActivity, "Reconfirm", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@SignupActivity, "All fields are mandatory", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
            finish()
        }
    }
}