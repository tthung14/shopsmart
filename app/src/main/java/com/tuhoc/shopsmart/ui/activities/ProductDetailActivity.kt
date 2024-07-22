package com.tuhoc.shopsmart.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tuhoc.shopsmart.R
import com.tuhoc.shopsmart.data.pojo.Product
import com.tuhoc.shopsmart.databinding.ActivityProductDetailBinding
import com.tuhoc.shopsmart.utils.Constants
import com.tuhoc.shopsmart.viewmodel.DetailsViewModel

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var binding: ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailsViewModel = ViewModelProvider(this)[DetailsViewModel::class.java]

        initView()
        onClick()
    }

    private fun onClick() {
        val product = intent.getSerializableExtra(Constants.PRODUCT) as Product

        binding.imgBack.setOnClickListener {
            finish()
        }

        detailsViewModel.isFavorites(product) {
            if (it) {
                binding.imgWishList.setBackgroundResource(R.drawable.ic_favorites_enable)
            } else {
                binding.imgWishList.setBackgroundResource(R.drawable.ic_favorites)
            }
        }
        binding.imgWishList.setOnClickListener {
            detailsViewModel.addToFavorites(product) {
                if (it) {
                    binding.imgWishList.setBackgroundResource(R.drawable.ic_favorites_enable)
                } else {
                    binding.imgWishList.setBackgroundResource(R.drawable.ic_favorites)
                }
            }
        }

        binding.apply {
            var quantity = tvQuantity.text.toString().toInt()
            val price = tvPrice.text.toString().split("$ ")[1].toDouble()
            tvTotal.text = "$ ${quantity * price}"

            btnDecrease.setOnClickListener {
                if (quantity > 0) {
                    quantity--
                }
                tvQuantity.text = quantity.toString()
                tvTotal.text = "$ ${quantity * price}"
            }
            btnIncrease.setOnClickListener {
                quantity++
                Log.d("TAG", "onClick: $quantity")
                tvQuantity.text = quantity.toString()
                tvTotal.text = "$ ${quantity * price}"
            }
        }

        binding.apply {
            btnAdd.setOnClickListener {
                var quantity = tvQuantity.text.toString().toInt()
                detailsViewModel.addToCart(product, quantity)
                Toast.makeText(this@ProductDetailActivity, "Add to cart Successful", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initView() {
        val intent = intent
        val product = intent.getSerializableExtra(Constants.PRODUCT) as Product

        binding.apply {
            Glide.with(this@ProductDetailActivity)
                .load(product.images?.get(0))
                .into(imgPhoto)
            tvTitle.text = product.title
            tvPrice.text = "$ ${product.price}"
            tvDescription.text = product.description
        }
    }
}