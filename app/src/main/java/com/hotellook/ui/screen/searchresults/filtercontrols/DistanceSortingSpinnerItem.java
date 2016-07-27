package com.hotellook.ui.screen.searchresults.filtercontrols;

public class DistanceSortingSpinnerItem extends SortingSpinnerItem {
    public final String destination;

    DistanceSortingSpinnerItem(int id, String title, String destination) {
        super(id, title);
        this.destination = destination;
    }
}
