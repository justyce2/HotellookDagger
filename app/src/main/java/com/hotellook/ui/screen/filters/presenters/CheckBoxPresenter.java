package com.hotellook.ui.screen.filters.presenters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.events.FiltersChangedEvent;
import me.zhanghai.android.materialprogressbar.C1759R;

public abstract class CheckBoxPresenter implements OnClickListener, FilterPresenter {
    private CheckBox checkBox;

    public abstract View createView(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup);

    public abstract boolean isChecked();

    public abstract void setChecked(boolean z);

    public void onFiltersUpdated() {
        this.checkBox.setChecked(isChecked());
    }

    public void addView(LayoutInflater inflater, ViewGroup container) {
        View view = createView(inflater, container);
        container.addView(view);
        this.checkBox = (CheckBox) view.findViewById(C1759R.id.checkbox);
        this.checkBox.setChecked(isChecked());
        view.findViewById(C1178R.id.clickable).setOnClickListener(this);
    }

    public void onClick(View view) {
        boolean checked = !isChecked();
        setChecked(checked);
        this.checkBox.setChecked(checked);
        HotellookApplication.eventBus().post(new FiltersChangedEvent());
    }
}
