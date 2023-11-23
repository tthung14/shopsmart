package com.tuhoc.shopsmart.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuhoc.shopsmart.data.pojo.Product
import com.tuhoc.shopsmart.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryViewModel : ViewModel() {
    private val productRepository = ProductRepository()
    val productList = MutableLiveData<List<Product>>()

    fun getProductsByCategory(categoryName: String) {
        viewModelScope.launch { // chạy bất đồng bộ
            try {
                val getProductListResult = withContext(Dispatchers.IO) {
                    productRepository.getProductsByCategory(categoryName)
                }
                if (getProductListResult.isSuccessful) {
                    productList.value = getProductListResult.body()?.products
                }
            } catch (e: Exception) {
                e.message
            }
        }
    }
}