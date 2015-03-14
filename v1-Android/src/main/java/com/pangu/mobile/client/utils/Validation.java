package com.pangu.mobile.client.utils;

import android.widget.EditText;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
    public boolean isIntParsable(String input) {
        boolean parsable = true;
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            parsable = false;
        }
        return parsable;
    }

    /**
     * Checks if a string is double parsable.
     * @param input
     * @return
     */
    public boolean isDoubleParsable(String input) {
        boolean parsable = true;
        try {
            Double.parseDouble(input);
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

    /**
     * Format a double to 2-decimal places
     * @param value
     * @param places
     * @return
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
