package com.pangu.mobile.client.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

//@TargetApi(Build.VERSION_CODES.CUPCAKE)
//public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
//    private MainActivity mActivity;
//    private ListView listView;
//    private BaseAdapter baseAdapter;
//
//    @TargetApi(Build.VERSION_CODES.FROYO)
//    public MainActivityTest() {
//        super(MainActivity.class);
//    }
//
//    private ListView findlistViewById(int id) {
//        return (ListView) mActivity.findViewById(id);
//    }
//
//    @Override
//    protected void setUp() throws Exception {
//        super.setUp();
//
//        mActivity = getActivity();
//        listView = findlistViewById(com.pangu.mobile.client.R.layout.list_view);
//    }
//
//    public void testActivity() {
//        assertNotNull(mActivity);
//    }

//    public void testImageAdapter() {
//        listView.setAdapter(imageAdapter);
//        assertSame(imageAdapter, listView.getAdapter());
//
//        listView.setAdapter(null);
//        assertNull(listView.getAdapter());
//    }
//
//    public void testGridSelection() {
//        listView.setSelection(0);
//        assertEquals(0, listView.getSelectedItemPosition());
//
//        listView.setSelection(-1);
//        assertEquals(-1, listView.getSelectedItemPosition());
//
//        listView.setSelection(listView.getCount());
//        assertEquals(listView.getCount(), listView.getSelectedItemPosition());
//    }
//}