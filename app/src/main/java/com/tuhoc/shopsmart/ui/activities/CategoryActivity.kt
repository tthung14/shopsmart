package com.tuhoc.shopsmart.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tuhoc.shopsmart.adapters.ProductAdapter
import com.tuhoc.shopsmart.data.pojo.Category
import com.tuhoc.shopsmart.data.pojo.Product
import com.tuhoc.shopsmart.databinding.ActivityCategoryBinding
import com.tuhoc.shopsmart.utils.Constants
import com.tuhoc.shopsmart.viewmodel.CategoryViewModel

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productAdapter = ProductAdapter()
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        prepareRecyclerView()
        observeProducts()
        onProductClick()
        onBack()
    }

    private fun onBack() {
        binding.imgBack.setOnClickListener {
            finishAndRemoveTask()
        }
    }

    private fun prepareRecyclerView() {
        binding.rlvProduct.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        }
    }

    private fun observeProducts() {
        try {
            val intent = intent
            val category: Category? = intent.getParcelableExtra(Constants.CATEGORY)

            binding.tvCategoryName.text = category?.name.toString()
            categoryViewModel.getProductsByCategory(category?.name?: "")
            categoryViewModel.productList.observe(this) { productList ->
                productAdapter.setProductList(productList.toMutableList())
            }
        } catch (e: Exception) {
            e.message
        }
    }

    private fun onProductClick() {
        productAdapter.onItemClicked(object : ProductAdapter.OnItemClick {
            override fun onClickListener(product: Product) {
                val intent = Intent(this@CategoryActivity, ProductDetailActivity::class.java)
                intent.putExtra(Constants.PRODUCT, product)
                startActivity(intent)
            }
        })
    }
}