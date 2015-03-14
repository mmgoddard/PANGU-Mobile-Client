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
import android.widget.Toast;
import com.pangu.mobile.client.R;
import com.pangu.mobile.client.models.ConfigurationModel;
import com.pangu.mobile.client.utils.Validation;
import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * Created by Mark on 03/02/15.
 */
public class UpdateConfigDialog extends DialogFragment implements View.OnClickListener{
    private EditText nameEditText, ipAddressEditText, portNumEditText;
    private Button updateConfigBtn;
    private UpdateOnCompleteListener mListener;
    private boolean nameCheck = true, ipAddressCheck = true, portNumCheck = true;
    private int id;
    public UpdateConfigDialog() {}

    public static UpdateConfigDialog newInstance(String title, ConfigurationModel cm) {
        UpdateConfigDialog frag = new UpdateConfigDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("id", cm.getId());
        args.putString("name", cm.getName());
        args.putString("ipAddress", cm.getIpAddress());
        args.putString("portNum", cm.getPortNum());
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyCustomTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_config_dialog, container);
        id = getArguments().getInt("id");
        updateConfigBtn = (Button) v.findViewById(R.id.add_config_btn);
        updateConfigBtn.setText("Update Configuration");
        updateConfigBtn.setOnClickListener(this);
        nameEditText = (EditText) v.findViewById(R.id.name_editText);
        nameEditText.setText(getArguments().getString("name"));
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
        ipAddressEditText.setText(getArguments().getString("ipAddress"));
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
        portNumEditText.setText(getArguments().getString("portNum"));
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
        getDialog().setTitle(getArguments().getString("title"));
        return v;
    }

    @Override
    public void onClick(View v) {
        if (ipAddressCheck == true && portNumCheck == true && nameCheck == true) {
            String name = nameEditText.getText().toString();
            String ipAddress = ipAddressEditText.getText().toString();
            String portNum = portNumEditText.getText().toString();

            ConfigurationModel cm = new ConfigurationModel(id, name, ipAddress, portNum);
            this.mListener.onCompleteUpdateConfiguration(cm);
            dismiss();
        } else {
            Toast.makeText(getActivity(), "The form has been incorrectly filled out.", Toast.LENGTH_LONG).show();
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (UpdateOnCompleteListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    public static interface UpdateOnCompleteListener {
        public abstract void onCompleteUpdateConfiguration(ConfigurationModel cm);
    }
}
