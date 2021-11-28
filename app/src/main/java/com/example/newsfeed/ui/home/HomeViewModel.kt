package com.example.newsfeed.ui.home

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.newsfeed.data.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository) : ViewModel(){

    val searchQuery =  MutableLiveData("")

    val articles =
        searchQuery.switchMap {Log.d(TAG, ":ok")
            repository.getSearchResults(it).cachedIn(viewModelScope)  }

    companion object{
        const val DEFAULT_QUERY = "Trump"
    }
}