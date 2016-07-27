package com.hotellook.ui.screen.filters.pois;

import android.content.Context;
import android.support.annotation.NonNull;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.search.Locations;
import com.hotellook.search.Pois;
import com.hotellook.search.SearchData;
import com.hotellook.search.SearchParams;
import com.hotellook.search.Seasons;
import com.hotellook.ui.screen.filters.SupportedSeasons;
import com.hotellook.ui.screen.filters.pois.adapter.ListItemBinder;
import com.hotellook.ui.screen.filters.pois.listitems.CenterItemBinder;
import com.hotellook.ui.screen.filters.pois.listitems.MapPointBinder;
import com.hotellook.ui.screen.filters.pois.listitems.MyLocationBinder;
import com.hotellook.ui.screen.filters.pois.listitems.TitleListItem;
import com.hotellook.utils.EventBus;
import com.hotellook.utils.LocationUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.zhanghai.android.materialprogressbar.BuildConfig;
import rx.Observable;

public class DefaultDataCreator {
    private final Map<Integer, SupportedSeasons> allSupportedSeasons;
    private final Context context;
    private final EventBus eventBus;
    private final FiltersSeasonsTitles filtersSeasonsTitles;
    private final Locations locations;
    private final Pois pois;
    private final boolean searchByLocation;
    private final SearchData searchData;

    public DefaultDataCreator(Context context, SearchData searchData, EventBus eventBus) {
        this.filtersSeasonsTitles = new FiltersSeasonsTitles();
        this.context = context;
        this.eventBus = eventBus;
        this.searchData = searchData;
        this.searchByLocation = isSearchByLocation(searchData.searchParams());
        this.locations = searchData.locations();
        this.pois = searchData.pois();
        this.allSupportedSeasons = supportSeasons(searchData);
    }

    private boolean isSearchByLocation(SearchParams searchParams) {
        return searchParams.isDestinationTypeUserLocation() || searchParams.isDestinationTypeMapPoint() || searchParams.isDestinationTypeNearbyCities();
    }

    private Map<Integer, SupportedSeasons> supportSeasons(SearchData searchData) {
        Map<Integer, SupportedSeasons> result = new HashMap();
        Seasons seasons = searchData.seasons();
        for (CityInfo city : this.locations.list()) {
            int id = city.getId();
            result.put(Integer.valueOf(id), new SupportedSeasons(seasons.inLocation(id)));
        }
        return result;
    }

    public Observable<List<ListItemBinder>> prepareItems() {
        Iterable observables = new ArrayList();
        observables.add(prepareHistoryItems());
        observables.add(prepareGeneralItems());
        observables.add(prepareSeasonPois());
        observables.add(prepareSection(Poi.CATEGORY_AIRPORT, this.context.getString(C1178R.string.airports)));
        observables.add(prepareSection(Poi.CATEGORY_TRAIN_STATION, this.context.getString(C1178R.string.train_stations)));
        observables.add(prepareOtherPois(Poi.CATEGORY_AIRPORT, Poi.CATEGORY_TRAIN_STATION));
        return Observable.merge(observables).toList();
    }

    private Observable<ListItemBinder> prepareGeneralItems() {
        Iterable allObservables = new ArrayList();
        allObservables.add(Observable.just(new TitleListItem(this.context.getString(C1178R.string.general))));
        if (this.searchData.searchParams().isDestinationTypeMapPoint()) {
            allObservables.add(Observable.just(new MapPointBinder(this.eventBus, 6)));
        } else {
            allObservables.add(Observable.just(new MapPointBinder(this.eventBus, 5)));
        }
        allObservables.add(Observable.just(new MyLocationBinder()));
        List<CityInfo> cityInfoList = this.locations.list();
        addCityCenters(allObservables, cityInfoList);
        addSeasons(allObservables, cityInfoList);
        return Observable.merge(allObservables);
    }

