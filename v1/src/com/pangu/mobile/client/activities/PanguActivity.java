package com.pangu.mobile.client.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.pangu.mobile.client.R;
import com.pangu.mobile.client.background_tasks.PanguConnection;
import com.pangu.mobile.client.models.ViewPoint;
import uk.ac.dundee.spacetech.pangu.ClientLibrary.Vector3D;

/**
 * Created by Mark on 20/01/15.
 */
public class PanguActivity extends Activity {
    private int dstPort = 10363;
    private double x_coordinate = 0.0;
    private double y_coordinate = 0.0;
    private double z_coordinate = 100000.0;
    private Vector3D vector3D = new Vector3D(x_coordinate, y_coordinate, z_coordinate);
    private ViewPoint viewPoint = new ViewPoint(vector3D, 0.0, -90.0, 0.0);

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

        x_coordinateEditText.setText(String.valueOf(x_coordinate), TextView.BufferType.EDITABLE);
        y_coordinateEditText.setText(String.valueOf(y_coordinate), TextView.BufferType.EDITABLE);
        z_coordinateEditText.setText(String.valueOf(z_coordinate), TextView.BufferType.EDITABLE);

        PanguConnection panguConnection = new PanguConnection(this, imgView, dstPort, viewPoint);
        panguConnection.execute();

    }

    //Called when the user touches the button */
    public void coordinateListener(View view) {
        ImageView imgView = (ImageView) findViewById(R.id.pangu_image);

        EditText x_coordinateEditText = (EditText) findViewById(R.id.x_coordinate);
        EditText y_coordinateEditText = (EditText) findViewById(R.id.y_coordinate);
        EditText z_coordinateEditText = (EditText) findViewById(R.id.z_coordinate);

        String xStr = x_coordinateEditText.getText().toString();
        String yStr = y_coordinateEditText.getText().toString();
        String zStr = z_coordinateEditText.getText().toString();
        String errorText = "A number must be entered";

        if(xStr.isEmpty()) x_coordinateEditText.setError(errorText);
        if(yStr.isEmpty()) y_coordinateEditText.setError(errorText);
        if(zStr.isEmpty()) z_coordinateEditText.setError(errorText);
        if(!xStr.isEmpty() && !yStr.isEmpty() && !zStr.isEmpty()) {
            double x = Double.parseDouble(x_coordinateEditText.getText().toString());
            double y = Double.parseDouble(y_coordinateEditText.getText().toString());
            double z = Double.parseDouble(z_coordinateEditText.getText().toString());

            vector3D = new Vector3D(x, y, z);
            viewPoint.setVector3D(vector3D);

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
