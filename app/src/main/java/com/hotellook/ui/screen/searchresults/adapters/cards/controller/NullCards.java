package com.hotellook.ui.screen.searchresults.adapters.cards.controller;

import com.hotellook.filters.Filters;
import com.hotellook.search.SearchData;
import com.hotellook.ui.screen.searchresults.adapters.cards.Card;
import java.util.Collections;
import java.util.List;

public class NullCards implements Cards {
    public List<Card> getCards() {
        return Collections.emptyList();
    }

    public void init(SearchData searchData, Filters filters) {
    }
}
