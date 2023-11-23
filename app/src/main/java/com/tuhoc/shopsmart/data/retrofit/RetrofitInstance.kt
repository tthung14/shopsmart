package com.tuhoc.shopsmart.data.retrofit

import com.tuhoc.shopsmart.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val instance: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val getProductApi: ProductApi = instance.create(ProductApi::class.java)
    }
}