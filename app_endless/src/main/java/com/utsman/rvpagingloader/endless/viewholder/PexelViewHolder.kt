/*
 * Created by Muhammad Utsman on 8/11/19 7:01 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/10/19 11:33 PM
 */

package com.utsman.rvpagingloader.endless.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.utsman.rvpagingloader.endless.R
import com.utsman.rvpagingloader.endless.data.Pexel

class PexelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(pexel: Pexel) = itemView.run {
        val imgView = findViewById<ImageView>(R.id.main_image)

        Glide.with(context)
            .load(pexel.src.medium)
            .into(imgView)
    }
}