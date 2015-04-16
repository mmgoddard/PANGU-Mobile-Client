package com.pangu.mobile.client.activities;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.view.VelocityTrackerCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.*;

import com.pangu.mobile.client.R;
import com.pangu.mobile.client.background_tasks.PanguConnection;
import com.pangu.mobile.client.base_classes.BaseActivity;
import com.pangu.mobile.client.dialogs.EditViewDialog;
import com.pangu.mobile.client.domain.ConfigurationModel;
import com.pangu.mobile.client.domain.ViewPointModel;
import com.pangu.mobile.client.utils.DatabaseHelper;
import com.pangu.mobile.client.utils.DatabaseOperations;
import com.pangu.mobile.client.utils.LoggerHandler;
import com.pangu.mobile.client.utils.Validation;

import uk.ac.dundee.spacetech.pangu.ClientLibrary.Vector3D;

/**
 * Created by Mark on 20/01/15.
 */
public class PanguActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {
    private Vector3D vector3D = new Vector3D(0, 0, 0);
    private ImageView imgView;
    private LinearLayout headerProgress;
    private Bundle extras;
    private ConfigurationModel cm;
    private ViewPointModel viewPoint = new ViewPointModel(vector3D, 0, 0, 0, 0);
    private boolean value = false;
    private TextView yawAngle_TextView, pitchAngle_TextView, rollAngle_TextView, xCoordinate_TextView, yCoordinate_TextView, zCoordinate_TextView;
    private double yawAngle = 0.0, pitchAngle = 0.0, rollAngle = 0.0;
    private int step = 0;
    private ScrollView scrollView;
    private VelocityTracker mVelocityTracker = null;
    private static final int NONE = 0;
    private static final int TOUCH = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private float oldDist = 1f;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pangu);
        setActionBarTitle("View Model");

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
        if (cm.getSaved().equals("true")) {
            viewPoint = cm.getViewPoint();
            value = true;
        }
        getImage(viewPoint, true);

        Button leftControl = (Button) findViewById(R.id.left_button);
        leftControl.setOnClickListener(this);

        Button upControl = (Button) findViewById(R.id.up_button);
        upControl.setOnClickListener(this);

        Button rightControl = (Button) findViewById(R.id.right_button);
        rightControl.setOnClickListener(this);

        leftControl.setWidth(getScreenWidth() / 3);
        rightControl.setWidth(getScreenWidth() / 3);
        upControl.setWidth(getScreenWidth() / 3);

        Button downControl = (Button) findViewById(R.id.down_button);
        downControl.setOnClickListener(this);
        downControl.setWidth(getScreenWidth() / 3);

        Button zoomIn = (Button) findViewById(R.id.zoom_in);
        zoomIn.setOnClickListener(this);
        zoomIn.setWidth(getScreenWidth() / 3);

        Button zoomOut = (Button) findViewById(R.id.zoom_out);
        zoomOut.setOnClickListener(this);
        zoomOut.setWidth(getScreenWidth() / 3);

        yawAngle_TextView = (TextView) this.findViewById(R.id.yawAngle_TextView);
        yawAngle_TextView.setText(String.valueOf("Yaw Angle: " + Validation.round(viewPoint.getYawAngle(), 2)));
        yawAngle_TextView.setWidth(getScreenWidth() / 2);

        pitchAngle_TextView = (TextView) this.findViewById(R.id.pitchAngle_TextView);
        pitchAngle_TextView.setText(String.valueOf("Pitch Angle: " + Validation.round(viewPoint.getPitchAngle(), 2)));
        pitchAngle_TextView.setWidth(getScreenWidth() / 2);

        rollAngle_TextView = (TextView) this.findViewById(R.id.rollAngle_TextView);
        rollAngle_TextView.setText(String.valueOf("Roll Angle: " + Validation.round(viewPoint.getRollAngle(), 2)));
        rollAngle_TextView.setWidth(getScreenWidth() / 2);

        xCoordinate_TextView = (TextView) this.findViewById(R.id.xCoordinate_TextView);
        xCoordinate_TextView.setText(String.valueOf("x-Coordinate: " + Validation.round(viewPoint.getVector3D().i,2)));
        xCoordinate_TextView.setWidth(getScreenWidth() / 2);

        yCoordinate_TextView = (TextView) this.findViewById(R.id.yCoordinate_TextView);
        yCoordinate_TextView.setText(String.valueOf("y-Coordinate: " + Validation.round(viewPoint.getVector3D().j, 2)));
        yCoordinate_TextView.setWidth(getScreenWidth() / 2);

        zCoordinate_TextView = (TextView) this.findViewById(R.id.zCoordinate_TextView);
        zCoordinate_TextView.setText(String.valueOf("z-Coordinate: " + Validation.round(viewPoint.getVector3D().k, 2)));
        zCoordinate_TextView.setWidth(getScreenWidth() / 2);

        scrollView = (ScrollView) findViewById(R.id.pangu_activity_scrollView);
        scrollView.requestDisallowInterceptTouchEvent(true);
        imgView.setOnTouchListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pangu_activity_action_bar_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (cm.getSaved().equals("true")) {
            viewPoint = cm.getViewPoint();
            value = true;
            getImage(viewPoint, true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        cm.setViewPoint(viewPoint);
        cm.setSaved("true");
        DatabaseOperations dOp = new DatabaseOperations(DatabaseHelper.getInstance(this));
        dOp.updateAll(cm);
    }

    public void onClick(View v) {
        step = viewPoint.getStep();
        switch (v.getId()) {
            case R.id.left_button:
                viewPoint.adjustOrigin(new Vector3D(-step, 0, 0));
                break;
            case R.id.right_button:
                viewPoint.adjustOrigin(new Vector3D(step, 0, 0));
                break;
            case R.id.up_button:
                viewPoint.adjustOrigin(new Vector3D(0, step, 0));
                break;
            case R.id.down_button:
                viewPoint.adjustOrigin(new Vector3D(0, -step, 0));
                break;
            case R.id.zoom_in:
                viewPoint.adjustOrigin(new Vector3D(0, 0, -step));
                break;
            case R.id.zoom_out:
                viewPoint.adjustOrigin(new Vector3D(0, 0, step));
                break;
            default:
                break;
        }
        getImage(viewPoint, value);
        xCoordinate_TextView.setText(String.valueOf("x-Coordinate: " + Validation.round(viewPoint.getVector3D().i,2)));
        yCoordinate_TextView.setText(String.valueOf("y-Coordinate: " + Validation.round(viewPoint.getVector3D().j,2)));
        zCoordinate_TextView.setText(String.valueOf("z-Coordinate: " + Validation.round(viewPoint.getVector3D().k,2)));
    }

    public void setView() {
        final EditViewDialog dialog = new EditViewDialog() {
            @Override
            public void submit(ViewPointModel v) {
                Toast.makeText(getApplicationContext(), "Updating Model", Toast.LENGTH_LONG).show();
                viewPoint = v;
                yawAngle = viewPoint.getYawAngle();
                pitchAngle = viewPoint.getPitchAngle();
                step = viewPoint.getStep();
                rollAngle_TextView.setText(String.valueOf("Roll Angle: " + Validation.round(rollAngle, 2)));
                yawAngle_TextView.setText(String.valueOf("Yaw Angle: " + Validation.round(yawAngle, 2)));
                pitchAngle_TextView.setText(String.valueOf("Pitch Angle: " + Validation.round(pitchAngle, 2)));
                xCoordinate_TextView.setText(String.valueOf("x-Coordinate: " + Validation.round(viewPoint.getVector3D().i, 2)));
                yCoordinate_TextView.setText(String.valueOf("y-Coordinate: " + Validation.round(viewPoint.getVector3D().j,2)));
                zCoordinate_TextView.setText(String.valueOf("z-Coordinate: " + Validation.round(viewPoint.getVector3D().k,2)));

                cm.setViewPoint(viewPoint);
                cm.setSaved("true");
                DatabaseOperations dOp = new DatabaseOperations(DatabaseHelper.getInstance(getApplicationContext()));
                dOp.updateAll(cm);

                value = true;
                getImage(viewPoint, true);
            }
        };
        dialog.setArgs("Edit View", viewPoint);
        showDialogFragment(dialog);
    }

    public void getImage(ViewPointModel v, boolean value) {
        viewPoint = v;
        PanguConnection panguConnection = new PanguConnection(this, imgView, cm.getIpAddress(), Integer.parseInt(cm.getPortNum()), viewPoint, headerProgress, value);
        panguConnection.execute();
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
                getImage(viewPoint, value);
                return true;
            case R.id.action_editView:
                setView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * OnTouchListener for Touch Gestures
     *
     * @param v
     * @param event
     * @return boolean
     */
    public boolean onTouch(View v, MotionEvent event) {
        float scale;
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        int pointerId = event.getPointerId(pointerIndex);

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                if (mVelocityTracker == null)
                    mVelocityTracker = VelocityTracker.obtain();
                else
                    mVelocityTracker.clear();
                mVelocityTracker.addMovement(event);
                mode = TOUCH;
                break;
            }
            case MotionEvent.ACTION_UP: {
                getImage(viewPoint, value);
            }
            case MotionEvent.ACTION_POINTER_UP: {
                mode = NONE;
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
                oldDist = distanceBetweenTwoPoints(event);
                //minimum distance between both the fingers
                if (oldDist > 5f) {
                    mode = ZOOM;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (mode == TOUCH) {
                    mVelocityTracker.addMovement(event);
                    mVelocityTracker.computeCurrentVelocity(1);

                    double xVelocity = VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId);
                    double yVelocity = VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerId);
                    LoggerHandler.i("X velocity: " + xVelocity);
                    LoggerHandler.i("Y velocity: " + yVelocity);

                    viewPoint.adjustYawAngle(xVelocity);
                    viewPoint.adjustPitchAngle(yVelocity);
                    LoggerHandler.i("Yaw Angle: " + viewPoint.getYawAngle());
                    LoggerHandler.i("Pitch Angle: " + viewPoint.getPitchAngle());

                    yawAngle_TextView.setText(String.valueOf("Yaw Angle: " + Validation.round(viewPoint.getYawAngle(), 2)));
                    pitchAngle_TextView.setText(String.valueOf("Pitch Angle: " + Validation.round(viewPoint.getPitchAngle(), 2)));

                } else if (mode == ZOOM) {
                    float newDist = distanceBetweenTwoPoints(event);
                    if (newDist > 5f) {
                        scale = newDist / oldDist * 1000;
                        LoggerHandler.i("SCALE: " + scale);
                        if (newDist < oldDist)
                            viewPoint.adjustOrigin(new Vector3D(0, 0, scale));
                        else
                            viewPoint.adjustOrigin(new Vector3D(0, 0, -scale));
                        zCoordinate_TextView.setText(String.valueOf("z-Coordinate: " + Validation.round(viewPoint.getVector3D().k, 2)));
                    }
                }
                break;
            }
        }
        return true;
    }

    /**
     * Calculates the distance between two points
     *
     * @param event
     * @return boolean
     */
    private float distanceBetweenTwoPoints(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }
}