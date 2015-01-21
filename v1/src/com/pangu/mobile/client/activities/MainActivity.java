package com.pangu.mobile.client.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.pangu.mobile.client.R;

/**
 * @Author Mark Goddard
 * @Date 08/10/2014
 * @Desc Starts the main activity.
 */
public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view);
    }

    /**
     * Called when the activity is resumed.
     */
    @Override
    public void onResume() {
        super.onResume();
        final GridView gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //ImageView imageView = (ImageView) findViewById(R.id.grid_image);
                //imageView.setBackgroundResource(R.drawable.selector_colour);

                v.findViewById(position);
                v.setBackgroundColor(getResources().getColor(R.color.blurred));

                Intent intent = new Intent(getApplicationContext(), PanguActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
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
}
