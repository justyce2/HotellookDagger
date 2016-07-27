package com.hotellook.ui.screen.filters.presenters;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.filters.items.BoolFilterItem;
import me.zhanghai.android.materialprogressbar.C1759R;

public class OptionServicePresenter extends BoolFilterPresenter {
    private final BoolFilterItem filterItem;
    private final int iconId;
    private final int textId;

    public static class Builder {
        private BoolFilterItem<?> filterItem;
        @DrawableRes
        private int iconId;
        @StringRes
        private int textId;

        public Builder filterItem(BoolFilterItem<?> item) {
            this.filterItem = item;
            return this;
        }

        public Builder iconId(@DrawableRes int iconId) {
            this.iconId = iconId;
            return this;
        }

        public Builder textId(@StringRes int textId) {
            this.textId = textId;
            return this;
        }

        public OptionServicePresenter build() {
            if (this.filterItem != null && this.iconId != 0 && this.textId != 0) {
                return new OptionServicePresenter(this.iconId, this.textId, null);
            }
            throw new IllegalStateException("Some fields are not set up " + toString());
        }
    }

    private OptionServicePresenter(@NonNull BoolFilterItem filterItem, @DrawableRes int iconId, @StringRes int textId) {
        super(filterItem);
        this.filterItem = filterItem;
        this.iconId = iconId;
        this.textId = textId;
    }

    public View createView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(C1178R.layout.filter_item_checkbox_icon, parent, false);
        ((ImageView) view.findViewById(C1759R.id.icon)).setImageResource(this.iconId);
        ((TextView) view.findViewById(C1759R.id.title)).setText(this.textId);
        ((TextView) view.findViewById(C1178R.id.count)).setText(String.valueOf(this.filterItem.getCount()));
        return view;
    }
}
