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
import com.hotellook.events.FiltersChangedEvent;
import com.hotellook.filters.items.RatingFilterItem;
import com.hotellook.utils.ValueFormat;
import com.jetradar.android.rangeseekbar.RangeSeekBar;
import com.jetradar.android.rangeseekbar.RangeSeekBar.OnRangeSeekBarChangeListener;
import me.zhanghai.android.materialprogressbar.C1759R;

public class RatingFilterPresenter implements FilterPresenter, OnRangeSeekBarChangeListener {
    private final RatingFilterItem ratingFilterItem;
    @Nullable
    private RangeSeekBar seekBar;
    @Nullable
    private TextView textView;

    public RatingFilterPresenter(RatingFilterItem ratingFilterItem) {
        this.ratingFilterItem = ratingFilterItem;
    }

    public void onFiltersUpdated() {
        setUpRatingTextView();
        setUpSeekbar();
    }

    public void addView(LayoutInflater inflater, ViewGroup container) {
        container.addView(inflateView(inflater, C1178R.layout.filter_rating, container));
    }

    @NonNull
    public View inflateView(LayoutInflater inflater, @LayoutRes int layoutId, ViewGroup container) {
        if (this.ratingFilterItem.hasValidData()) {
            View v = inflater.inflate(layoutId, container, false);
            this.seekBar = (RangeSeekBar) v.findViewById(C1178R.id.seekbar);
            this.seekBar.setOnRangeSeekBarChangeListener(this);
            this.textView = (TextView) v.findViewById(C1178R.id.rating_text);
            setUpSeekbar();
            setUpRatingTextView();
            return v;
        }
        v = inflater.inflate(C1178R.layout.filter_disabled, container, false);
        ((TextView) v.findViewById(C1759R.id.title)).setText(C1178R.string.hotels_rating);
        return v;
    }

    private void setUpRatingTextView() {
        if (this.textView != null) {
            this.textView.setText(ValueFormat.ratingToString(this.ratingFilterItem.getValue()));
        }
    }

    private void setUpSeekbar() {
        if (this.seekBar != null) {
            this.seekBar.setSelectedMaxValue((double) this.ratingFilterItem.getValue());
        }
    }

    public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Double minValue, Double maxValue) {
    }

    public void onStopTrackingTouch() {
        HotellookApplication.eventBus().post(new FiltersChangedEvent());
    }

    public void onRangeSeekBarTracking(RangeSeekBar rangeSeekBar, Double selectedMinValue, Double selectedMaxValue) {
        this.ratingFilterItem.setValue(selectedMaxValue.intValue());
        setUpRatingTextView();
    }
}
