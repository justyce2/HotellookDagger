package com.hotellook.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.hotellook.C1178R;

public class TwoButtonDialogFactory {
    private final Context context;

    public TwoButtonDialogFactory(Context context) {
        this.context = context;
    }

    public AlertDialog createDialog(TwoButtonDialogContent content) {
        TextView title = createTitle(content.title);
        return new Builder(this.context).setCustomTitle(title).setView(createContent(content.message, content.firstBtnTxtId, content.secondBtnTxtId, content.firstBtnClickListener, content.secondBtnClickListener, content.firstBtnColor)).create();
    }

    @NonNull
    private View createContent(int messageId, int firstBtnTextId, int secondBtnTextId, OnClickListener firstBtnListener, OnClickListener secondBtnListener, Integer firstBtnColor) {
        View view = LayoutInflater.from(this.context).inflate(C1178R.layout.dialog_twobutton, null);
        ((TextView) view.findViewById(C1178R.id.message)).setText(messageId);
        TextView firstBtn = (TextView) view.findViewById(C1178R.id.first_btn);
        TextView secondBtn = (TextView) view.findViewById(C1178R.id.second_btn);
        firstBtn.setText(firstBtnTextId);
        secondBtn.setText(secondBtnTextId);
        firstBtn.setOnClickListener(firstBtnListener);
        secondBtn.setOnClickListener(secondBtnListener);
        if (firstBtnColor != null) {
            firstBtn.setTextColor(firstBtnColor.intValue());
        }
        return view;
    }

    @NonNull
    private TextView createTitle(int titleTextId) {
        TextView title = (TextView) LayoutInflater.from(this.context).inflate(C1178R.layout.dialog_title, null);
        title.setText(titleTextId);
        return title;
    }
}
