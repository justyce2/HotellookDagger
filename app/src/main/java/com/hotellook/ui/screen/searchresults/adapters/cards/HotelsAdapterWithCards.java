package com.hotellook.ui.screen.searchresults.adapters.cards;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseIntArray;
import android.view.ViewGroup;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.ui.screen.searchresults.adapters.HotelsAdapter;
import com.hotellook.ui.screen.searchresults.adapters.itemsize.ItemLayoutParamsGenerator;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class HotelsAdapterWithCards extends Adapter<ViewHolder> {
    private final TreeMap<Integer, Card> cards;
    private final HotelsAdapter hotelsAdapter;
    private final ItemLayoutParamsGenerator itemLayoutParamsGenerator;
    private final ItemPositionCalculator itemPositionCalculator;

    public HotelsAdapterWithCards(HotelsAdapter hotelsAdapter) {
        this.cards = new TreeMap();
        this.hotelsAdapter = hotelsAdapter;
        this.itemPositionCalculator = new ItemPositionCalculator();
        this.itemLayoutParamsGenerator = hotelsAdapter.itemLayoutParamsGenerator();
    }

    public int getItemViewType(int position) {
        Card card = (Card) this.cards.get(Integer.valueOf(position));
        if (card != null) {
            return card.type();
        }
        return 1;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Card card = getCardByType(viewType);
        if (card != null) {
            return card.createViewHolder(parent);
        }
        return this.hotelsAdapter.onCreateViewHolder(parent, viewType);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        Card card = (Card) this.cards.get(Integer.valueOf(position));
        if (card != null) {
            card.onBindViewHolder(holder, position);
            if (this.itemLayoutParamsGenerator != null) {
                this.itemLayoutParamsGenerator.generateFullWidthItemsParams(holder.itemView);
                return;
            }
            return;
        }
        this.hotelsAdapter.onBindViewHolder(holder, position - this.itemPositionCalculator.countCardsBefore(position, this.cards.keySet()));
    }

    public int getItemCount() {
        return this.itemPositionCalculator.countItems(this.hotelsAdapter.getItemCount(), this.cards.keySet());
    }

    private Card getCardByType(int viewType) {
        for (Card card : this.cards.values()) {
            if (card != null && viewType == card.type()) {
                return card;
            }
        }
        return null;
    }

    public void update(List<HotelData> hotels, Map<Long, List<Offer>> searchResults, Map<Long, Offer> bestResults) {
        this.hotelsAdapter.update(hotels, searchResults, bestResults);
    }

    public TreeSet<Integer> updateCards(Collection<Card> actualCardsCollection) {
        Map<Integer, Card> actualCardsMap = new HashMap();
        TreeSet<Integer> indexesNotChanged = getIndexesOfNonChangedCards(actualCardsCollection, actualCardsMap);
        addOrReplaceNewCards(actualCardsCollection);
        removeNotActualCards(actualCardsMap);
        return indexesNotChanged;
    }

    @NonNull
    private TreeSet<Integer> getIndexesOfNonChangedCards(Collection<Card> actualCardsCollection, Map<Integer, Card> actualCards) {
        Set<Integer> cardsPositions = this.cards.keySet();
        for (Card card : actualCardsCollection) {
            actualCards.put(Integer.valueOf(card.position()), card);
        }
        TreeSet<Integer> indexesNotChanged = new TreeSet(cardsPositions);
        for (Integer cardPosition : cardsPositions) {
            if (!actualCards.containsKey(cardPosition)) {
                indexesNotChanged.remove(cardPosition);
            }
        }
        return indexesNotChanged;
    }

    private void removeNotActualCards(Map<Integer, Card> actualCards) {
        for (Integer index : calculateIndexesToRemove(actualCards)) {
            this.cards.remove(index);
        }
    }

    @NonNull
    private Set<Integer> calculateIndexesToRemove(Map<Integer, Card> actualCards) {
        Set<Integer> indexesToRemove = new HashSet();
        for (Entry<Integer, Card> entry : this.cards.entrySet()) {
            Card card = (Card) entry.getValue();
            int position = ((Integer) entry.getKey()).intValue();
            if (!(card == null || actualCards.containsKey(Integer.valueOf(position)))) {
                indexesToRemove.add(Integer.valueOf(position));
            }
        }
        return indexesToRemove;
    }

    private void addOrReplaceNewCards(Collection<Card> actualCardsCollection) {
        for (Card card : actualCardsCollection) {
            if (CardsUtils.differentCardTypes(card, (Card) this.cards.get(Integer.valueOf(card.position())))) {
                this.cards.put(Integer.valueOf(card.position()), card);
            }
        }
    }

    public SparseIntArray getPagersIndexes() {
        return this.hotelsAdapter.getPagersIndexes();
    }

    public void setPagersIndexes(SparseIntArray pageIndexes) {
        this.hotelsAdapter.setPagersIndexes(pageIndexes);
    }

    public void enablePrecache(int firstVisibleItemPosition, int lastVisibleItemPosition) {
        this.hotelsAdapter.enablePrecache(firstVisibleItemPosition, lastVisibleItemPosition);
    }

    public void disablePrecache() {
        this.hotelsAdapter.disablePrecache();
    }

    public void notifyCardsUpdate() {
        for (Card card : this.cards.values()) {
            card.notifyDataChanged();
        }
    }

    public void removeCard(int position) {
        this.cards.remove(Integer.valueOf(position));
    }
}
