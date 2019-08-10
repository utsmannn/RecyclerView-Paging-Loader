/*
 * Created by Muhammad Utsman on 8/10/19 11:32 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/10/19 11:07 PM
 */

package com.utsman.rvpagingloader.data

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.ItemKeyedDataSource
import android.util.Log
import com.utsman.rvpagingloader.RetrofitInstance
import com.utsman.rvpagingloader.adapter.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PexelDataSource : ItemKeyedDataSource<Long, Pexel>() {

    private val perPage = 10
    private var page = 1
    var networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Pexel>) {
        networkState.postValue(NetworkState.LOADING)
        RetrofitInstance.create().getCuratedPhoto(perPage, page)
            .enqueue(object : Callback<Responses> {
                override fun onResponse(call: Call<Responses>, response: Response<Responses>) {
                    val body = response.body()
                    if (body != null) {
                        page++
                        networkState.postValue(NetworkState.LOADED)
                        callback.onResult(body.photos)
                    }
                }

                override fun onFailure(call: Call<Responses>, t: Throwable) {
                    Log.e("anjay", t.localizedMessage)
                    networkState.postValue(NetworkState.error(t.localizedMessage))
                }

            })
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Pexel>) {
        networkState.postValue(NetworkState.LOADING)
        RetrofitInstance.create().getCuratedPhoto(perPage, page)
            .enqueue(object : Callback<Responses> {
                override fun onResponse(call: Call<Responses>, response: Response<Responses>) {
                    val body = response.body()
                    if (body != null) {
                        page++
                        networkState.postValue(NetworkState.LOADING)
                        callback.onResult(body.photos)
                    }
                }

                override fun onFailure(call: Call<Responses>, t: Throwable) {
                    Log.e("anjay", t.localizedMessage)
                    networkState.postValue(NetworkState.error(t.localizedMessage))
                }

            })
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Pexel>) {}

    override fun getKey(item: Pexel): Long = item.id
}