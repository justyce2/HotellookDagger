package com.hotellook.filters.sorting.comparator;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.hotellook.badges.Badge;
import com.hotellook.badges.Badges;
import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.minprice.ColoredMinPriceCalendar;
import com.hotellook.search.SearchParams;
import com.hotellook.search.Seasons;
import com.hotellook.ui.screen.filters.SupportedSeasons;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class BadgesComparator implements Comparator<HotelData> {
    private final Badges badges;
    private final SearchParams searchParams;
    private final Seasons seasons;

    public BadgesComparator(@NonNull Badges badges, @Nullable Seasons seasons, @NonNull SearchParams searchParams) {
        this.badges = badges;
        this.seasons = seasons;
        this.searchParams = searchParams;
    }

    public int compare(HotelData lhs, HotelData rhs) {
        boolean lhsNeedToLiftTop = needToLiftUp(lhs);
        boolean rhsNeedToLiftTop = needToLiftUp(rhs);
        if (lhsNeedToLiftTop && !rhsNeedToLiftTop) {
            return 1;
        }
        if (!rhsNeedToLiftTop || lhsNeedToLiftTop) {
            return 0;
        }
        return -1;
    }

    private boolean needToLiftUp(@NonNull HotelData hotel) {
        Set<String> badges = badgesNames(hotel);
        boolean isBeachSeason = isBeachSeason();
        if (hasBadgeWithName(badges, Badges.NAME_POPULAR, Badges.NAME_OPTIMAL, ColoredMinPriceCalendar.CHEAP_RATE, Badges.NAME_LUX)) {
            return true;
        }
        if (this.searchParams.kidsCount() > 0) {
            if (hasBadgeWithName(badges, Badges.NAME_CHILDREN)) {
                return true;
            }
        }
        if (this.searchParams.adults() == 1) {
            if (hasBadgeWithName(badges, Poi.CATEGORY_BUSINESS)) {
                return true;
            }
        }
        if (this.searchParams.adults() == 2) {
            if (hasBadgeWithName(badges, Badges.NAME_ROMANTIC)) {
                return true;
            }
        }
        if (isBeachSeason) {
            if (hasBadgeWithName(badges, SupportedSeasons.SEASON_BEACH)) {
                return true;
            }
        }
        if (hasBadgeWithName(badges, Badges.NAME_CENTER)) {
            return true;
        }
        return false;
    }

    @NonNull
    private Set<String> badgesNames(@NonNull HotelData hotel) {
        Set<String> names = new HashSet();
        for (Badge badge : this.badges.getBadges(hotel)) {
            names.add(badge.name);
        }
        return names;
    }

    private boolean hasBadgeWithName(@NonNull Set<String> badges, @NonNull String... names) {
        for (String name : names) {
            if (badges.contains(name)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBeachSeason() {
        if (this.seasons == null) {
            return false;
        }
        return new SupportedSeasons(this.seasons.anyCity()).contains(SupportedSeasons.SEASON_BEACH);
    }
}