    private void addSeasons(List<Observable<ListItemBinder>> allObservables, List<CityInfo> cityInfoList) {
        for (CityInfo cityInfo : cityInfoList) {
            SupportedSeasons supportedSeasons = (SupportedSeasons) this.allSupportedSeasons.get(Integer.valueOf(cityInfo.getId()));
            for (String season : supportedSeasons.getSeasons()) {
                allObservables.add(prepareSeasonAllItems(supportedSeasons.getPoiCategory(season), this.context.getString(this.filtersSeasonsTitles.seasonData(season).allPoisTitleId) + getCityNameInBraces(cityInfo), this.pois.inLocation(cityInfo.getId())));
            }
        }
    }

    private void addCityCenters(List<Observable<ListItemBinder>> allObservables, List<CityInfo> cityInfoList) {
        for (CityInfo cityInfo : cityInfoList) {
            allObservables.add(Observable.just(new CenterItemBinder(LocationUtils.from(cityInfo.getLocation()), this.context.getString(C1178R.string.city_center) + getCityNameInBraces(cityInfo))));
        }
    }

    @NonNull
    private String getCityNameInBraces(CityInfo cityInfo) {
        return this.searchByLocation ? " (" + cityInfo.getCity() + ")" : BuildConfig.FLAVOR;
    }

    private Observable<ListItemBinder> prepareSeasonAllItems(String season, String title, List<Poi> poiList) {
        return Observable.from((Iterable) poiList).filter(DefaultDataCreator$$Lambda$1.lambdaFactory$(season)).map(DefaultDataCreator$$Lambda$2.lambdaFactory$()).toList().map(DefaultDataCreator$$Lambda$3.lambdaFactory$(title, season));
    }

    private Observable<ListItemBinder> prepareOtherPois(String... exceptCategories) {
        return Observable.merge(otherPoisForAllCities(this.locations.list(), exceptCategories)).distinct(DefaultDataCreator$$Lambda$4.lambdaFactory$()).map(DefaultDataCreator$$Lambda$5.lambdaFactory$()).toList().doOnNext(DefaultDataCreator$$Lambda$6.lambdaFactory$(this)).flatMap(DefaultDataCreator$$Lambda$7.lambdaFactory$());
    }

    /* synthetic */ void lambda$prepareOtherPois$5(List listItemBinders) {
        if (!listItemBinders.isEmpty()) {
            listItemBinders.add(0, new TitleListItem(this.context.getString(C1178R.string.pois)));
        }
    }

    @NonNull
    private List<Observable<PoiWithCityId>> otherPoisForAllCities(List<CityInfo> cities, String[] exceptCategories) {
        List<Observable<PoiWithCityId>> observables = new ArrayList(cities.size());
        for (CityInfo cityInfo : cities) {
            observables.add(poiOtherPoisForCity(exceptCategories, cityInfo));
        }
        return observables;
    }

    @NonNull
    private Observable<PoiWithCityId> poiOtherPoisForCity(String[] exceptCategories, CityInfo cityInfo) {
        int cityId = cityInfo.getId();
        return prepareOtherPoisForCity(cityId, this.pois.inLocation(cityId), (SupportedSeasons) this.allSupportedSeasons.get(Integer.valueOf(cityId)), exceptCategories);
    }

    private Observable<PoiWithCityId> prepareOtherPoisForCity(int cityId, List<Poi> poiList, SupportedSeasons supportedSeasons, String... exceptCategories) {
        return Observable.from((Iterable) poiList).distinct(DefaultDataCreator$$Lambda$8.lambdaFactory$()).filter(DefaultDataCreator$$Lambda$9.lambdaFactory$(exceptCategories, supportedSeasons)).map(DefaultDataCreator$$Lambda$10.lambdaFactory$(cityId));
    }

