package com.hotellook.ui.screen.searchresults;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.badges.Badge;
import com.hotellook.badges.badgeviewgenerator.BadgeViewGenerator;
import com.hotellook.common.OfferUtils;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Discount;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.currency.CurrencyFormatter;
import com.hotellook.currency.CurrencyRepository;
import com.hotellook.db.data.FavoriteHotelDataEntity;
import com.hotellook.events.ImageShowedEvent;
import com.hotellook.events.OpenHotelDetailsEvent;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.HighlightsByRooms;
import com.hotellook.ui.screen.hotel.ratingcolorizer.BackgroundColorApplier;
import com.hotellook.ui.screen.hotel.ratingcolorizer.RatingColorizer;
import com.hotellook.ui.screen.searchresults.adapters.ImagePagerAdapter;
import com.hotellook.ui.screen.searchresults.adapters.ImagePagerAdapter.OnItemClickListener;
import com.hotellook.ui.screen.searchresults.map.ClusterView;
import com.hotellook.ui.view.DeactivatableViewPager;
import com.hotellook.ui.view.FlowLayout;
import com.hotellook.utils.CompareUtils;
import com.hotellook.utils.Size;
import com.hotellook.utils.ValueFormat;
import com.hotellook.utils.sdk.OnPageChangeListenerAdapter;
import java.util.Collections;
import java.util.List;

public class SearchResultsItemView extends RelativeLayout {
    private FlowLayout badgesLayout;
    private CurrencyFormatter currencyFormatter;
    private CurrencyRepository currencyRepository;
    private TextView debug;
    private View discountContainer;
    private FavoriteHotelDataEntity favoriteHotelData;
    private HotelData hotelData;
    private ImagePagerAdapter imagePagerAdapter;
    private Size imageSize;
    @Nullable
    private DeactivatableViewPager imagesPager;
    private TextView oldPrice;
    private boolean pagingDisabled;
    private int positionInList;
    private TextView price;
    private LinearLayout priceContainer;
    private TextView rate;
    private RatingColorizer rateColorized;
    private List<Offer> results;
    private TextView specialOffer;
    private TextView specialPrice;
    private RatingBar stars;
    private TextView title;
    private View touchable;
    private TextView tvNights;

    public interface OnImageSelectedListener {
        void onImageSelected(int i);
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.SearchResultsItemView.1 */
    class C13641 implements OnItemClickListener {
        C13641() {
        }

