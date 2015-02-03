package com.pangu.mobile.client.utils;

/**
 * Created by Mark on 26/01/15.
 */
public enum ErrorHandler {
    OK("200", "OK", "Successful Request"),
    NO_INTERNET_CONNECTION("503", "Service Unavailable", "There is no active Internet Connection"),
    IO_ERROR("501", "Internal Server Error", "I/O Error Occurred"),

    //Database Messages
    SQL_EXECUTION_ERROR("800", "SQL Execution Error", "An Error Occurred"),
    SQL_EXECUTION_SUCCESS("801", "SQL Execution Success", "SQL Execution Success");

    private final String code;
    private final String shortMessage;
    private final String longMessage;

    private ErrorHandler(String code, String shortMessage, String longMessage) {
        this.code = code;
        this.shortMessage = shortMessage;
        this.longMessage = longMessage;
    }

    public String code() {
        return code;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public String getLongMessage(){
        return longMessage;
    }
}