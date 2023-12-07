package com.tuhoc.shopsmart.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuhoc.shopsmart.adapters.CartAdapter
import com.tuhoc.shopsmart.data.pojo.Cart
import com.tuhoc.shopsmart.databinding.FragmentCartBinding
import com.tuhoc.shopsmart.viewmodel.CartViewModel

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
        cartAdapter = CartAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeCart()
        onClick()
    }

    private fun prepareRecyclerView() {
        binding.rlvCarts.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun observeCart() {
        cartViewModel.getCart()
        cartViewModel.cartList.observe(viewLifecycleOwner) { cartList ->
            cartAdapter.setCartList(cartList.toMutableList())
            if (cartList.isEmpty()) {
                binding.tvCarts.visibility = View.VISIBLE
                binding.clFooter.visibility = View.GONE
            }
            else
                binding.tvCarts.visibility = View.GONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onClick() {
        cartAdapter.onItemClicked(object : CartAdapter.OnItemClick {
            override fun onDeleteClick(cart: Cart) {
                cartViewModel.deleteCart(cart)
            }

            override fun onDecreaseClick(cart: Cart) {
                cartViewModel.decreaseQuantity(cart)
            }

            override fun onIncreaseClick(cart: Cart) {
                cartViewModel.increaseQuantity(cart)
            }

            override fun onCheckboxClick(cart: Cart, isChecked: Boolean) {
                cartViewModel.onCheckboxClick(cart, isChecked)

                binding.tvTotal.text = "$ " +cartViewModel.totalCost(cartViewModel.itemsToOrder)
            }
        })

        binding.btnOrder.setOnClickListener {
            cartViewModel.addToOrder(cartViewModel.itemsToOrder)
            binding.tvTotal.text = "$ 0"
            Toast.makeText(requireContext(), "Order Successful", Toast.LENGTH_SHORT).show()
        }
    }
}