        public void onItemClicked(int index) {
            SearchResultsItemView.this.onItemClick(index);
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.SearchResultsItemView.2 */
    class C13652 extends OnPageChangeListenerAdapter {
        C13652() {
        }

        public void onPageSelected(int position) {
            HotellookApplication.eventBus().post(new ImageShowedEvent(SearchResultsItemView.this.getHotelId(), position));
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.SearchResultsItemView.3 */
    class C13663 extends MonkeySafeClickListener {
        C13663() {
        }

        public void onSafeClick(View v) {
            SearchResultsItemView.this.onItemClick(0);
        }
    }

    class AdapterNotifier implements OnPageChangeListener {
        private final OnImageSelectedListener listener;
        private int prevPosition;

        public AdapterNotifier(OnImageSelectedListener listener) {
            this.listener = listener;
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (Math.abs(positionOffsetPixels) > 0) {
                SearchResultsItemView.this.imagePagerAdapter.setImageLoadingEnabled(true);
            }
        }

        public void onPageSelected(int position) {
            if (this.listener != null) {
                this.listener.onImageSelected(position);
            }
            SearchResultsItemView.this.imagePagerAdapter.onItemSelected(position, position > this.prevPosition);
            this.prevPosition = position;
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    public SearchResultsItemView(Context context) {
        this(context, null);
    }

    public SearchResultsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    private void setUp() {
        this.rateColorized = new RatingColorizer(new BackgroundColorApplier(), getContext().getResources());
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            this.currencyRepository = ((HotellookApplication) getContext().getApplicationContext()).getComponent().currencyRepository();
            this.currencyFormatter = new CurrencyFormatter(this.currencyRepository);
            this.discountContainer = findViewById(C1178R.id.discount_container);
            this.priceContainer = (LinearLayout) findViewById(C1178R.id.price_container);
            this.specialOffer = (TextView) findViewById(C1178R.id.spec_offer);
            this.touchable = findViewById(C1178R.id.sr_touchable);
            this.title = (TextView) findViewById(C1178R.id.name);
            this.tvNights = (TextView) findViewById(C1178R.id.nights);
            this.rate = (TextView) findViewById(C1178R.id.rate);
            this.price = (TextView) findViewById(C1178R.id.price);
            this.specialPrice = (TextView) findViewById(C1178R.id.special_price);
            this.imagesPager = (DeactivatableViewPager) findViewById(C1178R.id.images_pager);
            if (this.imagesPager != null) {
                this.imagesPager.setOffscreenPageLimit(1);
            }
            this.badgesLayout = (FlowLayout) findViewById(C1178R.id.badges);
            this.debug = (TextView) findViewById(C1178R.id.debug);
            this.stars = (RatingBar) findViewById(C1178R.id.stars);
            this.oldPrice = (TextView) findViewById(C1178R.id.old_price);
        }
    }

    public void setBadges(List<Badge> badges, BadgeViewGenerator badgeGenerator) {
        if (badges.size() != 0) {
            this.badgesLayout.setVisibility(0);
            for (Badge badge : badges) {
                this.badgesLayout.addView(badgeGenerator.generateView(badge.text, badge.color));
            }
        }
    }

    public void setBadgesTopMargin(int margin) {
        LayoutParams params = (LayoutParams) this.badgesLayout.getLayoutParams();
        params.setMargins(params.leftMargin, margin, params.rightMargin, params.bottomMargin);
    }

    public void setData(List<Offer> results, HotelData hotel, Offer bestPrice, int nights, int firstImageIdx, Size imageSize, OnImageSelectedListener listener, String currency, DiscountsByRooms discounts, HighlightsByRooms highlights) {
        this.favoriteHotelData = null;
        this.hotelData = hotel;
        this.results = results;
        this.imageSize = imageSize;
        int rating = hotel.getRating();
        int imgsCount = hotel.getPhotoCount();
        this.title.setText(hotel.getName());
        this.stars.setProgress(hotel.getStars());
        setUpViews(firstImageIdx, listener, bestPrice, nights, rating, imgsCount, currency, discounts, highlights);
    }

    public void setData(FavoriteHotelDataEntity hotel, @Nullable List<Offer> results, int firstImageIdx, Size imageSize, OnImageSelectedListener listener, String currency, DiscountsByRooms discounts, HighlightsByRooms highlights) {
        this.hotelData = null;
        this.favoriteHotelData = hotel;
        this.results = results;
        this.imageSize = imageSize;
        Offer bestPrice = null;
        if (results != null) {
            bestPrice = getBestPrice(results, discounts, highlights);
        }
        int nights = hotel.getNights();
        int rating = hotel.getRating();
        int imgsCount = hotel.getPhotoCount();
        this.title.setText(hotel.getHotelName());
        this.stars.setProgress(hotel.getStars());
        setUpViews(firstImageIdx, listener, bestPrice, nights, rating, imgsCount, currency, discounts, highlights);
    }

    private void setUpViews(int firstImageIdx, OnImageSelectedListener listener, Offer bestPrice, int nights, int rating, int imgsCount, String currency, DiscountsByRooms discounts, HighlightsByRooms highlights) {
        String priceTxt;
        if (rating > 0) {
            this.rate.setVisibility(0);
            this.rate.setText(ValueFormat.ratingToString(rating));
            this.rateColorized.apply(this.rate, rating);
        } else {
            this.rate.setVisibility(8);
        }
        if (bestPrice != null) {
            priceTxt = this.currencyFormatter.formatPrice(bestPrice.getPrice(), currency);
        } else {
            priceTxt = ClusterView.EMPTY_VALUE;
        }
        this.price.setText(priceTxt);
        this.specialPrice.setText(priceTxt);
        this.tvNights.setVisibility(0);
        this.oldPrice.setVisibility(8);
        this.tvNights.setVisibility(8);
        this.priceContainer.setBackgroundColor(getResources().getColor(C1178R.color.gray_464748_pct_80));
        this.specialOffer.setVisibility(8);
        boolean hasSpecialOffer = OfferUtils.hasSpecialOffer(bestPrice, highlights);
        if (OfferUtils.hasValidDiscount(bestPrice, discounts, highlights)) {
            setUpViewOnDiscount(nights, currency, discounts.get(bestPrice));
        } else {
            if (this.discountContainer != null) {
                this.discountContainer.setVisibility(8);
            }
            if (hasSpecialOffer) {
                setUpViewOnSpecialOffer();
            } else {
                this.tvNights.setVisibility(0);
                this.tvNights.setText(getContext().getResources().getQuantityString(C1178R.plurals.nights, nights, new Object[]{Integer.valueOf(nights)}));
            }
        }
        if (hasSpecialOffer) {
            this.price.setVisibility(8);
            this.specialPrice.setVisibility(0);
        } else {
            this.price.setVisibility(0);
            this.specialPrice.setVisibility(8);
        }
        if (imgsCount == 0 || this.imagesPager == null) {
            setUpNoImagesState();
        } else {
            setUpImagesPager(firstImageIdx, listener);
        }
        cleanBadgesLayout();
    }

    private void setUpViewOnSpecialOffer() {
        this.specialOffer.setVisibility(0);
        this.priceContainer.setBackgroundColor(getResources().getColor(C1178R.color.red_B3D0021A));
    }

    private void setUpViewOnDiscount(int nights, String currency, Discount discount) {
        this.oldPrice.setVisibility(0);
        this.oldPrice.setText(this.currencyFormatter.formatPrice(discount.getOldPrice(), currency));
        this.oldPrice.setPaintFlags(this.oldPrice.getPaintFlags() | 16);
        this.tvNights.setVisibility(0);
        this.tvNights.setText(getContext().getResources().getQuantityString(C1178R.plurals.for_nights, nights, new Object[]{Integer.valueOf(nights)}));
    }

    private void cleanBadgesLayout() {
        this.badgesLayout.removeAllViews();
        this.badgesLayout.setVisibility(8);
    }

    private Offer getBestPrice(@NonNull List<Offer> results, DiscountsByRooms discounts, HighlightsByRooms highlights) {
        return (Offer) Collections.min(results, CompareUtils.getComparatorByRoomPrice(discounts, highlights));
    }

    private void fillDebugData(HotelData hotelData, Offer bestOffer) {
        if (this.debug != null) {
            this.debug.setVisibility(8);
        }
    }

    private void setUpImagesPager(int firstImageIdx, OnImageSelectedListener listener) {
        boolean z = false;
        if (this.imagesPager != null) {
            this.touchable.setVisibility(8);
            this.imagesPager.setVisibility(0);
            this.imagesPager.setPagingEnabled(false);
            this.imagePagerAdapter = new ImagePagerAdapter(getContext().getApplicationContext(), this.imageSize, this.imagesPager);
            this.imagesPager.setAdapter(this.imagePagerAdapter);
            if (this.pagingDisabled) {
                this.imagePagerAdapter.disablePaging();
            }
            this.imagePagerAdapter.setActualData(getHotelId(), getPhotoCount());
            this.imagePagerAdapter.setOnItemClickListener(new C13641());
            this.imagesPager.clearOnPageChangeListeners();
            this.imagesPager.setCurrentItem(firstImageIdx, false);
            ImagePagerAdapter imagePagerAdapter = this.imagePagerAdapter;
            if (firstImageIdx > 0) {
                z = true;
            }
            imagePagerAdapter.setImageLoadingEnabled(z);
            this.imagePagerAdapter.notifyDataSetChanged();
            this.imagesPager.addOnPageChangeListener(new AdapterNotifier(listener));
            this.imagesPager.addOnPageChangeListener(new C13652());
        }
    }

    private int getPhotoCount() {
        return this.hotelData != null ? this.hotelData.getPhotoCount() : this.favoriteHotelData.getPhotoCount();
    }

    private long getHotelId() {
        return this.hotelData != null ? this.hotelData.getId() : this.favoriteHotelData.getHotelId();
    }

    private void setUpNoImagesState() {
        this.touchable.setVisibility(0);
        if (this.imagesPager != null) {
            this.imagesPager.setVisibility(8);
            this.imagesPager.setOnPageChangeListener(null);
        }
        this.touchable.setOnClickListener(new C13663());
    }

    public void disableViewpager() {
        if (this.imagesPager != null) {
            this.imagesPager.setPagingEnabled(false);
        }
        if (this.imagePagerAdapter != null) {
            this.imagePagerAdapter.disablePaging();
        }
        this.pagingDisabled = true;
    }

    private void onItemClick(int photoIdx) {
        if (this.hotelData == null) {
            HotellookApplication.eventBus().post(new OpenHotelDetailsEvent(this.favoriteHotelData, this.results, this.imageSize, photoIdx, this.positionInList));
            return;
        }
        HotellookApplication.eventBus().post(new OpenHotelDetailsEvent(this.hotelData, this.results, this.imageSize, photoIdx, this.positionInList));
    }

    public void hidePriceViews() {
        this.price.setVisibility(8);
        this.tvNights.setVisibility(8);
    }

    public void setPositionInList(int positionInList) {
        this.positionInList = positionInList;
    }

    public void setFavoriteBadge(BadgeViewGenerator badgeGenerator) {
        this.badgesLayout.setVisibility(0);
        this.badgesLayout.addView(badgeGenerator.generateFavoriteBadge(), 0);
    }
}
