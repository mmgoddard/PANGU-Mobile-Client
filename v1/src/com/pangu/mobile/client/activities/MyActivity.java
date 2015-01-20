package com.pangu.mobile.client.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import com.pangu.mobile.client.R;
import com.pangu.mobile.client.background_tasks.PanguConnection;

/**
 * @Author Mark Goddard
 * @Date 08/10/2014
 * @Desc Starts the main activity.
 */
public class MyActivity extends Activity {
    private int dstPort = 10363;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ImageView imgView = (ImageView) findViewById(R.id.pangu_image);
        PanguConnection panguConnection = new PanguConnection(this, imgView, dstPort);
        panguConnection.execute();
    }
}
