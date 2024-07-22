package com.tuhoc.shopsmart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tuhoc.shopsmart.data.pojo.Cart
import com.tuhoc.shopsmart.databinding.CartProductCardBinding

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private var cartList = mutableListOf<Cart>()
    private lateinit var onItemClick: CartAdapter.OnItemClick

    fun onItemClicked(onItemClick: CartAdapter.OnItemClick) {
        this.onItemClick = onItemClick
    }

    fun setCartList(cartList: List<Cart>) {
        this.cartList = cartList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
        val currentItem = cartList[position]
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(currentItem.product?.images?.get(0))
                .into(imgImage)

            cbChoose.isChecked = false

            tvTitle.text = currentItem.product?.title
            tvPrice.text = currentItem.product?.price.toString()
            tvQuantity.text = currentItem.quantity.toString()
            tvTotal.text = "$ " + tvPrice.text.toString().toDouble() * tvQuantity.text.toString().toInt()

            imgDelete.setOnClickListener {
                onItemClick.onDeleteClick(currentItem)
            }

            btnDecrease.setOnClickListener {
                onItemClick.onDecreaseClick(currentItem)
            }
            btnIncrease.setOnClickListener {
                onItemClick.onIncreaseClick(currentItem)
            }

            cbChoose.setOnClickListener {
                onItemClick.onCheckboxClick(currentItem, cbChoose.isChecked)
            }
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    class CartViewHolder(val binding: CartProductCardBinding): RecyclerView.ViewHolder(binding.root){

        fun onBind(cart: Cart) {
            adapterPosition
//            binding.
        }
    }

    interface OnItemClick{
        fun onDeleteClick(cart: Cart)
        fun onCheckboxClick(cart: Cart, isChecked: Boolean)
        fun onDecreaseClick(cart: Cart)
        fun onIncreaseClick(cart: Cart)
    }
}