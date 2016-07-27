package com.hotellook.filters.task;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.filters.items.HotelComparatorProducer;
import com.hotellook.filters.items.criterion.Criterion;
import com.hotellook.filters.items.criterion.CriterionSet;
import com.hotellook.filters.items.criterion.SequentialCriterionSet;
import com.hotellook.search.SearchData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import timber.log.Timber;

public class FilterTask implements Runnable {
    private final Callback callback;
    private HotelComparatorProducer comparatorProducer;
    private final Handler handler;
    private final CriterionSet<HotelData> hotelFiltersChain;
    private final CriterionSet<Offer> roomFilterChain;
    private SearchData searchData;

    public interface Callback {
        void onFiltered(FilteredData filteredData);
    }

    public static final class FilteredData {
        public final Map<Integer, List<HotelData>> filteredHotels;
        public final Map<Long, List<Offer>> filteredOffers;
        public final List<HotelData> sortedHotels;

        public FilteredData(Map<Integer, List<HotelData>> filteredHotels, Map<Long, List<Offer>> filteredOffers, List<HotelData> sortedHotels) {
            this.filteredHotels = filteredHotels;
            this.filteredOffers = filteredOffers;
            this.sortedHotels = sortedHotels;
        }
    }

    public FilterTask(SearchData searchData, Callback callback) {
        this.handler = new Handler(Looper.getMainLooper());
        this.hotelFiltersChain = new SequentialCriterionSet();
        this.roomFilterChain = new SequentialCriterionSet();
        this.searchData = searchData;
        this.callback = callback;
    }

    public void addHotelFilter(@Nullable Criterion<HotelData> criterion) {
        if (criterion != null) {
            this.hotelFiltersChain.add(criterion);
        }
    }

    public void addRoomFilter(@Nullable Criterion<Offer> criterion) {
        if (criterion != null) {
            this.roomFilterChain.add(criterion);
        }
    }

    public void run() {
        long startTime = System.currentTimeMillis();
        Map<Integer, List<HotelData>> filteredHotels = new HashMap();
        Map<Long, List<Offer>> filteredPrices = new HashMap();
        List<HotelData> sortedHotels = new ArrayList();
        if (hasAnyCriteria()) {
            for (CityInfo cityInfo : this.searchData.locations().all()) {
                int locationId = cityInfo.getId();
                if (!filteredHotels.containsKey(Integer.valueOf(locationId))) {
                    filteredHotels.put(Integer.valueOf(locationId), new ArrayList());
                }
                for (HotelData hotelData : this.searchData.hotels().hotelsInLocation(locationId)) {
                    if (this.hotelFiltersChain.passes(hotelData)) {
                        if (this.roomFilterChain.hasCriteria()) {
                            List<Offer> offers = (List) this.searchData.offers().all().get(Long.valueOf(hotelData.getId()));
                            if (offers == null) {
                                offers = Collections.emptyList();
                            }
                            List<Offer> filtered = new ArrayList();
                            for (Offer offer : offers) {
                                if (this.roomFilterChain.passes(offer)) {
                                    filtered.add(offer);
                                }
                            }
                            if (!filtered.isEmpty()) {
                                ((List) filteredHotels.get(Integer.valueOf(locationId))).add(hotelData);
                                filteredPrices.put(Long.valueOf(hotelData.getId()), filtered);
                            }
                        } else {
                            ((List) filteredHotels.get(Integer.valueOf(locationId))).add(hotelData);
                            filteredPrices.put(Long.valueOf(hotelData.getId()), this.searchData.offers().all().get(Long.valueOf(hotelData.getId())));
                        }
                    }
                }
            }
        } else {
            filteredHotels = this.searchData.hotels().toMap();
            filteredPrices = this.searchData.offers().all();
        }
        for (List<HotelData> hotels : filteredHotels.values()) {
            sortedHotels.addAll(hotels);
        }
        if (this.comparatorProducer != null) {
            try {
                Collections.sort(sortedHotels, this.comparatorProducer.newComparator(filteredPrices));
            } catch (Throwable error) {
                Integer[] numArr = new Object[2];
                numArr[0] = Integer.valueOf(sortedHotels.size());
                numArr[1] = this.comparatorProducer;
                Timber.m754e(error, "Problem while sorting. Sorted hotels size: %d. Comparator: %s", numArr);
            }
        }
        FilteredData data = new FilteredData(filteredHotels, filteredPrices, sortedHotels);
        this.handler.post(FilterTask$$Lambda$1.lambdaFactory$(this, data));
        Timber.m751d("FiltersLogic: Filter time %d", Long.valueOf(System.currentTimeMillis() - startTime));
    }

    /* synthetic */ void lambda$run$0(FilteredData data) {
        this.callback.onFiltered(data);
    }

    private boolean hasAnyCriteria() {
        return this.hotelFiltersChain.hasCriteria() || this.roomFilterChain.hasCriteria();
    }

    public void setComparatorProducer(HotelComparatorProducer comparatorProducer) {
        this.comparatorProducer = comparatorProducer;
    }
}
