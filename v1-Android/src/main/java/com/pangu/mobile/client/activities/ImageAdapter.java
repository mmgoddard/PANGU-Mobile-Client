package com.pangu.mobile.client.activities;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.pangu.mobile.client.R;
import com.pangu.mobile.client.models.ConfigurationModel;
import com.pangu.mobile.client.utils.DatabaseHelper;
import java.util.List;

/**
 * Created by Mark on 20/01/15.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<ConfigurationModel> values;

    public ImageAdapter(Context c, List<ConfigurationModel> values) {
        mContext = c;
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
            grid = inflater.inflate(R.layout.grid, parent, false);
        } else {
            grid = convertView;
        }

        TextView imageView = (TextView)grid.findViewById(R.id.grid_image);
        TextView textView = (TextView)grid.findViewById(R.id.grid_text);
        imageView.setBackgroundColor(Color.RED);
        if(values.size() != 0)
            textView.setText(values.get(position).getName());
        return grid;
    }
}
