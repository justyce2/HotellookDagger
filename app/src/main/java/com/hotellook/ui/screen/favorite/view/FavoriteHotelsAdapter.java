package com.hotellook.ui.screen.favorite.view;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.request.ImageRequest;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.api.data.DestinationType;
import com.hotellook.common.OfferUtils;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.currency.CurrencyRepository;
import com.hotellook.db.data.FavoriteHotelDataEntity;
import com.hotellook.search.Discounts;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.Highlights;
import com.hotellook.search.HighlightsByRooms;
import com.hotellook.search.KidsSerializer;
import com.hotellook.search.Search;
import com.hotellook.search.SearchKeeper;
import com.hotellook.search.SearchParams;
import com.hotellook.search.SearchParams.Builder;
import com.hotellook.search.ServerDateFormatter;
import com.hotellook.ui.screen.gallery.ViewSizeCalculator;
import com.hotellook.ui.screen.searchresults.HotelImageController;
import com.hotellook.ui.screen.searchresults.SearchResultsItemView;
import com.hotellook.ui.screen.searchresults.SearchResultsItemView.OnImageSelectedListener;
import com.hotellook.ui.screen.searchresults.adapters.HotelViewHolder;
import com.hotellook.ui.screen.searchresults.adapters.itemsize.ItemLayoutParamsGenerator;
import com.hotellook.ui.screen.searchresults.adapters.itemsize.ListItemImageParams;
import com.hotellook.ui.view.image.imagehierarchy.PortraitDefaultHierarchyFactory;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.CommonPreferences;
import com.hotellook.utils.CompareUtils;
import com.hotellook.utils.LocationUtils;
import com.hotellook.utils.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;

public class FavoriteHotelsAdapter extends Adapter<ViewHolder> {
    private boolean areEnGatesAllowed;
    private final CurrencyRepository currencyRepository;
    private final int defaultBadgesMargin;
    @Nullable
    private Discounts discounts;
    private List<FavoriteHotelDataEntity> favoriteHotels;
    private final View footer;
    private final View header;
    @Nullable
    private Highlights highlights;
    private final Size imageSize;
    @Nullable
    private ItemLayoutParamsGenerator itemLayoutParamsGenerator;
    private SparseIntArray pagersIndexes;
    private boolean precacheEnabled;
    private int prevBindIndex;
    private final Resources resources;
    private final Search search;

    /* renamed from: com.hotellook.ui.screen.favorite.view.FavoriteHotelsAdapter.1 */
    class C12521 implements OnImageSelectedListener {
        final /* synthetic */ int val$index;

        C12521(int i) {
            this.val$index = i;
        }

        public void onImageSelected(int position) {
            FavoriteHotelsAdapter.this.pagersIndexes.put(this.val$index, position);
        }
    }

    /* renamed from: com.hotellook.ui.screen.favorite.view.FavoriteHotelsAdapter.2 */
    class C12532 implements OnImageSelectedListener {
        final /* synthetic */ int val$index;

        C12532(int i) {
            this.val$index = i;
        }

        public void onImageSelected(int position) {
            FavoriteHotelsAdapter.this.pagersIndexes.put(this.val$index, position);
        }
    }

    @Inject
    public FavoriteHotelsAdapter(Context context, CommonPreferences commonPreferences, @Nullable ItemLayoutParamsGenerator itemLayoutParamsGenerator, SearchKeeper searchKeeper) {
        this.precacheEnabled = true;
        this.resources = context.getResources();
        this.search = searchKeeper.lastSearch();
        if (!(this.search == null || this.search.searchData() == null)) {
            this.discounts = this.search.searchData().discounts();
            this.highlights = this.search.searchData().highlights();
        }
        this.favoriteHotels = new ArrayList();
        this.areEnGatesAllowed = commonPreferences.areEnGatesAllowed();
        Collections.sort(this.favoriteHotels, getFavoriteHotelsComparator());
        this.defaultBadgesMargin = this.resources.getDimensionPixelOffset(C1178R.dimen.top_badges_margin);
        this.currencyRepository = HotellookApplication.from(context).getComponent().currencyRepository();
        this.pagersIndexes = new SparseIntArray();
        this.footer = new View(context);
        this.footer.setLayoutParams(new LayoutParams(-1, AndroidUtils.hasNavBar(context) ? AndroidUtils.getNavBarHeight(context) : 0));
        this.header = new View(context);
        this.header.setLayoutParams(new LayoutParams(-1, context.getResources().getDimensionPixelOffset(C1178R.dimen.sr_list_top_padding)));
        this.imageSize = new ViewSizeCalculator(context).calculatePagerImageSize();
        this.itemLayoutParamsGenerator = itemLayoutParamsGenerator;
    }

    private Comparator<FavoriteHotelDataEntity> getFavoriteHotelsComparator() {
        return new FavoriteHotelDataEntityComparator();
    }

