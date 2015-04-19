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
import com.pangu.mobile.client.base_classes.BaseDialog;
import com.pangu.mobile.client.domain.ViewPointModel;
import com.pangu.mobile.client.utils.Validation;
import uk.ac.dundee.spacetech.pangu.ClientLibrary.Vector3D;

/**
 * Created by Mark on 04/03/15.
 */
public abstract class EditViewDialog extends BaseDialog implements View.OnClickListener {
    private final int DIALOG_LAYOUT = R.layout.edit_view_dialog;
    private final int SUBMIT_BTN = R.id.edit_view_btn;
    private final int X_COORDINATE_EDIT_TEXT = R.id.x_coordinate_editText;
    private final int Y_COORDINATE_EDIT_TEXT = R.id.y_coordinate_editText;
    private final int Z_COORDINATE_EDIT_TEXT = R.id.z_coordinate_editText;
    private final int YAW_ANGLE_EDIT_TEXT = R.id.yaw_editText;
    private final int PITCH_ANGLE_EDIT_TEXT = R.id.pitch_editText;
    private final int ROLL_ANGLE_EDIT_TEXT = R.id.roll_editText;
    private final int STEP_EDIT_TEXT = R.id.step_editText;

    private final String KEY_TITLE = "title";

    private EditText xCoordinateEditText, yCoordinateEditText, zCoordinateEditText, yawAngleEditText, pitchAngleEditText, rollAngleEditText, stepEditText;
    private boolean xCoordinateCheck = true, yCoordinateCheck = true, zCoordinateCheck = true, yawAngleCheck = true, pitchAngleCheck = true, rollAngleCheck = true, stepCheck = true;
    private ViewPointModel viewPoint;
    public EditViewDialog() {}

    public void setArgs(String title, ViewPointModel v) {
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        setArguments(args);
        this.viewPoint = v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(DIALOG_LAYOUT, container);
        setToolbar(v, getArguments().getString(KEY_TITLE));

        Button submitBtn = (Button) v.findViewById(SUBMIT_BTN);
        submitBtn.setOnClickListener(this);

        xCoordinateEditText = (EditText) v.findViewById(X_COORDINATE_EDIT_TEXT);
        xCoordinateEditText.setText(String.valueOf(viewPoint.getVector3D().i));
        xCoordinateEditText.setSelection(xCoordinateEditText.getText().length());
        xCoordinateEditText.addTextChangedListener(new GeneralTextWatcher(xCoordinateEditText));


        yCoordinateEditText = (EditText) v.findViewById(Y_COORDINATE_EDIT_TEXT);
        yCoordinateEditText.setText(String.valueOf(viewPoint.getVector3D().j));
        yCoordinateEditText.setSelection(yCoordinateEditText.getText().length());
        yCoordinateEditText.addTextChangedListener(new GeneralTextWatcher(yCoordinateEditText));

        zCoordinateEditText = (EditText) v.findViewById(Z_COORDINATE_EDIT_TEXT);
        zCoordinateEditText.setText(String.valueOf(viewPoint.getVector3D().k));
        zCoordinateEditText.setSelection(zCoordinateEditText.getText().length());
        zCoordinateEditText.addTextChangedListener(new GeneralTextWatcher(zCoordinateEditText));

        yawAngleEditText = (EditText) v.findViewById(YAW_ANGLE_EDIT_TEXT);
        yawAngleEditText.setText(String.valueOf(viewPoint.getYawAngle()));
        yawAngleEditText.setSelection(yawAngleEditText.getText().length());
        yawAngleEditText.addTextChangedListener(new GeneralTextWatcher(yawAngleEditText));

        pitchAngleEditText = (EditText) v.findViewById(PITCH_ANGLE_EDIT_TEXT);
        pitchAngleEditText.setText(String.valueOf(viewPoint.getPitchAngle()));
        pitchAngleEditText.setSelection(pitchAngleEditText.getText().length());
        pitchAngleEditText.addTextChangedListener(new GeneralTextWatcher(pitchAngleEditText));

        rollAngleEditText = (EditText) v.findViewById(ROLL_ANGLE_EDIT_TEXT);
        rollAngleEditText.setText(String.valueOf(viewPoint.getRollAngle()));
        rollAngleEditText.setSelection(rollAngleEditText.getText().length());
        rollAngleEditText.addTextChangedListener(new GeneralTextWatcher(rollAngleEditText));

        stepEditText = (EditText) v.findViewById(STEP_EDIT_TEXT);
        stepEditText.setText(String.valueOf(viewPoint.getStep()));
        stepEditText.setSelection(stepEditText.getText().length());
        stepEditText.addTextChangedListener(new GeneralTextWatcher(stepEditText));
        return v;
    }

