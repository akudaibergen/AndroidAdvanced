package com.paging_library.android_paging.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.paging_library.android_paging.R
import com.paging_library.android_paging.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user.view.*

class UserFragment : Fragment() {

    private val adapter = UserAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_user, container, false)

        val activity = activity as Context

        view.recycler_view.layoutManager = LinearLayoutManager(activity)
        val itemViewModel = ViewModelProviders.of(this)
            .get(UserViewModel::class.java)

        itemViewModel.userPagedList.observe(this, Observer {
            adapter.submitList(it)
        })

        view.recycler_view.adapter = adapter

        return view
    }

}