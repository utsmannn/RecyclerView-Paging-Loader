/*
 * Created by Muhammad Utsman on 8/10/19 11:32 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/10/19 10:49 PM
 */

package com.utsman.rvpagingloader.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

abstract class NetworkViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun bind(idProgress: Int?, idText: Int?, networkState: NetworkState?) = itemView.run {

        val progressLoader = idProgress?.let { findViewById<View>(it) }
        val textView = idText?.let { findViewById<TextView>(it) }

        progressLoader?.visibility = toVisibility(networkState?.status == Status.RUNNING)
        textView?.visibility = toVisibility(networkState?.status == Status.FAILED)

        textView?.text = "Network error: ${networkState?.msg}"
    }

    private fun toVisibility(constraint: Boolean): Int {
        return if (constraint) View.VISIBLE
        else View.GONE
    }
}