package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.filters.FiltersSnapshot;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.distancetarget.DefaultTargetSelector;
import com.hotellook.filters.distancetarget.DistanceTarget;
import com.hotellook.filters.distancetarget.DistanceTarget.DestinationType;
import com.hotellook.filters.distancetarget.TargetSelector;
import com.hotellook.filters.items.criterion.Criterion;
import com.hotellook.filters.items.criterion.DistanceCriterion;
import com.hotellook.search.SearchData;
import com.hotellook.utils.ExponentialInterpolator;
import com.hotellook.utils.LocationUtils;
import java.util.List;

public class DistanceFilterItem extends FilterItem<HotelData> {
    private static final String SAVE_MAX;
    private static final String SAVE_MIN;
    private static final String SAVE_TAG;
    private static final String SAVE_TARGET;
    private static final String SAVE_VALUE;
    private DistanceTarget distanceTarget;
    private List<HotelData> hotels;
    private ExponentialInterpolator interpolator;
    private double maxDistance;
    private double minDistance;
    private TargetSelector targetSelector;
    private double value;

    public DistanceFilterItem() {
        this.minDistance = Double.MAX_VALUE;
        this.maxDistance = Double.MIN_VALUE;
        this.targetSelector = new DefaultTargetSelector();
    }

    static {
        SAVE_TAG = DistanceFilterItem.class.getSimpleName();
        SAVE_VALUE = SAVE_TAG + "_value";
        SAVE_TARGET = SAVE_TAG + "_target";
        SAVE_MAX = SAVE_TAG + "_max";
        SAVE_MIN = SAVE_TAG + "_min";
    }

    @NonNull
    protected Criterion<HotelData> getCriterion() {
        return new DistanceCriterion(getInterpolatedValue(), this.distanceTarget);
    }

    public void reset() {
        this.value = this.maxDistance;
    }

    public void setUp(SearchData searchData, PersistentFilters persistentFilters) {
        setUpDefaultTarget(searchData);
        this.hotels = searchData.hotels().all();
        recalculate();
    }

    public void applyFrom(DistanceFilterItem distanceFilterItem) {
        this.distanceTarget = distanceFilterItem.distanceTarget;
        this.maxDistance = distanceFilterItem.maxDistance;
        this.minDistance = distanceFilterItem.minDistance;
        this.value = distanceFilterItem.value;
        this.hotels = distanceFilterItem.hotels;
    }

    private void setUpDefaultTarget(SearchData searchData) {
        this.distanceTarget = this.targetSelector.select(searchData);
    }

    private void recalculate() {
        this.minDistance = Double.MAX_VALUE;
        this.maxDistance = Double.MIN_VALUE;
        for (HotelData hotel : this.hotels) {
            double distance = this.distanceTarget.distanceTo(LocationUtils.from(hotel.getLocation()));
            if (this.minDistance >= distance) {
                this.minDistance = distance;
            } else if (this.maxDistance <= distance) {
                this.maxDistance = distance;
            }
        }
        this.value = this.maxDistance;
        this.interpolator = new ExponentialInterpolator(this.minDistance, this.maxDistance);
    }

    public boolean hasNoData() {
        return this.minDistance >= this.maxDistance;
    }

    public boolean inDefaultState() {
        return this.value == this.maxDistance;
    }

    public int hashCode() {
        return _hashCode();
    }

    public int _hashCode() {
        int result = this.distanceTarget != null ? this.distanceTarget.hashCode() : 0;
        long temp = Double.doubleToLongBits(this.value);
        return (result * 31) + ((int) ((temp >>> 32) ^ temp));
    }

    public void saveState(FiltersSnapshot snapshot) {
        snapshot.addData(SAVE_VALUE, Double.valueOf(this.value));
        snapshot.addData(SAVE_TARGET, this.distanceTarget);
        snapshot.addData(SAVE_MAX, Double.valueOf(this.maxDistance));
        snapshot.addData(SAVE_MIN, Double.valueOf(this.minDistance));
    }

    public void restoreState(FiltersSnapshot snapshot) {
        this.value = ((Double) snapshot.getData(SAVE_VALUE)).doubleValue();
        this.distanceTarget = (DistanceTarget) snapshot.getData(SAVE_TARGET);
        this.maxDistance = ((Double) snapshot.getData(SAVE_MAX)).doubleValue();
        this.minDistance = ((Double) snapshot.getData(SAVE_MIN)).doubleValue();
    }

    public void release() {
        this.hotels = null;
    }

    public DestinationType getDistanceType() {
        return this.distanceTarget.getType();
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getInterpolatedValue() {
        return this.interpolator.getInterpolation(this.value);
    }

    public void setInterpolatedValue(int interpolatedValue) {
        this.value = this.interpolator.backInterpolate((double) interpolatedValue);
    }

    public String getDistanceTargetTitle() {
        return this.distanceTarget.getTitle();
    }

    public double getMinDistance() {
        return this.minDistance;
    }

    public double getMaxDistance() {
        return this.maxDistance;
    }

    public DistanceTarget getDistanceTarget() {
        return this.distanceTarget;
    }

    public void setDistanceTarget(DistanceTarget distanceTarget) {
        this.distanceTarget = distanceTarget;
        recalculate();
    }

    public void setTargetSelector(TargetSelector targetSelector) {
        this.targetSelector = targetSelector;
    }
}
