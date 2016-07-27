package com.hotellook.ui.screen.searchresults.adapters.cards.distancecard;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.hotellook.C1178R;
import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.core.api.pojo.hotelsdump.SeasonDates;
import com.hotellook.filters.distancetarget.DistanceTarget;
import com.hotellook.filters.distancetarget.DistanceTarget.DestinationType;
import com.hotellook.filters.distancetarget.LocationDistanceTarget;
import com.hotellook.filters.distancetarget.SearchPointDistanceTarget;
import com.hotellook.filters.distancetarget.SeasonLocationDistanceTarget;
import com.hotellook.filters.distancetarget.TargetSelector;
import com.hotellook.search.Pois;
import com.hotellook.search.SearchData;
import com.hotellook.search.SearchParams;
import com.hotellook.ui.screen.filters.SupportedSeasons;
import com.hotellook.ui.screen.filters.pois.FiltersSeasonsTitles;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.DateUtils;
import com.hotellook.utils.LocationUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CardTargetSelector implements TargetSelector {
    private static final String SERVER_DATE_FORMAT = "yyyy-MM-dd";
    private final Context context;
    private final SimpleDateFormat dateFormat;
    private final FiltersSeasonsTitles filtersSeasonsTitles;

    public CardTargetSelector(Context context) {
        this.dateFormat = new SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US);
        this.filtersSeasonsTitles = new FiltersSeasonsTitles();
        this.context = context;
    }

    public DistanceTarget select(SearchData search) {
        SearchParams searchParams = search.searchParams();
        if (searchParams.isDestinationTypeUserLocation()) {
            return getMyLocationTarget(search);
        }
        if (searchParams.isDestinationTypeMapPoint()) {
            return new SearchPointDistanceTarget(this.context, searchParams.location());
        }
        if (searchParams.isDestinationTypeNearbyCities()) {
            return new LocationDistanceTarget(searchParams.location(), searchParams.destinationName(), DestinationType.CENTER);
        }
        DistanceTarget distanceTarget = getTargetForSeason(search);
        if (distanceTarget == null) {
            return getCityCenterTarget(search);
        }
        return distanceTarget;
    }

    @NonNull
    private DistanceTarget getCityCenterTarget(SearchData search) {
        return new LocationDistanceTarget(search.searchParams().location(), this.context.getString(C1178R.string.city_center), DestinationType.CENTER);
    }

    @NonNull
    private DistanceTarget getMyLocationTarget(SearchData search) {
        return new LocationDistanceTarget(search.searchParams().location(), this.context.getString(C1178R.string.my_location), DestinationType.USER);
    }

    @Nullable
    private DistanceTarget getTargetForSeason(SearchData search) {
        Map<String, Map<String, SeasonDates>> seasonsForCity = search.seasons().anyCity();
        SupportedSeasons supportedSeasons = new SupportedSeasons(seasonsForCity);
        String seasonOnCheckIn = getSeasonOnCheckInDates(search, seasonsForCity, supportedSeasons);
        if (TextUtils.isEmpty(seasonOnCheckIn)) {
            return null;
        }
        List<Location> locations = getLocationsByPoiType(search.pois(), supportedSeasons.getPoiCategory(seasonOnCheckIn));
        if (!this.filtersSeasonsTitles.hasFor(seasonOnCheckIn) || CollectionUtils.isEmpty(locations)) {
            return null;
        }
        return new SeasonLocationDistanceTarget(locations, this.context.getString(this.filtersSeasonsTitles.seasonData(seasonOnCheckIn).allPoisTitleId), seasonOnCheckIn);
    }

    private List<Location> getLocationsByPoiType(Pois pois, String category) {
        List<Poi> allPois = pois.all();
        List<Location> locations = new ArrayList();
        for (Poi poi : allPois) {
            if (category.equals(poi.getCategory())) {
                locations.add(LocationUtils.from(poi.getLocation()));
            }
        }
        return locations;
    }

    private String getSeasonOnCheckInDates(SearchData search, Map<String, Map<String, SeasonDates>> seasonsForCity, SupportedSeasons supportedSeasons) {
        for (String season : supportedSeasons.getSeasons()) {
            for (SeasonDates d : ((Map) seasonsForCity.get(season)).values()) {
                if (checkinInSeason(search.searchParams().checkIn().getTime(), d)) {
                    return season;
                }
            }
        }
        return null;
    }

    private boolean checkinInSeason(Date checkIn, SeasonDates dates) {
        try {
            return DateUtils.isBetween(checkIn, this.dateFormat.parse(dates.from), this.dateFormat.parse(dates.to));
        } catch (ParseException e) {
            return false;
        }
    }
}
