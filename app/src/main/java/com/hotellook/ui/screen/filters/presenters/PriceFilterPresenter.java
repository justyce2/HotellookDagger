package com.hotellook.ui.screen.filters.presenters;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.currency.CurrencyFormatter;
import com.hotellook.events.FiltersChangedEvent;
import com.hotellook.filters.items.PriceFilterItem;
import com.jetradar.android.rangeseekbar.RangeSeekBar;
import com.jetradar.android.rangeseekbar.RangeSeekBar.OnRangeSeekBarChangeListener;
import me.zhanghai.android.materialprogressbar.C1759R;

public class PriceFilterPresenter implements FilterPresenter, OnRangeSeekBarChangeListener {
    private final CurrencyFormatter currencyFormatter;
    private final PriceFilterItem filterItem;
    @Nullable
    private TextView priceFrom;
    @Nullable
    private TextView priceTo;
    @Nullable
    private RangeSeekBar rangeSeekBar;

    public PriceFilterPresenter(PriceFilterItem filterItem) {
        this.filterItem = filterItem;
        this.currencyFormatter = new CurrencyFormatter(HotellookApplication.getApp().getComponent().currencyRepository());
    }

    public void onFiltersUpdated() {
        setUpRatingBar();
        setUpTextViews();
    }

    public void addView(LayoutInflater inflater, ViewGroup container) {
        container.addView(inflateView(inflater, C1178R.layout.filter_price, container));
    }

    @NonNull
    public View inflateView(LayoutInflater inflater, @LayoutRes int layoutId, ViewGroup container) {
        if (this.filterItem.hasValidData()) {
            View v = inflater.inflate(layoutId, container, false);
            this.rangeSeekBar = (RangeSeekBar) v.findViewById(C1178R.id.seekbar);
            this.rangeSeekBar.setRangeValues(this.filterItem.getMinPrice(), this.filterItem.getMaxPrice());
            this.rangeSeekBar.setNotifyWhileDragging(true);
            this.priceFrom = (TextView) v.findViewById(C1178R.id.price_from);
            this.priceTo = (TextView) v.findViewById(C1178R.id.price_to);
            onFiltersUpdated();
            return v;
        }
        v = inflater.inflate(C1178R.layout.filter_disabled, container, false);
        ((TextView) v.findViewById(C1759R.id.title)).setText(C1178R.string.price);
        return v;
    }

    private void setUpTextViews() {
        String currency = this.filterItem.getCurrency();
        if (this.priceFrom != null && this.priceTo != null) {
            this.priceFrom.setText(this.currencyFormatter.formatPrice(this.filterItem.getMinInterpolatedValue(), currency));
            this.priceTo.setText(this.currencyFormatter.formatPrice(this.filterItem.getMaxInterpolatedValue(), currency));
        }
    }

    private void setUpRatingBar() {
        if (this.rangeSeekBar != null) {
            this.rangeSeekBar.setOnRangeSeekBarChangeListener(null);
            this.rangeSeekBar.setSelectedMinValue(this.filterItem.getMinValue());
            this.rangeSeekBar.setSelectedMaxValue(this.filterItem.getMaxValue());
            this.rangeSeekBar.setOnRangeSeekBarChangeListener(this);
        }
    }

    public void onRangeSeekBarTracking(RangeSeekBar rangeSeekBar, Double minValue, Double maxValue) {
        this.filterItem.setMinValue(minValue);
        this.filterItem.setMaxValue(maxValue);
        setUpTextViews();
    }

    public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Double minValue, Double maxValue) {
    }

    public void onStopTrackingTouch() {
        HotellookApplication.eventBus().post(new FiltersChangedEvent());
    }
}
