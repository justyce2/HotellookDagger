package com.hotellook.ui.screen.searchresults.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.request.ImageRequest;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.badges.Badge;
import com.hotellook.badges.Badges;
import com.hotellook.badges.badgeviewgenerator.BadgeViewGenerator;
import com.hotellook.common.OfferUtils;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.db.SearchDestinationFavoritesCache;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.search.Discounts;
import com.hotellook.search.Highlights;
import com.hotellook.ui.screen.gallery.ViewSizeCalculator;
import com.hotellook.ui.screen.searchresults.HotelImageController;
import com.hotellook.ui.screen.searchresults.SearchResultsItemView;
import com.hotellook.ui.screen.searchresults.SearchResultsItemView.OnImageSelectedListener;
import com.hotellook.ui.screen.searchresults.adapters.itemsize.ItemLayoutParamsGenerator;
import com.hotellook.ui.screen.searchresults.adapters.itemsize.ListItemImageParams;
import com.hotellook.ui.view.image.imagehierarchy.PortraitDefaultHierarchyFactory;
import com.hotellook.utils.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HotelsAdapter extends Adapter<ViewHolder> {
    private final Badges badges;
    private Map<Long, Offer> bestResultDataCache;
    private final Activity context;
    private String currency;
    private final int defaultBadgesMargin;
    private final Discounts discounts;
    private final Highlights highlights;
    private List<HotelData> hotelData;
    private final Size imageSize;
    @Nullable
    private ItemLayoutParamsGenerator itemLayoutParamsGenerator;
    private final int nightsCount;
    private SparseIntArray pagersIndexes;
    private boolean precacheEnabled;
    private int prevBindIndex;
    private final SearchDestinationFavoritesCache searchDestinationFavoritesCache;
    private Map<Long, List<Offer>> searchResult;

    /* renamed from: com.hotellook.ui.screen.searchresults.adapters.HotelsAdapter.1 */
    class C13671 implements OnImageSelectedListener {
        final /* synthetic */ int val$itemPosition;

        C13671(int i) {
            this.val$itemPosition = i;
        }

        public void onImageSelected(int position) {
            HotelsAdapter.this.pagersIndexes.put(this.val$itemPosition, position);
        }
    }

    public static final class Builder {
        private Badges badges;
        private Map<Long, Offer> bestResultDataCache;
        private Activity context;
        private String currency;
        private Discounts discounts;
        private Highlights highlights;
        private List<HotelData> hotelData;
        private int nightsCount;
        private Map<Long, List<Offer>> searchResult;

        private Builder() {
        }

        public Builder activity(Activity val) {
            this.context = val;
            return this;
        }

        public Builder nightsCount(int val) {
            this.nightsCount = val;
            return this;
        }

        public Builder hotelData(List<HotelData> val) {
            this.hotelData = val;
            return this;
        }

        public Builder searchResult(Map<Long, List<Offer>> val) {
            this.searchResult = val;
            return this;
        }

        public Builder bestResultDataCache(Map<Long, Offer> val) {
            this.bestResultDataCache = val;
            return this;
        }

        public Builder currency(String val) {
            this.currency = val;
            return this;
        }

        public Builder discounts(Discounts discounts) {
            this.discounts = discounts;
            return this;
        }

        public Builder highlights(Highlights highlights) {
            this.highlights = highlights;
            return this;
        }

        public String toString() {
            return "Builder{context=" + this.context + ", hotelData=" + this.hotelData + ", searchResult=" + this.searchResult + ", bestResultDataCache=" + this.bestResultDataCache + ", nightsCount=" + this.nightsCount + ", currency='" + this.currency + '\'' + ", badges=" + this.badges + ", discounts=" + this.discounts + ", highlights=" + this.highlights + '}';
        }

        public HotelsAdapter build() {
            if (this.context != null && this.hotelData != null && this.searchResult != null && this.bestResultDataCache != null && this.nightsCount >= 1 && !TextUtils.isEmpty(this.currency) && this.discounts != null && this.highlights != null) {
                return new HotelsAdapter(this.hotelData, this.searchResult, this.bestResultDataCache, this.nightsCount, this.currency, this.badges, this.discounts, this.highlights, null);
            }
            throw new IllegalArgumentException("Some values are not set " + toString());
        }

        public Builder badges(Badges badges) {
            this.badges = badges;
            return this;
        }
    }

    private HotelsAdapter(@NonNull Activity context, @NonNull List<HotelData> hotelData, @NonNull Map<Long, List<Offer>> searchResult, @NonNull Map<Long, Offer> bestPrices, int nightsCount, String currency, @NonNull Badges badges, Discounts discounts, Highlights highlights) {
        this.precacheEnabled = true;
        this.context = context;
        this.currency = currency;
        this.hotelData = hotelData;
        this.searchResult = searchResult;
        this.bestResultDataCache = bestPrices;
        this.nightsCount = nightsCount;
        this.discounts = discounts;
        this.highlights = highlights;
        this.pagersIndexes = new SparseIntArray();
        this.badges = badges;
        this.imageSize = new ViewSizeCalculator(context).calculatePagerImageSize();
        this.itemLayoutParamsGenerator = ListItemLayoutFactory.createHotelImageSize(context, this.imageSize);
        this.defaultBadgesMargin = context.getResources().getDimensionPixelOffset(C1178R.dimen.top_badges_margin);
        this.searchDestinationFavoritesCache = getComponent().getLocationFavoritesCache();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private HotellookComponent getComponent() {
        return HotellookApplication.getApp().getComponent();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.view_search_hotel_list_item, parent, false);
        item.getLayoutParams().width = this.imageSize.getWidth();
        item.getLayoutParams().height = this.imageSize.getHeight();
        return new HotelViewHolder(item);
    }

    public void onBindViewHolder(ViewHolder viewholder, int itemPosition) {
        Size size;
        HotelData hotelData = (HotelData) this.hotelData.get(itemPosition);
        long id = hotelData.getId();
        Offer offer = (Offer) this.bestResultDataCache.get(Long.valueOf(id));
        List<Badge> badges = this.badges.getBadges(hotelData);
        ListItemImageParams listItemImageParams = null;
        if (this.itemLayoutParamsGenerator != null) {
            listItemImageParams = this.itemLayoutParamsGenerator.generateHotelItemParams(itemPosition, this.hotelData.size());
        }
        HotelViewHolder holder = (HotelViewHolder) viewholder;
        SearchResultsItemView searchResultsItemView = holder.searchResultsItemView;
        List list = (List) this.searchResult.get(Long.valueOf(id));
        int i = this.nightsCount;
        int i2 = this.pagersIndexes.get(itemPosition);
        if (listItemImageParams != null) {
            size = listItemImageParams.imageSize;
        } else {
            size = this.imageSize;
        }
        searchResultsItemView.setData(list, hotelData, offer, i, i2, size, new C13671(itemPosition), this.currency, this.discounts.get(id), this.highlights.get(id));
        BadgeViewGenerator badgeViewGenerator = BadgeViewGenerator.withLargeView(this.context);
        if (this.searchDestinationFavoritesCache.contains(id)) {
            holder.searchResultsItemView.setFavoriteBadge(badgeViewGenerator);
        }
        holder.searchResultsItemView.setBadges(badges, badgeViewGenerator);
        holder.searchResultsItemView.setPositionInList(itemPosition + 1);
        precacheNext(itemPosition, itemPosition > this.prevBindIndex, listItemImageParams != null ? listItemImageParams.imageSize : this.imageSize);
        this.prevBindIndex = itemPosition;
        holder.discountContainer.setVisibility(8);
        if (OfferUtils.hasValidDiscount(offer, this.discounts.get(id), this.highlights.get(id))) {
            holder.discountContainer.setVisibility(0);
            holder.discountTitle.setText(((int) this.discounts.get(id).get(offer).getChangePercentage()) + "%");
        }
        holder.searchResultsItemView.setBadgesTopMargin(this.defaultBadgesMargin);
        if (listItemImageParams != null) {
            viewholder.itemView.setLayoutParams(listItemImageParams.layoutParams);
        }
    }

    @Nullable
    public ItemLayoutParamsGenerator itemLayoutParamsGenerator() {
        return this.itemLayoutParamsGenerator;
    }

    private void precacheNext(int position, boolean movingDown, Size size) {
        if (this.precacheEnabled) {
            int targetPosition = position;
            if (movingDown) {
                if (position < this.hotelData.size() - 4) {
                    targetPosition += 3;
                }
            } else if (position > 3) {
                targetPosition -= 3;
            }
            if (targetPosition != position) {
                Fresco.getImagePipeline().prefetchToBitmapCache(ImageRequest.fromUri(new HotelImageController(this.pagersIndexes.get(targetPosition), size, new PortraitDefaultHierarchyFactory(this.context.getResources())).getImageUrl(((HotelData) this.hotelData.get(targetPosition)).getId())), null);
            }
        }
    }

    public int getItemCount() {
        return this.hotelData.size();
    }

    public void enablePrecache(int firstVisibleIndex, int lastVisibleIndex) {
        if (!this.precacheEnabled) {
            boolean movingDown = lastVisibleIndex >= this.prevBindIndex;
            this.precacheEnabled = true;
            for (int i = firstVisibleIndex; i < lastVisibleIndex; i++) {
                Size size;
                ListItemImageParams listItemImageParams = null;
                if (this.itemLayoutParamsGenerator != null) {
                    listItemImageParams = this.itemLayoutParamsGenerator.generateHotelItemParams(i, this.hotelData.size());
                }
                if (listItemImageParams != null) {
                    size = listItemImageParams.imageSize;
                } else {
                    size = this.imageSize;
                }
                precacheNext(i, movingDown, size);
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

    public void update(List<HotelData> hotels, Map<Long, List<Offer>> searchResults, Map<Long, Offer> bestResults) {
        this.hotelData = hotels;
        this.searchResult = searchResults;
        this.bestResultDataCache = bestResults;
    }

    public void update(int visibleItemsCount, List<HotelData> hotels, Map<Long, List<Offer>> searchResults, Map<Long, Offer> bestResults) {
        this.hotelData = hotels;
        this.searchResult = searchResults;
        this.bestResultDataCache = bestResults;
        if (getItemCount() == 0) {
            notifyDataSetChanged();
            return;
        }
        List<Integer> diff = calculateIndexesToRemove(visibleItemsCount, hotels);
        for (int i = diff.size() - 1; i >= 0; i--) {
            notifyItemRemoved(((Integer) diff.get(i)).intValue());
        }
    }

    private List<Integer> calculateIndexesToRemove(int visibleItemsCount, List<HotelData> hotels) {
        List<Integer> diff = new ArrayList();
        for (int i = 0; i < Math.min(this.hotelData.size(), visibleItemsCount); i++) {
            HotelData currentHotel = (HotelData) this.hotelData.get(i);
            HotelData filtered = (HotelData) hotels.get(i);
            if (filtered == null || currentHotel.getId() != filtered.getId()) {
                diff.add(Integer.valueOf(i));
            }
        }
        return diff;
    }
}
