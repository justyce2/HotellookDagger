package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.api.exchange.ExchangeRateSource;
import com.hotellook.core.api.HotellookService;
import com.hotellook.dependencies.qualifier.Token;
import com.hotellook.search.AsyncOffersSource;
import com.hotellook.search.DumpCacher;
import com.hotellook.search.DumpSource;
import com.hotellook.search.HotelDetailSource;
import com.hotellook.search.LocationDumpSource;
import com.hotellook.search.LocationIdsSource;
import com.hotellook.search.LocationsSource;
import com.hotellook.search.NearbyCitiesAvailabilitySource;
import com.hotellook.search.RoomTypesSource;
import com.hotellook.ui.screen.hotel.api.BookingSource;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.utils.EventBus;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class DataSourceModule {
    @Singleton
    @Provides
    public DumpCacher provideDumpCacher(Application app) {
        return new DumpCacher(app);
    }

    @Provides
    public DumpSource provideDump(LocationsSource locationsSource, LocationDumpSource locationDumpSource) {
        return new DumpSource(locationsSource, locationDumpSource);
    }

    @Provides
    public LocationsSource provideLocationsSource(HotellookService hotellookService) {
        return new LocationsSource(hotellookService);
    }

    @Provides
    public LocationDumpSource provideHotelsSource(HotellookService hotellookService) {
        return new LocationDumpSource(hotellookService);
    }

    @Provides
    public AsyncOffersSource provideAsyncOffersSource(HotellookService api, @Token String token) {
        return new AsyncOffersSource(api, token);
    }

    @Provides
    public HotelDetailSource provideHotelDetailSource(HotellookService hotellookService) {
        return new HotelDetailSource(hotellookService);
    }

    @Provides
    public HotelDataSource provideHotelDataSource(Application app, EventBus eventBus) {
        return new HotelDataSource(app, eventBus);
    }

    @Provides
    public LocationIdsSource provideLocationIdsSource(HotellookService hotellookService) {
        return new LocationIdsSource(hotellookService);
    }

    @Provides
    public RoomTypesSource provideRoomTypesSource(HotellookService hotellookService) {
        return new RoomTypesSource(hotellookService);
    }

    @Provides
    public ExchangeRateSource provideExchangeRateSource(HotellookService hotellookService) {
        return new ExchangeRateSource(hotellookService);
    }

    @Provides
    public BookingSource provideDeeplinkSource(HotellookService api, @Token String token, Application app) {
        return new BookingSource(api, token, app);
    }

    @Provides
    public NearbyCitiesAvailabilitySource nearbySearchAvailabilitySource(HotellookService api) {
        return new NearbyCitiesAvailabilitySource(api);
    }
}