    public void onClick(View v) {
        if (xCoordinateCheck && yCoordinateCheck && zCoordinateCheck && yawAngleCheck && pitchAngleCheck && rollAngleCheck && stepCheck) {
            Double xCoordinate = Double.parseDouble(xCoordinateEditText.getText().toString());
            Double yCoordinate = Double.parseDouble(yCoordinateEditText.getText().toString());
            Double zCoordinate = Double.parseDouble(zCoordinateEditText.getText().toString());
            Double yawAngle = Double.parseDouble(yawAngleEditText.getText().toString());
            Double pitchAngle = Double.parseDouble(pitchAngleEditText.getText().toString());
            Double rollAngle = Double.parseDouble(rollAngleEditText.getText().toString());
            int step = Integer.parseInt(stepEditText.getText().toString());

            Vector3D vector3D = new Vector3D(xCoordinate, yCoordinate, zCoordinate);
            ViewPointModel vp = new ViewPointModel(vector3D, yawAngle, pitchAngle, rollAngle, step);
            submit(vp);
            dismiss();
        } else
            Toast.makeText(getActivity(), "The form has been incorrectly filled out.", Toast.LENGTH_LONG).show();
    }

    private class GeneralTextWatcher implements TextWatcher {
        private View view;

        private GeneralTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch (view.getId()) {
                case X_COORDINATE_EDIT_TEXT:
                    if (Validation.getInstance().isEmpty(xCoordinateEditText) || !Validation.getInstance().isDoubleParsable(text)) {
                        xCoordinateEditText.setError("e.g. 433 Eros");
                        xCoordinateCheck = false;
                    } else {
                        xCoordinateEditText.setError(null);
                        xCoordinateCheck = true;
                    }
                    break;
                case Y_COORDINATE_EDIT_TEXT:
                    if (Validation.getInstance().isEmpty(yCoordinateEditText) || !Validation.getInstance().isDoubleParsable(text)) {
                        yCoordinateEditText.setError("e.g. 192.168.0.13");
                        yCoordinateCheck = false;
                    } else {
                        yCoordinateEditText.setError(null);
                        yCoordinateCheck = true;
                    }
                    break;
                case Z_COORDINATE_EDIT_TEXT:
                    if (Validation.getInstance().isEmpty(zCoordinateEditText) || !Validation.getInstance().isDoubleParsable(text)) {
                        zCoordinateEditText.setError("e.g. 192.168.0.13");
                        zCoordinateCheck = false;
                    } else {
                        zCoordinateEditText.setError(null);
                        zCoordinateCheck = true;
                    }
                    break;
                case YAW_ANGLE_EDIT_TEXT:
                    if (Validation.getInstance().isEmpty(yawAngleEditText) || !Validation.getInstance().isDoubleParsable(text)) {
                        yawAngleEditText.setError("e.g. 192.168.0.13");
                        yawAngleCheck = false;
                    } else {
                        yawAngleEditText.setError(null);
                        yawAngleCheck = true;
                    }
                    break;
                case PITCH_ANGLE_EDIT_TEXT:
                    if (Validation.getInstance().isEmpty(pitchAngleEditText) || !Validation.getInstance().isDoubleParsable(text)) {
                        pitchAngleEditText.setError("e.g. 192.168.0.13");
                        pitchAngleCheck = false;
                    } else {
                        pitchAngleEditText.setError(null);
                        pitchAngleCheck = true;
                    }
                    break;
                case ROLL_ANGLE_EDIT_TEXT:
                    if (Validation.getInstance().isEmpty(rollAngleEditText) || !Validation.getInstance().isDoubleParsable(text)) {
                        rollAngleEditText.setError("e.g. 192.168.0.13");
                        rollAngleCheck = false;
                    } else {
                        rollAngleEditText.setError(null);
                        rollAngleCheck = true;
                    }
                    break;
                case STEP_EDIT_TEXT:
                    if (Validation.getInstance().isEmpty(stepEditText) || !Validation.getInstance().isIntParsable(text)) {
                        stepEditText.setError("e.g. 8080");
                        stepCheck = false;
                    } else {
                        stepEditText.setError(null);
                        stepCheck = true;
                    }
                    break;
            }
        }
    }
}
