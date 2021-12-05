package com.example.newsfeed.ui.article

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.newsfeed.R
import com.example.newsfeed.databinding.FragmentArticleBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

private const val TAG = "ArticleFragment"

@AndroidEntryPoint
class ArticleFragment : Fragment(R.layout.fragment_article) {
    private val viewModel: ArticleViewHolder by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentArticleBinding.bind(view)
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(viewModel.article.url)
        }
        binding.apply {
            fab.setOnClickListener {
                try {
                    Log.d(TAG, "onViewCreated: start")
                    viewModel.onFavoritesChanged(viewModel.article)
                    Log.d(TAG, "onViewCreated: finish")
                }
                catch (exception: Exception){
                    Log.e(TAG, "onViewCreated: ", exception)
                }

            }
        }
    }
}