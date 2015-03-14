package com.pangu.mobile.client.base_classes;

import android.app.DialogFragment;
import android.os.Bundle;
import com.pangu.mobile.client.R;

/**
 * Created by Mark on 04/03/15.
 */
public class InputDialog extends DialogFragment {
    public void setArgs(String title) {
        Bundle args = new Bundle();
        args.putString("title", title);
        setArguments(args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyCustomTheme);
    }
}
