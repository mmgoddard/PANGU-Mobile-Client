package com.pangu.mobile.client.dialogs;

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
import com.pangu.mobile.client.interfaces.CallbackCodes;
import com.pangu.mobile.client.base_classes.BaseDialog;
import com.pangu.mobile.client.domain.ConfigurationModel;
import com.pangu.mobile.client.domain.ViewPointModel;
import com.pangu.mobile.client.utils.Validation;
import org.apache.commons.validator.routines.InetAddressValidator;
import uk.ac.dundee.spacetech.pangu.ClientLibrary.Vector3D;

/**
 * Created by Mark on 17/04/15.
 */
public abstract class AddDialog extends BaseDialog implements View.OnClickListener {
    private final int DIALOG_LAYOUT = R.layout.fragment_config_dialog;
    private final int SUBMIT_BTN = R.id.add_config_btn;
    private final int NAME_EDIT_TEXT = R.id.name_editText;
    private final int IP_ADDRESS_EDIT_TEXT = R.id.ipAddress_editText;
    private final int PORT_NUM_EDIT_TEXT = R.id.portNum_editText;

    private final String KEY_TITLE = "title";
    private final String KEY_CONFIGURATION = "configuration";
    private final String KEY_ACTION_CODE = "action_code";

    private EditText nameEditText, ipAddressEditText, portNumEditText;
    private boolean validateName = true, validateIpAddress = true, validatePortNum = true;
    private ConfigurationModel config;
    public AddDialog() {}

    public void setArgs(String title, ConfigurationModel cm, int actionCode) {
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putParcelable(KEY_CONFIGURATION, cm);
        args.putInt(KEY_ACTION_CODE, actionCode);
        setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(DIALOG_LAYOUT, container);
        setToolbar(v, getArguments().getString(KEY_TITLE));
        config = getArguments().getParcelable(KEY_CONFIGURATION);

        Button updateConfigBtn = (Button) v.findViewById(SUBMIT_BTN);
        updateConfigBtn.setText("Update Configuration");
        updateConfigBtn.setOnClickListener(this);

        nameEditText = (EditText) v.findViewById(NAME_EDIT_TEXT);
        nameEditText.addTextChangedListener(new GeneralTextWatcher(nameEditText));

        ipAddressEditText = (EditText) v.findViewById(IP_ADDRESS_EDIT_TEXT);
        ipAddressEditText.addTextChangedListener(new GeneralTextWatcher(ipAddressEditText));

        portNumEditText = (EditText) v.findViewById(PORT_NUM_EDIT_TEXT);
        portNumEditText.addTextChangedListener(new GeneralTextWatcher(portNumEditText));

        if(getArguments().getInt(KEY_ACTION_CODE) != CallbackCodes.ADD_MODEL_CONFIG) {
            nameEditText.setText(config.getName());
            ipAddressEditText.setText(config.getIpAddress());
            portNumEditText.setText(config.getPortNum());
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        if (validateName && validateIpAddress && validatePortNum) {
            String name = nameEditText.getText().toString();
            String ipAddress = ipAddressEditText.getText().toString();
            String portNum = portNumEditText.getText().toString();

            ConfigurationModel cm;
            int actionCode = getArguments().getInt(KEY_ACTION_CODE);
            if(actionCode == CallbackCodes.UPDATE_MODEL_CONFIG)
                cm = new ConfigurationModel(config.getId(), name, ipAddress, portNum);
            else {
                Vector3D vector3D = new Vector3D(0.0, 0.0, 0.0);
                ViewPointModel viewPoint = new ViewPointModel(vector3D, 0.0, 0.0, 0.0, 0);
                cm = new ConfigurationModel(name, ipAddress, portNum, viewPoint, "false");
            }

            submit(cm);
        } else {
            Toast.makeText(getActivity(), "The form has been incorrectly filled out.", Toast.LENGTH_LONG).show();
        }
    }

    private class GeneralTextWatcher implements TextWatcher {
        private View view;
        private GeneralTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch (view.getId()) {
                case NAME_EDIT_TEXT:
                    if (Validation.getInstance().isEmpty(nameEditText) || !Validation.getInstance().isAlphaNumeric(text) || text.length() > 15) {
                        nameEditText.setError("e.g. 433 Eros");
                        validateName = false;
                    } else {
                        nameEditText.setError(null);
                        validateName = true;
                    }
                    break;
                case IP_ADDRESS_EDIT_TEXT:
                    if (Validation.getInstance().isEmpty(ipAddressEditText) || !InetAddressValidator.getInstance().isValid(text)) {
                        ipAddressEditText.setError("e.g. 192.168.0.13");
                        validateIpAddress = false;
                    } else {
                        ipAddressEditText.setError(null);
                        validateIpAddress = true;
                    }
                    break;
                case PORT_NUM_EDIT_TEXT:
                    if (Validation.getInstance().isEmpty(portNumEditText) || !Validation.getInstance().isIntParsable(text) || Integer.parseInt(text) > 65565) {
                        portNumEditText.setError("e.g. 8080");
                        validatePortNum = false;
                    } else {
                        portNumEditText.setError(null);
                        validatePortNum = true;
                    }
                    break;
            }
        }
    }
}