package com.pangu.mobile.client.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import com.pangu.mobile.client.domain.ConfigurationModel;
import com.pangu.mobile.client.domain.ViewPoint;
import uk.ac.dundee.spacetech.pangu.ClientLibrary.Vector3D;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 30/01/15.
 */
public class DatabaseOperations {
    private SQLiteDatabase db;

    public DatabaseOperations(DatabaseHelper dbHelper) {
        db = dbHelper.getWritableDatabase();
    }

    public ErrorHandler insertConfiguration(ConfigurationModel cm) {
        try {
            ContentValues values = new ContentValues();
            values.put(ConfigurationContract.PanguEntry.PANGU_NAME, cm.getName());
            values.put(ConfigurationContract.PanguEntry.PANGU_IP_ADDRESS, cm.getIpAddress());
            values.put(ConfigurationContract.PanguEntry.PANGU_PORT_NUM, cm.getPortNum());
            values.put(ConfigurationContract.PanguEntry.PANGU_X_COORDINATE, String.valueOf(cm.getViewPoint().getVector3D().i));
            values.put(ConfigurationContract.PanguEntry.PANGU_Y_COORDINATE, String.valueOf(cm.getViewPoint().getVector3D().j));
            values.put(ConfigurationContract.PanguEntry.PANGU_Z_COORDINATE, String.valueOf(cm.getViewPoint().getVector3D().k));
            values.put(ConfigurationContract.PanguEntry.PANGU_YAW_ANGLE, String.valueOf(cm.getViewPoint().getYawAngle()));
            values.put(ConfigurationContract.PanguEntry.PANGU_PITCH_ANGLE, String.valueOf(cm.getViewPoint().getPitchAngle()));
            values.put(ConfigurationContract.PanguEntry.PANGU_ROLL_ANGLE, String.valueOf(cm.getViewPoint().getRollAngle()));
            values.put(ConfigurationContract.PanguEntry.PANGU_STEP, String.valueOf(cm.getViewPoint().getStep()));
            values.put(ConfigurationContract.PanguEntry.PANGU_SAVED, cm.getSaved());

            db.insert(ConfigurationContract.PanguEntry.PANGU_TABLE, null, values);
            return ErrorHandler.SQL_EXECUTION_SUCCESS;
        } catch (SQLiteException e) {
            return ErrorHandler.SQL_EXECUTION_ERROR;
        }
    }

    public ErrorHandler deleteConfiguration(int id) {
        try {
            String selection = ConfigurationContract.PanguEntry._ID + " = ?";
            String[] selectionArgs = {String.valueOf(id)};
            db.delete(ConfigurationContract.PanguEntry.PANGU_TABLE, selection, selectionArgs);
            return ErrorHandler.SQL_EXECUTION_SUCCESS;
        } catch (SQLiteException e) {
            return ErrorHandler.SQL_EXECUTION_ERROR;
        }
    }

    public ErrorHandler updateConfiguration(ConfigurationModel cm) {
        try {
            ContentValues values = new ContentValues();
            values.put(ConfigurationContract.PanguEntry.PANGU_NAME, cm.getName());
            values.put(ConfigurationContract.PanguEntry.PANGU_IP_ADDRESS, cm.getIpAddress());
            values.put(ConfigurationContract.PanguEntry.PANGU_PORT_NUM, cm.getPortNum());

            String selection = ConfigurationContract.PanguEntry._ID + " = ?";
            String[] selectionArgs = {String.valueOf(cm.getId())};

            db.update(ConfigurationContract.PanguEntry.PANGU_TABLE, values, selection, selectionArgs);
            return ErrorHandler.SQL_EXECUTION_SUCCESS;
        } catch (SQLiteException e) {
            return ErrorHandler.SQL_EXECUTION_ERROR;
        }
    }

    public ErrorHandler updateAll(ConfigurationModel cm) {
        try {
            ContentValues values = new ContentValues();
            values.put(ConfigurationContract.PanguEntry.PANGU_NAME, cm.getName());
            values.put(ConfigurationContract.PanguEntry.PANGU_IP_ADDRESS, cm.getIpAddress());
            values.put(ConfigurationContract.PanguEntry.PANGU_PORT_NUM, cm.getPortNum());
            values.put(ConfigurationContract.PanguEntry.PANGU_X_COORDINATE, String.valueOf(cm.getViewPoint().getVector3D().i));
            values.put(ConfigurationContract.PanguEntry.PANGU_Y_COORDINATE, String.valueOf(cm.getViewPoint().getVector3D().j));
            values.put(ConfigurationContract.PanguEntry.PANGU_Z_COORDINATE, String.valueOf(cm.getViewPoint().getVector3D().k));
            values.put(ConfigurationContract.PanguEntry.PANGU_YAW_ANGLE, String.valueOf(cm.getViewPoint().getYawAngle()));
            values.put(ConfigurationContract.PanguEntry.PANGU_PITCH_ANGLE, String.valueOf(cm.getViewPoint().getPitchAngle()));
            values.put(ConfigurationContract.PanguEntry.PANGU_ROLL_ANGLE, String.valueOf(cm.getViewPoint().getRollAngle()));
            values.put(ConfigurationContract.PanguEntry.PANGU_STEP, String.valueOf(cm.getViewPoint().getStep()));
            values.put(ConfigurationContract.PanguEntry.PANGU_SAVED, cm.getSaved());

            String selection = ConfigurationContract.PanguEntry._ID + " = ?";
            String[] selectionArgs = {String.valueOf(cm.getId())};

            db.update(ConfigurationContract.PanguEntry.PANGU_TABLE, values, selection, selectionArgs);
            return ErrorHandler.SQL_EXECUTION_SUCCESS;
        } catch (SQLiteException e) {
            return ErrorHandler.SQL_EXECUTION_ERROR;
        }
    }

    public List<ConfigurationModel> readConfiguration() {
        Cursor c = db.query(ConfigurationContract.PanguEntry.PANGU_TABLE, null, null, null, null, null, null);
        List<ConfigurationModel> list = new ArrayList<ConfigurationModel>();
        ConfigurationModel cm;
        ViewPoint v;
        Vector3D vec3;
        int count = 0;
        while (c.moveToNext()) {
            vec3 = new Vector3D(Double.parseDouble(c.getString(4)), Double.parseDouble(c.getString(5)), Double.parseDouble(c.getString(6)));
            v = new ViewPoint(vec3, Double.parseDouble(c.getString(7)), Double.parseDouble(c.getString(8)), Double.parseDouble(c.getString(9)), Double.parseDouble(c.getString(10)));
            cm = new ConfigurationModel(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), v, c.getString(11));
            list.add(count, cm);
            count++;
        }
        c.close();
        return list;
    }
}