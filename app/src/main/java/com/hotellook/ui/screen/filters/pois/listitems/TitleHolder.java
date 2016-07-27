package com.hotellook.ui.screen.filters.pois.listitems;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;
import me.zhanghai.android.materialprogressbar.C1759R;

public class TitleHolder extends ViewHolder {
    TextView title;

    public TitleHolder(View view) {
        super(view);
        this.title = (TextView) view.findViewById(C1759R.id.title);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }
}
