package com.hotellook.ui.screen.filters.presenters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.filters.items.NoRoomsFilterItem;
import me.zhanghai.android.materialprogressbar.C1759R;

public class NoRoomsPresenter extends BoolFilterPresenter {
    public NoRoomsPresenter(@NonNull NoRoomsFilterItem filterItem) {
        super(filterItem);
    }

    public View createView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(C1178R.layout.filter_item_checkbox, parent, false);
        ((TextView) view.findViewById(C1759R.id.title)).setText(C1178R.string.hide_hotels_no_rooms);
        view.findViewById(C1178R.id.count).setVisibility(8);
        return view;
    }
}
