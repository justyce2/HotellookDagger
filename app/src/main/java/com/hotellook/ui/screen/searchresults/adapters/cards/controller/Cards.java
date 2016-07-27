package com.hotellook.ui.screen.searchresults.adapters.cards.controller;

import com.hotellook.filters.Filters;
import com.hotellook.search.SearchData;
import com.hotellook.ui.screen.searchresults.adapters.cards.Card;
import java.util.List;

public interface Cards {
    List<Card> getCards();

    void init(SearchData searchData, Filters filters);
}
