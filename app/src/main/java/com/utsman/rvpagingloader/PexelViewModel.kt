/*
 * Created by Muhammad Utsman on 8/10/19 11:32 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/10/19 11:21 PM
 */

package com.utsman.rvpagingloader

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.utsman.rvpagingloader.adapter.NetworkState
import com.utsman.rvpagingloader.data.Pexel
import com.utsman.rvpagingloader.data.PexelDataFactory
import com.utsman.rvpagingloader.data.PexelDataSource

class PexelViewModel : ViewModel() {

    private var pagingDataFactory: PexelDataFactory? = null

    private fun configPaged(size: Int): PagedList.Config = PagedList.Config.Builder()
        .setPageSize(size)
        .setInitialLoadSizeHint(size * 2)
        .setEnablePlaceholders(true)
        .build()

    fun getCuratedPhoto(): LiveData<PagedList<Pexel>> {
        pagingDataFactory = PexelDataFactory()
        return LivePagedListBuilder(pagingDataFactory!!, configPaged(4)).build()
    }

    fun getLoader(): LiveData<NetworkState> = Transformations.switchMap<PexelDataSource, NetworkState>(
        pagingDataFactory?.pagingLiveData!!
    ) { it.networkState }
}