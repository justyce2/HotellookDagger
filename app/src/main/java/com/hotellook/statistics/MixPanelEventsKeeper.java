package com.hotellook.statistics;

import android.support.annotation.Nullable;
import com.hotellook.events.HotelDataLoadedEvent;
import com.hotellook.events.HotelFragmentCreatedEvent;
import com.hotellook.events.SearchStartEvent;
import com.hotellook.statistics.Constants.Source;
import com.hotellook.statistics.mixpanel.LaunchSource;
import com.squareup.otto.Subscribe;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MixPanelEventsKeeper {
    public static final int ACTION_FILTERED = 15;
    public static final int ACTION_FRAME_OPENED = 17;
    public static final int ACTION_GALLERY = 0;
    public static final int ACTION_HOTEL_MAP_OPENED = 7;
    public static final int ACTION_PRICES_SHOWED = 11;
    public static final int ACTION_RATINGS_SHOWED = 12;
    public static final int ACTION_REVIEWS_SHOWED = 13;
    public static final int ID_WHOLE_RESULTS = -1;
    private final HotelOpenEventsKeeper hotelFragmentEventsKeeper;
    private LaunchSource launchReferrer;
    private final Map<Long, String> openHotelSources;
    private Source searchSource;
    private final Set<Long> showedHotels;
    private final Set<Long> showedHotelsBeforeSorting;
    private final Map<Long, Set<Integer>> showedImages;
    private final Set<String> singleActionInSession;

    private static class HotelOpenEventsKeeper {
        private final Map<Long, HotelFragmentCreatedEvent> createHotelFragmentEvents;
        private final Map<Long, HotelDataLoadedEvent> hotelDataLoadedEvents;

        private HotelOpenEventsKeeper() {
            this.createHotelFragmentEvents = new HashMap();
            this.hotelDataLoadedEvents = new HashMap();
        }

        @Nullable
        public HotelFragmentCreatedEvent getHotelFragmentCreateEvent(long id) {
            return (HotelFragmentCreatedEvent) this.createHotelFragmentEvents.get(Long.valueOf(id));
        }

        @Nullable
        public HotelDataLoadedEvent getHotelDataLoadedEvent(long id) {
            return (HotelDataLoadedEvent) this.hotelDataLoadedEvents.get(Long.valueOf(id));
        }

        public void putHotelFragmentCreateEvent(long id, HotelFragmentCreatedEvent event) {
            this.createHotelFragmentEvents.put(Long.valueOf(id), event);
        }

        public void putHotelDataLoadedEvent(long id, HotelDataLoadedEvent event) {
            this.hotelDataLoadedEvents.put(Long.valueOf(id), event);
        }

        public void clear() {
            this.createHotelFragmentEvents.clear();
            this.hotelDataLoadedEvents.clear();
        }
    }

    public MixPanelEventsKeeper() {
        this.showedHotels = new HashSet();
        this.showedHotelsBeforeSorting = new HashSet();
        this.singleActionInSession = new HashSet();
        this.openHotelSources = new HashMap();
        this.showedImages = new HashMap();
        this.hotelFragmentEventsKeeper = new HotelOpenEventsKeeper();
    }

    public LaunchSource getLaunchReferrer() {
        return this.launchReferrer;
    }

    public void setLaunchReferrer(LaunchSource launchReferrer) {
        this.launchReferrer = launchReferrer;
    }

    @Subscribe
    public void onNewSearch(SearchStartEvent event) {
        if (!event.searchParams.isHotelSearch()) {
            this.showedHotels.clear();
            this.showedHotelsBeforeSorting.clear();
            this.singleActionInSession.clear();
            this.openHotelSources.clear();
            this.hotelFragmentEventsKeeper.clear();
            this.showedImages.clear();
        }
    }

    @Subscribe
    public void onHotelOpenedEvent(HotelFragmentCreatedEvent event) {
        long id = event.hotelId;
        this.showedHotels.add(Long.valueOf(id));
        this.showedHotelsBeforeSorting.add(Long.valueOf(id));
    }

    public int getHotelsShowedCount() {
        return this.showedHotels.size();
    }

    public int getHotelsShowedCountBeforeSortingAndResetIt() {
        int count = this.showedHotelsBeforeSorting.size();
        this.showedHotelsBeforeSorting.clear();
        return count;
    }

    public void setActionMet(long hotelId, int action) {
        this.singleActionInSession.add(makeKey(hotelId, action));
    }

    public boolean isActionMet(long hotelId, int action) {
        return this.singleActionInSession.contains(makeKey(hotelId, action));
    }

    public void clearAction(long hotelId, int action) {
        this.singleActionInSession.remove(makeKey(hotelId, action));
    }

    private String makeKey(long id, int action) {
        return id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + action;
    }

    public Source getSearchSource() {
        return this.searchSource;
    }

    public void setSearchSource(Source searchSource) {
        this.searchSource = searchSource;
    }

    public void setHotelOpenedSource(long id, com.hotellook.ui.screen.hotel.Source source) {
        this.openHotelSources.put(Long.valueOf(id), source.mixpanelName);
    }

    public String getHotelOpenedSource(long hotelId) {
        return (String) this.openHotelSources.get(Long.valueOf(hotelId));
    }

    public void setCreateHotelFragmentEvent(long hotelId, HotelFragmentCreatedEvent event) {
        this.hotelFragmentEventsKeeper.putHotelFragmentCreateEvent(hotelId, event);
    }

    public HotelFragmentCreatedEvent getHotelFragmentCreateEvent(long hotelId) {
        return this.hotelFragmentEventsKeeper.getHotelFragmentCreateEvent(hotelId);
    }

    public HotelDataLoadedEvent getHotelDataLoadedEvent(long hotelId) {
        return this.hotelFragmentEventsKeeper.getHotelDataLoadedEvent(hotelId);
    }

    public void setHotelDataLoadedEvent(long hotelId, HotelDataLoadedEvent event) {
        this.hotelFragmentEventsKeeper.putHotelDataLoadedEvent(hotelId, event);
    }

    public void clearHotelFragmentsEvents() {
        this.hotelFragmentEventsKeeper.clear();
    }

    public void setImageShowed(long hotelId, int position) {
        getShowedImagesSet(hotelId).add(Integer.valueOf(position));
    }

    public int getShowedImagesCount(long hotelId) {
        return getShowedImagesSet(hotelId).size();
    }

    private Set<Integer> getShowedImagesSet(long hotelId) {
        if (!this.showedImages.containsKey(Long.valueOf(hotelId))) {
            this.showedImages.put(Long.valueOf(hotelId), new HashSet());
        }
        return (Set) this.showedImages.get(Long.valueOf(hotelId));
    }
}
