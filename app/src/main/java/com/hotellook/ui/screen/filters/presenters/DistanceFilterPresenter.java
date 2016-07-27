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
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.FiltersChangedEvent;
import com.hotellook.events.OpenDistanceSelectorEvent;
import com.hotellook.filters.items.DistanceFilterItem;
import com.hotellook.utils.AndroidUtils;
import com.jetradar.android.rangeseekbar.RangeSeekBar;
import com.jetradar.android.rangeseekbar.RangeSeekBar.OnRangeSeekBarChangeListener;
import java.text.DecimalFormat;
import me.zhanghai.android.materialprogressbar.C1759R;

public class DistanceFilterPresenter implements FilterPresenter, OnRangeSeekBarChangeListener {
    private final DistanceFilterItem distanceFilterItem;
    @Nullable
    private TextView distanceTextView;
    private final boolean isMetricUnits;
    private final String metricsText;
    private final DecimalFormat rateFormatter;
    @Nullable
    private RangeSeekBar seekBar;
    @Nullable
    private TextView titleTextView;

    /* renamed from: com.hotellook.ui.screen.filters.presenters.DistanceFilterPresenter.1 */
    class C12831 extends MonkeySafeClickListener {
        C12831() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new OpenDistanceSelectorEvent(1));
        }
    }

    public DistanceFilterPresenter(DistanceFilterItem distanceFilterItem) {
        this.rateFormatter = new DecimalFormat("0.0");
        this.distanceFilterItem = distanceFilterItem;
        this.isMetricUnits = AndroidUtils.isMetricUnits();
        this.metricsText = HotellookApplication.getApp().getString(this.isMetricUnits ? C1178R.string.kilometers : C1178R.string.miles);
    }

    public void onFiltersUpdated() {
        setUpTitle();
        setUpSeekbar();
        setUpDistanceTextView();
    }

    public void addView(LayoutInflater inflater, ViewGroup container) {
        container.addView(inflateView(inflater, container));
    }

    @NonNull
    public View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflateView(inflater, C1178R.layout.filter_distance, container);
    }

    @NonNull
    public View inflateView(LayoutInflater inflater, @LayoutRes int layoutId, ViewGroup container) {
        if (this.distanceFilterItem.hasNoData()) {
            View v = inflater.inflate(C1178R.layout.filter_disabled, container, false);
            ((TextView) v.findViewById(C1759R.id.title)).setText(C1178R.string.distance_to);
            return v;
        }
        v = inflater.inflate(layoutId, container, false);
        this.titleTextView = (TextView) v.findViewById(C1178R.id.distance_to);
        this.distanceTextView = (TextView) v.findViewById(C1178R.id.value);
        v.findViewById(C1178R.id.distance_to).setOnClickListener(new C12831());
        this.seekBar = (RangeSeekBar) v.findViewById(C1178R.id.seekbar);
        this.seekBar.setOnRangeSeekBarChangeListener(this);
        setUpDistanceTextView();
        setUpSeekbar();
        setUpTitle();
        return v;
    }

    private void setUpTitle() {
        if (this.titleTextView != null) {
            this.titleTextView.setText(this.distanceFilterItem.getDistanceTargetTitle());
        }
    }

    private void setUpDistanceTextView() {
        double dist = this.distanceFilterItem.getInterpolatedValue() / 1000.0d;
        if (!this.isMetricUnits) {
            dist *= 0.6209999918937683d;
        }
        if (this.distanceTextView != null) {
            this.distanceTextView.setText(this.rateFormatter.format(dist) + " " + this.metricsText);
        }
    }

    private void setUpSeekbar() {
        if (this.seekBar != null) {
            this.seekBar.setOnRangeSeekBarChangeListener(null);
            this.seekBar.setRangeValues(0.0d, this.distanceFilterItem.getMaxDistance() - this.distanceFilterItem.getMinDistance());
            this.seekBar.setSelectedMaxValue(this.distanceFilterItem.getValue() - this.distanceFilterItem.getMinDistance());
            this.seekBar.setOnRangeSeekBarChangeListener(this);
        }
    }

    public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Double minValue, Double maxValue) {
    }

    public void onStopTrackingTouch() {
        HotellookApplication.eventBus().post(new FiltersChangedEvent());
    }

    public void onRangeSeekBarTracking(RangeSeekBar rangeSeekBar, Double selectedMinValue, Double selectedMaxValue) {
        this.distanceFilterItem.setValue(selectedMaxValue.doubleValue() + this.distanceFilterItem.getMinDistance());
        setUpDistanceTextView();
    }
}
