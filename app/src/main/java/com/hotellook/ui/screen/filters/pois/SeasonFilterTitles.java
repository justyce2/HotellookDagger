package com.hotellook.ui.screen.filters.pois;

import android.support.annotation.StringRes;

public class SeasonFilterTitles {
    @StringRes
    public final int allPoisTitleId;
    @StringRes
    public final int categoryTitleId;

    public SeasonFilterTitles(@StringRes int categoryTitleId, @StringRes int allPoisTitleId) {
        this.categoryTitleId = categoryTitleId;
        this.allPoisTitleId = allPoisTitleId;
    }
}
