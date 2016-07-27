package com.hotellook.events;

import com.hotellook.search.SearchParams;
import com.hotellook.statistics.Constants.Source;
import com.hotellook.statistics.Constants.Tonight;
import com.hotellook.utils.DateUtils;

public class SearchStartEvent {
    public final String imageUrl;
    public final SearchParams searchParams;
    public final Source source;
    public final Tonight tonight;

    public SearchStartEvent(SearchParams searchParams, Source source, String imageUrl) {
        this.searchParams = searchParams;
        this.source = source;
        this.imageUrl = imageUrl;
        this.tonight = DateUtils.isTonight(searchParams) ? Tonight.YES_MANUAL : Tonight.NO;
    }
}
