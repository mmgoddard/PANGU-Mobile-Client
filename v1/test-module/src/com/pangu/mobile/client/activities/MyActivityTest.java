package com.pangu.mobile.client.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class MyActivityTest extends ActivityInstrumentationTestCase2<MyActivity> {

    @TargetApi(Build.VERSION_CODES.FROYO)
    public MyActivityTest() {
        super(MyActivity.class);
    }
}
