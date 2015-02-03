package com.pangu.mobile.client.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.pangu.mobile.client.R;
import com.pangu.mobile.client.models.ConfigurationModel;
import com.pangu.mobile.client.utils.DatabaseHelper;
import com.pangu.mobile.client.utils.DatabaseOperations;
import java.util.List;

/**
 * @Author Mark Goddard
 * @Date 08/10/2014
 * @Desc Starts the main activity.
 */
public class MainActivity extends Activity implements AddConfigDialog.OnCompleteListener {
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
//
//        db = new DatabaseHelper(getApplicationContext());
//        DatabaseOperations databaseOperations = new DatabaseOperations(db);
//        ConfigurationModel cm = new ConfigurationModel(1, "Test Model", "172.16.178.129", "10363");
//        ConfigurationModel cm1 = new ConfigurationModel(2, "Test Model", "172.16.178.129", "10363");
//        ConfigurationModel cm2 = new ConfigurationModel(3, "Test Model", "172.16.178.129", "10363");
//        databaseOperations.insertConfiguration(cm);
//        databaseOperations.insertConfiguration(cm1);
//        databaseOperations.insertConfiguration(cm2);
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
     * @param menu
     * @return onCreateOptionsMenu()
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Called when action bar item is selected.
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

    public void addConfiguration() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        AddConfigDialog newFragment = AddConfigDialog.newInstance("Some Title");
        newFragment.show(ft, "dialog");
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
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    /**
     * After the AddConfigDialog fragment completes, it calls this callback.
     * @param cm
     */
    public void onCompleteAddConfiguration(ConfigurationModel cm) {
        db = new DatabaseHelper(getApplicationContext());
        DatabaseOperations databaseOperations = new DatabaseOperations(db);
        databaseOperations.insertConfiguration(cm);
        Toast.makeText(getApplicationContext(), "Added Configuration", Toast.LENGTH_LONG).show();
        getGridItems();
    }

    /**
     * Get any configurations from database.
     */
    public void getGridItems() {
        final GridView gridView = (GridView) findViewById(R.id.grid_view);
        db = new DatabaseHelper(getApplicationContext());
        DatabaseOperations databaseOperations = new DatabaseOperations(db);
        final List<ConfigurationModel> values = databaseOperations.readConfiguration();
        if(values.size() != 0) {
            gridView.setAdapter(new ImageAdapter(this, values));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    ConfigurationModel config = values.get(position);
                    v.findViewById(position);
                    v.setBackgroundColor(getResources().getColor(R.color.blurred));
                    Intent intent = new Intent(getApplicationContext(), PanguActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("name", config.getName());
                    intent.putExtra("ipAddress", config.getIpAddress());
                    intent.putExtra("portNum", config.getPortNum());
                    startActivity(intent);
                }
            });
        } else {
            //display no configurations
        }
    }
}
