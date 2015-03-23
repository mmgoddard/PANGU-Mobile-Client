package com.pangu.mobile.client.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.pangu.mobile.client.R;
import com.pangu.mobile.client.models.ConfigurationModel;

import java.net.InetAddress;
import java.util.List;

/**
 * Created by Mark on 20/01/15.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<ConfigurationModel> values;
    private FragmentManager fm;
    private ViewClickListener mViewClickListener;

    public ImageAdapter(Context c, List<ConfigurationModel> values, FragmentManager fm) {
        mContext = c;
        this.inflater = LayoutInflater.from(c);
        this.values = values;
        this.fm = fm;
    }

    public int getCount() {
        return values.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final View grid;
        if (convertView == null) {
            grid = inflater.inflate(R.layout.list_view_content, parent, false);
        } else {
            grid = convertView;
        }

        TextView title = (TextView)grid.findViewById(R.id.item_title);
        //TextView desc = (TextView)grid.findViewById(R.id.item_desc);
        final ConfigurationModel element = values.get(position);
        if(values.size() != 0) {
            title.setText(element.getName());
            //desc.setText(element.getIpAddress()+":"+element.getPortNum());
        }

        TextView deleteBtn = (TextView)grid.findViewById(R.id.item_delete);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mViewClickListener.onCompleteDeleteConfiguration(element.getId());
            }
        });

        TextView editBtn = (TextView)grid.findViewById(R.id.item_edit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateConfiguration(element);
            }
        });

        TextView runBtn = (TextView)grid.findViewById(R.id.item_run);
        runBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                runConfiguration(element);
            }
        });
        return grid;
    }

    private void runConfiguration(ConfigurationModel config) {
        Intent intent = new Intent(mContext, PanguActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("Configuration",config);
        mContext.startActivity(intent);
    }

    private void updateConfiguration(ConfigurationModel cm) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        UpdateConfigDialog newFragment = UpdateConfigDialog.newInstance("Update Configuration", cm);
        newFragment.show(ft, "dialog");
    }

    public interface ViewClickListener {
        void onCompleteDeleteConfiguration(int id);
    }

    public void setViewClickListener (ViewClickListener viewClickListener) {
        mViewClickListener = viewClickListener;
    }
}
