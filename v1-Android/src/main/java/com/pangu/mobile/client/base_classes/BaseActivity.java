package com.pangu.mobile.client.base_classes;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * Created by Mark on 09/02/15.
 */
public abstract class BaseActivity extends Activity {
    private int screenWidth;
    protected int getScreenWidth() { return screenWidth; }
    protected void setScreenWidth() { this.screenWidth = calculateWidthOfScreen(); }
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

    /**
     * Called when the action bar is created.
     *
     * @param menu
     * @return onCreateOptionsMenu()
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(getOptionsMenuLayoutResID() != 0)
            inflater.inflate(getOptionsMenuLayoutResID(), menu);
        return super.onCreateOptionsMenu(menu);
    }

    private int getSlideLeft() {
        return android.R.anim.slide_in_left;
    }
    private int getSlideRight() {
        return android.R.anim.slide_out_right;
    }
    protected abstract int getMainLayoutResID();
    protected abstract int getOptionsMenuLayoutResID();

    /**
     * Calculate the width of display in use. Used to calculate the alignment of element on display.
     * @return size.x
     */
    protected int calculateWidthOfScreen() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    protected void showDialogFragment(DialogFragment newFragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
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