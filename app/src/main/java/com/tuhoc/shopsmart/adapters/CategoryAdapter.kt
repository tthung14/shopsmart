package com.tuhoc.shopsmart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tuhoc.shopsmart.data.pojo.Category
import com.tuhoc.shopsmart.data.pojo.Product
import com.tuhoc.shopsmart.databinding.CategoryCardBinding

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var categoryList = mutableListOf<Category>()

    private lateinit var onItemClick: OnItemClick

    fun onItemClicked(onItemClick: OnItemClick) {
        this.onItemClick = onItemClick
    }

    fun setCategoryList(categoryList: List<Category>) {
        this.categoryList = categoryList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = categoryList[position]
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(currentItem.imageUrl)
                .into(imgCategory)

            tvCategoryName.text = currentItem.name
        }
        holder.itemView.setOnClickListener {
            onItemClick.onClickListener(categoryList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class CategoryViewHolder(val binding: CategoryCardBinding): RecyclerView.ViewHolder(binding.root)

    interface OnItemClick{
        fun onClickListener(category: Category)
    }
}
