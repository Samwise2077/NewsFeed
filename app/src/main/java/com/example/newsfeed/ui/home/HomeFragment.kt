package com.example.newsfeed.ui.home

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.newsfeed.R
import com.example.newsfeed.data.NewsArticle
import com.example.newsfeed.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), HomeAdapter.OnItemClickListener {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        val adapter = HomeAdapter(this)
         binding.apply {
             recyclerview.setHasFixedSize(true)
             recyclerview.itemAnimator = null
             recyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
                 header = HomeLoadStateAdapter{adapter.retry()},
                 footer = HomeLoadStateAdapter{adapter.retry()})
             buttonRetry.setOnClickListener {
                 adapter.retry()
             }


         }
        Log.d(TAG, "onViewCreated: check")
        viewModel.articles.observe(viewLifecycleOwner){
              adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerview.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textviewError.isVisible = loadState.source.refresh is LoadState.Error


                // empty view
                if(loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                        adapter.itemCount < 1){
                    recyclerview.isVisible = false
                    textviewEmpty.isVisible = true

                }
                else{
                    textviewEmpty.isVisible = false
                }
            }
        }
        setHasOptionsMenu(true)
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    binding.recyclerview.scrollToPosition(0)
                    viewModel.searchQuery.value = query
                    searchView.clearFocus()

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(article: NewsArticle) {
        val action = HomeFragmentDirections.actionHomeFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }
}