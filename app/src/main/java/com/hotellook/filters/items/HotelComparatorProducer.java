package com.hotellook.filters.items;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface HotelComparatorProducer {
    Comparator<HotelData> newComparator(Map<Long, List<Offer>> map);
}
