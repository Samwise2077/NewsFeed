package com.example.newsfeed.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.databinding.NewsLoadStateFooterBinding

class HomeLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<HomeLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(private val binding: NewsLoadStateFooterBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener {
               retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState is LoadState.Error
                textViewError.isVisible = loadState is LoadState.Error
            }
        }

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = NewsLoadStateFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  LoadStateViewHolder(binding)
    }


}
