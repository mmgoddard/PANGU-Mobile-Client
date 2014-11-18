package com.pangu.mobile.client.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import com.pangu.mobile.client.background_tasks.Logger;
import com.pangu.mobile.client.background_tasks.PanguConnection;
import com.pangu.mobile.client.interfaces.AsyncResponse;
import com.pangu.mobile.client.R;

/**
 * @Author Mark Goddard
 * @Date 08/10/2014
 * @Desc Starts the main activity.
 */
public class MyActivity extends Activity implements AsyncResponse {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        PanguConnection panguConnection = new PanguConnection(this);
        panguConnection.execute();
        panguConnection.asyncResponse = this;
    }

    /**
     * @desc processes the image from the async-task (Pangu Connection).
     * @param bitmap
     */
    public void processImage(Bitmap bitmap) {
        Logger.i("Displaying Image");
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageBitmap(bitmap);
    }
}
