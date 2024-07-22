package com.tuhoc.shopsmart.data.pojo


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val slug: String,
    val name: String,
    val url: String
) : Parcelable

//data class Category(
//    @SerializedName("slug")
//    val slug: String?=null,
//    @SerializedName("name")
//    val name: String?=null,
//    @SerializedName("url")
//    val url: String?=null
//) : Serializable