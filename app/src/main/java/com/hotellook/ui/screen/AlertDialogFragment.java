package com.hotellook.ui.screen;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class AlertDialogFragment extends DialogFragment {
    private AlertDialog alertDialog;

    public static AlertDialogFragment create(AlertDialog alertDialog) {
        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.setDialog(alertDialog);
        return dialogFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    private void setDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return this.alertDialog;
    }

    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }
}
