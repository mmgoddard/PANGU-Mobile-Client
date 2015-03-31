package com.pangu.mobile.client.base_classes;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import com.pangu.mobile.client.R;
import com.pangu.mobile.client.models.ViewPoint;

/**
 * Created by Mark on 04/03/15.
 */
public class InputDialog extends DialogFragment {
    protected ViewPoint viewPoint;

    public void setArgs(String title) {
        Bundle args = new Bundle();
        args.putString("title", title);
        setArguments(args);
    }

    public void setArgs(String title, ViewPoint v) {
        Bundle args = new Bundle();
        args.putString("title", title);
        setArguments(args);
        this.viewPoint = v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyCustomTheme);
    }
}
