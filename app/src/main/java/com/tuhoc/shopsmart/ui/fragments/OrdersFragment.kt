package com.tuhoc.shopsmart.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuhoc.shopsmart.adapters.OrdersAdapter
import com.tuhoc.shopsmart.databinding.FragmentOrdersBinding
import com.tuhoc.shopsmart.viewmodel.OrdersViewModel

class OrdersFragment : Fragment() {
    private lateinit var binding: FragmentOrdersBinding
    private lateinit var ordersViewModel: OrdersViewModel
    private lateinit var ordersAdapter: OrdersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ordersViewModel = ViewModelProvider(this)[OrdersViewModel::class.java]
        ordersAdapter = OrdersAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observeOrders()
    }
    private fun prepareRecyclerView() {
        binding.rlvOrders.apply {
            adapter = ordersAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun observeOrders() {
        ordersViewModel.getOrder()
        ordersViewModel.orderList.observe(viewLifecycleOwner) { orderList ->
            ordersAdapter.setOrderList(orderList.toMutableList())
            if (orderList.isEmpty())
                binding.tvOrders.visibility = View.VISIBLE
            else
                binding.tvOrders.visibility = View.GONE
        }
    }
}