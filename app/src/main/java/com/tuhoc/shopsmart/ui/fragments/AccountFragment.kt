package com.tuhoc.shopsmart.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.tuhoc.shopsmart.databinding.FragmentAccountBinding
import com.tuhoc.shopsmart.ui.activities.EditAccountActivity
import com.tuhoc.shopsmart.ui.activities.LoginActivity
import com.tuhoc.shopsmart.viewmodel.AccountViewModel

class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    private lateinit var accountViewModel: AccountViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        accountViewModel = ViewModelProvider(this)[AccountViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun initData() {
        if (accountViewModel.getUser()!!.photoUrl != null) {
            Glide.with(requireContext())
                .load(accountViewModel.getUser()?.photoUrl)
                .into(binding.imgImage)
        }

        if (accountViewModel.getUser()!!.displayName != null) {
            binding.tvName.text = accountViewModel.getUser()?.displayName
        } else {
            binding.tvName.text = accountViewModel.getUser()?.email.toString().split("@gmail.com")[0]
        }

        binding.tvEmail.text = accountViewModel.getUser()?.email
        accountViewModel.orderQuantity {
            binding.tvOrderQuantity.text = it.toString()
        }
    }

    private fun initView() {
        binding.btnLogout.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            FirebaseAuth.getInstance().signOut()
            activity?.finish()
        }

        binding.btnEdit.setOnClickListener {
            startActivity(Intent(requireContext(), EditAccountActivity::class.java))
        }
    }
}