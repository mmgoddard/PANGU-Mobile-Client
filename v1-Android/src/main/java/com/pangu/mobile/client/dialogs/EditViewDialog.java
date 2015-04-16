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
import com.pangu.mobile.client.domain.ViewPointModel;
import com.pangu.mobile.client.utils.TypefaceSpan;
import com.pangu.mobile.client.utils.Validation;
import uk.ac.dundee.spacetech.pangu.ClientLibrary.Vector3D;

/**
 * Created by Mark on 04/03/15.
 */
public abstract class EditViewDialog extends InputDialog {
    private EditText xCoordinateEditText, yCoordinateEditText, zCoordinateEditText, yawAngleEditText, pitchAngleEditText, rollAngleEditText, stepEditText;
    private boolean xCoordinateCheck = true, yCoordinateCheck = true, zCoordinateCheck = true, yawAngleCheck = true, pitchAngleCheck = true, rollAngleCheck = true, stepCheck = true;
    private double xCoordinate, yCoordinate, zCoordinate, yawAngle, pitchAngle, rollAngle;
    private int step;
    private Button submitBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_view_dialog, container);
        String title = getArguments().getString("title");

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar_actionbar);
        toolbar.setLogo(R.drawable.ic_action_planet);
        TextView titleTextView = (TextView) v.findViewById(R.id.toolbar_title);
        SpannableString s = new SpannableString(title);
        s.setSpan(new TypefaceSpan(v.getContext(), "Roboto-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        titleTextView.setText(s);

        xCoordinateEditText = (EditText) v.findViewById(R.id.x_coordinate_editText);
        xCoordinateEditText.setText(String.valueOf(viewPoint.getVector3D().i));
        xCoordinateEditText.setSelection(xCoordinateEditText.getText().length());
        xCoordinateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (Validation.getInstance().isEmpty(xCoordinateEditText) || !Validation.getInstance().isDoubleParsable(str)) {
                    xCoordinateEditText.setError("Value needs to be number");
                    xCoordinateCheck = false;
                } else {
                    xCoordinateEditText.setError(null);
                    xCoordinateCheck = true;
                }
            }
        });
        yCoordinateEditText = (EditText) v.findViewById(R.id.y_coordinate_editText);
        yCoordinateEditText.setText(String.valueOf(viewPoint.getVector3D().j));
        yCoordinateEditText.setSelection(yCoordinateEditText.getText().length());
        yCoordinateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (Validation.getInstance().isEmpty(yCoordinateEditText) || !Validation.getInstance().isDoubleParsable(str)) {
                    yCoordinateEditText.setError("Value needs to be number");
                    yCoordinateCheck = false;
                } else {
                    yCoordinateEditText.setError(null);
                    yCoordinateCheck = true;}
            }
        });
        zCoordinateEditText = (EditText) v.findViewById(R.id.z_coordinate_editText);
        zCoordinateEditText.setText(String.valueOf(viewPoint.getVector3D().k));
        zCoordinateEditText.setSelection(zCoordinateEditText.getText().length());
        zCoordinateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (Validation.getInstance().isEmpty(zCoordinateEditText) || !Validation.getInstance().isDoubleParsable(str)) {
                    zCoordinateEditText.setError("Value needs to be number");
                    zCoordinateCheck = false;
                } else {
                    zCoordinateEditText.setError(null);
                    zCoordinateCheck = true; }
                }
        });
        yawAngleEditText = (EditText) v.findViewById(R.id.yaw_editText);
        yawAngleEditText.setText(String.valueOf(viewPoint.getYawAngle()));
        yawAngleEditText.setSelection(yawAngleEditText.getText().length());
        yawAngleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (Validation.getInstance().isEmpty(yawAngleEditText) || !Validation.getInstance().isDoubleParsable(str)) {
                    yawAngleEditText.setError("Value needs to be number");
                    yawAngleCheck = false;
                } else {
                    yawAngleEditText.setError(null);
                    yawAngleCheck = true;
                }
            }
        });
        pitchAngleEditText = (EditText) v.findViewById(R.id.pitch_editText);
        pitchAngleEditText.setText(String.valueOf(viewPoint.getPitchAngle()));
        pitchAngleEditText.setSelection(pitchAngleEditText.getText().length());
        pitchAngleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (Validation.getInstance().isEmpty(pitchAngleEditText) || !Validation.getInstance().isDoubleParsable(str)) {
                    pitchAngleEditText.setError("Value needs to be number");
                    pitchAngleCheck = false;
                } else {
                    pitchAngleEditText.setError(null);
                    pitchAngleCheck = true;
                }
            }
        });
        rollAngleEditText = (EditText) v.findViewById(R.id.roll_editText);
        rollAngleEditText.setText(String.valueOf(viewPoint.getRollAngle()));
        rollAngleEditText.setSelection(rollAngleEditText.getText().length());
        rollAngleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (Validation.getInstance().isEmpty(rollAngleEditText) || !Validation.getInstance().isDoubleParsable(str)) {
                    rollAngleEditText.setError("Value needs to be number");
                    rollAngleCheck = false;
                } else {
                    rollAngleEditText.setError(null);
                    rollAngleCheck = true;
                }
            }
        });
        stepEditText = (EditText) v.findViewById(R.id.step_editText);
        stepEditText.setText(String.valueOf(viewPoint.getStep()));
        stepEditText.setSelection(stepEditText.getText().length());
        stepEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (Validation.getInstance().isEmpty(stepEditText) || !Validation.getInstance().isDoubleParsable(str)) {
                    stepEditText.setError("Value needs to be number");
                    stepCheck = false;
                } else {
                    stepEditText.setError(null);
                    stepCheck = true;
                }
            }
        });
        submitBtn = (Button) v.findViewById(R.id.edit_view_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (xCoordinateCheck == true && yCoordinateCheck == true && zCoordinateCheck == true && yawAngleCheck == true && pitchAngleCheck == true && rollAngleCheck == true && stepCheck == true) {
                    xCoordinate = Double.parseDouble(xCoordinateEditText.getText().toString());
                    yCoordinate = Double.parseDouble(yCoordinateEditText.getText().toString());
                    zCoordinate = Double.parseDouble(zCoordinateEditText.getText().toString());
                    yawAngle = Double.parseDouble(yawAngleEditText.getText().toString());
                    pitchAngle = Double.parseDouble(pitchAngleEditText.getText().toString());
                    rollAngle = Double.parseDouble(rollAngleEditText.getText().toString());
                    step = Integer.parseInt(stepEditText.getText().toString());

                    Vector3D vector3D = new Vector3D(xCoordinate, yCoordinate, zCoordinate);
                    ViewPointModel vp = new ViewPointModel(vector3D, yawAngle, pitchAngle, rollAngle, step);
                    submit(vp);
                    dismiss();
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
    public abstract void submit(ViewPointModel v);
}
