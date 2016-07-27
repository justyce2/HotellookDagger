package com.hotellook.api;

import android.support.annotation.Nullable;
import com.hotellook.HotellookApplication;
import com.hotellook.api.trackers.RequestFlagsHelperTracker;
import com.hotellook.badges.Badge;
import com.hotellook.search.SearchParams;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.ui.screen.hotel.Source;
import com.hotellook.utils.CommonPreferences;
import com.hotellook.utils.DateUtils;
import java.util.List;
import pl.charmas.android.reactivelocation.C1822R;

public class RequestFlagsGenerator {
    private final RequestFlagsHelperTracker helper;
    private final CommonPreferences prefs;

    /* renamed from: com.hotellook.api.RequestFlagsGenerator.1 */
    static /* synthetic */ class C11801 {
        static final /* synthetic */ int[] $SwitchMap$com$hotellook$ui$screen$hotel$Source;

        static {
            $SwitchMap$com$hotellook$ui$screen$hotel$Source = new int[Source.values().length];
            try {
                $SwitchMap$com$hotellook$ui$screen$hotel$Source[Source.CITY_SEARCH_RESULTS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hotellook$ui$screen$hotel$Source[Source.FAVORITES_SEARCH_RESULTS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hotellook$ui$screen$hotel$Source[Source.BEST_HOTELS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$hotellook$ui$screen$hotel$Source[Source.CITY_SEARCH_MAP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hotellook$ui$screen$hotel$Source[Source.FAVORITES.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hotellook$ui$screen$hotel$Source[Source.HOTEL_SEARCH.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public RequestFlagsGenerator() {
        this.prefs = HotellookApplication.getApp().getComponent().getCommonPreferences();
        this.helper = new RequestFlagsHelperTracker();
    }

    public RequestFlags citySearchFromSearchForm(SearchParams searchParams) {
        return createRequestFlags(this.prefs.getUrlSource(), getSection(searchParams), null, null);
    }

    @Nullable
    private String getSection(SearchParams searchParams) {
        return isTonightSearch(searchParams) ? MixPanelParams.TONIGHT : null;
    }

    private boolean isTonightSearch(SearchParams searchParams) {
        Integer lastKnownLocationId = HotellookApplication.getApp().getComponent().getRequestFlagsHelperTracker().getLastKnownLocationId();
        if (lastKnownLocationId != null && searchParams.locationId() == lastKnownLocationId.intValue() && DateUtils.areDatesEqualsByDay(DateUtils.getTodayCalendar(), searchParams.checkIn())) {
            return true;
        }
        return false;
    }

    public RequestFlags hotelSearchFromSearchForm(SearchParams searchParams) {
        return createRequestFlags(this.prefs.getUrlSource(), getSection(searchParams), RequestFlags.FUNCTION_HOTEL_SEARCH_FROM_SEARCH_FORM, null);
    }

    public RequestFlags searchFromHotelScreenSearchForm(SearchParams params) {
        return createRequestFlags(this.prefs.getUrlSource(), getSection(params), RequestFlags.FUNCTION_SOURCE_FAVORITES, null);
    }

    public RequestFlags bookingFromHotelScreen(SearchParams searchParams, String searchResultsRequestFlagSource, Source source, List<Badge> badges) {
        return createRequestFlags(searchResultsRequestFlagSource, getSection(searchParams), getFunction(source), transformToFlag(badges));
    }

    public RequestFlags createRequestFlags(String source, String section, String func, String badge) {
        return new RequestFlags(this.helper.getMarker(), source, section, func, badge, this.helper.getHls(), this.helper.getUtmDetail());
    }

    private String transformToFlag(List<Badge> badges) {
        if (badges == null || badges.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Badge badge : badges) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(badge.name);
        }
        return sb.toString();
    }

    private String getFunction(Source source) {
        switch (C11801.$SwitchMap$com$hotellook$ui$screen$hotel$Source[source.ordinal()]) {
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
            case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                return RequestFlags.FUNCTION_SOURCE_RESULTS;
            case C1822R.styleable.MapAttrs_cameraTilt /*4*/:
                return MixPanelParams.MAP;
            case C1822R.styleable.MapAttrs_cameraZoom /*5*/:
                return RequestFlags.FUNCTION_SOURCE_FAVORITES;
            case C1822R.styleable.MapAttrs_liteMode /*6*/:
                return RequestFlags.FUNCTION_SOURCE_SEARCH_FORM;
            default:
                return null;
        }
    }
}
