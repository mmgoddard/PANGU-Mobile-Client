package com.pangu.mobile.client.utils;

import android.provider.BaseColumns;

/**
 * Created by Mark on 30/01/15.
 */
public class ConfigurationContract {
    public ConfigurationContract() {}

    public static abstract class PanguEntry implements BaseColumns {
        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        private static final String AUTO_INCREMENT = " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL";

        //Pangu Table
        public static final String PANGU_TABLE = "configurations";

        //Pangu Columns
        public static final String PANGU_NAME = "name";
        public static final String PANGU_IP_ADDRESS = "ip_address";
        public static final String PANGU_PORT_NUM = "port_num";
        public static final String PANGU_X_COORDINATE = "x_coordinate";
        public static final String PANGU_Y_COORDINATE = "y_coordinate";
        public static final String PANGU_Z_COORDINATE = "z_coordinate";
        public static final String PANGU_YAW_ANGLE = "yaw_angle";
        public static final String PANGU_PITCH_ANGLE = "pitch_angle";
        public static final String PANGU_ROLL_ANGLE = "roll_angle";
        public static final String PANGU_SAVED = "saved";

        //PANGU Table - Create Statement
        public static final String CREATE_TABLE_PANGU = "CREATE TABLE IF NOT EXISTS "
                + PANGU_TABLE+ "("
                + _ID + AUTO_INCREMENT + COMMA_SEP
                + PANGU_NAME + TEXT_TYPE + COMMA_SEP
                + PANGU_IP_ADDRESS + TEXT_TYPE + COMMA_SEP
                + PANGU_PORT_NUM + TEXT_TYPE + COMMA_SEP
                + PANGU_X_COORDINATE + TEXT_TYPE + COMMA_SEP
                + PANGU_Y_COORDINATE + TEXT_TYPE + COMMA_SEP
                + PANGU_Z_COORDINATE + TEXT_TYPE + COMMA_SEP
                + PANGU_YAW_ANGLE + TEXT_TYPE + COMMA_SEP
                + PANGU_PITCH_ANGLE + TEXT_TYPE + COMMA_SEP
                + PANGU_ROLL_ANGLE + TEXT_TYPE + COMMA_SEP
                + PANGU_SAVED + TEXT_TYPE +")";

        //PANGU TABLE - Delete Statement
        public static final String DELETE_TABLE_PANGU = "DROP TABLE IF EXISTS " + PANGU_TABLE;
    }
}
