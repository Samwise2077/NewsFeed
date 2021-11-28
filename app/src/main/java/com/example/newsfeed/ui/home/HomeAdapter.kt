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

class HomeAdapter : PagingDataAdapter<NewsArticle, HomeAdapter.HomeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemNewsArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class HomeViewHolder(private val binding: ItemNewsArticleBinding) : RecyclerView.ViewHolder(binding.root){
           fun bind(article: NewsArticle){
               binding.apply {
                   Glide.with(itemView)
                       .load(article.urlToImage)
                       .transition(DrawableTransitionOptions.withCrossFade())
                       .into(picture)
                   titleTextView.text = article.title
                   dateCreatedTextView.text = article.publishedAt
                   descriptionTextView.text = article.description
                   siteTextView.text = article.source.name
               }
           }

    }

    class DiffCallback : DiffUtil.ItemCallback<NewsArticle>() {
        override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle) = oldItem.url == newItem.url


        override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle) = oldItem == newItem

    }


}