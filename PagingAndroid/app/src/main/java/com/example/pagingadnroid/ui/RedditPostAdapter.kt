package com.example.pagingadnroid.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingadnroid.R
import com.example.pagingadnroid.data.model.RedditPost
import com.example.pagingadnroid.utils.DiffUtilCallBack
import kotlinx.android.synthetic.main.item_list.view.*

class RedditPostsAdapter : PagedListAdapter<RedditPost, RedditPostsAdapter.MyViewHolder>(
    DiffUtilCallBack()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bindPost(it) }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val scoreText = itemView.score
        val commentsText = itemView.comments
        val titleText = itemView.title

        fun bindPost(redditPost: RedditPost) {
            with(redditPost) {
                scoreText.text = score.toString()
                commentsText.text = commentCount.toString()
                titleText.text = title
            }
        }
    }
}