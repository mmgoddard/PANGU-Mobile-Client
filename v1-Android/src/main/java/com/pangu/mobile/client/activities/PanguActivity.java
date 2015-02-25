package com.pangu.mobile.client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.pangu.mobile.client.R;
import com.pangu.mobile.client.background_tasks.DataCollectionTask;
import com.pangu.mobile.client.background_tasks.PanguConnection;
import com.pangu.mobile.client.models.ConfigurationModel;
import com.pangu.mobile.client.models.ViewPoint;
import uk.ac.dundee.spacetech.pangu.ClientLibrary.Vector3D;

/**
 * Created by Mark on 20/01/15.
 */
public class PanguActivity extends BaseActivity implements View.OnClickListener {
    private double x_coordinate = 0.0, y_coordinate = 0.0, z_coordinate = 100000.0;
    private double range = 0.0, yaw = 0.0, pitch = -90.0, roll = 0.0;
    private Vector3D vector3D = new Vector3D(x_coordinate, y_coordinate, z_coordinate);
    private ViewPoint viewPoint = new ViewPoint(vector3D, yaw, pitch, roll);
    private Button leftControl, upControl, rightControl, downControl;
    private PanguConnection panguConnection;
    private ImageView imgView;
    private LinearLayout headerProgress;
    private Bundle extras;
    private ConfigurationModel cm;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResID());

        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if (extras != null) {
                cm = extras.getParcelable("Configuration");
            }
        } else {
            cm = extras.getParcelable("Configuration");
        }

        headerProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);

        imgView = (ImageView) findViewById(R.id.pangu_image);
        getImage(vector3D);

        leftControl = (Button) findViewById(R.id.left_button);
        leftControl.setOnClickListener(this);

        upControl = (Button) findViewById(R.id.up_button);
        upControl.setOnClickListener(this);

        rightControl = (Button) findViewById(R.id.right_button);
        rightControl.setOnClickListener(this);

        downControl = (Button) findViewById(R.id.down_button);
        downControl.setOnClickListener(this);
    }

    @Override
    protected int getResID() {
        return R.layout.pangu;
    }

    @Override
    public void onClick(View v) {
        int step = 1000;
        switch (v.getId()) {
            case R.id.left_button:
                x_coordinate -= step;
            case R.id.up_button:
                y_coordinate += step;
            case R.id.right_button:
                x_coordinate += step;
            case R.id.down_button:
                y_coordinate -= step;
        }
        vector3D = new Vector3D(x_coordinate, y_coordinate, z_coordinate);
        getImage(vector3D);
    }

    public void getImage(Vector3D vector3D) {
        viewPoint = new ViewPoint(vector3D, yaw, pitch, roll);
        panguConnection = new PanguConnection(this, imgView, cm.getIpAddress(), Integer.parseInt(cm.getPortNum()), viewPoint, headerProgress);
        panguConnection.execute();
    }

    /**
     * Called when the action bar is created.
     *
     * @param menu
     * @return onCreateOptionsMenu()
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pangu_activity_action_bar_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Called when action bar item is selected.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_info:
                Intent intent = new Intent(this, InformationActivity.class);
                intent.putExtra("modelName", cm.getName());
                this.startActivity(intent);
                return true;
            case R.id.action_reload:
                vector3D = new Vector3D(x_coordinate, y_coordinate, z_coordinate);
                getImage(vector3D);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
