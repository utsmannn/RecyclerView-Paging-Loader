/*
 * Created by Muhammad Utsman on 8/11/19 6:43 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/11/19 6:43 AM
 */

package com.utsman.adapter.endless

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: Status,
    val msg: String? = null) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
    }
}