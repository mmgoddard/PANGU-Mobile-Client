package com.pangu.mobile.client.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.pangu.mobile.client.R;
import com.pangu.mobile.client.models.ConfigurationModel;
import com.pangu.mobile.client.utils.Validation;
import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * Created by Mark on 02/02/15.
 */
public class AddConfigDialog extends DialogFragment implements View.OnClickListener {
    private EditText nameEditText, ipAddressEditText, portNumEditText;
    private Button addConfigBtn;
    private OnCompleteListener mListener;
    private boolean nameCheck = false, ipAddressCheck = false, portNumCheck = false;

    public AddConfigDialog() {}

    public static AddConfigDialog newInstance(String title) {
        AddConfigDialog frag = new AddConfigDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL;
        int theme = android.R.style.Theme_Holo_Light;
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_config, container);
        addConfigBtn = (Button) v.findViewById(R.id.add_config_btn);
        addConfigBtn.setOnClickListener(this);
        addConfigBtn.setEnabled(false);
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
                    nameEditText.setError("e.g. Config1");
                    nameCheck = false;
                } else {
                    nameEditText.setError(null);
                    nameCheck = true;
                }
                enableBtnIfReady();
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
                enableBtnIfReady();
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
                enableBtnIfReady();
            }
        });
        getDialog().setTitle("Add Configuration");
        return v;
    }

    @Override
    public void onClick(View v) {
        String name = nameEditText.getText().toString();
        String ipAddress = nameEditText.getText().toString();
        String portNum = portNumEditText.getText().toString();

        ConfigurationModel cm = new ConfigurationModel(name, ipAddress, portNum);
        this.mListener.onCompleteAddConfiguration(cm);
        dismiss();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    public static interface OnCompleteListener {
        public abstract void onCompleteAddConfiguration(ConfigurationModel cm);
    }

    public void enableBtnIfReady() {
        if (ipAddressCheck == true && portNumCheck == true && nameCheck == true)
            addConfigBtn.setEnabled(true);
        else
            addConfigBtn.setEnabled(false);
    }
}
