package com.pangu.mobile.client.base_classes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
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

import android.widget.TextView;
import com.pangu.mobile.client.R;
import com.pangu.mobile.client.utils.TypefaceSpan;

/**
 * Created by Mark on 09/02/15.
 */
public abstract class BaseActivity extends ActionBarActivity {
    protected Menu actionBarMenu;
    protected TextView toolbarTitle;
    protected Toolbar mActionBarToolbar;
    private int screenWidth;

    protected int getScreenWidth() {
        return screenWidth;
    }
    protected void setScreenWidth() {
        this.screenWidth = calculateWidthOfScreen();
    }
    protected int getCurrentApiVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    public BaseActivity() {}

    @Override
    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setScreenWidth();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
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


    /**
     * Calculate the width of display in use. Used to calculate the alignment of element on display.
     *
     * @return size.x
     */
    protected int calculateWidthOfScreen() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        if (getCurrentApiVersion() >= Build.VERSION_CODES.HONEYCOMB_MR2) {
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

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
                mActionBarToolbar.setLogo(R.drawable.ic_action_planet);
                toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
                SpannableString s = new SpannableString("PANGU");
                s.setSpan(new TypefaceSpan(this, "Roboto-Regular.ttf"), 0, s.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                toolbarTitle.setText(s);
            }
        }
        return mActionBarToolbar;
    }

    protected TextView setToolbarTitle(String title) {
        if (toolbarTitle != null) {
            SpannableString s = new SpannableString(title);
            s.setSpan(new TypefaceSpan(this, "Roboto-Regular.ttf"), 0, s.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            toolbarTitle.setText(s);
        }
        return toolbarTitle;
    }
}