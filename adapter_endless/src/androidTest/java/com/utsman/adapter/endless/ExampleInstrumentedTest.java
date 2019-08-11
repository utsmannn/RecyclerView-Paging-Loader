/*
 * Created by Muhammad Utsman on 8/11/19 6:42 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/11/19 6:42 AM
 */

package com.utsman.adapter.endless;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.utsman.adapter.endless.test", appContext.getPackageName());
    }
}
