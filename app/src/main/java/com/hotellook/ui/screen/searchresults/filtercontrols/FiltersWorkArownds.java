package com.hotellook.ui.screen.searchresults.filtercontrols;

import android.widget.Spinner;
import com.hotellook.utils.AndroidUtils;

public class FiltersWorkArownds {
    public void closeSpinner(Spinner spinner) {
        AndroidUtils.forceHideSpinners(spinner);
    }
}
