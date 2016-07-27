package com.hotellook.badges.selectors;

import android.support.annotation.Nullable;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.SearchData;
import java.util.List;
import java.util.Map;

public interface HotelSelector {
    @Nullable
    HotelData select(SearchData searchData, List<HotelData> list, Map<Long, Offer> map);
}
