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
            Category("beauty","Beauty", "https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/thumbnail.png"),
            Category("fragrances", "Fragrances", "https://cdn.dummyjson.com/products/images/fragrances/Chanel%20Coco%20Noir%20Eau%20De/thumbnail.png"),
            Category("furniture", "Furniture", "https://cdn.dummyjson.com/products/images/furniture/Annibale%20Colombo%20Bed/thumbnail.png"),
            Category("groceries", "Groceries", "https://cdn.dummyjson.com/products/images/groceries/Apple/thumbnail.png"),
            Category("home-decoration", "Home Decoration", "https://cdn.dummyjson.com/products/images/home-decoration/Decoration%20Swing/thumbnail.png"),
            Category("kitchen-accessories", "Kitchen Accessories", "https://cdn.dummyjson.com/products/images/kitchen-accessories/Bamboo%20Spatula/thumbnail.png"),
            Category("laptops", "Laptops", "https://cdn.dummyjson.com/products/images/laptops/Apple%20MacBook%20Pro%2014%20Inch%20Space%20Grey/thumbnail.png"),
            Category("mens-shirts", "Mens Shirts", "https://cdn.dummyjson.com/products/images/mens-shirts/Blue%20&%20Black%20Check%20Shirt/thumbnail.png"),
            Category("mens-shoes", "Mens Shoes", "https://cdn.dummyjson.com/products/images/mens-shoes/Nike%20Air%20Jordan%201%20Red%20And%20Black/thumbnail.png"),
            Category("mens-watches", "Mens Watches", "https://cdn.dummyjson.com/products/images/mens-watches/Brown%20Leather%20Belt%20Watch/thumbnail.png"),
            Category("mobile-accessories", "Mobile Accessories", "https://cdn.dummyjson.com/products/images/mobile-accessories/Amazon%20Echo%20Plus/thumbnail.png"),
            Category("motorcycle", "Motorcycle", "https://cdn.dummyjson.com/products/images/motorcycle/Generic%20Motorcycle/thumbnail.png"),
            Category("skin-care", "Skin Care", "https://cdn.dummyjson.com/products/images/skin-care/Attitude%20Super%20Leaves%20Hand%20Soap/thumbnail.png"),
            Category("smartphones", "Smartphones", "https://cdn.dummyjson.com/products/images/smartphones/iPhone%20X/thumbnail.png"),
            Category("sports-accessories", "Sports Accessories", "https://cdn.dummyjson.com/products/images/sports-accessories/American%20Football/thumbnail.png"),
            Category("sunglasses", "Sunglasses", "https://cdn.dummyjson.com/products/images/sunglasses/Black%20Sun%20Glasses/thumbnail.png"),
            Category("tablets", "Tablets", "https://cdn.dummyjson.com/products/images/tablets/iPad%20Mini%202021%20Starlight/thumbnail.png"),
            Category("tops", "Tops", "https://cdn.dummyjson.com/products/images/tops/Blue%20Frock/thumbnail.png"),
            Category("vehicle", "Vehicle", "https://cdn.dummyjson.com/products/images/vehicle/300%20Touring/2.png"),
            Category("womens-bags", "Womens Bags", "https://cdn.dummyjson.com/products/images/womens-bags/Blue%20Women's%20Handbag/thumbnail.png"),
            Category("womens-dresses", "Womens Dresses", "https://cdn.dummyjson.com/products/images/womens-dresses/Black%20Women's%20Gown/thumbnail.png"),
            Category("womens-jewellery", "Womens Jewellery", "https://cdn.dummyjson.com/products/images/womens-jewellery/Green%20Crystal%20Earring/thumbnail.png"),
            Category("womens-shoes", "Womens Shoes", "https://cdn.dummyjson.com/products/images/womens-shoes/Black%20&%20Brown%20Slipper/thumbnail.png"),
            Category("womens-watches", "Womens Watches", "https://cdn.dummyjson.com/products/images/womens-watches/IWC%20Ingenieur%20Automatic%20Steel/thumbnail.png")
        )
        categoryList.value = sampleCategories

        sliderList.value = sampleCategories.map { it.url }
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
//    fun getAllCategories() {
//        viewModelScope.launch { // chạy bất đồng bộ
//            try {
//                val getProductListResult = withContext(Dispatchers.IO) {
//                    productRepository.getAllProducts()
//                }
//                if (getProductListResult.isSuccessful) {
//                    productList.value = getProductListResult.body()?.products
//                }
//            } catch (e: Exception) {
//                e.message
//            }
//        }
//    }
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