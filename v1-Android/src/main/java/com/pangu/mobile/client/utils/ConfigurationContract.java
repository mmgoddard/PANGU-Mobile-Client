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
        private static final String PK = " PRIMARY KEY";

        //Pangu Table
        public static final String PANGU_TABLE = "configurations";

        //Pangu Columns
        public static final String PANGU_NAME = "name";
        public static final String PANGU_IP_ADDRESS = "ip_address";
        public static final String PANGU_PORT_NUM = "port_num";

        //PANGU Table - Create Statement
        public static final String CREATE_TABLE_PANGU = "CREATE TABLE IF NOT EXISTS "
                + PANGU_TABLE+ "(" + _ID + TEXT_TYPE + PK + COMMA_SEP
                + PANGU_NAME + TEXT_TYPE + COMMA_SEP
                + PANGU_IP_ADDRESS + TEXT_TYPE + COMMA_SEP
                + PANGU_PORT_NUM + TEXT_TYPE + ")";

        //PANGU TABLE - Delete Statement
        public static final String DELETE_TABLE_PANGU = "DROP TABLE IF EXISTS " + PANGU_TABLE;

        public static final String[] allPanguColumns = new String[] { _ID, PANGU_NAME, PANGU_IP_ADDRESS, PANGU_PORT_NUM };
    }
}
