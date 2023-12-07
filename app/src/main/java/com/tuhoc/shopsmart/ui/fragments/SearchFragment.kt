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
import com.tuhoc.shopsmart.adapters.ProductAdapter
import com.tuhoc.shopsmart.data.pojo.Product
import com.tuhoc.shopsmart.databinding.FragmentSearchBinding
import com.tuhoc.shopsmart.ui.activities.ProductDetailActivity
import com.tuhoc.shopsmart.utils.Constants
import com.tuhoc.shopsmart.viewmodel.HomeViewModel

class SearchFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var productAdapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        productAdapter = ProductAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeProducts()
        onProductClick()
    }
    private fun prepareRecyclerView() {
        binding.rlvSearch.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeProducts() {
        val name: String? = arguments?.getString("name")
        homeViewModel.searchByName(name.toString())
        homeViewModel.productList.observe(viewLifecycleOwner) {
            productAdapter.setProductList(it.toMutableList())
            if (it.isEmpty())
                binding.tvNoProduct.visibility = View.VISIBLE
            else
                binding.tvNoProduct.visibility = View.GONE
        }
    }

    private fun onProductClick() {
        productAdapter.onItemClicked(object : ProductAdapter.OnItemClick {
            override fun onClickListener(product: Product) {
                // Chuyển đến màn hình chi tiết sản phẩm khi sản phẩm được click
                val intent = Intent(requireContext(), ProductDetailActivity::class.java)
                intent.putExtra(Constants.PRODUCT, product)
                startActivity(intent)
            }
        })
    }
}