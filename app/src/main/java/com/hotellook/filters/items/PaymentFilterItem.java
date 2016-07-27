package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.filters.FiltersSnapshot;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.items.criterion.Criterion;
import com.hotellook.filters.items.criterion.PaymentCriterion;
import com.hotellook.search.SearchData;
import com.hotellook.utils.CollectionUtils;
import java.util.List;
import java.util.Map;

public class PaymentFilterItem extends FilterItem<Offer> {
    private static final String TAG_CARD_NOT_REQUIRED;
    private static final String TAG_PAY_LATER;
    private static final String TAG_PAY_NOW;
    private boolean cardNotRequired;
    private int cardNotRequiredCount;
    private boolean payLater;
    private int payLaterCount;
    private boolean payNow;
    private int payNowCount;

    static {
        TAG_PAY_NOW = PaymentFilterItem.class.getSimpleName() + "_PAY_NOW";
        TAG_PAY_LATER = PaymentFilterItem.class.getSimpleName() + "_PAY_LATER";
        TAG_CARD_NOT_REQUIRED = PaymentFilterItem.class.getSimpleName() + "_CARD_NOT_REQUIRED";
    }

    public boolean isPayNow() {
        return this.payNow;
    }

    public void setPayNow(boolean payNow) {
        this.payNow = payNow;
    }

    public boolean isPayLater() {
        return this.payLater;
    }

    public void setPayLater(boolean payLater) {
        this.payLater = payLater;
    }

    public boolean isCardNotRequired() {
        return this.cardNotRequired;
    }

    public void setCardNotRequired(boolean cardNotRequired) {
        this.cardNotRequired = cardNotRequired;
    }

    public int getPayNowCount() {
        return this.payNowCount;
    }

    public int getPayLaterCount() {
        return this.payLaterCount;
    }

    public int getCardNotRequiredCount() {
        return this.cardNotRequiredCount;
    }

    @NonNull
    protected Criterion<Offer> getCriterion() {
        return new PaymentCriterion(this.payNow, this.payLater, this.cardNotRequired);
    }

    public void reset() {
        this.payNow = false;
        this.payLater = false;
        this.cardNotRequired = false;
    }

    public void setUp(SearchData searchData, PersistentFilters persistentFilters) {
        this.payNowCount = calculateCount(searchData, PaymentCriterion.payNow());
        this.payLaterCount = calculateCount(searchData, PaymentCriterion.payLater());
        this.cardNotRequiredCount = calculateCount(searchData, PaymentCriterion.cardNotRequired());
    }

    public boolean inDefaultState() {
        return (this.payNow || this.payLater || this.cardNotRequired) ? false : true;
    }

    public int hashCode() {
        return _hashCode();
    }

    public int _hashCode() {
        int result;
        int i;
        int i2 = 1;
        if (this.payNow) {
            result = 1;
        } else {
            result = 0;
        }
        int i3 = result * 31;
        if (this.payLater) {
            i = 1;
        } else {
            i = 0;
        }
        i = (i3 + i) * 31;
        if (!this.cardNotRequired) {
            i2 = 0;
        }
        return i + i2;
    }

    public void saveState(FiltersSnapshot snapshot) {
        snapshot.addData(TAG_PAY_NOW, Boolean.valueOf(this.payNow));
        snapshot.addData(TAG_PAY_LATER, Boolean.valueOf(this.payLater));
        snapshot.addData(TAG_CARD_NOT_REQUIRED, Boolean.valueOf(this.cardNotRequired));
    }

    public void restoreState(FiltersSnapshot snapshot) {
        this.payNow = ((Boolean) snapshot.getData(TAG_PAY_NOW)).booleanValue();
        this.payLater = ((Boolean) snapshot.getData(TAG_PAY_LATER)).booleanValue();
        this.cardNotRequired = ((Boolean) snapshot.getData(TAG_CARD_NOT_REQUIRED)).booleanValue();
    }

    public void release() {
    }

    private int calculateCount(@NonNull SearchData data, @NonNull PaymentCriterion criterion) {
        int counter = 0;
        List<HotelData> hotels = data.hotels().all();
        Map<Long, List<Offer>> offersForHotels = data.offers().all();
        if (CollectionUtils.isNotEmpty(hotels)) {
            for (HotelData hotel : hotels) {
                List<Offer> offers = (List) offersForHotels.get(Long.valueOf(hotel.getId()));
                if (CollectionUtils.isNotEmpty(offers)) {
                    for (Offer offer : offers) {
                        if (criterion.passes(offer)) {
                            counter++;
                            break;
                        }
                    }
                }
            }
        }
        return counter;
    }
}