    static /* synthetic */ Boolean lambda$prepareOtherPoisForCity$6(String[] exceptCategories, SupportedSeasons supportedSeasons, Poi poi) {
        String poiCategory = poi.getCategory();
        if (Poi.CATEGORY_SKI.equals(poiCategory)) {
            return Boolean.valueOf(true);
        }
        for (String exceptCategory : exceptCategories) {
            if (exceptCategory.equals(poiCategory) || supportedSeasons.contains(exceptCategory)) {
                return Boolean.valueOf(false);
            }
        }
        return Boolean.valueOf(true);
    }

    private Observable<ListItemBinder> prepareSeasonPois() {
        Iterable seasonsObservables = new ArrayList();
        for (CityInfo cityInfo : this.locations.list()) {
            int cityId = cityInfo.getId();
            SupportedSeasons supportedSeasonsForCity = (SupportedSeasons) this.allSupportedSeasons.get(Integer.valueOf(cityId));
            for (String season : supportedSeasonsForCity.getSeasonsForSections()) {
                seasonsObservables.add(prepareCategoryForCity(cityInfo, this.pois.inLocation(cityId), supportedSeasonsForCity.getPoiCategory(season)).map(DefaultDataCreator$$Lambda$11.lambdaFactory$()).toList().doOnNext(DefaultDataCreator$$Lambda$12.lambdaFactory$(this, this.filtersSeasonsTitles.seasonData(season), cityInfo)).flatMap(DefaultDataCreator$$Lambda$13.lambdaFactory$()));
            }
        }
        return Observable.merge(seasonsObservables);
    }

    /* synthetic */ void lambda$prepareSeasonPois$9(SeasonFilterTitles seasonFilterTitles, CityInfo cityInfo, List listItemBinders) {
        if (!listItemBinders.isEmpty()) {
            listItemBinders.add(0, new TitleListItem(this.context.getString(seasonFilterTitles.categoryTitleId) + getCityNameInBraces(cityInfo)));
        }
    }

    private Observable<ListItemBinder> prepareHistoryItems() {
        return HotellookApplication.getApp().getComponent().getPoiHistoryDBHelper().getItemsForCities(this.locations.list(), 10).flatMap(DefaultDataCreator$$Lambda$14.lambdaFactory$()).map(DefaultDataCreator$$Lambda$15.lambdaFactory$()).toList().doOnNext(DefaultDataCreator$$Lambda$16.lambdaFactory$(this)).flatMap(DefaultDataCreator$$Lambda$17.lambdaFactory$());
    }

    /* synthetic */ void lambda$prepareHistoryItems$10(List listItemBinders) {
        if (!listItemBinders.isEmpty()) {
            listItemBinders.add(0, new TitleListItem(this.context.getString(C1178R.string.recent_search)));
        }
    }

    private Observable<PoiWithCityId> prepareCategoryForCity(CityInfo cityInfo, List<Poi> poiList, String category) {
        return Observable.from((Iterable) poiList).filter(DefaultDataCreator$$Lambda$18.lambdaFactory$(category)).map(DefaultDataCreator$$Lambda$19.lambdaFactory$(cityInfo));
    }

    private Observable<ListItemBinder> prepareSection(String category, String title) {
        List<CityInfo> locations = this.locations.list();
        Iterable sectionItems = new ArrayList(locations.size());
        for (CityInfo cityInfo : locations) {
            sectionItems.add(prepareCategoryForCity(cityInfo, this.pois.inLocation(cityInfo.getId()), category));
        }
        return Observable.merge(sectionItems).distinct(DefaultDataCreator$$Lambda$20.lambdaFactory$()).map(DefaultDataCreator$$Lambda$21.lambdaFactory$()).toList().doOnNext(DefaultDataCreator$$Lambda$22.lambdaFactory$(title)).flatMap(DefaultDataCreator$$Lambda$23.lambdaFactory$());
    }

    static /* synthetic */ void lambda$prepareSection$15(String title, List listItemBinders) {
        if (!listItemBinders.isEmpty()) {
            listItemBinders.add(0, new TitleListItem(title));
        }
    }
}
