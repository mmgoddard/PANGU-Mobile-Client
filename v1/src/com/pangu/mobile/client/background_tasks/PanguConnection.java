package com.pangu.mobile.client.background_tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;
import com.pangu.mobile.client.models.ViewPoint;
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
public class PanguConnection extends AsyncTask<Void, Void, Boolean> {
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
    protected Boolean doInBackground(Void... arg0) {
        if (checkInternetConnection(context)) {
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

                //Convert .ppm byte array to bitmap
                bitmap = PanguImage.Image(image_data);
                if(bitmap == null) return false;

                //Close connection
                client.stop();
                return true;
            } catch (IOException e) {
                Logger.e("IO Exception has occurred when connecting to PANGU server.");
                e.printStackTrace();
                return true;
            }
        } else {
            //There is no Internet Connection available.
            return false;
        }
    }

    /**
     * @param result the result of connection to the PANGU Server.
     * @desc Runs on the UI thread after doInBackground().
     */
    @Override
    protected void onPostExecute(Boolean result) {
        if (result == true) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        } else {
            Toast.makeText(context, "You are currently not connected to the Internet.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @param context state of the application used to access the connectivity service.
     * @return boolean value - if the phone/app is connected to the Internet.
     * @desc Checks if the phone/app is connected to the Internet.
     */
    private boolean checkInternetConnection(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
