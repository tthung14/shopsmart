package com.tuhoc.shopsmart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tuhoc.shopsmart.data.pojo.Order
import com.tuhoc.shopsmart.databinding.OrderCardBinding

class OrdersAdapter : RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {
    private var orderList = mutableListOf<Order>()

    fun setOrderList(orderList: List<Order>) {
        this.orderList = orderList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val binding = OrderCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrdersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersAdapter.OrdersViewHolder, position: Int) {
        val currentItem = orderList[position]
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(currentItem.product?.images?.get(0))
                .into(imgImage)
            tvTitle.text = currentItem.product?.title
            tvQuantity.text = currentItem.quantity.toString()

            val price = currentItem.product?.price ?: 0.0
            val quantity = currentItem.quantity ?: 0
            val totalPrice = price * quantity
            tvPrice.text = "$ $totalPrice"

            tvDate.text = currentItem.date
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class OrdersViewHolder(val binding: OrderCardBinding): RecyclerView.ViewHolder(binding.root)
}