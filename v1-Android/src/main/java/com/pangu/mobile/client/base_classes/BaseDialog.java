package com.pangu.mobile.client.base_classes;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import com.pangu.mobile.client.R;
import com.pangu.mobile.client.domain.ConfigurationModel;
import com.pangu.mobile.client.domain.ViewPointModel;
import com.pangu.mobile.client.utils.TypefaceSpan;

/**
 * Created by Mark on 04/03/15.
 */
public abstract class BaseDialog extends DialogFragment {
    private final int LOGO_DRAWABLE = R.drawable.ic_action_planet;
    private final int TOOLBAR_ID = R.id.toolbar_actionbar;
    private final int TOOLBAR_TITLE_ID = R.id.toolbar_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyCustomTheme);
    }

    public Toolbar setToolbar(View v, String title) {
        Toolbar toolbar = (Toolbar) v.findViewById(TOOLBAR_ID);
        toolbar.setLogo(LOGO_DRAWABLE);
        TextView toolbarTitle = (TextView) v.findViewById(TOOLBAR_TITLE_ID);
        SpannableString s = new SpannableString(title);
        s.setSpan(new TypefaceSpan(getActivity(), "Roboto-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toolbarTitle.setText(s);
        return toolbar;
    }

    public abstract <T> void submit(T v);
}
