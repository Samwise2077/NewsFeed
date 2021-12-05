package com.example.newsfeed.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.newsfeed.R
import com.example.newsfeed.data.NewsArticle
import com.example.newsfeed.databinding.FragmentFavoriteNewsBinding
import com.example.newsfeed.ui.home.HomeAdapter
import com.example.newsfeed.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "FavoriteNewsFragment"

@AndroidEntryPoint
class FavoriteNewsFragment : Fragment(R.layout.fragment_favorite_news), HomeAdapter.OnItemClickListener {
    private val viewModel: FavoriteNewsViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFavoriteNewsBinding.bind(view)
        val homeAdapter = HomeAdapter(this)
        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                adapter = homeAdapter
            }
            bottomNavigationView.selectedItemId = R.id.savedNewsFragment
            bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                when(item.itemId){
                    R.id.breakingNewsFragment -> {
                        val action = FavoriteNewsFragmentDirections.actionFavoriteNewsFragmentToHomeFragment()
                        findNavController().navigate(action)
                    }
                }
                true
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.onFavoriteArticlesGot().collect {
                Log.d(TAG, "onViewCreated: $it ")
                homeAdapter.submitData(viewLifecycleOwner.lifecycle, PagingData.from(it))
            }
        }
    }

    override fun onItemClick(article: NewsArticle) {
        val action = FavoriteNewsFragmentDirections.actionFavoriteNewsFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }
}