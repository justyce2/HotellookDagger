package com.hotellook.ui.screen.favorite.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.hotellook.interactor.favorites.FavoriteCityData;
import com.hotellook.interactor.favorites.FavoritesParsedData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CitiesMerge {
    @Nullable
    private final FavoritesParsedData previousData;

    public CitiesMerge(FavoritesParsedData previousData) {
        this.previousData = previousData;
    }

    @NonNull
    public List<FavoriteCityData> mergeWith(List<FavoriteCityData> actualCities) {
        actualCities.addAll(findEmptyKnownCities(actualCities));
        return actualCities;
    }

    @NonNull
    private List<FavoriteCityData> findEmptyKnownCities(List<FavoriteCityData> actualCities) {
        if (this.previousData == null) {
            return Collections.emptyList();
        }
        Set<Integer> actualCitiesIds = new HashSet();
        for (FavoriteCityData city : actualCities) {
            actualCitiesIds.add(Integer.valueOf(city.id));
        }
        List<FavoriteCityData> prevCities = this.previousData.cities();
        List<FavoriteCityData> noHotelsCities = new ArrayList();
        for (FavoriteCityData prevCity : prevCities) {
            if (!(actualCitiesIds.contains(Integer.valueOf(prevCity.id)) || prevCity.id == -1)) {
                noHotelsCities.add(prevCity.withCount(0));
            }
        }
        return noHotelsCities;
    }
}
