/*
 * Created by Muhammad Utsman on 8/11/19 6:44 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/11/19 6:44 AM
 */

package com.utsman.adapter.endless

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

@Suppress("UNCHECKED_CAST")
abstract class EndlessLoaderAdapter<V, VH : RecyclerView.ViewHolder, VHN : RecyclerView.ViewHolder> constructor(private val loaderIdentifierId: LoaderIdentifierId) : RecyclerView.Adapter<VH>() {

    private var list: MutableList<V>? = mutableListOf()

    private var networkState: NetworkState? = null

    abstract fun onItemCreateViewHolder(parent: ViewGroup, viewType: Int) : VH

    abstract fun onNetworkViewHolder(parent: ViewGroup, viewType: Int) : VHN

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return when(viewType) {
            ITEM -> onItemCreateViewHolder(parent, viewType)
            LOADER -> onNetworkViewHolder(parent, viewType) as VH
            else  -> throw IllegalArgumentException("not found view holder")
        }
    }

    abstract fun onItemBindViewHolder(holder: VH, position: Int)

    open fun onLoaderBindViewHolder(holder: NetworkViewHolder, position: Int, loaderIdentifierRes: LoaderIdentifierId) {
        val idLoader = loaderIdentifierRes.idLoader
        val idTextView = loaderIdentifierRes.idTextError
        holder.bind(idLoader, idTextView, networkState)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        when(getItemViewType(position)) {
            ITEM -> onItemBindViewHolder(holder, position)
            LOADER -> onLoaderBindViewHolder(holder as NetworkViewHolder, position, loaderIdentifierId)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount -1 ) {
            LOADER
        } else {
            ITEM
        }
    }

    fun getItem(position: Int): V? {
        return list?.get(position)
    }

    override fun getItemCount(): Int = (list?.size ?: 0) + if (hasExtraRow()) 1 else 0

    fun submitList(list: List<V>?) {
        list?.let { this.list?.addAll(it) }
        notifyDataSetChanged()
    }

    fun submitNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    fun setGridSpan(column: Int) = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when (getItemViewType(position)) {
                ITEM -> 1
                LOADER -> column
                else -> 1
            }
        }

    }

    companion object TYPE {
        const val ITEM = 0
        const val LOADER = 1
    }
}