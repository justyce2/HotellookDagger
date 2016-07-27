package com.hotellook.events;

import com.hotellook.badges.Badges;
import com.hotellook.search.Offers;
import com.hotellook.search.SearchData;
import com.hotellook.search.SearchParams;

public class SearchFinishEvent {
    private final Badges badges;
    private final SearchData searchData;

    public SearchFinishEvent(SearchData searchData, Badges badges) {
        this.searchData = searchData;
        this.badges = badges;
    }

    public SearchParams searchParams() {
        return this.searchData.searchParams();
    }

    public Badges badges() {
        return this.badges;
    }

    public Offers offers() {
        return this.searchData.offers();
    }

    public SearchData searchData() {
        return this.searchData;
    }
}
