package com.pangu.mobile.client.background_tasks;

/**
 * Created by Mark on 23/02/15.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.google.gson.Gson;
import com.pangu.mobile.client.interfaces.AsyncResponse;
import com.pangu.mobile.client.models.InformationModel;
import com.pangu.mobile.client.utils.ErrorHandler;
import com.pangu.mobile.client.utils.LoggerHandler;
import com.pangu.mobile.client.utils.NetworkHelper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Mark Goddard
 * @date 08/10/2014
 * @desc Connects to a PANGU server and returns the current image.
 */
public class DataCollectionTask extends AsyncTask<Void, Void, ErrorHandler> {
    private Context context;
    private InformationModel data;
    private String baseUrl = "http://192.168.0.7:11000/api/models/name/";
    public AsyncResponse asyncResponse = null;
    private String modelName;

    /**
     * @param context the current state of application.
     * @desc Constructor for the Socket Connection class
     */
    public DataCollectionTask(Context context, String modelName) {
        this.context = context;
        this.modelName = modelName;
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
     * @desc Connects to a remote database and pulls down data related to the model
     */
    @Override
    protected ErrorHandler doInBackground(Void... arg0) {
        String formattedUrl = null;
        try {
            formattedUrl = getFormattedURL(baseUrl, modelName);
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (NetworkHelper.isOnline(context)) {
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            Reader reader = null;
            HttpGet httpGet = new HttpGet(formattedUrl);
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json;charset=utf-8");
            try {
                HttpResponse response = defaultHttpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                reader = new InputStreamReader(entity.getContent());
                data = new Gson().fromJson(reader, InformationModel.class);
                return ErrorHandler.OK;
            } catch (IOException e) {
                LoggerHandler.e("IO Exception has occurred when connecting to PANGU server.");
                e.printStackTrace();
                return ErrorHandler.IO_ERROR;
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return ErrorHandler.NO_INTERNET_CONNECTION;
    }

    /**
     * @param result the result of connection to the remote database.
     * @desc Runs on the UI thread after doInBackground().
     */
    @Override
    protected void onPostExecute(ErrorHandler result) {
        if (result == ErrorHandler.OK) {
            asyncResponse.processData(data);
        } else if (result == ErrorHandler.NO_INTERNET_CONNECTION || result == ErrorHandler.IO_ERROR) {
            Toast.makeText(context, result.getLongMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String getFormattedURL(String baseUrl, String modelName) throws UnsupportedEncodingException {
        String encodedModelName = modelName.replace(" ", "%20");
        return new StringBuilder().append(baseUrl).append(encodedModelName).toString();
    }
}