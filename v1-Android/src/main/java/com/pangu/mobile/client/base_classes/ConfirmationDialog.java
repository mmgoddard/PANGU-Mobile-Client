package com.pangu.mobile.client.base_classes;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import com.pangu.mobile.client.R;

/**
 * Created by Mark on 06/02/15.
 */
public abstract class ConfirmationDialog extends DialogFragment {

    public void setArg(String message) {
        setArgs("", message);
    }

    public void setArgs(String title, String message) {
        Bundle args = new Bundle();
        args.putString("message", message);
        args.putString("title", title);
        setArguments(args);
    }

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getActivity().getResources();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String title = getArguments().getString("title");
        if(!title.equals("")) {
            builder.setTitle(getArguments().getString("title"));
        }
        builder.setMessage(getArguments().getString("message"));
        // Set cancel action
        builder.setPositiveButton(res.getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancel();
            }
        });
        // Set confirmation action
        builder.setNegativeButton(res.getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirm();
            }
        });
        return builder.create();
    }


    /**
     * Must override this method to handle confirmation event
     */
    public abstract void confirm();


    /**
     * Override this method to handle when operation is cancelled
     */
    public void cancel() {
        dismiss();
    }
}
