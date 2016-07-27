package com.hotellook.ui.screen.filters.pois;

import com.hotellook.C1178R;
import com.hotellook.ui.screen.filters.SupportedSeasons;
import java.util.HashMap;
import java.util.Map;

public class FiltersSeasonsTitles {
    private final Map<String, SeasonFilterTitles> seasonsInfo;

    /* renamed from: com.hotellook.ui.screen.filters.pois.FiltersSeasonsTitles.1 */
    class C12741 extends HashMap<String, SeasonFilterTitles> {
        C12741() {
            put(SupportedSeasons.SEASON_BEACH, new SeasonFilterTitles(C1178R.string.beaches, C1178R.string.all_beaches));
            put(SupportedSeasons.SEASON_SKI, new SeasonFilterTitles(C1178R.string.ski_lifts, C1178R.string.all_ski_lifts));
        }
    }

    public FiltersSeasonsTitles() {
        this.seasonsInfo = new C12741();
    }

    public SeasonFilterTitles seasonData(String season) {
        return (SeasonFilterTitles) this.seasonsInfo.get(season);
    }

    public boolean hasFor(String season) {
        return this.seasonsInfo.containsKey(season);
    }
}
