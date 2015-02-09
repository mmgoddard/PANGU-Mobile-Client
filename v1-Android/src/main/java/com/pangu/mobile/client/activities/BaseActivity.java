package com.pangu.mobile.client.activities;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Mark on 09/02/15.
 */
public abstract class BaseActivity extends Activity {
    public BaseActivity() {}

    @Override
    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
    }

    protected abstract int getResID();

    /**
     * Called when the activity is resumed.
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * Called when the activity is paused.
     */
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(getSlideLeft(), getSlideRight());
    }

    private int getSlideLeft()  {
        return android.R.anim.slide_in_left;
    }

    private int getSlideRight() {
        return android.R.anim.slide_out_right;
    }
}