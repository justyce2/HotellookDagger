package com.hotellook.ui.screen.searchresults.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.badges.Badge;
import com.hotellook.badges.Badges;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.currency.CurrencyFormatter;
import com.hotellook.currency.CurrencyRepository;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.search.Discounts;
import com.hotellook.search.Highlights;
import com.hotellook.search.Search;
import com.hotellook.ui.view.MarkerBadgesView;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.CompareUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ClusterView extends LinearLayout {
    public static final String EMPTY_VALUE = "\u2014";
    private Badges badges;
    private MarkerBadgesView badgesView;
    private int bottomPadding;
    private String currency;
    private CurrencyFormatter currencyFormatter;
    private CurrencyRepository currencyRepository;
    private int groupColorTxt;
    private int hotelColorTxt;
    private int leftPadding;
    private TextView price;
    private int rightPadding;
    private Search search;
    private int topPadding;

    public ClusterView(Context context) {
        this(context, null);
    }

    public ClusterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.currencyRepository = HotellookApplication.from(getContext()).getComponent().currencyRepository();
        this.currencyFormatter = new CurrencyFormatter(this.currencyRepository);
        this.search = getComponent().searchKeeper().lastSearchOrThrowException();
        this.badges = this.search.badges();
    }

    private HotellookComponent getComponent() {
        return HotellookApplication.from(getContext()).getComponent();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.price = (TextView) findViewById(C1178R.id.price);
        this.badgesView = (MarkerBadgesView) findViewById(C1178R.id.badges);
        this.leftPadding = getResources().getDimensionPixelSize(C1178R.dimen.results_marker_side_padding);
        this.rightPadding = this.leftPadding;
        this.topPadding = getResources().getDimensionPixelSize(C1178R.dimen.results_marker_top_padding);
        this.bottomPadding = getResources().getDimensionPixelSize(C1178R.dimen.results_marker_bottom_padding);
        this.hotelColorTxt = getResources().getColor(C1178R.color.results_map_marker_item_txt);
        this.groupColorTxt = getResources().getColor(C1178R.color.results_map_marker_group_txt);
    }

    public Bitmap getBitmap() {
        measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        Bitmap b = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
        Canvas c = new Canvas(b);
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        draw(c);
        return b;
    }

    public void setHotel(HotelData hotel, List<Offer> rooms) {
        setBackgroundResource(C1178R.drawable.white_marker);
        this.price.setTextColor(this.hotelColorTxt);
        Discounts discounts = this.search.searchData().discounts();
        Highlights highlights = this.search.searchData().highlights();
        if (rooms == null || rooms.size() <= 0) {
            this.price.setText(EMPTY_VALUE);
        } else {
            long id = hotel.getId();
            this.price.setText(this.currencyFormatter.formatPrice(((Offer) Collections.min(rooms, CompareUtils.getComparatorByRoomPrice(discounts.get(id), highlights.get(id)))).getPrice(), this.currency));
        }
        List<Badge> badges = this.badges.getBadges(hotel);
        if (badges.size() > 0) {
            this.badgesView.setVisibility(0);
            this.badgesView.setData(true, badges);
        } else {
            this.badgesView.setVisibility(8);
        }
        setPaddings();
    }

    public void setGroup(Collection<HotelData> hotels, Map<Long, List<Offer>> offers) {
        setBackgroundResource(C1178R.drawable.green_marker);
        this.price.setTextColor(this.groupColorTxt);
        int hotelsWithOffersCount = countHotelsWithOffers(hotels, offers);
        this.price.setText(hotelsWithOffersCount == 0 ? EMPTY_VALUE : String.valueOf(hotelsWithOffersCount));
        List<Badge> badges = this.badges.getBadges((Collection) hotels);
        if (badges.size() > 0) {
            this.badgesView.setVisibility(0);
            this.badgesView.setData(false, badges);
        } else {
            this.badgesView.setVisibility(8);
        }
        setPaddings();
    }

    private int countHotelsWithOffers(Collection<HotelData> hotels, Map<Long, List<Offer>> offers) {
        int hotelsWithOffersCount = 0;
        for (HotelData hotel : hotels) {
            if (CollectionUtils.isNotEmpty((List) offers.get(Long.valueOf(hotel.getId())))) {
                hotelsWithOffersCount++;
            }
        }
        return hotelsWithOffersCount;
    }

    private void setPaddings() {
        setPadding(this.leftPadding, this.topPadding, this.rightPadding, this.bottomPadding);
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
