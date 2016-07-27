package com.hotellook.ui.view.calendar;

import com.hotellook.api.data.MinPricesCalendarUtils.PriceGroup;
import java.util.Date;

class MonthCellDescriptor {
    private final Date date;
    private final boolean isCurrentMonth;
    private boolean isHighlighted;
    private final boolean isSelectable;
    private boolean isSelected;
    private final boolean isToday;
    private final PriceGroup priceGroupType;
    private RangeState rangeState;
    private boolean rangeValid;
    private final int value;

    public enum RangeState {
        NONE,
        SINGLE,
        FIRST,
        MIDDLE,
        LAST
    }

    MonthCellDescriptor(Date date, boolean currentMonth, boolean selectable, boolean selected, boolean today, boolean highlighted, int value, RangeState rangeState, boolean rangeValid, PriceGroup priceGroupType) {
        this.date = date;
        this.isCurrentMonth = currentMonth;
        this.isSelectable = selectable;
        this.isHighlighted = highlighted;
        this.isSelected = selected;
        this.isToday = today;
        this.value = value;
        this.rangeState = rangeState;
        this.rangeValid = rangeValid;
        this.priceGroupType = priceGroupType;
    }

    public Date getDate() {
        return this.date;
    }

    public boolean isCurrentMonth() {
        return this.isCurrentMonth;
    }

    public boolean isSelectable() {
        return this.isSelectable;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    boolean isHighlighted() {
        return this.isHighlighted;
    }

    void setHighlighted(boolean highlighted) {
        this.isHighlighted = highlighted;
    }

    public boolean isToday() {
        return this.isToday;
    }

    public RangeState getRangeState() {
        return this.rangeState;
    }

    public void setRangeState(RangeState rangeState) {
        this.rangeState = rangeState;
    }

    public int getValue() {
        return this.value;
    }

    public boolean isRangeValid() {
        return this.rangeValid;
    }

    public void setRangeValid(boolean rangeValid) {
        this.rangeValid = rangeValid;
    }

    public PriceGroup getPriceGroupType() {
        return this.priceGroupType;
    }

    public String toString() {
        return "MonthCellDescriptor{date=" + this.date + ", value=" + this.value + ", isCurrentMonth=" + this.isCurrentMonth + ", isSelected=" + this.isSelected + ", isToday=" + this.isToday + ", isSelectable=" + this.isSelectable + ", isHighlighted=" + this.isHighlighted + ", rangeState=" + this.rangeState + '}';
    }
}
