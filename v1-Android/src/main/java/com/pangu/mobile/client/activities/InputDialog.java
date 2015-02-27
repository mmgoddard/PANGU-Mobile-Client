package com.pangu.mobile.client.activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.pangu.mobile.client.R;
import com.pangu.mobile.client.models.ConfigurationModel;
import com.pangu.mobile.client.utils.Validation;
import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * Created by Mark on 06/02/15.
 */
public abstract class InputDialog extends DialogFragment {
    private EditText nameEditText, ipAddressEditText, portNumEditText;
    private boolean nameCheck = false, ipAddressCheck = false, portNumCheck = false;
    private Button confirmBtn;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_config_dialog, container);
        String title = getArguments().getString("title");

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
                String ipAddress = s.toString();
                if (Validation.getInstance().isEmpty(ipAddressEditText) || !InetAddressValidator.getInstance().isValid(ipAddress)) {
                    ipAddressEditText.setError("e.g. 192.168.0.13");
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
                if (Validation.getInstance().isEmpty(portNumEditText) || !Validation.getInstance().isParsable(portNum) || Integer.parseInt(portNum) > 65565) {
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

                    ConfigurationModel cm = new ConfigurationModel(name, ipAddress, portNum);
                    confirm(cm);
                } else
                    Toast.makeText(getActivity(), "The form has been incorrectly filled out.", Toast.LENGTH_LONG).show();
            }
        });
        getDialog().setTitle(title);
        return v;
    }

    /**
     * Must override this method to handle confirmation event
     */
    public abstract void confirm(ConfigurationModel cm);
}
