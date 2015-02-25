package com.pangu.mobile.client.background_tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.pangu.mobile.client.R;
import com.pangu.mobile.client.models.ViewPoint;
import com.pangu.mobile.client.utils.ErrorHandler;
import com.pangu.mobile.client.utils.LoggerHandler;
import com.pangu.mobile.client.utils.NetworkHelper;
import uk.ac.dundee.spacetech.pangu.ClientLibrary.ClientConnection;

import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author Mark Goddard
 * @date 08/10/2014
 * @desc Connects to a PANGU server and returns the current image.
 */
public class PanguConnection extends AsyncTask<Void, Void, ErrorHandler> {
    private final WeakReference<LinearLayout> headerProgressReference;
    private final WeakReference<ImageView> imageViewReference;
    private Context context;
    private String dstName;
    private int dstPort;
    private ClientConnection client;
    private Bitmap bitmap;
    private ViewPoint viewPoint;

    /**
     * @param context the current state of application.
     * @desc Constructor for the Socket Connection class
     */
    public PanguConnection(Context context, ImageView imageView, String dstName, int dstPort, ViewPoint viewPoint, LinearLayout headerProgress) {
        this.context = context;
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.headerProgressReference = new WeakReference<LinearLayout>(headerProgress);
        this.dstPort = dstPort;
        this.viewPoint = viewPoint;
        this.dstName = dstName;
    }

    /**
     * @desc: Runs on the UI thread before doInBackground().
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        final LinearLayout linearLayout = headerProgressReference.get();
        linearLayout.setVisibility(View.VISIBLE);
    }

    /**
     * @param arg0 not used, but still needed for Android.
     * @return
     * @desc Connects to PANGU sever and pulls down an .ppm image and converts
     * it to a bitmap image.
     */
    @Override
    protected ErrorHandler doInBackground(Void... arg0) {
        if (NetworkHelper.isOnline(context)) {
            try {
                InetAddress dstAddress = InetAddress.getByName(dstName);
                Socket sock = new Socket(dstAddress, dstPort);
                client = new ClientConnection(sock);
                byte[] image_data = client.getImageByDegrees(viewPoint.getVector3D(), viewPoint.getYawAngle(), viewPoint.getPitchAngle(), viewPoint.getRollAngle());
                //byte[] image_data = client.getImage();
                bitmap = PanguImage.Image(image_data);
                if(bitmap == null) return ErrorHandler.IO_ERROR;
                client.stop();
                return ErrorHandler.OK;
            } catch (Exception e) {
                LoggerHandler.e("IO Exception has occurred when connecting to PANGU server.");
                e.printStackTrace();
                return ErrorHandler.IO_ERROR;
            }
        }
        return ErrorHandler.NO_INTERNET_CONNECTION;
    }

    /**
     * @param result the result of connection to the PANGU Server.
     * @desc Runs on the UI thread after doInBackground().
     */
    @Override
    protected void onPostExecute(ErrorHandler result) {
        final LinearLayout linearLayout = headerProgressReference.get();
        linearLayout.setVisibility(View.GONE);
        if (result == ErrorHandler.OK) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        } else if (result == ErrorHandler.NO_INTERNET_CONNECTION || result == ErrorHandler.IO_ERROR) {
            Toast.makeText(context, result.getLongMessage(), Toast.LENGTH_LONG).show();
            final ImageView imageView = imageViewReference.get();
            imageView.setImageResource(R.drawable.no_image_available);
        }
    }
}
