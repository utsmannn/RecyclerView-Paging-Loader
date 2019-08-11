/*
 * Created by Muhammad Utsman on 8/11/19 7:01 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/11/19 7:01 AM
 */

package com.utsman.rvpagingloader.endless.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Pexel(val id: Long,
                 val width: Int,
                 val height: Int,
                 val url: String,
                 val photographer: String,
                 val photographer_url: String,
                 val src: Source
)

data class Source(val original: String,
                  val large2x: String,
                  val large: String,
                  val medium: String,
                  val small: String,
                  val portrait: String,
                  val landscape: String,
                  val tiny: String)

data class Responses(val page: Int,
                     val per_page: Int,
                     val photos: List<Pexel>)

data class Category(val query: String,
                    val url: String)

const val HEADER = "Authorization: 563492ad6f91700001000001880a2e3eb5d6452a94a3dd050f6395a6"
const val BASE_URL = "https://api.pexels.com/"