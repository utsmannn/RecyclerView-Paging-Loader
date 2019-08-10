/*
 * Created by Muhammad Utsman on 8/10/19 11:32 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/10/19 11:11 PM
 */

package com.utsman.rvpagingloader.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.utsman.rvpagingloader.R
import com.utsman.rvpagingloader.data.Pexel

class PexelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(pexel: Pexel) = itemView.run {
        val imgView = findViewById<ImageView>(R.id.main_image)

        Glide.with(context)
            .load(pexel.src.medium)
            .into(imgView)
    }
}