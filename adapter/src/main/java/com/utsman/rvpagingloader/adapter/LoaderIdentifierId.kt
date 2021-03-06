/*
 * Created by Muhammad Utsman on 8/10/19 11:32 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/10/19 10:44 PM
 */

package com.utsman.rvpagingloader.adapter

data class LoaderIdentifierId(val idLoader: Int? = null,
                              val idTextError: Int? = null) {

    data class Builder(private var idLoader: Int? = null,
                       private var idTextError: Int? = null) {

        fun setIdProgressLoader(id: Int) = apply { this.idLoader = id }
        fun setIdTextViewError(id: Int) = apply { this.idTextError = id }

        fun build() = LoaderIdentifierId(idLoader, idTextError)
    }
}