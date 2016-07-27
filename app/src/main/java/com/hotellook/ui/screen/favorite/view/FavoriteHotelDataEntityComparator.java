package com.hotellook.ui.screen.favorite.view;

import com.hotellook.db.data.FavoriteHotelDataEntity;
import com.hotellook.utils.CompareUtils;
import java.util.Comparator;

public class FavoriteHotelDataEntityComparator implements Comparator<FavoriteHotelDataEntity> {
    public int compare(FavoriteHotelDataEntity lhs, FavoriteHotelDataEntity rhs) {
        int compareResult = CompareUtils.compare(rhs.getRating(), lhs.getRating());
        if (compareResult == 0) {
            return CompareUtils.compare(lhs.getHotelName(), rhs.getHotelName());
        }
        return compareResult;
    }
}
