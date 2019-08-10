/*
 * Created by Muhammad Utsman on 8/10/19 11:32 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/10/19 11:07 PM
 */

package com.utsman.rvpagingloader.data

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource

class PexelDataFactory : DataSource.Factory<Long, Pexel>() {

    val pagingLiveData = MutableLiveData<PexelDataSource>()
    override fun create(): DataSource<Long, Pexel> {
        val data = PexelDataSource()
        pagingLiveData.postValue(data)
        return data
    }
}