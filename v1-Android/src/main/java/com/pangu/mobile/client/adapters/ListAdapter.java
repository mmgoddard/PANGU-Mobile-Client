package com.pangu.mobile.client.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.pangu.mobile.client.R;
import com.pangu.mobile.client.domain.ConfigurationModel;
import com.pangu.mobile.client.interfaces.CallbackCodes;

import java.util.List;

/**
 * Created by Mark on 20/01/15.
 */
public class ListAdapter extends BaseAdapter implements View.OnClickListener {
    private LayoutInflater inflater;
    private List<ConfigurationModel> values;
    private ViewClickListener mViewClickListener;
    ConfigurationModel element;

    public ListAdapter(Context c, List<ConfigurationModel> values) {
        this.inflater = LayoutInflater.from(c);
        this.values = values;
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

        TextView title = (TextView) grid.findViewById(R.id.item_title);
        element = values.get(position);
        if (values.size() != 0) {
            title.setText(element.getName());
        }

        TextView deleteBtn = (TextView) grid.findViewById(R.id.item_delete);
        deleteBtn.setOnClickListener(this);
        TextView editBtn = (TextView) grid.findViewById(R.id.item_edit);
        editBtn.setOnClickListener(this);
        TextView runBtn = (TextView) grid.findViewById(R.id.item_run);
        runBtn.setOnClickListener(this);
        return grid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_delete:
                mViewClickListener.onListViewClickListener(CallbackCodes.DELETE_MODEL_CONFIG, element);
                break;
            case R.id.item_edit:
                mViewClickListener.onListViewClickListener(CallbackCodes.UPDATE_MODEL_CONFIG, element);
                break;
            case R.id.item_run:
                mViewClickListener.onListViewClickListener(CallbackCodes.RUN_MODEL_CONFIG, element);
                break;
        }
    }

    public interface ViewClickListener {
        void onListViewClickListener(int code, ConfigurationModel cm);
    }

    public void setViewClickListener(ViewClickListener viewClickListener) {
        mViewClickListener = viewClickListener;
    }
}
