package com.pangu.mobile.client.dialogs;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.pangu.mobile.client.R;
import com.pangu.mobile.client.base_classes.InputDialog;
import com.pangu.mobile.client.domain.ConfigurationModel;
import com.pangu.mobile.client.domain.ViewPoint;
import com.pangu.mobile.client.utils.TypefaceSpan;
import com.pangu.mobile.client.utils.Validation;
import uk.ac.dundee.spacetech.pangu.ClientLibrary.Vector3D;

/**
 * Created by Mark on 06/02/15.
 */
public abstract class AddConfigDialog extends InputDialog {
    private EditText nameEditText, ipAddressEditText, portNumEditText;
    private boolean nameCheck = false, ipAddressCheck = false, portNumCheck = false;
    private Button confirmBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_config_dialog, container);
        String title = getArguments().getString("title");

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar_actionbar);
        toolbar.setLogo(R.drawable.ic_action_planet);
        TextView titleTextView = (TextView) v.findViewById(R.id.toolbar_title);
        SpannableString s = new SpannableString(title);
        s.setSpan(new TypefaceSpan(v.getContext(), "Roboto-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        titleTextView.setText(s);

        nameEditText = (EditText) v.findViewById(R.id.name_editText);
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String name = s.toString();
                if (Validation.getInstance().isEmpty(nameEditText) || !Validation.getInstance().isAlphaNumeric(name) || name.length() > 15) {
                    nameEditText.setError("e.g. 433 Eros");
                    nameCheck = false;
                } else {
                    nameEditText.setError(null);
                    nameCheck = true;
                }
            }
        });
        ipAddressEditText = (EditText) v.findViewById(R.id.ipAddress_editText);
        ipAddressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (Validation.getInstance().isEmpty(ipAddressEditText)) {
                    ipAddressCheck = false;
                } else {
                    ipAddressEditText.setError(null);
                    ipAddressCheck = true;
                }
            }
        });
        portNumEditText = (EditText) v.findViewById(R.id.portNum_editText);
        portNumEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String portNum = s.toString();
                if (Validation.getInstance().isEmpty(portNumEditText) || !Validation.getInstance().isIntParsable(portNum) || Integer.parseInt(portNum) > 65565) {
                    portNumEditText.setError("e.g. 8080");
                    portNumCheck = false;
                } else {
                    portNumEditText.setError(null);
                    portNumCheck = true;
                }
            }
        });
        confirmBtn = (Button) v.findViewById(R.id.add_config_btn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ipAddressCheck == true && portNumCheck == true && nameCheck == true) {
                    String name = nameEditText.getText().toString();
                    String ipAddress = ipAddressEditText.getText().toString();
                    String portNum = portNumEditText.getText().toString();

                    Vector3D vector3D = new Vector3D(0.0, 0.0, 0.0);
                    ViewPoint viewPoint = new ViewPoint(vector3D, 0.0, 0.0, 0.0);
                    ConfigurationModel cm = new ConfigurationModel(name, ipAddress, portNum, viewPoint, "false");
                    submit(cm);
                } else
                    Toast.makeText(getActivity(), "The form has been incorrectly filled out.", Toast.LENGTH_LONG).show();
            }
        });
        getDialog().setTitle(title);
        return v;
    }

    /**
     * Must override this method to handle submission event
     */
    public abstract void submit(ConfigurationModel v);
}
