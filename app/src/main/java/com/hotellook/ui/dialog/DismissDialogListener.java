package com.hotellook.ui.dialog;

import android.support.v7.app.AlertDialog;
import android.view.View;
import com.hotellook.common.view.MonkeySafeClickListener;
import java.lang.ref.WeakReference;

public class DismissDialogListener extends MonkeySafeClickListener {
    private WeakReference<AlertDialog> dialog;

    public void onSafeClick(View v) {
        if (this.dialog != null) {
            AlertDialog dialog = (AlertDialog) this.dialog.get();
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }

    public void setDialog(AlertDialog dialog) {
        this.dialog = new WeakReference(dialog);
    }
}
