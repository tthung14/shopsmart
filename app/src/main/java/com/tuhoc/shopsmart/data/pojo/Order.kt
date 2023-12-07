package com.tuhoc.shopsmart.data.pojo

data class Order(val product: Product ?= null, val quantity: Int ?= null, val date: String ?= null)