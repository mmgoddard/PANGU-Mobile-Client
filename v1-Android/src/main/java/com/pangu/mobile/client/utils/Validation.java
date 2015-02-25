package com.pangu.mobile.client.utils;

import android.widget.EditText;

/**
 * Created by Mark on 03/02/15.
 */
public class Validation {
    private static Validation validation = null;
    protected Validation() {}

    public static Validation getInstance() {
        if(validation == null) {
            validation = new Validation();
        }
        return validation;
    }

    /**
     * Checks if a string is integer parsable.
     * @param input
     * @return
     */
    public boolean isParsable(String input) {
        boolean parsable = true;
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            parsable = false;
        }
        return parsable;
    }

    /**
     * Check if a EditText is empty.
     * @param input
     * @return
     */
    public boolean isEmpty(EditText input) {
        return input.getText().toString().trim().equals("");
    }

    /**
     * Check if string is alpha-numeric
     * @param input
     * @return
     */
    public boolean isAlphaNumeric(String input){
        String pattern= "^[a-zA-Z0-9 ]*$";
        if(input.matches(pattern)){
            return true;
        }
        return false;
    }
}
