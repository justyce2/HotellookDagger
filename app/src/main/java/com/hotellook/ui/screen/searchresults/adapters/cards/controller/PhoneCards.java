package com.hotellook.ui.screen.searchresults.adapters.cards.controller;

import android.content.Context;
import android.support.annotation.Nullable;
import com.hotellook.common.locale.Constants.Language;
import com.hotellook.filters.Filters;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.search.SearchData;
import com.hotellook.search.SearchParams;
import com.hotellook.ui.screen.searchresults.adapters.cards.Card;
import com.hotellook.ui.screen.searchresults.adapters.cards.ENGatesCard;
import com.hotellook.ui.screen.searchresults.adapters.cards.ItemPositionCalculator;
import com.hotellook.ui.screen.searchresults.adapters.cards.NearbyCitiesSearchCard;
import com.hotellook.ui.screen.searchresults.adapters.cards.PriceFilterCard;
import com.hotellook.ui.screen.searchresults.adapters.cards.RatingFilterCard;
import com.hotellook.ui.screen.searchresults.adapters.cards.ResetFiltersCard;
import com.hotellook.ui.screen.searchresults.adapters.cards.distancecard.CloseableDistanceFilterCard;
import com.hotellook.ui.screen.searchresults.adapters.cards.distancecard.DistanceFilterCard;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CommonPreferences;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class PhoneCards implements Cards {
    private static final int DISTANCE_CARD_POSITION = 6;
    private static final int EN_GATES_CARD_POSITION = 10;
    private static final int MIN_HOTELS_FOR_DISTANCE_CARD = 10;
    private final List<Card> cards;
    private final CloseableDistanceFilterCard closeableDistanceCard;
    private Context context;
    private final ENGatesCard enGatesCard;
    private Filters filters;
    private final DistanceFilterCard firstPositionDistanceFilterCard;
    private final NearbyCitiesSearchCard nearbyCitiesSearchCard;
    private final PersistentFilters persistentFilters;
    private final CommonPreferences preferences;
    private final PriceFilterCard priceFilterCard;
    private final RatingFilterCard ratingFilterCard;
    private final ResetFiltersCard resetFiltersCard;
    private boolean searchByLocation;
    private SearchData searchData;

    public PhoneCards(Context context, CommonPreferences commonPreferences, PersistentFilters persistentFilters) {
        this.preferences = commonPreferences;
        this.context = context;
        this.persistentFilters = persistentFilters;
        this.closeableDistanceCard = new CloseableDistanceFilterCard(context);
        this.firstPositionDistanceFilterCard = new DistanceFilterCard(context);
        this.priceFilterCard = new PriceFilterCard();
        this.ratingFilterCard = new RatingFilterCard();
        this.enGatesCard = new ENGatesCard();
        this.resetFiltersCard = new ResetFiltersCard();
        this.nearbyCitiesSearchCard = new NearbyCitiesSearchCard();
        this.cards = Arrays.asList(new Card[]{this.priceFilterCard, this.ratingFilterCard, this.closeableDistanceCard, this.enGatesCard, this.firstPositionDistanceFilterCard, this.resetFiltersCard, this.nearbyCitiesSearchCard});
    }

    public void init(SearchData searchData, Filters filters) {
        this.searchData = searchData;
        SearchParams searchParams = searchData.searchParams();
        boolean z = searchParams.isDestinationTypeUserLocation() || searchParams.isDestinationTypeMapPoint() || searchParams.isDestinationTypeNearbyCities();
        this.searchByLocation = z;
        this.filters = filters;
        for (Card card : this.cards) {
            card.init(searchData, filters, this.persistentFilters);
        }
    }

    public List<Card> getCards() {
        List<Card> cards = new LinkedList();
        if (AndroidUtils.isPortrait(this.context)) {
            boolean hasFirstFilterItem = hasFirstFilterItem();
            if (hasFirstFilterItem) {
                Card topFilterCard = getTopFilterCard();
                if (topFilterCard != null) {
                    cards.add(topFilterCard);
                }
            }
            if (this.filters.getFilteredHotelsSize() == 0) {
                cards.add(this.resetFiltersCard);
            }
            Card distanceCard = getCloseableDistanceCard();
            if (distanceCard != null) {
                cards.add(distanceCard);
            }
            if (showENGatesQuestion()) {
                this.enGatesCard.setPosition(hasFirstFilterItem ? 11 : MIN_HOTELS_FOR_DISTANCE_CARD);
                cards.add(this.enGatesCard);
            }
        }
        if (this.nearbyCitiesSearchCard.isVisible() && this.filters.getFilteredHotelsSize() > 0) {
            ItemPositionCalculator itemPositionCalculator = new ItemPositionCalculator();
            Set<Integer> cardsPositions = new HashSet(cards.size());
            for (Card card : cards) {
                cardsPositions.add(Integer.valueOf(card.position()));
            }
            this.nearbyCitiesSearchCard.position(itemPositionCalculator.countItems(this.filters.getFilteredOffers().size(), cardsPositions));
            if (enGatesCardAndNeighbourSearchCardStandTogether()) {
                cards.remove(this.enGatesCard);
            }
            cards.add(this.nearbyCitiesSearchCard);
        }
        return cards;
    }

    private boolean enGatesCardAndNeighbourSearchCardStandTogether() {
        return this.enGatesCard.position() != 0 && Math.abs(this.nearbyCitiesSearchCard.position() - this.enGatesCard.position()) == 1;
    }

    @Nullable
    private Card getCloseableDistanceCard() {
        if (this.filters.getFilteredHotelsSize() < MIN_HOTELS_FOR_DISTANCE_CARD || firstShouldBeDistanceCard() || !this.closeableDistanceCard.isEnabled()) {
            return null;
        }
        this.closeableDistanceCard.setPosition(DISTANCE_CARD_POSITION);
        return this.closeableDistanceCard;
    }

    private boolean showENGatesQuestion() {
        return (isEnLocale() || this.preferences.areEnGatesAllowed() || !this.preferences.showEnGatesQuestionInResults()) ? false : true;
    }

    @Nullable
    private Card getTopFilterCard() {
        if (firstShouldBeDistanceCard()) {
            return this.firstPositionDistanceFilterCard;
        }
        if (firstShouldBeRatingCard()) {
            return this.ratingFilterCard;
        }
        if (firstShouldBePriceCard()) {
            return this.priceFilterCard;
        }
        return null;
    }

    public boolean hasFirstFilterItem() {
        return firstShouldBeDistanceCard() || firstShouldBeRatingCard() || firstShouldBePriceCard();
    }

    private boolean firstShouldBeDistanceCard() {
        return this.searchByLocation;
    }

    private boolean firstShouldBePriceCard() {
        return (this.searchByLocation || this.filters.getSortingCategory().getSortingItem().isPriceSorting() || !this.filters.getGeneralPage().getPriceFilterItem().hasValidData()) ? false : true;
    }

    private boolean firstShouldBeRatingCard() {
        return !this.searchByLocation && this.filters.getSortingCategory().getSortingItem().isPriceSorting() && this.filters.getGeneralPage().getRatingFilterItem().hasValidData();
    }

    private boolean isEnLocale() {
        return AndroidUtils.getLanguage().equals(Language.ENGLISH);
    }
}
