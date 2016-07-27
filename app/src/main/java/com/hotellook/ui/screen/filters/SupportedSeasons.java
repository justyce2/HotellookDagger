package com.hotellook.ui.screen.filters;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.core.api.pojo.hotelsdump.SeasonDates;
import com.hotellook.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SupportedSeasons {
    public static final String SEASON_BEACH = "beach";
    public static final String SEASON_SKI = "ski";
    private static final Set<String> SUPPORTED_SEASONS;
    private final List<String> seasons;
    private final Map<String, String> seasonsToPoi;

    /* renamed from: com.hotellook.ui.screen.filters.SupportedSeasons.1 */
    class C12611 extends HashMap<String, String> {
        C12611() {
            put(SupportedSeasons.SEASON_BEACH, SupportedSeasons.SEASON_BEACH);
            put(SupportedSeasons.SEASON_SKI, Poi.CATEGORY_SKI);
        }
    }

    static {
        SUPPORTED_SEASONS = CollectionUtils.asSet(SEASON_BEACH, SEASON_SKI);
    }

    public SupportedSeasons(Map<String, Map<String, SeasonDates>> allSeasons) {
        this.seasons = new ArrayList(SUPPORTED_SEASONS.size());
        this.seasonsToPoi = new C12611();
        if (allSeasons != null) {
            for (String season : allSeasons.keySet()) {
                if (SUPPORTED_SEASONS.contains(season)) {
                    this.seasons.add(season);
                }
            }
        }
    }

    public List<String> getSeasonsForSections() {
        List<String> seasonsForSections = new ArrayList(this.seasons);
        seasonsForSections.remove(SEASON_SKI);
        return seasonsForSections;
    }

    public List<String> getSeasons() {
        return this.seasons;
    }

    public String getPoiCategory(String season) {
        return (String) this.seasonsToPoi.get(season);
    }

    @NonNull
    public List<String> getPoiCategories() {
        List<String> poiCategories = new ArrayList(this.seasons.size());
        for (String season : this.seasons) {
            poiCategories.add(this.seasonsToPoi.get(season));
        }
        return poiCategories;
    }

    public boolean contains(String category) {
        return this.seasons.contains(category);
    }
}
