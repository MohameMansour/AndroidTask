package com.example.task.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task.data.remote.dto.PostDto
import com.example.task.databinding.ItemPostBinding
import javax.inject.Inject

class PostAdapter @Inject constructor() :
    ListAdapter<PostDto, PostAdapter.ProductViewHolder>(PRODUCT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        getItem(position).let { holder.bind(it) }
    }

    inner class ProductViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: PostDto) {
            binding.apply {
                itemCategoryTvTitle.text = model.title
                itemCategoryTvBody.text = model.body
            }
        }
    }

    //check difference
    companion object {
        private val PRODUCT_COMPARATOR = object : DiffUtil.ItemCallback<PostDto>() {
            override fun areItemsTheSame(
                oldItem: PostDto,
                newItem: PostDto
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: PostDto,
                newItem: PostDto
            ) = oldItem == newItem
        }
    }

}