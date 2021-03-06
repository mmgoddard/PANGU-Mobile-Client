package com.pangu.mobile.client.utils;

import android.util.Log;

/**
 * @author Mark Goddard
 * @date 08/10/2014
 * @desc Display log information based on a msg and where it was initiated from.
 */
public class LoggerHandler {

    //INFO
    public static void i(String msg) {
        final Throwable t = new Throwable();
        final StackTraceElement[] elements = t.getStackTrace();

        final String callerClassName = elements[1].getClassName();
        Log.i(callerClassName, "[INFO] " + msg);
    }

    //VERBOSE
    public static void v(String msg) {
        final Throwable t = new Throwable();
        final StackTraceElement[] elements = t.getStackTrace();

        final String callerClassName = elements[1].getClassName();
        final String callerMethodName = elements[1].getMethodName();
        Log.v(callerClassName, "[DEBUG: " + callerMethodName + "] " + msg);
    }

    //EXCEPTION
    public static void e(String msg) {
        final Throwable t = new Throwable();
        final StackTraceElement[] elements = t.getStackTrace();

        final String callerClassName = elements[1].getClassName();
        final String callerMethodName = elements[1].getMethodName();
        Log.e(callerClassName, "[EXCEPTION: " + callerMethodName + "] " + msg);
    }
}