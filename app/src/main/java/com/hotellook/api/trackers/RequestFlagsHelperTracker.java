package com.hotellook.api.trackers;

import com.hotellook.HotellookApplication;
import com.hotellook.core.api.pojo.geo.GeoLocationData;
import com.hotellook.events.BestLocationsLoadingSuccessEvent;
import com.hotellook.ui.screen.searchform.LaunchParams;
import com.hotellook.utils.CommonPreferences;
import com.squareup.otto.Subscribe;
import java.util.List;

public class RequestFlagsHelperTracker {
    private static final long DAY_IN_MLS = 86400000;
    private static final long MONTH_IN_MLS = 2592000000L;
    private static final long WEEK_IN_MLS = 604800000;
    private Integer lastKnownLocationId;
    private final CommonPreferences prefs;

    public RequestFlagsHelperTracker() {
        this.prefs = HotellookApplication.getApp().getComponent().getCommonPreferences();
    }

    public Integer getLastKnownLocationId() {
        return this.lastKnownLocationId;
    }

    @Subscribe
    public void onNewLocation(BestLocationsLoadingSuccessEvent event) {
        List<GeoLocationData> locations = event.locations;
        if (locations != null && locations.size() != 0) {
            this.lastKnownLocationId = Integer.valueOf(((GeoLocationData) locations.get(0)).getId());
        }
    }

    public void saveLaunchRequestFlags(LaunchParams launchParams) {
        if (!(launchParams.getHls() == null && launchParams.getMarker() == null && launchParams.getUtmDetails() == null)) {
            this.prefs.clearLaunchRequestFlags();
            this.prefs.putLaunchFlagsTimestamp(System.currentTimeMillis());
        }
        if (launchParams.getHls() != null) {
            this.prefs.putLaunchFlagHls(launchParams.getHls());
        }
        if (launchParams.getMarker() != null) {
            this.prefs.putLaunchFlagMarker(launchParams.getMarker());
        }
        if (launchParams.getUtmDetails() != null) {
            this.prefs.putLaunchFlagUtmDetails(launchParams.getUtmDetails());
        }
    }

    public String getMarker() {
        return getLaunchRequestFlag(this.prefs.getLaunchFlagMarker(), MONTH_IN_MLS);
    }

    public String getHls() {
        return getLaunchRequestFlag(this.prefs.getLaunchFlagHls(), WEEK_IN_MLS);
    }

    public String getUtmDetail() {
        return getLaunchRequestFlag(this.prefs.getLaunchFlagUtmDetails(), WEEK_IN_MLS);
    }

    private String getLaunchRequestFlag(String flagValue, long timeout) {
        return System.currentTimeMillis() - this.prefs.getLaunchFlagsTimestamp() < timeout ? flagValue : null;
    }
}
