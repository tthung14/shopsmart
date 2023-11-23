package com.tuhoc.shopsmart.data.retrofit

import com.tuhoc.shopsmart.data.pojo.ProductsResponse
import com.tuhoc.shopsmart.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET(Constants.ALL_PRODUCT_URL)
    suspend fun getProducts(): Response<ProductsResponse> // suspend để đánh dấu là một hàm chờ

    @GET("${Constants.PRODUCT_BY_CATEGORY_URL}{categoryName}")
    suspend fun getProductsByCategory(@Path("categoryName") categoryName: String): Response<ProductsResponse>

}