    public void setData(List<FavoriteHotelDataEntity> favoriteHotels) {
        this.favoriteHotels = favoriteHotels;
        this.pagersIndexes.clear();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotelViewHolder(LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.view_search_hotel_list_item, parent, false));
    }

    public void onBindViewHolder(ViewHolder viewholder, int itemPosition) {
        Size size;
        int index = itemPosition;
        HotelViewHolder holder = (HotelViewHolder) viewholder;
        FavoriteHotelDataEntity hotel = (FavoriteHotelDataEntity) this.favoriteHotels.get(index);
        ServerDateFormatter dateFormatter = new ServerDateFormatter();
        SearchParams pseudoSearchParams = new Builder().locationId(hotel.getLocationId()).hotelId(hotel.getHotelId()).checkIn(dateFormatter.parse(hotel.getCheckIn())).checkOut(dateFormatter.parse(hotel.getCheckOut())).adults(hotel.getAdults()).kids(KidsSerializer.parse(hotel.getChildren())).location(LocationUtils.from(hotel.getLocation())).destinationName(hotel.getLocationName()).currency(getCurrencyCode()).destinationType(DestinationType.HOTEL).language(AndroidUtils.getLanguage()).allowEnGates(this.areEnGatesAllowed).build();
        List<Offer> cachedOffers = null;
        if (!(this.search == null || this.search.searchData() == null)) {
            cachedOffers = (List) this.search.searchData().offers().all().get(Long.valueOf(hotel.getHotelId()));
        }
        Offer bestResult = null;
        if (CollectionUtils.isNotEmpty(cachedOffers)) {
            bestResult = findBestOffer(hotel, cachedOffers);
        }
        ListItemImageParams listItemImageParams = null;
        if (this.itemLayoutParamsGenerator != null) {
            listItemImageParams = this.itemLayoutParamsGenerator.generateHotelItemParams(index, this.favoriteHotels.size());
        }
        SearchResultsItemView searchResultsItemView = holder.searchResultsItemView;
        HotelData toHotelData = hotel.toHotelData();
        int nights = hotel.getNights();
        int i = this.pagersIndexes.get(index);
        if (listItemImageParams != null) {
            size = listItemImageParams.imageSize;
        } else {
            size = this.imageSize;
        }
        searchResultsItemView.setData(cachedOffers, toHotelData, bestResult, nights, i, size, new C12521(index), pseudoSearchParams.currency(), null, null);
        if (cachedOffers == null || cachedOffers.size() == 0) {
            holder.searchResultsItemView.hidePriceViews();
        }
        holder.searchResultsItemView.setData(hotel, cachedOffers, this.pagersIndexes.get(index), this.imageSize, new C12532(index), pseudoSearchParams.currency(), discount(hotel), highlight(hotel));
        holder.searchResultsItemView.setPositionInList(index + 1);
        precacheNext(itemPosition, itemPosition > this.prevBindIndex);
        this.prevBindIndex = itemPosition;
        if (OfferUtils.hasValidDiscount(bestResult, discount(hotel), highlight(hotel))) {
            holder.discountContainer.setVisibility(0);
            holder.discountTitle.setText(((int) this.discounts.get(hotel.getHotelId()).get(bestResult).getChangePercentage()) + "%");
        } else {
            holder.discountContainer.setVisibility(8);
        }
        holder.searchResultsItemView.setBadgesTopMargin(this.defaultBadgesMargin);
        if (listItemImageParams != null) {
            holder.itemView.setLayoutParams(listItemImageParams.layoutParams);
        }
    }

    private Offer findBestOffer(FavoriteHotelDataEntity hotel, List<Offer> cachedOffers) {
        HighlightsByRooms highlightsByRooms = null;
        DiscountsByRooms discountsByRooms = this.discounts != null ? this.discounts.get(hotel.getHotelId()) : null;
        if (this.highlights != null) {
            highlightsByRooms = this.highlights.get(hotel.getHotelId());
        }
        return (Offer) Collections.min(cachedOffers, CompareUtils.getComparatorByRoomPrice(discountsByRooms, highlightsByRooms));
    }

    @Nullable
    private HighlightsByRooms highlight(FavoriteHotelDataEntity hotel) {
        return this.highlights == null ? null : this.highlights.get(hotel.getHotelId());
    }

    @Nullable
    private DiscountsByRooms discount(FavoriteHotelDataEntity hotel) {
        return this.discounts == null ? null : this.discounts.get(hotel.getHotelId());
    }

    private String getCurrencyCode() {
        return this.search != null ? this.search.searchParams().currency() : this.currencyRepository.currencyCode();
    }

    private void precacheNext(int position, boolean movingDown) {
        if (this.precacheEnabled) {
            int targetPosition = position;
            if (movingDown) {
                if (position < this.favoriteHotels.size() - 4) {
                    targetPosition += 3;
                }
            } else if (position > 3) {
                targetPosition -= 3;
            }
            if (targetPosition != position) {
                Fresco.getImagePipeline().prefetchToBitmapCache(ImageRequest.fromUri(new HotelImageController(this.pagersIndexes.get(targetPosition), this.imageSize, new PortraitDefaultHierarchyFactory(this.resources)).getImageUrl(((FavoriteHotelDataEntity) this.favoriteHotels.get(targetPosition)).getHotelId())), null);
            }
        }
    }

    public int getItemCount() {
        return this.favoriteHotels.size();
    }

    public void enablePrecache(int firstVisibleIndex, int lastVisibleIndex) {
        if (!this.precacheEnabled) {
            boolean movingDown = lastVisibleIndex >= this.prevBindIndex;
            this.precacheEnabled = true;
            for (int i = firstVisibleIndex; i < lastVisibleIndex; i++) {
                precacheNext(i, movingDown);
            }
        }
    }

    public void disablePrecache() {
        this.precacheEnabled = false;
    }

    public SparseIntArray getPagersIndexes() {
        return this.pagersIndexes;
    }

    public void setPagersIndexes(SparseIntArray pagersIndexes) {
        this.pagersIndexes = pagersIndexes;
    }

    public boolean isDataDifferent(List<FavoriteHotelDataEntity> hotels) {
        return !hotels.equals(this.favoriteHotels);
    }
}
