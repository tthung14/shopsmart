package com.tuhoc.shopsmart.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tuhoc.shopsmart.R
import com.tuhoc.shopsmart.data.pojo.Product
import com.tuhoc.shopsmart.databinding.ProductCardBinding
import java.util.Random

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var productList = mutableListOf<Product>()

    private lateinit var onItemClick: OnItemClick

    fun onItemClicked(onItemClick: OnItemClick) {
        this.onItemClick = onItemClick
    }

    fun setProductList(productList: List<Product>) {
        this.productList = productList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        val currentItem = productList[position]
        val rand = Random()
        holder.binding.apply {
            Glide.with(holder.itemView)
//                .load(currentItem.images?.get(rand.nextInt(currentItem.images.size)))
                .load(currentItem.images[0])
                .into(imgImage)

            tvTitle.text = currentItem.title
            tvPrice.text = currentItem.price.toString()
            tvRating.text = currentItem.rating.toString()
        }

        holder.itemView.setOnClickListener {
            onItemClick.onClickListener(productList[position])
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(val binding: ProductCardBinding): RecyclerView.ViewHolder(binding.root)

    interface OnItemClick{
        fun onClickListener(product: Product)
    }
}
