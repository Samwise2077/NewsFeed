package com.example.newsfeed.ui.home

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsfeed.R
import com.example.newsfeed.data.NewsArticle
import com.example.newsfeed.databinding.ItemNewsArticleBinding

private const val TAG = "HomeAdapter"

class HomeAdapter(private val listener: OnItemClickListener) : PagingDataAdapter<NewsArticle, HomeAdapter.HomeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        Log.d(TAG, "view holder: created")
        val binding = ItemNewsArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class HomeViewHolder(private val binding: ItemNewsArticleBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }
           fun bind(article: NewsArticle){
               binding.apply {
                   Log.d(TAG, "bind: ${article.title}")

                   Glide.with(itemView)
                       .load(article.urlToImage)
                       .transition(DrawableTransitionOptions.withCrossFade())
                   //  .error(R.drawable.)
                       .into(picture)
                   titleTextView.text = article.title
                   dateCreatedTextView.text = article.publishedAt
                   descriptionTextView.text = article.description
                   siteTextView.text = article.source.name
               }
           }

    }

    interface OnItemClickListener{
        fun onItemClick(article: NewsArticle)
    }

    class DiffCallback : DiffUtil.ItemCallback<NewsArticle>() {
        override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle) = oldItem.url == newItem.url


        override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle) = oldItem == newItem

    }


}