package com.tuhoc.shopsmart.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.tuhoc.shopsmart.adapters.ProductAdapter
import com.tuhoc.shopsmart.data.pojo.Product
import com.tuhoc.shopsmart.databinding.FragmentFavoritesBinding
import com.tuhoc.shopsmart.ui.activities.ProductDetailActivity
import com.tuhoc.shopsmart.utils.Constants
import com.tuhoc.shopsmart.viewmodel.FavoritesViewModel

class FavoritesFragment : Fragment() {
    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favoritesViewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
        productAdapter = ProductAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeProducts()
        onProductClick()
    }

    private fun prepareRecyclerView() {
        binding.rlvFavorites.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun observeProducts() {
        favoritesViewModel.getProductsByFavorite()
        favoritesViewModel.productList.observe(viewLifecycleOwner) { productList ->
            productAdapter.setProductList(productList.toMutableList())
            if (productList.isEmpty())
                binding.tvFavorites.visibility = View.VISIBLE
            else
                binding.tvFavorites.visibility = View.GONE
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