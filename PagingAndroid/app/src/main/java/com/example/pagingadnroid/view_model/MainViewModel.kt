package com.example.pagingadnroid.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pagingadnroid.data.model.RedditPost
import com.example.pagingadnroid.data.repository.PostsDataSource
import kotlinx.coroutines.Dispatchers

class MainViewModel : ViewModel() {
    var postsLiveData  : LiveData<PagedList<RedditPost>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()
        postsLiveData  = initializedPagedListBuilder(config).build()
    }

    fun getPosts():LiveData<PagedList<RedditPost>> = postsLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<String, RedditPost> {

        val dataSourceFactory = object : DataSource.Factory<String, RedditPost>() {
            override fun create(): DataSource<String, RedditPost> {
                return PostsDataSource(Dispatchers.IO)
            }
        }
        return LivePagedListBuilder<String, RedditPost>(dataSourceFactory, config)
    }
}