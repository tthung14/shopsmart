package com.tuhoc.shopsmart.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.smarteist.autoimageslider.SliderView
import com.tuhoc.shopsmart.R
import com.tuhoc.shopsmart.adapters.CategoryAdapter
import com.tuhoc.shopsmart.adapters.ProductAdapter
import com.tuhoc.shopsmart.adapters.SliderAdapter
import com.tuhoc.shopsmart.data.pojo.Category
import com.tuhoc.shopsmart.data.pojo.Product
import com.tuhoc.shopsmart.databinding.FragmentHomeBinding
import com.tuhoc.shopsmart.ui.activities.CategoryActivity
import com.tuhoc.shopsmart.ui.activities.ProductDetailActivity
import com.tuhoc.shopsmart.utils.Constants.CATEGORY
import com.tuhoc.shopsmart.utils.Constants.PRODUCT
import com.tuhoc.shopsmart.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var sliderAdapter: SliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        productAdapter = ProductAdapter()
        categoryAdapter = CategoryAdapter()
        sliderAdapter = SliderAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeProducts()
        observeCategories()
        observeSliders()
        onProductClick()
        onCategoryClick()
        onSearchClick()
    }

    private fun onSearchClick() {
        binding.btnSearch.setOnClickListener {
            val name = binding.edtSearch.text.toString()
            val bundle = Bundle()
            bundle.putString("name", name)
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment, bundle)
        }
    }

    private fun prepareRecyclerView() {
        binding.apply {
            rlvAllProduct.adapter = productAdapter

            rlvCategory.apply {
                adapter = categoryAdapter
                layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            }

            // slider
            slvPhoto.setSliderAdapter(sliderAdapter)
            slvPhoto.apply {
                autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
                scrollTimeInSec = 3
                isAutoCycle = true
                startAutoCycle()
            }
        }
    }

    private fun observeProducts() {
        homeViewModel.getAllProducts()
        homeViewModel.productList.observe(viewLifecycleOwner) { productList ->
            productAdapter.setProductList(productList.toMutableList())
        }
    }

    private fun observeCategories() {
        homeViewModel.getCategories().observe(viewLifecycleOwner) { categories ->
            categoryAdapter.setCategoryList(categories)
        }
    }

    private fun observeSliders() {
        homeViewModel.getSliders().observe(viewLifecycleOwner) { sliders ->
            sliderAdapter.setSliderList(sliders)
        }
    }

    private fun onProductClick() {
        productAdapter.onItemClicked(object : ProductAdapter.OnItemClick {
            override fun onClickListener(product: Product) {
                // Chuyển đến màn hình chi tiết sản phẩm khi sản phẩm được click
                val intent = Intent(requireContext(), ProductDetailActivity::class.java)
                intent.putExtra(PRODUCT, product)
                startActivity(intent)
            }
        })
    }

    private fun onCategoryClick() {
        categoryAdapter.onItemClicked(object : CategoryAdapter.OnItemClick {
            override fun onClickListener(category: Category) {
                val intent = Intent(requireContext(), CategoryActivity::class.java)
                intent.putExtra(CATEGORY, category)
                startActivity(intent)
            }
        })
    }
}
