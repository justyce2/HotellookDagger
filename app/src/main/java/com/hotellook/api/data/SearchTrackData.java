package com.hotellook.api.data;

import com.hotellook.search.SearchParams;
import java.util.HashMap;
import java.util.Map;

public class SearchTrackData {
    private Map<Long, String> mBookClicks;
    private long mFinishTimestamp;
    private final SearchParams mSearchParams;
    private SearchResult mSearchResult;
    private long mStartTimestamp;

    protected enum SearchResult {
        IDLE,
        SUCCESS,
        ERROR,
        CANCELED
    }

    public SearchTrackData(SearchParams searchParams) {
        this.mSearchResult = SearchResult.IDLE;
        this.mBookClicks = new HashMap();
        this.mSearchParams = searchParams;
        this.mStartTimestamp = System.currentTimeMillis();
    }

    public void onSearchSuccess() {
        this.mSearchResult = SearchResult.SUCCESS;
        this.mFinishTimestamp = System.currentTimeMillis();
    }

    public void onSearchCanceled() {
        this.mSearchResult = SearchResult.CANCELED;
        this.mFinishTimestamp = System.currentTimeMillis();
    }

    public void onSearchFailed() {
        this.mSearchResult = SearchResult.ERROR;
        this.mFinishTimestamp = System.currentTimeMillis();
    }

    public SearchParams getSearchParams() {
        return this.mSearchParams;
    }

    public void addBookClick(double price, String currencyCode) {
        this.mBookClicks.put(Long.valueOf(System.currentTimeMillis()), price + " " + currencyCode);
    }
}
