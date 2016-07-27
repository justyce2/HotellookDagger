package com.hotellook.filters.pages;

import com.hotellook.filters.items.RoomAmenityFilterItem;
import com.hotellook.filters.items.criterion.BreakfastOptionCriterion;
import com.hotellook.filters.items.criterion.RefundableOptionCriterion;
import com.hotellook.filters.items.criterion.SmokingOptionCriterion;
import com.hotellook.filters.items.criterion.ViewOptionCriterion;

public class RoomsAmenitiesFiltersCategory extends SequentalRoomListFiltersCategory {
    private final RoomAmenityFilterItem breakfast;
    private final RoomAmenityFilterItem refundable;
    private final RoomAmenityFilterItem smoking;
    private final RoomAmenityFilterItem view;

    public RoomsAmenitiesFiltersCategory() {
        this.breakfast = new RoomAmenityFilterItem(new BreakfastOptionCriterion());
        addFilter(this.breakfast);
        this.view = new RoomAmenityFilterItem(new ViewOptionCriterion());
        addFilter(this.view);
        this.smoking = new RoomAmenityFilterItem(new SmokingOptionCriterion());
        addFilter(this.smoking);
        this.refundable = new RoomAmenityFilterItem(new RefundableOptionCriterion());
        addFilter(this.refundable);
    }

    public boolean isBreakfastInDefaultState() {
        return this.breakfast.inDefaultState();
    }

    public boolean isViewInDefaultState() {
        return this.view.inDefaultState();
    }

    public boolean isSmokingInDefaultState() {
        return this.smoking.inDefaultState();
    }

    public boolean isRefundableInDefaultState() {
        return this.refundable.inDefaultState();
    }

    public RoomAmenityFilterItem getBreakfastItem() {
        return this.breakfast;
    }

    public RoomAmenityFilterItem getViewItem() {
        return this.view;
    }

    public RoomAmenityFilterItem getSmokingItem() {
        return this.smoking;
    }

    public RoomAmenityFilterItem getRefundableItem() {
        return this.refundable;
    }
}
