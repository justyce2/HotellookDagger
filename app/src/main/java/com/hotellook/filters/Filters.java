package com.hotellook.filters;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import com.hotellook.HotellookApplication;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.events.FilteringFinishedEvent;
import com.hotellook.events.FiltersChangedEvent;
import com.hotellook.events.FiltersInittedEvent;
import com.hotellook.events.FiltersResetEvent;
import com.hotellook.events.FiltersSortingChangedEvent;
import com.hotellook.events.GoodHotelsRatingEvent;
import com.hotellook.filters.items.NoRoomsFilterItem;
import com.hotellook.filters.items.PriceFilterItem;
import com.hotellook.filters.pages.AgencyFiltersCategory;
import com.hotellook.filters.pages.AmenitiesFiltersCategory;
import com.hotellook.filters.pages.DistrictsFiltersCategory;
import com.hotellook.filters.pages.FiltersCategory;
import com.hotellook.filters.pages.GeneralFiltersCategory;
import com.hotellook.filters.pages.SortingFiltersCategory;
import com.hotellook.filters.task.FilterTask;
import com.hotellook.filters.task.FilterTask.FilteredData;
import com.hotellook.search.SearchData;
import com.hotellook.utils.threads.TaskExecutor;
import com.squareup.otto.Subscribe;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import timber.log.Timber;

public class Filters {
    public static final int GOOD_HOTELS_MIN_RATING = 70;
    private final AgencyFiltersCategory agencyPage;
    private final DistrictsFiltersCategory districtsPage;
    private EventListener eventListener;
    private final TaskExecutor executor;
    private Map<Integer, List<HotelData>> filteredHotels;
    private Map<Long, List<Offer>> filteredOffers;
    private final List<FiltersCategory> filtersCategories;
    private final GeneralFiltersCategory generalPage;
    private final AmenitiesFiltersCategory optionsAndServicesPage;
    private final PersistentFilters persistentFilters;
    private SearchData searchData;
    private List<HotelData> sortedHotels;
    private final SortingFiltersCategory sortingPage;

    private class EventListener {
        private EventListener() {
        }

        @Subscribe
        public void onFiltersChanged(FiltersChangedEvent event) {
            if (Filters.this.searchData != null) {
                Filters.this.filter(Filters.this.searchData);
            }
        }

        @Subscribe
        public void onSortingChanged(FiltersSortingChangedEvent event) {
            Filters.this.sort();
        }

        @Subscribe
        public void onRatingChanged(GoodHotelsRatingEvent event) {
            Filters.this.generalPage.setRating(Filters.GOOD_HOTELS_MIN_RATING);
        }
    }

    public Filters(PersistentFilters persistentFilters) {
        this.sortingPage = new SortingFiltersCategory();
        this.agencyPage = new AgencyFiltersCategory();
        this.districtsPage = new DistrictsFiltersCategory();
        this.optionsAndServicesPage = new AmenitiesFiltersCategory();
        this.executor = TaskExecutor.getTaskExecutor("FILTERS_THREAD");
        this.eventListener = new EventListener();
        this.persistentFilters = persistentFilters;
        this.generalPage = new GeneralFiltersCategory();
        this.filtersCategories = Arrays.asList(new FiltersCategory[]{this.generalPage, this.optionsAndServicesPage, this.agencyPage, this.sortingPage, this.districtsPage});
    }

    @WorkerThread
    public void init(SearchData searchData) {
        this.searchData = searchData;
        initFilters(searchData);
    }

    public void filterInitData() {
        startFilterTask(this.searchData, new FiltersInittedEvent(), this.filtersCategories);
    }

    public FiltersSnapshot createSnapshot() {
        FiltersSnapshot snapshot = new FiltersSnapshot(this.filteredHotels, this.filteredOffers, this.sortedHotels, hashCode());
        for (FiltersCategory filtersCategory : this.filtersCategories) {
            filtersCategory.saveState(snapshot);
        }
        return snapshot;
    }

    public void restoreState(FiltersSnapshot snapshot) {
        this.filteredHotels = snapshot.filteredHotels;
        this.filteredOffers = snapshot.filteredOffers;
        this.sortedHotels = snapshot.sortedHotels;
        for (FiltersCategory filtersCategory : this.filtersCategories) {
            filtersCategory.restoreState(snapshot);
        }
    }

