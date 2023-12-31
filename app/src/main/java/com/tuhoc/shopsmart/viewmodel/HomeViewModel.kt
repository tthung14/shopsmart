package com.tuhoc.shopsmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuhoc.shopsmart.data.pojo.Category
import com.tuhoc.shopsmart.data.pojo.Product
import com.tuhoc.shopsmart.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel: ViewModel() {
    private val productRepository = ProductRepository()
    val productList = MutableLiveData<List<Product>>()
    private val categoryList = MutableLiveData<List<Category>>()
    private val sliderList = MutableLiveData<List<String>>()

    init {
        val sampleCategories = listOf(
            Category("smartphones", "https://i.dummyjson.com/data/products/2/1.jpg"),
            Category("laptops", "https://i.dummyjson.com/data/products/6/2.jpg"),
            Category("fragrances", "https://i.dummyjson.com/data/products/13/1.jpg"),
            Category("skincare", "https://i.dummyjson.com/data/products/16/thumbnail.jpg"),
            Category("groceries", "https://i.dummyjson.com/data/products/21/2.jpg"),
            Category("home-decoration", "https://i.dummyjson.com/data/products/28/3.png"),
            Category("furniture", "https://i.dummyjson.com/data/products/31/thumbnail.jpg"),
            Category("tops", "https://i.dummyjson.com/data/products/36/1.jpg"),
            Category("womens-dresses", "https://i.dummyjson.com/data/products/41/1.jpg"),
            Category("womens-shoes", "https://i.dummyjson.com/data/products/46/2.jpg"),
            Category("mens-shirts", "https://i.dummyjson.com/data/products/53/thumbnail.jpg"),
            Category("mens-shoes", "https://i.dummyjson.com/data/products/58/2.jpg"),
            Category("mens-watches", "https://i.dummyjson.com/data/products/61/thumbnail.jpg"),
            Category("womens-watches", "https://i.dummyjson.com/data/products/68/2.jpg"),
            Category("womens-bags", "https://i.dummyjson.com/data/products/71/thumbnail.jpg"),
            Category("womens-jewellery", "https://i.dummyjson.com/data/products/78/thumbnail.jpg"),
            Category("sunglasses", "https://i.dummyjson.com/data/products/84/thumbnail.jpg"),
            Category("automotive", "https://i.dummyjson.com/data/products/86/thumbnail.jpg"),
            Category("motorcycle", "https://i.dummyjson.com/data/products/92/2.jpg"),
            Category("lighting", "https://i.dummyjson.com/data/products/97/thumbnail.jpg"),
        )
        categoryList.value = sampleCategories

        sliderList.value = sampleCategories.map { it.imageUrl }
    }

    fun getCategories(): LiveData<List<Category>> {
        return categoryList
    }

    fun getSliders(): LiveData<List<String>> {
        return sliderList
    }

    fun getAllProducts() {
        viewModelScope.launch { // chạy bất đồng bộ
            try {
                val getProductListResult = withContext(Dispatchers.IO) {
                    productRepository.getAllProducts()
                }
                if (getProductListResult.isSuccessful) {
                    productList.value = getProductListResult.body()?.products
                }
            } catch (e: Exception) {
                e.message
            }
        }
    }
    fun searchByName(name: String) {
        viewModelScope.launch { // chạy bất đồng bộ
            try {
                val getProductListResult = withContext(Dispatchers.IO) {
                    productRepository.getAllProducts()
                }
                if (getProductListResult.isSuccessful) {
                    val allProducts = getProductListResult.body()?.products

                    val filteredProducts = allProducts?.filter {
                        it.title?.toLowerCase()?.contains(name.trim()) == true
                    }
                    productList.value = filteredProducts!!
                }
            } catch (e: Exception) {
                // Xử lý ngoại lệ nếu cần
                e.message
            }
        }
    }
}