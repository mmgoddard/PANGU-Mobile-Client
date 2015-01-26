package com.pangu.mobile.client.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Mark on 26/01/15.
 */
public final class NetworkHelper {
    /**
     * @param context state of the application used to access the connectivity service.
     * @return boolean value - if the phone/app is connected to the Internet.
     * @desc Checks if the phone/app is connected to the Internet.
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }
}
