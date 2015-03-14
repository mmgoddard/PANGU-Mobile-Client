package com.pangu.mobile.client.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.pangu.mobile.client.R;
import com.pangu.mobile.client.background_tasks.DataCollectionTask;
import com.pangu.mobile.client.base_classes.BaseActivity;
import com.pangu.mobile.client.interfaces.AsyncResponse;
import com.pangu.mobile.client.models.InformationModel;

/**
 * Created by Mark on 24/02/15.
 */
public class InformationActivity extends BaseActivity implements AsyncResponse {
    private LinearLayout headerProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getMainLayoutResID());
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("About Model");
        headerProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        Bundle extras = getIntent().getExtras();
        DataCollectionTask data = new DataCollectionTask(this, extras.getString("modelName"), headerProgress);
        data.asyncResponse = this;
        data.execute();
    }

    @Override
    protected int getMainLayoutResID() {
        return R.layout.information_view;
    }
    @Override
    protected int getOptionsMenuLayoutResID() { return 0; }

    /**
     * @desc processes the image from the async-task (Pangu Connection).
     * @param im
     */
    public void processData(InformationModel im) {
        TextView nameText = (TextView) this.findViewById(R.id.info_name_title);
        nameText.setText("Name: " + im.getName());
        if(im.getApproximateMass() != null) {
            TextView discoveredByText = (TextView) this.findViewById(R.id.info_discoveredBy_content);
            discoveredByText.setText(im.getDiscoveredBy());

            TextView discoveryDateText = (TextView) this.findViewById(R.id.info_discoveryDate_content);
            discoveryDateText.setText(im.getDiscoveryDate());

            TextView sizeText = (TextView) this.findViewById(R.id.info_size_content);
            sizeText.setText(im.getSize());

            TextView approximateMassText = (TextView) this.findViewById(R.id.info_approximateMass_content);
            approximateMassText.setText(im.getApproximateMass());

            TextView orbitalPeriodText = (TextView) this.findViewById(R.id.info_orbitalPeriod_content);
            orbitalPeriodText.setText(im.getOrbitalPeriod());

            TextView descriptionText = (TextView) this.findViewById(R.id.info_description_content);
            descriptionText.setText(im.getDescription());

            TextView interestingFactsText = (TextView) this.findViewById(R.id.info_interestingFacts_content);
            interestingFactsText.setText(im.getComments());
        } else {
            Toast.makeText(this, "There is currently no data available for this model.", Toast.LENGTH_LONG).show();
        }
    }
}
