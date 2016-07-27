package com.hotellook.ui.screen.searchresults.adapters.cards.distancecard;

import android.content.Context;
import android.support.annotation.Nullable;
import com.hotellook.C1178R;
import com.hotellook.ui.screen.filters.SupportedSeasons;
import java.util.HashMap;
import java.util.Map;

public class CardsSeasonTitles {
    private final Map<String, String> seasonsTitles;

    public CardsSeasonTitles(Context context) {
        this.seasonsTitles = new HashMap();
        this.seasonsTitles.put(SupportedSeasons.SEASON_BEACH, context.getString(C1178R.string.search_hotels_near_any_beach));
        this.seasonsTitles.put(SupportedSeasons.SEASON_SKI, context.getString(C1178R.string.search_hotels_near_any_ski_lifts));
    }

    @Nullable
    public String title(String season) {
        return (String) this.seasonsTitles.get(season);
    }
}
