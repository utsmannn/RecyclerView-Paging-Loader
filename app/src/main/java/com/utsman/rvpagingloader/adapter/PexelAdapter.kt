/*
 * Created by Muhammad Utsman on 8/10/19 11:33 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/10/19 11:33 PM
 */

package com.utsman.rvpagingloader.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.utsman.rvpagingloader.R
import com.utsman.rvpagingloader.data.Pexel
import com.utsman.rvpagingloader.adapter.viewholder.LoaderViewHolder
import com.utsman.rvpagingloader.adapter.viewholder.PexelViewHolder

class PexelAdapter(loaderIdentifierId: LoaderIdentifierId) :
    PagingLoaderAdapter<Pexel, PexelViewHolder, LoaderViewHolder>(PexelDiffUtil(), loaderIdentifierId) {

    override fun onItemCreateViewHolder(parent: ViewGroup, viewType: Int): PexelViewHolder =
        PexelViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false))

    override fun onNetworkViewHolder(parent: ViewGroup, viewType: Int): LoaderViewHolder =
        LoaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loader, parent, false))

    override fun onItemBindViewHolder(holder: PexelViewHolder, position: Int) {
        val pexel = getItem(position)
        if (pexel != null) holder.bind(pexel)
    }
}