package com.pangu.mobile.client.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.pangu.mobile.client.R;
import com.pangu.mobile.client.background_tasks.APICall;
import com.pangu.mobile.client.background_tasks.PanguConnection;
import com.pangu.mobile.client.models.ViewPoint;
import uk.ac.dundee.spacetech.pangu.ClientLibrary.Vector3D;

/**
 * Created by Mark on 20/01/15.
 */
public class PanguActivity extends Activity {
    private int dstPort = 10363;
    private double x_coordinate = 0.0, y_coordinate = 0.0, z_coordinate = 100000.0;
    private double range = 0.0, yaw = 0.0, pitch = -90.0, roll = 0.0;
    private Vector3D vector3D = new Vector3D(x_coordinate, y_coordinate, z_coordinate);
    private ViewPoint viewPoint = new ViewPoint(vector3D, yaw, pitch, roll);

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pangu);

        ImageView imgView = (ImageView) findViewById(R.id.pangu_image);
        EditText x_coordinateEditText = (EditText) findViewById(R.id.x_coordinate);
        EditText y_coordinateEditText = (EditText) findViewById(R.id.y_coordinate);
        EditText z_coordinateEditText = (EditText) findViewById(R.id.z_coordinate);
        EditText rangeEditText = (EditText) findViewById(R.id.range);
        EditText azimuthEditText = (EditText) findViewById(R.id.azimuth);
        EditText elevationEditText = (EditText) findViewById(R.id.elevation);
        EditText rollEditText = (EditText) findViewById(R.id.roll);

        x_coordinateEditText.setText(String.valueOf(x_coordinate), TextView.BufferType.EDITABLE);
        y_coordinateEditText.setText(String.valueOf(y_coordinate), TextView.BufferType.EDITABLE);
        z_coordinateEditText.setText(String.valueOf(z_coordinate), TextView.BufferType.EDITABLE);
        rangeEditText.setText(String.valueOf(range), TextView.BufferType.EDITABLE);
        azimuthEditText.setText(String.valueOf(yaw), TextView.BufferType.EDITABLE);
        elevationEditText.setText(String.valueOf(pitch), TextView.BufferType.EDITABLE);
        rollEditText.setText(String.valueOf(roll), TextView.BufferType.EDITABLE);

        PanguConnection panguConnection = new PanguConnection(this, imgView, dstPort, viewPoint);
        panguConnection.execute();

        APICall apiCall = new APICall(this);
        apiCall.execute();
    }

    //Called when the user touches the submit button
    public void coordinateListener(View view) {
        ImageView imgView = (ImageView) findViewById(R.id.pangu_image);

        EditText x_coordinateEditText = (EditText) findViewById(R.id.x_coordinate);
        EditText y_coordinateEditText = (EditText) findViewById(R.id.y_coordinate);
        EditText z_coordinateEditText = (EditText) findViewById(R.id.z_coordinate);
        EditText rangeEditText = (EditText) findViewById(R.id.range);
        EditText azimuthEditText = (EditText) findViewById(R.id.azimuth);
        EditText elevationEditText = (EditText) findViewById(R.id.elevation);
        EditText rollEditText = (EditText) findViewById(R.id.roll);

        String xStr = x_coordinateEditText.getText().toString();
        String yStr = y_coordinateEditText.getText().toString();
        String zStr = z_coordinateEditText.getText().toString();
        String rangeStr = rangeEditText.getText().toString();
        String azimuthStr = azimuthEditText.getText().toString();
        String elevationStr = elevationEditText.getText().toString();
        String rollStr = rollEditText.getText().toString();
        String errorText = "A number must be entered";

        if(xStr.isEmpty()) x_coordinateEditText.setError(errorText);
        if(yStr.isEmpty()) y_coordinateEditText.setError(errorText);
        if(rangeStr.isEmpty()) rangeEditText.setError(errorText);
        if(azimuthStr.isEmpty()) azimuthEditText.setError(errorText);
        if(elevationStr.isEmpty()) elevationEditText.setError(errorText);
        if(rollStr.isEmpty()) rollEditText.setError(errorText);
        if(!xStr.isEmpty() && !yStr.isEmpty() && !zStr.isEmpty()) {
            double x = Double.parseDouble(x_coordinateEditText.getText().toString());
            double y = Double.parseDouble(y_coordinateEditText.getText().toString());
            double z = Double.parseDouble(z_coordinateEditText.getText().toString());
            double range = Double.parseDouble(rangeEditText.getText().toString());
            double azimuth = Double.parseDouble(azimuthEditText.getText().toString());
            double elevation = Double.parseDouble(elevationEditText.getText().toString());
            double roll = Double.parseDouble(rollEditText.getText().toString());

            vector3D = new Vector3D(x, y, z);
            viewPoint.setVector3D(vector3D);
            viewPoint.setYawAngle(azimuth);
            viewPoint.setPitchAngle(elevation);
            viewPoint.setRollAngle(roll);

            PanguConnection panguConnection = new PanguConnection(this, imgView, dstPort, viewPoint);
            panguConnection.execute();
        }
    }

    /**
     * Called when the activity is paused.
     */
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
