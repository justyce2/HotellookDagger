package com.hotellook.ui.screen.locationchooser;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.events.FiltersSortingChangedEvent;
import com.hotellook.filters.Filters;
import com.hotellook.filters.distancetarget.MapPointDistanceTarget;
import com.hotellook.filters.items.DistanceFilterItem;
import com.hotellook.filters.items.SortingItem;
import com.hotellook.ui.screen.OnBackPressHandler;
import com.hotellook.utils.AndroidUtils;

public class FilterLocationChooserFragment extends LocationChooserFragment implements OnBackPressHandler {
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AndroidUtils.addMarginToOffsetNavBarBottom(this.applyBtn);
    }

    void choosePointAndReturn(Location location) {
        Filters filters = getComponent().searchKeeper().lastSearchOrThrowException().filters();
        DistanceFilterItem filterItem = filters.getGeneralPage().getDistanceFilterItem();
        SortingItem sortingItem = filters.getSortingCategory().getSortingItem();
        MapPointDistanceTarget distanceTarget = new MapPointDistanceTarget(getContext(), location);
        sortingItem.setDistanceTarget(distanceTarget);
        filterItem.setDistanceTarget(distanceTarget);
        sortingItem.setAlgoId(C1178R.id.sorting_distance);
        HotellookApplication.eventBus().post(new FiltersSortingChangedEvent(sortingItem));
        returnToFiltersScreen();
    }

    private void returnToFiltersScreen() {
        getMainActivity().goBack();
        getMainActivity().goBack();
    }

    public static Fragment create(Location location) {
        FilterLocationChooserFragment fragment = new FilterLocationChooserFragment();
        LocationChooserFragment.fillArgs(fragment, location);
        return fragment;
    }

    public boolean onBackPressedHandled() {
        getMainActivity().goBack();
        return true;
    }
}
