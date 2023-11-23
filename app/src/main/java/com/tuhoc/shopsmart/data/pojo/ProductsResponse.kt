package com.tuhoc.shopsmart.data.pojo


import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("total")
    val total: Int
)