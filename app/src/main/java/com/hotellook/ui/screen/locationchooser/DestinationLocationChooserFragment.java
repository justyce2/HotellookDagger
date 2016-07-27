package com.hotellook.ui.screen.locationchooser;

import android.location.Location;
import android.support.v4.app.Fragment;
import com.hotellook.api.data.SearchFormData;

public class DestinationLocationChooserFragment extends LocationChooserFragment {
    void choosePointAndReturn(Location location) {
        SearchFormData searchFormData = new SearchFormData(getContext(), getComponent().getSearchFormPreferences());
        searchFormData.updateWithLocationDestination(location);
        searchFormData.saveData();
        getMainActivity().returnToSearchForm();
    }

    public static Fragment create(Location location) {
        DestinationLocationChooserFragment fragment = new DestinationLocationChooserFragment();
        LocationChooserFragment.fillArgs(fragment, location);
        return fragment;
    }
}
