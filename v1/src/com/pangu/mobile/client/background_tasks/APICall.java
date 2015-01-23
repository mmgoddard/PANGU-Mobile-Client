package com.pangu.mobile.client.background_tasks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by Mark on 23/01/15.
 */
public class APICall extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private final String url = "http://192.168.56.1:8081/hello";

    /**
     * @param context the current state of application.
     * @desc Constructor for the Socket Connection class
     */
    public APICall(Context context) {
        this.context = context;
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
     * @desc Connects to RESTful API and pulls down required information from it.
     */
    @Override
    protected Boolean doInBackground(Void... arg0) {
        if (checkInternetConnection(context)) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                HttpResponse response = httpclient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();
                Logger.i("Connection complete.");
                return true;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * @param result the result of connection to the RESTful API.
     * @desc Runs on the UI thread after doInBackground().
     */
    @Override
    protected void onPostExecute(Boolean result) {
        if (result == true) {

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

