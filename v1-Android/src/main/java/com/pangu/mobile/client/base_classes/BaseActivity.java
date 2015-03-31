package com.pangu.mobile.client.base_classes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.DialogFragment;
import android.view.MenuItem;
import android.view.View;

import com.pangu.mobile.client.R;

/**
 * Created by Mark on 09/02/15.
 */
public abstract class BaseActivity extends ActionBarActivity {
    private int screenWidth;

    protected int getScreenWidth() {
        return screenWidth;
    }

    protected void setScreenWidth() {
        this.screenWidth = calculateWidthOfScreen();
    }

    protected Menu actionBarMenu;
    protected int currentApiVersion = android.os.Build.VERSION.SDK_INT;

    public BaseActivity() {}

    @Override
    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setScreenWidth();
    }

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

    private int getSlideLeft() {
        return android.R.anim.slide_in_left;
    }

    private int getSlideRight() {
        return android.R.anim.slide_out_right;
    }

    protected abstract int getMainLayoutResID();

    protected abstract int getOptionsMenuLayoutResID();

    protected int getToolbarLayoutResID() {
        return R.id.global_toolbar;
    }

    /**
     * Calculate the width of display in use. Used to calculate the alignment of element on display.
     *
     * @return size.x
     */
    protected int calculateWidthOfScreen() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        if (currentApiVersion >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(size);
            return size.x;
        } else {
            return display.getWidth();
        }
    }

    protected void showDialogFragment(DialogFragment newFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack("dialog");
        newFragment.show(ft, "dialog");
    }

    protected void startIntent(String intentStr) {
        Intent intent = new Intent(intentStr);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}