package com.example.pagingadnroid.data.repository

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.pagingadnroid.data.model.RedditPost
import com.example.pagingadnroid.data.network.ApiService
import com.example.pagingadnroid.data.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PostsDataSource(coroutineContext: CoroutineContext) :
    PageKeyedDataSource<String, RedditPost>() {
    private val apiService = RetrofitClient.getClient().create(ApiService::class.java)

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, RedditPost>) {
        scope.launch {
            try {
                val response = apiService.fetchPosts(loadSize = params.requestedLoadSize)
                when{
                    response.isSuccessful -> {
                        val listing = response.body()?.data
                        val redditPosts = listing?.children?.map { it.data }
                        callback.onResult(redditPosts ?: listOf(), listing?.before, listing?.after)
                    }
                }

            }catch (exception : Exception){
                Log.e("PostsDataSource", "Failed to fetch data!")
            }

        }

    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        scope.launch {
            try {
                val response =
                    apiService.fetchPosts(loadSize = params.requestedLoadSize, after = params.key)
                when{
                    response.isSuccessful -> {
                        val listing = response.body()?.data
                        val items = listing?.children?.map { it.data }
                        callback.onResult(items ?: listOf(), listing?.after)
                    }
                }

            }catch (exception : Exception){
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }

    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        scope.launch {
            try {
                val response =
                    apiService.fetchPosts(loadSize = params.requestedLoadSize, before = params.key)
                when{
                    response.isSuccessful -> {
                        val listing = response.body()?.data
                        val items = listing?.children?.map { it.data }
                        callback.onResult(items ?: listOf(), listing?.after)
                    }
                }

            }catch (exception : Exception){
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }

    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }

}