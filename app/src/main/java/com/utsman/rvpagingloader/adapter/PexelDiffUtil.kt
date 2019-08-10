/*
 * Created by Muhammad Utsman on 8/10/19 11:32 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/10/19 11:07 PM
 */

package com.utsman.rvpagingloader.adapter

import android.support.v7.util.DiffUtil
import com.utsman.rvpagingloader.data.Pexel

class PexelDiffUtil : DiffUtil.ItemCallback<Pexel>() {
    override fun areItemsTheSame(oldItem: Pexel, newItem: Pexel): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Pexel, newItem: Pexel): Boolean = oldItem == newItem
}