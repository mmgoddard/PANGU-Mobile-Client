package com.pangu.mobile.client.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pangu.mobile.client.R;
import com.pangu.mobile.client.adapters.ImageAdapter;
import com.pangu.mobile.client.base_classes.BaseActivity;
import com.pangu.mobile.client.base_classes.ConfirmationDialog;
import com.pangu.mobile.client.dialogs.AddConfigDialog;
import com.pangu.mobile.client.dialogs.UpdateConfigDialog;
import com.pangu.mobile.client.domain.ConfigurationModel;
import com.pangu.mobile.client.utils.DatabaseHelper;
import com.pangu.mobile.client.utils.DatabaseOperations;
import com.pangu.mobile.client.utils.ErrorHandler;
import com.pangu.mobile.client.utils.TypefaceSpan;

import java.util.List;

/**
 * @Author Mark Goddard
 * @Date 08/10/2014
 * @Desc Starts the main activity.
 */
public class MainActivity extends BaseActivity implements UpdateConfigDialog.UpdateOnCompleteListener, ImageAdapter.ViewClickListener {
    private View mainActivityTopLevelLayout;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        setActionBarTitle("PANGU (v"+getApplicationVersionCode()+")");

        mainActivityTopLevelLayout = findViewById(R.id.top_layout);
        if (isFirstTime()) {
            mainActivityTopLevelLayout.setVisibility(View.INVISIBLE);
            if (actionBarMenu != null) {
                actionBarMenu.findItem(R.id.action_add).setEnabled(true);
            }
        }
        mainActivityTopLevelLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mainActivityTopLevelLayout.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        //Testing Database
        //getApplicationContext().deleteDatabase("Pangu.db");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_action_bar_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("mainActivityHelp", false);
        if (!ranBefore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("mainActivityHelp", true);
            editor.commit();
            mainActivityTopLevelLayout.setVisibility(View.VISIBLE);
            mainActivityTopLevelLayout.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mainActivityTopLevelLayout.setVisibility(View.INVISIBLE);
                    actionBarMenu.findItem(R.id.action_add).setEnabled(true);
                    return false;
                }
            });
        }
        return ranBefore;
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
        DatabaseOperations dOp = new DatabaseOperations(DatabaseHelper.getInstance(this));
        ErrorHandler e = dOp.updateConfiguration(cm);
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
                DatabaseOperations dOp = new DatabaseOperations(DatabaseHelper.getInstance(getApplicationContext()));
                ErrorHandler e = dOp.deleteConfiguration(id);
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
        DatabaseOperations dOp = new DatabaseOperations(DatabaseHelper.getInstance(this));
        final List<ConfigurationModel> values = dOp.readConfiguration();
        FragmentManager fm = getSupportFragmentManager();
        ImageAdapter imageAdapter = new ImageAdapter(this, values, fm);
        imageAdapter.setViewClickListener(this);
        gridView.setAdapter(imageAdapter);
    }

    public void addConfiguration() {
        final AddConfigDialog dialog = new AddConfigDialog() {
            @Override
            public void submit(ConfigurationModel cm) {
                DatabaseOperations dOp = new DatabaseOperations(DatabaseHelper.getInstance(getApplicationContext()));
                ErrorHandler e = dOp.insertConfiguration(cm);
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

    private String getApplicationVersionCode() {
        Context context = getApplicationContext();
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        String myVersionName = "";

        try {
            myVersionName = packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return myVersionName;
    }
}