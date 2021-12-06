package com.example.newsfeed.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.R
import com.example.newsfeed.data.NewsArticle
import com.example.newsfeed.databinding.FragmentFavoriteNewsBinding
import com.example.newsfeed.ui.home.HomeAdapter
import com.example.newsfeed.ui.home.HomeFragmentDirections
import com.google.android.material.snackbar.Snackbar
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
            bottomNavigationView.setOnItemSelectedListener { item ->
                when(item.itemId){
                    R.id.breakingNewsFragment -> {
                        val action = FavoriteNewsFragmentDirections.actionFavoriteNewsFragmentToHomeFragment()
                        findNavController().navigate(action)
                    }
                }
                true
            }
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val article = homeAdapter.snapshot()[viewHolder.adapterPosition]
                    viewModel.onArticleSwiped(article)

                }

            }).attachToRecyclerView(recyclerview)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.onFavoriteArticlesGot().collect {
                Log.d(TAG, "onViewCreated: $it ")
                homeAdapter.submitData(viewLifecycleOwner.lifecycle, PagingData.from(it))
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.favoriteNewsEvent.collect { event ->
                when(event){
                    is FavoriteNewsViewModel.FavoriteNewsEvent.ShowUndoDeleteMessage ->{
                        Snackbar.make(requireView(), "Article was deleted", Snackbar.LENGTH_LONG)
                            .setAction("UNDO"){
                                viewModel.onUndoClick(event.article)
                            }.show()
                    }
                }
            }
        }
    }

    override fun onItemClick(article: NewsArticle) {
        val action = FavoriteNewsFragmentDirections.actionFavoriteNewsFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }
}