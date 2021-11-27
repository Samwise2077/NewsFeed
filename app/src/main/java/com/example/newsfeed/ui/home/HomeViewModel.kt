package com.example.newsfeed.ui.home

import androidx.lifecycle.ViewModel
import com.example.newsfeed.data.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository) : ViewModel(){
}