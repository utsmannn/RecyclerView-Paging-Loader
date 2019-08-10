/*
 * Created by Muhammad Utsman on 8/10/19 11:33 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/10/19 11:33 PM
 */

package com.utsman.rvpagingloader

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.utsman.rvpagingloader.adapter.LoaderIdentifierId
import com.utsman.rvpagingloader.adapter.PexelAdapter

class MainActivity : AppCompatActivity() {

    private val GRID_COLUMN = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this)[PexelViewModel::class.java]

        val loaderIdentifierId = LoaderIdentifierId.Builder()
            .setIdProgressLoader(R.id.progress_circular)
            .setIdTextViewError(R.id.error_text_view)
            .build()

        val pexelAdapter = PexelAdapter(loaderIdentifierId)

        val layoutManager = GridLayoutManager(this, GRID_COLUMN)
        layoutManager.spanSizeLookup = pexelAdapter.setGridSpan(GRID_COLUMN)

        //val layoutManager = LinearLayoutManager(this)

        val recyclerView = findViewById<RecyclerView>(R.id.main_recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = pexelAdapter

        viewModel.getCuratedPhoto().observe(this, Observer { dataPexel ->
            pexelAdapter.submitList(dataPexel)
        })

        viewModel.getLoader().observe(this, Observer { networkState ->
            pexelAdapter.submitNetworkState(networkState)
        })

    }
}
