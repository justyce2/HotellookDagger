package com.hotellook.ui.screen.filters;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.hotelsdump.SeasonDates;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SeasonsUtils {
    @NonNull
    public static List<String> seasonsPoiCategories(Map<String, Map<String, SeasonDates>> seasons) {
        if (seasons != null) {
            return new SupportedSeasons(seasons).getPoiCategories();
        }
        return Collections.emptyList();
    }
}