    public synchronized void sort() {
        startFilterTaskAsync(new SearchData(this.filteredHotels, this.filteredOffers, this.searchData), new FilteringFinishedEvent(), Arrays.asList(new FiltersCategory[]{this.sortingPage}));
    }

    public synchronized void filter(SearchData searchData) {
        startFilterTaskAsync(searchData, new FilteringFinishedEvent(), this.filtersCategories);
    }

    public synchronized void reset() {
        for (FiltersCategory filtersCategory : this.filtersCategories) {
            filtersCategory.reset();
        }
        startFilterTaskAsync(this.searchData, new FiltersResetEvent(), this.filtersCategories);
    }

    private void startFilterTaskAsync(SearchData searchData, Object event, List<FiltersCategory> filtersCategories) {
        this.executor.post(prepareFilterTask(searchData, event, filtersCategories));
    }

    private void startFilterTask(SearchData searchData, Object event, List<FiltersCategory> filtersCategories) {
        prepareFilterTask(searchData, event, filtersCategories).run();
    }

    @NonNull
    private FilterTask prepareFilterTask(SearchData searchData, Object event, List<FiltersCategory> filtersCategories) {
        FilterTask task = new FilterTask(searchData, Filters$$Lambda$1.lambdaFactory$(this, event));
        for (FiltersCategory filtersCategory : filtersCategories) {
            filtersCategory.addFilterLogic(task);
        }
        return task;
    }

    /* synthetic */ void lambda$prepareFilterTask$0(Object event, FilteredData data) {
        this.filteredHotels = data.filteredHotels;
        this.filteredOffers = data.filteredOffers;
        this.sortedHotels = data.sortedHotels;
        HotellookApplication.eventBus().post(event);
    }

    private synchronized void initFilters(SearchData searchData) {
        long startTime = System.currentTimeMillis();
        this.filteredHotels = new HashMap(searchData.hotels().toMap());
        this.filteredOffers = new HashMap(searchData.offers().all());
        for (FiltersCategory filtersCategory : this.filtersCategories) {
            filtersCategory.setUp(searchData, this.persistentFilters);
        }
        Timber.m751d("FilterTask: Analyze time %d", Long.valueOf(System.currentTimeMillis() - startTime));
    }

    @NonNull
    public Map<Long, List<Offer>> getFilteredOffers() {
        return this.filteredOffers == null ? Collections.emptyMap() : this.filteredOffers;
    }

    @NonNull
    public List<HotelData> getSortedHotels() {
        return this.sortedHotels == null ? Collections.emptyList() : this.sortedHotels;
    }

    public boolean inInittedState() {
        for (FiltersCategory filtersCategory : this.filtersCategories) {
            if (!filtersCategory.inDefaultState()) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return this.filtersCategories.hashCode();
    }

    public void subscribeToEvents() {
        HotellookApplication.eventBus().register(this.eventListener);
    }

    public void unsubscribeFromEvents() {
        HotellookApplication.eventBus().unregister(this.eventListener);
    }

    public int getSortingAlgorithmId() {
        return this.sortingPage.getAlgoId();
    }

    public int getFilteredHotelsSize() {
        return this.sortedHotels != null ? this.sortedHotels.size() : 0;
    }

    public NoRoomsFilterItem getNoRoomsFilterItem() {
        return this.generalPage.getNoRoomsFilterItem();
    }

    public PriceFilterItem getPriceFilterFilterItem() {
        return this.generalPage.getPriceFilterItem();
    }

    public AgencyFiltersCategory getAgencyPage() {
        return this.agencyPage;
    }

    public DistrictsFiltersCategory getDistrictsPage() {
        return this.districtsPage;
    }

    public SortingFiltersCategory getSortingCategory() {
        return this.sortingPage;
    }

    public AmenitiesFiltersCategory getOptionsAndServicesPage() {
        return this.optionsAndServicesPage;
    }

    public GeneralFiltersCategory getGeneralPage() {
        return this.generalPage;
    }
}
