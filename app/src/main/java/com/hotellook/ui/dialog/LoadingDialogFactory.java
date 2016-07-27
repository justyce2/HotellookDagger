package com.hotellook.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.hotellook.C1178R;

public class LoadingDialogFactory {
    private final Context mContext;

    public LoadingDialogFactory(Context context) {
        this.mContext = context;
    }

    public AlertDialog createDialog(LoadingDialogContent content) {
        return new Builder(this.mContext).setView(createContent(content.message)).create();
    }

    @NonNull
    private View createContent(int messageId) {
        View view = LayoutInflater.from(this.mContext).inflate(C1178R.layout.dialog_loading, null);
        ((TextView) view.findViewById(C1178R.id.message)).setText(messageId);
        return view;
    }
}
