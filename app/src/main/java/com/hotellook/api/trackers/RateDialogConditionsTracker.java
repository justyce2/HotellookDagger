package com.hotellook.api.trackers;

import com.hotellook.BuildConfig;
import com.hotellook.events.HotelFragmentCreatedEvent;
import com.hotellook.events.OnAppRatedEvent;
import com.hotellook.events.OnDismissRateDialog;
import com.hotellook.events.SearchFinishEvent;
import com.hotellook.utils.CommonPreferences;
import com.squareup.otto.Subscribe;

public class RateDialogConditionsTracker {
    public static final int MIN_HOTEL_VIEWS_CONDITION = 2;
    public static final int MIN_SEARCHES_CONDITION = 5;
    public static final int MIN_SESSIONS_CONDITION = 3;
    private boolean doNotShow;
    private int hotelViews;
    private CommonPreferences prefs;
    private int rating;
    private int sessions;
    private int successfulSearches;

    public RateDialogConditionsTracker(CommonPreferences prefs) {
        this.prefs = prefs;
        this.successfulSearches = this.prefs.getRateConditionSuccessfulSearchesCount();
        this.sessions = this.prefs.getRateConditionSessionCount();
        this.hotelViews = this.prefs.getRateConditionHotelViewCount();
        this.rating = this.prefs.getAppRated();
        onNewSession();
    }

    private void onNewSession() {
        this.sessions++;
        this.prefs.putRateConditionSessions(this.sessions);
    }

    @Subscribe
    public void onSearchSucceed(SearchFinishEvent event) {
        this.successfulSearches++;
        this.prefs.putRateConditionSuccessfulSearches(this.successfulSearches);
    }

    @Subscribe
    public void onHotelScreenOpen(HotelFragmentCreatedEvent event) {
        this.hotelViews++;
        this.prefs.putRateConditionHotelViews(this.hotelViews);
    }

    @Subscribe
    public void onAppRated(OnAppRatedEvent event) {
        this.rating = event.rating;
        this.prefs.putAppRated(this.rating);
    }

    @Subscribe
    public void onRateDialogNotNowEvent(OnDismissRateDialog event) {
        this.doNotShow = true;
    }

    public void onCrash() {
        this.successfulSearches = 0;
        this.sessions = 0;
        this.hotelViews = 0;
        this.prefs.resetRateConditions();
    }

    public boolean areConditionsMet() {
        if (this.rating > 0 || this.doNotShow || !BuildConfig.GOOGLE_PLAY_BUILD.booleanValue() || this.sessions < MIN_SESSIONS_CONDITION || this.successfulSearches < MIN_SEARCHES_CONDITION || this.hotelViews < MIN_HOTEL_VIEWS_CONDITION) {
            return false;
        }
        return true;
    }

    public int getRating() {
        return this.rating;
    }
}
