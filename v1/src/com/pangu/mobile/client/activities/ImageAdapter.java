package com.pangu.mobile.client.activities;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.pangu.mobile.client.R;

/**
 * Created by Mark on 20/01/15.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;

    public ImageAdapter(Context c) {
        mContext = c;
        this.inflater = LayoutInflater.from(c);
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    //Create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        if (convertView == null) {
            grid = inflater.inflate(R.layout.grid, parent, false);
        } else {
            grid = convertView;
        }

        ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
        TextView textView = (TextView)grid.findViewById(R.id.grid_text);
        imageView.setImageResource(mThumbIds[position]);
        textView.setText("Position: " + String.valueOf(position));
        return grid;
    }

    //References to our images
    private Integer[] mThumbIds = {
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher
    };
}
