package com.hotellook.ui.screen.searchresults.adapters.cards.controller;

import com.hotellook.filters.Filters;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.search.SearchData;
import com.hotellook.ui.screen.searchresults.adapters.cards.Card;
import com.hotellook.ui.screen.searchresults.adapters.cards.ItemPositionCalculator;
import com.hotellook.ui.screen.searchresults.adapters.cards.NearbyCitiesSearchCard;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TabletCards implements Cards {
    private final List<Card> cards;
    private Filters filters;
    private final NearbyCitiesSearchCard nearbyCitiesSearchCard;
    private final PersistentFilters persistentFilters;

    public TabletCards(PersistentFilters persistentFilters) {
        this.persistentFilters = persistentFilters;
        this.nearbyCitiesSearchCard = new NearbyCitiesSearchCard();
        this.cards = Arrays.asList(new Card[]{this.nearbyCitiesSearchCard});
    }

    public void init(SearchData searchData, Filters filters) {
        this.filters = filters;
        for (Card card : this.cards) {
            card.init(searchData, filters, this.persistentFilters);
        }
    }

    public List<Card> getCards() {
        List<Card> cards = new LinkedList();
        if (this.nearbyCitiesSearchCard.isVisible()) {
            ItemPositionCalculator itemPositionCalculator = new ItemPositionCalculator();
            Set<Integer> cardsPositions = new HashSet(cards.size());
            for (Card card : cards) {
                cardsPositions.add(Integer.valueOf(card.position()));
            }
            this.nearbyCitiesSearchCard.position(itemPositionCalculator.countItems(this.filters.getFilteredOffers().size(), cardsPositions));
            cards.add(this.nearbyCitiesSearchCard);
        }
        return cards;
    }
}
