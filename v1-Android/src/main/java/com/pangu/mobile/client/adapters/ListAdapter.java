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
public class ListAdapter extends BaseAdapter {
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

    public View getView(final int position, View convertView, ViewGroup parent) {
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
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewClickListener.onListViewClickListener(CallbackCodes.DELETE_MODEL_CONFIG, values.get(position));
            }
        });
        TextView editBtn = (TextView) grid.findViewById(R.id.item_edit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewClickListener.onListViewClickListener(CallbackCodes.UPDATE_MODEL_CONFIG, values.get(position));
            }
        });
        TextView runBtn = (TextView) grid.findViewById(R.id.item_run);
        runBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewClickListener.onListViewClickListener(CallbackCodes.RUN_MODEL_CONFIG, values.get(position));
            }
        });
        return grid;
    }

    public interface ViewClickListener {
        void onListViewClickListener(int code, ConfigurationModel cm);
    }

    public void setViewClickListener(ViewClickListener viewClickListener) {
        mViewClickListener = viewClickListener;
    }
}
