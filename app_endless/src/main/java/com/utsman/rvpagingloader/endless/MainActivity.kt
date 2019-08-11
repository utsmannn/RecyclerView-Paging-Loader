/*
 * Created by Muhammad Utsman on 8/11/19 6:58 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/11/19 6:58 AM
 */

package com.utsman.rvpagingloader.endless

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.utsman.adapter.endless.EndlessScrollListener
import com.utsman.adapter.endless.LoaderIdentifierId
import com.utsman.adapter.endless.NetworkState
import com.utsman.rvpagingloader.endless.data.Responses
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val COLUMN_SIZE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loaderIdentifierId = LoaderIdentifierId.Builder()
            .setIdProgressLoader(R.id.progress_circular)
            .setIdTextViewError(R.id.error_text_view)
            .build()

        val endlessAdapter = MyEndlessAdapter(loaderIdentifierId)
        val layoutManager = GridLayoutManager(this, COLUMN_SIZE)
        layoutManager.spanSizeLookup = endlessAdapter.setGridSpan(COLUMN_SIZE)

        main_recycler_view.layoutManager = layoutManager
        main_recycler_view.adapter = endlessAdapter

        setupData(endlessAdapter, 1)
        main_recycler_view.addOnScrollListener(object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                setupData(endlessAdapter, page+1)
            }

        })
    }

    private fun setupData(endlessAdapter: MyEndlessAdapter, page: Int) {
        endlessAdapter.submitNetworkState(NetworkState.LOADING)
        RetrofitInstance.create().getCuratedPhoto(10, page)
            .enqueue(object : Callback<Responses> {
                override fun onFailure(call: Call<Responses>, t: Throwable) {
                    Log.e("anjay", t.localizedMessage)
                    endlessAdapter.submitNetworkState(NetworkState.error(t.localizedMessage))
                }

                override fun onResponse(call: Call<Responses>, response: Response<Responses>) {
                    endlessAdapter.submitList(response.body()?.photos)
                    endlessAdapter.submitNetworkState(NetworkState.LOADED)
                }

            })
    }
}
