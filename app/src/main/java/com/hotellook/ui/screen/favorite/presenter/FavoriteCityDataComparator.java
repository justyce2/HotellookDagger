package com.hotellook.ui.screen.favorite.presenter;

import com.hotellook.interactor.favorites.FavoriteCityData;
import com.hotellook.utils.CompareUtils;
import java.util.Comparator;

public class FavoriteCityDataComparator implements Comparator<FavoriteCityData> {
    public int compare(FavoriteCityData lhs, FavoriteCityData rhs) {
        if (lhs.id == -1) {
            return -1;
        }
        if (rhs.id == -1) {
            return 1;
        }
        return CompareUtils.compare(lhs.name, rhs.name);
    }
}
