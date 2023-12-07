package com.tuhoc.shopsmart.data.pojo
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(
    @SerializedName("brand")
    val brand: String?=null,
    @SerializedName("category")
    val category: String?=null,
    @SerializedName("description")
    val description: String?=null,
    @SerializedName("discountPercentage")
    val discountPercentage: Double?=null,
    @SerializedName("id")
    val id: Int?=null,
    @SerializedName("images")
    val images: List<String>?=null,
    @SerializedName("price")
    val price: Int?=null,
    @SerializedName("rating")
    val rating: Double?=null,
    @SerializedName("stock")
    val stock: Int?=null,
    @SerializedName("thumbnail")
    val thumbnail: String?=null,
    @SerializedName("title")
    val title: String?=null
): Serializable