package com.tuhoc.shopsmart.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.tuhoc.shopsmart.R
import com.tuhoc.shopsmart.data.pojo.Product
import com.tuhoc.shopsmart.databinding.ActivityProductDetailBinding
import com.tuhoc.shopsmart.utils.Constants
import com.tuhoc.shopsmart.viewmodel.DetailsViewModel

class ProductDetailActivity : AppCompatActivity() {
    lateinit var detailsViewModel: DetailsViewModel
    private lateinit var binding: ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        onBack()
    }

    private fun onBack() {
        binding.imgBack.setOnClickListener {
            finishAndRemoveTask()
        }
    }

    private fun initView() {
        val intent = intent
        val product = intent.getSerializableExtra(Constants.PRODUCT) as Product

        binding.apply {
            Glide.with(this@ProductDetailActivity)
                .load(product.images[0])
                .into(imgPhoto)
            tvTitle.text = product.title
            tvPrice.text = "$ ${product.price}"
            tvDescription.text = product.description
        }
    }

}