package com.tuhoc.shopsmart.repositories

import com.tuhoc.shopsmart.data.retrofit.RetrofitInstance

class ProductRepository { // kiểu api thì mới dùng response
     suspend fun getAllProducts() = RetrofitInstance.getProductApi.getProducts()

     suspend fun getProductsByCategory(categoryName: String) = RetrofitInstance.getProductApi.getProductsByCategory(categoryName)
}