package com.hotellook.ui.screen.hotel.prices;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.api.data.SearchFormData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.HighlightsByRooms;
import com.hotellook.search.RoomTypes;
import com.hotellook.search.SearchParams;
import com.hotellook.utils.CompareUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RoomsAdapter extends Adapter<ViewHolder> {
    public static final int ROOM_TYPE_NOT_DEFINED = -1;
    private static final int TYPE_ROOM = 0;
    private static final int TYPE_SEARCH_FORM = 1;
    private Offer bestRoom;
    @Nullable
    DiscountsByRooms discounts;
    private final Set<Integer> expandedItems;
    private boolean hasSearchForm;
    @Nullable
    HighlightsByRooms highlights;
    private List<Offer> offers;
    private RoomTypes roomTypes;
    private final List<Integer> rooms;
    private final Map<Integer, List<Offer>> roomsPrices;
    private final SearchFormData searchFormData;
    private final SearchParams searchParams;

    /* renamed from: com.hotellook.ui.screen.hotel.prices.RoomsAdapter.1 */
    class C13341 implements RoomItemExpandCollapseListener {
        final /* synthetic */ int val$position;

        C13341(int i) {
            this.val$position = i;
        }

        public void onExpand() {
            RoomsAdapter.this.expandedItems.add(Integer.valueOf(this.val$position));
            RoomsAdapter.this.notifyItemChanged(this.val$position);
        }

        public void onCollapse() {
            RoomsAdapter.this.expandedItems.remove(Integer.valueOf(this.val$position));
            RoomsAdapter.this.notifyItemChanged(this.val$position);
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.prices.RoomsAdapter.2 */
    class C13352 implements Comparator<Integer> {
        C13352() {
        }

        public int compare(Integer lRoomId, Integer rRoomId) {
            return Double.compare(getPrice(lRoomId), getPrice(rRoomId));
        }

        private double getPrice(Integer roomId) {
            return ((Offer) ((List) RoomsAdapter.this.roomsPrices.get(roomId)).get(RoomsAdapter.TYPE_ROOM)).getPrice();
        }
    }

    public RoomsAdapter(SearchParams searchParams, SearchFormData searchFormData, @NonNull Set<Integer> expandedItems) {
        this.roomsPrices = new HashMap();
        this.rooms = new ArrayList();
        this.expandedItems = expandedItems;
        this.searchParams = searchParams;
        this.searchFormData = searchFormData;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ROOM /*0*/:
                return new RoomItemHolder(inflateRoomView(parent));
            case TYPE_SEARCH_FORM /*1*/:
                return new SearchFormItemHolder(inflateSearchFormOfEmbeddedListType(parent));
            default:
                throw new RuntimeException("type not known");
        }
    }

    public RoomOffersView inflateRoomView(ViewGroup parent) {
        return (RoomOffersView) LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.room_offers_view, parent, false);
    }

    private View inflateSearchFormOfEmbeddedListType(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.list_embedded_search_form, parent, false);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_SEARCH_FORM) {
            onBindSearchForm((SearchFormItemHolder) holder);
        } else {
            onBindRoom((RoomItemHolder) holder, position);
        }
    }

    private int getNonRegularTopItemsCount() {
        return this.hasSearchForm ? TYPE_SEARCH_FORM : TYPE_ROOM;
    }

    private void onBindRoom(RoomItemHolder roomHolder, int position) {
        Integer roomId = (Integer) this.rooms.get(position - getNonRegularTopItemsCount());
        RoomOffersView roomView = roomHolder.roomView;
        roomView.setData(this.searchParams, (List) this.roomsPrices.get(roomId), this.roomTypes.all(), this.bestRoom, isItemExpanded(position), this.discounts, this.highlights);
        roomView.setExpandCollapseListener(new C13341(position));
    }

    private void onBindSearchForm(SearchFormItemHolder searchFormHolder) {
        searchFormHolder.searchForm.setUpData(this.searchFormData, this.expandedItems.contains(Integer.valueOf(OffersFragment.SEARCH_FORM_ID)));
    }

    private boolean isItemExpanded(int position) {
        return this.expandedItems.contains(Integer.valueOf(position));
    }

    public int getItemViewType(int position) {
        if (this.hasSearchForm && position == 0) {
            return TYPE_SEARCH_FORM;
        }
        return TYPE_ROOM;
    }

    public int getItemCount() {
        return this.hasSearchForm ? this.rooms.size() + TYPE_SEARCH_FORM : this.rooms.size();
    }

    private void sortRoomsByBestPrice() {
        Collections.sort(this.rooms, new C13352());
    }

    private boolean fillRoomsList() {
        return this.rooms.addAll(this.roomsPrices.keySet());
    }

    private void sortRoomResultsLists() {
        for (List<Offer> roomResults : this.roomsPrices.values()) {
            Collections.sort(roomResults, CompareUtils.getComparatorByRoomPrice(this.discounts, this.highlights));
        }
    }

    private void fillRoomsResults() {
        for (Offer result : getHotelResults()) {
            Integer typeId = result.getInternalTypeId();
            if (typeId == null) {
                typeId = Integer.valueOf(ROOM_TYPE_NOT_DEFINED);
            }
            if (!this.roomsPrices.containsKey(typeId)) {
                this.roomsPrices.put(typeId, new ArrayList());
            }
            ((List) this.roomsPrices.get(typeId)).add(result);
        }
    }

    private List<Offer> getHotelResults() {
        return this.offers;
    }

    private void clearData() {
        this.roomsPrices.clear();
        this.rooms.clear();
    }

    public void setData(long hotelId, List<Offer> offers, RoomTypes roomTypes, boolean addSearchForm, DiscountsByRooms discounts, HighlightsByRooms highlights) {
        this.hasSearchForm = addSearchForm;
        this.offers = offers;
        this.roomTypes = roomTypes;
        this.discounts = discounts;
        this.highlights = highlights;
        clearData();
        fillRoomsResults();
        sortRoomResultsLists();
        fillRoomsList();
        sortRoomsByBestPrice();
        this.bestRoom = (Offer) ((List) this.roomsPrices.get(this.rooms.get(TYPE_ROOM))).get(TYPE_ROOM);
        expandSingleItem();
    }

    private void expandSingleItem() {
        if (this.expandedItems.isEmpty() && this.rooms.size() == TYPE_SEARCH_FORM && !this.hasSearchForm) {
            this.expandedItems.add(Integer.valueOf(TYPE_ROOM));
        }
    }
}
