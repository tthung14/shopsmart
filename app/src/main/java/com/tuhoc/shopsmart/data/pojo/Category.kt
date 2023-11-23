package com.tuhoc.shopsmart.data.pojo


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val name: String,
    val imageUrl: String
) : Parcelable