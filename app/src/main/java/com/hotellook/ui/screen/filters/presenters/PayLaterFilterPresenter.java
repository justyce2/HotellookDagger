package com.hotellook.ui.screen.filters.presenters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.filters.items.PaymentFilterItem;
import me.zhanghai.android.materialprogressbar.C1759R;

public class PayLaterFilterPresenter extends CheckBoxPresenter {
    private final PaymentFilterItem filterItem;

    public PayLaterFilterPresenter(@NonNull PaymentFilterItem filterItem) {
        this.filterItem = filterItem;
    }

    public boolean isChecked() {
        return this.filterItem.isPayLater();
    }

    public void setChecked(boolean checked) {
        this.filterItem.setPayLater(checked);
    }

    public View createView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(C1178R.layout.filter_item_checkbox_icon, parent, false);
        ((ImageView) view.findViewById(C1759R.id.icon)).setImageResource(C1178R.drawable.ic_pay_later);
        ((TextView) view.findViewById(C1759R.id.title)).setText(C1178R.string.filter_pay_later);
        ((TextView) view.findViewById(C1178R.id.count)).setText(String.valueOf(this.filterItem.getPayLaterCount()));
        return view;
    }
}
