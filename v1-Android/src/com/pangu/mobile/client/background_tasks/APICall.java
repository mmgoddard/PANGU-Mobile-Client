package com.pangu.mobile.client.background_tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.pangu.mobile.client.utils.ErrorHandler;
import com.pangu.mobile.client.utils.NetworkHelper;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.IOException;

/**
 * Created by Mark on 23/01/15.
 */
public class APICall extends AsyncTask<Void, Void, ErrorHandler> {
    private Context context;
    private final String url = "http://10.0.2.2:11000/api";

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
     * @desc Connects to a RESTful API and pulls down required information from it.
     */
    @Override
    protected ErrorHandler doInBackground(Void... arg0) {
        if (NetworkHelper.isOnline(context)) {
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json;charset=utf-8");
            ResponseHandler responseHandler = new BasicResponseHandler();
            try {
                defaultHttpClient.execute(httpGet, responseHandler);
                return ErrorHandler.OK;
            } catch (IOException e) {
                e.printStackTrace();
                return ErrorHandler.IO_ERROR;
            }
        }
        return ErrorHandler.NO_INTERNET_CONNECTION;
    }

    /**
     * @param result the result of connection to the RESTful API.
     * @desc Runs on the UI thread after doInBackground().
     */
    @Override
    protected void onPostExecute(ErrorHandler result) {
        if (result == ErrorHandler.IO_ERROR)
            Toast.makeText(context, result.getLongMessage(), Toast.LENGTH_LONG).show();
        else if (result == ErrorHandler.NO_INTERNET_CONNECTION)
            Toast.makeText(context, result.getLongMessage(), Toast.LENGTH_LONG).show();
    }
}

