package com.hotellook.ui.screen.searchresults.filtercontrols;

import android.content.Context;
import com.hotellook.HotellookApplication;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.search.Search;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.EventBus;

public class FilterControlsPresenterFactory {
    public static FilterControlsPresenter create(Context context, boolean hasGMS) {
        HotellookComponent component = HotellookApplication.from(context).getComponent();
        Search search = component.searchKeeper().lastSearchOrThrowException();
        EventBus eventBus = component.eventBus();
        if (AndroidUtils.isPhone(context)) {
            return new PhoneFilterControlsPresenter(search.filters(), search.cards(), search.searchData(), eventBus, hasGMS);
        }
        return new TabletFiltersControlsPresenter(search.filters(), eventBus, new FiltersWorkArownds());
    }
}
