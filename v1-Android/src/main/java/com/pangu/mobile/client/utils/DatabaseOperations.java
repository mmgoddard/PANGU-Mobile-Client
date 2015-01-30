package com.pangu.mobile.client.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.pangu.mobile.client.models.ConfigurationModel;

import java.util.*;

/**
 * Created by Mark on 30/01/15.
 */
public class DatabaseOperations {
    private SQLiteDatabase db;

    public DatabaseOperations(DatabaseHelper dbHelper) {
        db = dbHelper.getWritableDatabase();
    }

    public void insertConfiguration(ConfigurationModel cm) {
        ContentValues values = new ContentValues();
        values.put(ConfigurationContract.PanguEntry._ID, cm.getId());
        values.put(ConfigurationContract.PanguEntry.PANGU_NAME, cm.getName());
        values.put(ConfigurationContract.PanguEntry.PANGU_IP_ADDRESS, cm.getIpAddress());
        values.put(ConfigurationContract.PanguEntry.PANGU_PORT_NUM, cm.getPortNum());
        db.insert(ConfigurationContract.PanguEntry.PANGU_TABLE, null, values);
    }

    public void deleteConfiguration(int id) {
        String selection = ConfigurationContract.PanguEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };
        db.delete(ConfigurationContract.PanguEntry.PANGU_TABLE, selection, selectionArgs);
    }

    public void updateConfiguration(ConfigurationModel cm) {
        ContentValues values = new ContentValues();
        values.put(ConfigurationContract.PanguEntry._ID, cm.getId());
        values.put(ConfigurationContract.PanguEntry.PANGU_NAME, cm.getName());
        values.put(ConfigurationContract.PanguEntry.PANGU_IP_ADDRESS, cm.getIpAddress());
        values.put(ConfigurationContract.PanguEntry.PANGU_PORT_NUM, cm.getPortNum());

        String selection = ConfigurationContract.PanguEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(cm.getId()) };

         db.update(ConfigurationContract.PanguEntry.PANGU_TABLE,
                values,
                selection,
                selectionArgs);
    }

    public List<ConfigurationModel> readConfiguration() {
        Cursor c = db.query(ConfigurationContract.PanguEntry.PANGU_TABLE, null, null, null,null, null, null);
        List<ConfigurationModel> list = new ArrayList<ConfigurationModel>();
        ConfigurationModel cm;
        int count = 0;
        while (c.moveToNext()) {
            cm = new ConfigurationModel(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2), c.getString(3));
            list.add(count, cm);
            LoggerHandler.i(list.get(0).getName());
            count++;
        }
        return list;
    }
}
