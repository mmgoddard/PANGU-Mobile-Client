package com.pangu.mobile.client.activities;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.pangu.mobile.client.R;
import com.pangu.mobile.client.models.ConfigurationModel;
import com.pangu.mobile.client.utils.DatabaseHelper;
import com.pangu.mobile.client.utils.DatabaseOperations;
import com.pangu.mobile.client.utils.ErrorHandler;

import java.util.List;

/**
 * @Author Mark Goddard
 * @Date 08/10/2014
 * @Desc Starts the main activity.
 */
public class MainActivity extends Activity implements AddConfigDialog.OnCompleteListener, UpdateConfigDialog.UpdateOnCompleteListener, ImageAdapter.ViewClickListener {
    private DatabaseHelper db;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view);

        //Testing Database
        //getApplicationContext().deleteDatabase("Pangu.db");
        db = new DatabaseHelper(getApplicationContext());
    }

    /**
     * Called when the activity is resumed.
     */
    @Override
    public void onResume() {
        super.onResume();
        getGridItems();
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
        inflater.inflate(R.menu.action_bar_actions, menu);
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
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add:
                addConfiguration();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

    /**
     * Called when the back button is pressed
     */
    @Override
    public void onBackPressed() {
        DialogFragment newFragment = new CloseAppDialog();
        newFragment.show(getFragmentManager(), "missiles");
    }

    /**
     * After the AddConfigDialog fragment completes, it calls this callback.
     *
     * @param cm
     */
    public void onCompleteAddConfiguration(ConfigurationModel cm) {
        db = new DatabaseHelper(getApplicationContext());
        DatabaseOperations databaseOperations = new DatabaseOperations(db);
        ErrorHandler e = databaseOperations.insertConfiguration(cm);
        if (e == ErrorHandler.SQL_EXECUTION_SUCCESS) {
            Toast.makeText(getApplicationContext(), "Added Configuration", Toast.LENGTH_LONG).show();
            getGridItems();
        } else {
            Toast.makeText(getApplicationContext(), e.getLongMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * After the AddConfigDialog fragment completes, it calls this callback.
     *
     * @param cm
     */
    public void onCompleteUpdateConfiguration(ConfigurationModel cm) {
        db = new DatabaseHelper(getApplicationContext());
        DatabaseOperations databaseOperations = new DatabaseOperations(db);
        ErrorHandler e = databaseOperations.updateConfiguration(cm);
        if (e == ErrorHandler.SQL_EXECUTION_SUCCESS) {
            Toast.makeText(getApplicationContext(), "Updated Configuration", Toast.LENGTH_LONG).show();
            getGridItems();
        } else {
            Toast.makeText(getApplicationContext(), e.getLongMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * After a the list item delete has been clicked.
     *
     * @param id
     */
    public void onCompleteDeleteConfiguration(int id) {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        DatabaseOperations databaseOperations = new DatabaseOperations(db);
        ErrorHandler e = databaseOperations.deleteConfiguration(id);
        if (e == ErrorHandler.SQL_EXECUTION_ERROR)
            Toast.makeText(getApplicationContext(), e.getLongMessage(), Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(getApplicationContext(), "Deleted Configuration", Toast.LENGTH_LONG).show();
            getGridItems();
        }
    }

    /**
     * Get configurations from database.
     */
    public void getGridItems() {
        final ListView gridView = (ListView) findViewById(R.id.grid_view);
        db = new DatabaseHelper(getApplicationContext());
        DatabaseOperations databaseOperations = new DatabaseOperations(db);
        final List<ConfigurationModel> values = databaseOperations.readConfiguration();
        FragmentManager fm = getFragmentManager();
        ImageAdapter imageAdapter = new ImageAdapter(this, values, fm);
        imageAdapter.setViewClickListener(this);
        gridView.setAdapter(imageAdapter);
    }

    public void addConfiguration() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        AddConfigDialog newFragment = AddConfigDialog.newInstance("Add Configuration");
        newFragment.show(ft, "dialog");
    }
}
