package com.tuhoc.shopsmart.repositories

import com.tuhoc.shopsmart.data.retrofit.RetrofitInstance

class ProductRepository {
     suspend fun getAllProducts() = RetrofitInstance.getProductApi.getProducts()

     suspend fun getProductsByCategory(categoryName: String) = RetrofitInstance.getProductApi.getProductsByCategory(categoryName)
}