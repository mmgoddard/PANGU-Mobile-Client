package com.pangu.mobile.client.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.GridView;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mActivity;
    private GridView gridView;
    private ImageAdapter imageAdapter;

    @TargetApi(Build.VERSION_CODES.FROYO)
    public MainActivityTest() {
        super(MainActivity.class);
    }

    private GridView findGridViewById(int id) {
        return (GridView) mActivity.findViewById(id);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mActivity = getActivity();
        gridView = new GridView(mActivity);
        imageAdapter = new ImageAdapter(mActivity);
    }

    public void testGridView() {
        assertNotNull(mActivity);
        assertNotNull(gridView);
        assertNotNull(imageAdapter);
        assertTrue(imageAdapter.areAllItemsEnabled());
    }

    public void testImageAdapter() {
        gridView.setAdapter(imageAdapter);
        assertSame(imageAdapter, gridView.getAdapter());

        gridView.setAdapter(null);
        assertNull(gridView.getAdapter());
    }

    public void testGridSelection() {
        gridView.setSelection(0);
        assertEquals(0, gridView.getSelectedItemPosition());

        gridView.setSelection(-1);
        assertEquals(-1, gridView.getSelectedItemPosition());

        gridView.setSelection(gridView.getCount());
        assertEquals(gridView.getCount(), gridView.getSelectedItemPosition());
    }
}