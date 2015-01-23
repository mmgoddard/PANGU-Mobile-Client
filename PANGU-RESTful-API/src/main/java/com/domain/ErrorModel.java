package com.domain;

/**
 * Created by Mark on 23/01/15.
 */
public class ErrorModel {
    private final String errorCode;
    private final String errorDesc;

    public ErrorModel(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }
}
