package com.hotellook.api.dataloaders;

import android.support.annotation.NonNull;
import com.hotellook.core.api.HotellookService;
import com.hotellook.core.api.pojo.minprice.ColoredMinPriceCalendar;
import com.hotellook.currency.CurrencyRepository;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MinPricesLoader extends BaseLoader {
    private static final int THREE_COLORED_RATES = 1;
    private int adults;
    private final HotellookService api;
    private final CurrencyRepository currencyRepository;
    private int locationId;
    private ColoredMinPriceCalendar minPricesCalendar;

    public MinPricesLoader(@NonNull HotellookService api, @NonNull CurrencyRepository currencyRepository) {
        this.api = api;
        this.currencyRepository = currencyRepository;
    }

    public void load(int locationId, int adults) {
        if (!hasData() || this.locationId != locationId || this.adults != adults) {
            this.locationId = locationId;
            this.adults = adults;
            this.state = State.LOADING;
            this.api.observeCalendarMinPrices(locationId, this.currencyRepository.currencyCode(), adults, THREE_COLORED_RATES).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(MinPricesLoader$$Lambda$1.lambdaFactory$(this), MinPricesLoader$$Lambda$2.lambdaFactory$(this));
        }
    }

    /* synthetic */ void lambda$load$0(ColoredMinPriceCalendar coloredMinPriceCalendar) {
        if (isMinPriceCalendarValid(coloredMinPriceCalendar)) {
            this.minPricesCalendar = coloredMinPriceCalendar;
            this.state = State.LOADED;
            return;
        }
        this.state = State.FAILED;
    }

    /* synthetic */ void lambda$load$1(Throwable error) {
        this.state = State.FAILED;
    }

    private boolean isMinPriceCalendarValid(ColoredMinPriceCalendar minPricesCalendar) {
        return minPricesCalendar != null && isDatesMapValid(minPricesCalendar) && areGroupRatesValid(minPricesCalendar);
    }

    private boolean areGroupRatesValid(ColoredMinPriceCalendar minPricesCalendar) {
        return minPricesCalendar.getGroupPricesRates() != null && minPricesCalendar.getGroupPricesRates().size() > 0;
    }

    private boolean isDatesMapValid(ColoredMinPriceCalendar minPricesCalendar) {
        return minPricesCalendar.getDates() != null && minPricesCalendar.getDates().size() > 0;
    }

    public void reset() {
        this.state = State.IDLE;
        this.minPricesCalendar = null;
    }

    public ColoredMinPriceCalendar getData() {
        return this.minPricesCalendar;
    }

    public boolean hasData() {
        return this.state == State.LOADED && this.minPricesCalendar != null;
    }

    public int getLocationId() {
        return this.locationId;
    }
}
