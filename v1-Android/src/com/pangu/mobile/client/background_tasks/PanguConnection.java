package com.pangu.mobile.client.background_tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;
import com.pangu.mobile.client.utils.ErrorHandler;
import com.pangu.mobile.client.models.ViewPoint;
import com.pangu.mobile.client.utils.Logger;
import com.pangu.mobile.client.utils.NetworkHelper;
import uk.ac.dundee.spacetech.pangu.ClientLibrary.ClientConnection;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author Mark Goddard
 * @date 08/10/2014
 * @desc Connects to a PANGU server and returns the current image.
 */
public class PanguConnection extends AsyncTask<Void, Void, ErrorHandler> {
    private final WeakReference<ImageView> imageViewReference;
    private Context context;
    private final String dstName = "172.16.178.129";
    private int dstPort;
    private ClientConnection client;
    private Bitmap bitmap;
    private ViewPoint viewPoint;

    /**
     * @param context the current state of application.
     * @desc Constructor for the Socket Connection class
     */
    public PanguConnection(Context context, ImageView imageView, int dstPort, ViewPoint viewPoint) {
        this.context = context;
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.dstPort = dstPort;
        this.viewPoint = viewPoint;
    }

    /**
     * @desc: Runs on the UI thread before doInBackground().
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
                //Connecting to the PANGU Server.
                Logger.i("Connecting to address: " + dstName + " & port: " + dstPort);
                InetAddress dstAddress = InetAddress.getByName(dstName);
                Socket sock = new Socket(dstAddress, dstPort);
                client = new ClientConnection(sock);
                Logger.i("Connection complete.");

                //Get new image data from the server.
                Logger.i("Getting Image from Server.");
                //Vector3D initialVec = new Vector3D(0, 0, 100000.0);
                //double yawAngle = 0.0, pitchAngle = -90.0, rollAngle = 0.0;
                //byte[] image_data = client.getImageByDegrees(initialVec, yawAngle, pitchAngle, rollAngle);
                byte[] image_data = client.getImageByDegrees(viewPoint.getVector3D(), viewPoint.getYawAngle(), viewPoint.getPitchAngle(), viewPoint.getRollAngle());
                Logger.v("Yaw: "+viewPoint.getYawAngle());
                Logger.v("Pitch: "+viewPoint.getPitchAngle());
                Logger.v("Roll: "+viewPoint.getRollAngle());
                //Convert .ppm byte array to bitmap
                bitmap = PanguImage.Image(image_data);
                if(bitmap == null) return ErrorHandler.IO_ERROR;

                //Close connection
                client.stop();
                return ErrorHandler.OK;
            } catch (IOException e) {
                Logger.e("IO Exception has occurred when connecting to PANGU server.");
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
        if (result == ErrorHandler.OK) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        } else if (result == ErrorHandler.NO_INTERNET_CONNECTION || result == ErrorHandler.IO_ERROR) {
            Toast.makeText(context, result.getLongMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
