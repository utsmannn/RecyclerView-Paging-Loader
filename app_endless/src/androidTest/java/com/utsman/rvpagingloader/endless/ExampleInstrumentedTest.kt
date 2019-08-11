/*
 * Created by Muhammad Utsman on 8/11/19 6:58 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/11/19 6:58 AM
 */

package com.utsman.rvpagingloader.endless

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.utsman.rvpagingloader.endless", appContext.packageName)
    }
}
