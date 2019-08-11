/*
 * Created by Muhammad Utsman on 8/11/19 7:05 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/11/19 7:05 AM
 */

package com.utsman.rvpagingloader.endless

import android.view.LayoutInflater
import android.view.ViewGroup
import com.utsman.adapter.endless.EndlessLoaderAdapter
import com.utsman.adapter.endless.LoaderIdentifierId
import com.utsman.adapter.endless.NetworkViewHolder
import com.utsman.rvpagingloader.endless.data.Pexel
import com.utsman.rvpagingloader.endless.viewholder.LoaderViewHolder
import com.utsman.rvpagingloader.endless.viewholder.PexelViewHolder

class MyEndlessAdapter(identifierId: LoaderIdentifierId) : EndlessLoaderAdapter<Pexel, PexelViewHolder, LoaderViewHolder>(identifierId) {

    override fun onItemCreateViewHolder(parent: ViewGroup, viewType: Int): PexelViewHolder =
        PexelViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false))

    override fun onNetworkViewHolder(parent: ViewGroup, viewType: Int): LoaderViewHolder =
        LoaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loader, parent, false))

    override fun onItemBindViewHolder(holder: PexelViewHolder, position: Int) {
        val pexel = getItem(position)
        if (pexel != null) holder.bind(pexel)
    }
}