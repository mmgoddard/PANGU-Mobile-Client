package com.pangu.mobile.client.activities;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;
import com.pangu.mobile.client.R;
import com.pangu.mobile.client.base_classes.BaseActivity;
import com.pangu.mobile.client.base_classes.ConfirmationDialog;
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
public class MainActivity extends BaseActivity implements UpdateConfigDialog.UpdateOnCompleteListener, ImageAdapter.ViewClickListener {
    private DatabaseHelper db;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getMainLayoutResID());

        //Testing Database
        //getApplicationContext().deleteDatabase("Pangu.db");
        db = new DatabaseHelper(getApplicationContext());
    }

    @Override
    protected int getMainLayoutResID() {
        return R.layout.list_view;
    }
    @Override
    protected int getOptionsMenuLayoutResID() { return R.menu.main_activity_action_bar_actions; }

    /**
     * Called when the activity is resumed.
     */
    @Override
    public void onResume() {
        super.onResume();
        getGridItems();
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
            case R.id.action_add:
                addConfiguration();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when the back button is pressed
     */
    @Override
    public void onBackPressed() {
        String message = "Really Quit";
        ConfirmationDialog dialog = new ConfirmationDialog() {
            @Override
            public void confirm() {
                startIntent(Intent.ACTION_MAIN);
            }
        };
        dialog.setArgs("", message);
        showDialogFragment(dialog);
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
    public void onCompleteDeleteConfiguration(final int id) {
        String message = "Are are you sure you want to delete this configuration?";
        ConfirmationDialog dialog = new ConfirmationDialog() {
            @Override
            public void confirm() {
                db = new DatabaseHelper(getApplicationContext());
                DatabaseOperations databaseOperations = new DatabaseOperations(db);
                ErrorHandler e = databaseOperations.deleteConfiguration(id);
                if (e == ErrorHandler.SQL_EXECUTION_ERROR)
                    Toast.makeText(getApplicationContext(), e.getLongMessage(), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Deleted Configuration", Toast.LENGTH_LONG).show();
                getGridItems();
            }
        };
        dialog.setArgs("", message);
        showDialogFragment(dialog);

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
        final AddConfigDialog dialog = new AddConfigDialog() {
            @Override
            public void submit(ConfigurationModel cm) {
                db = new DatabaseHelper(getApplicationContext());
                DatabaseOperations databaseOperations = new DatabaseOperations(db);
                ErrorHandler e = databaseOperations.insertConfiguration(cm);
                if (e == ErrorHandler.SQL_EXECUTION_SUCCESS) {
                    Toast.makeText(getApplicationContext(), "Added Configuration", Toast.LENGTH_LONG).show();
                    dismiss();
                    getGridItems();
                } else {
                    Toast.makeText(getApplicationContext(), e.getLongMessage(), Toast.LENGTH_LONG).show();
                }
            }
        };
        dialog.setArgs("Add Configuration");
        showDialogFragment(dialog);
    }
}