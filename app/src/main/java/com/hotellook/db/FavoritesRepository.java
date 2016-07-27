package com.hotellook.db;

import com.hotellook.HotellookApplication;
import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.db.data.FavoriteHotelDataEntity;
import com.hotellook.events.FavoriteHotelAddedEvent;
import com.hotellook.events.FavoriteHotelRemovedEvent;
import com.hotellook.search.SearchParams;
import com.hotellook.ui.screen.hotel.data.BasicHotelData;
import com.j256.ormlite.stmt.QueryBuilder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

public class FavoritesRepository {
    public static final String FIELD_LOCATION_ID = "locationId";
    private DatabaseHelper dbHelper;

    /* renamed from: com.hotellook.db.FavoritesRepository.1 */
    class C11961 implements OnSubscribe<List<FavoriteHotelDataEntity>> {
        C11961() {
        }

        public void call(Subscriber<? super List<FavoriteHotelDataEntity>> subscriber) {
            try {
                List<FavoriteHotelDataEntity> items = FavoritesRepository.this.dbHelper.getFavoriteHotelDataDao().queryForAll();
                if (items != null) {
                    subscriber.onNext(items);
                }
                subscriber.onCompleted();
            } catch (SQLException e) {
                subscriber.onError(e);
            }
        }
    }

    public FavoritesRepository(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public boolean isInFavorites(long id) {
        try {
            if (((FavoriteHotelDataEntity) this.dbHelper.getFavoriteHotelDataDao().queryForId(Long.valueOf(id))) != null) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addToFavorites(BasicHotelData basicHotelData, CityInfo cityInfo, SearchParams searchParams) {
        try {
            this.dbHelper.getFavoriteHotelDataDao().createOrUpdate(new FavoriteHotelDataEntity(basicHotelData, cityInfo, searchParams));
            postHotelAddedEvent(basicHotelData.id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void postHotelAddedEvent(long id) {
        HotellookApplication.eventBus().post(new FavoriteHotelAddedEvent(Long.valueOf(id)));
    }

    private void postHotelRemovedEvent(long id) {
        HotellookApplication.eventBus().post(new FavoriteHotelRemovedEvent(Long.valueOf(id)));
    }

    public void addToFavorites(BasicHotelData basicHotelData, CityInfo cityInfo) {
        try {
            this.dbHelper.getFavoriteHotelDataDao().createOrUpdate(new FavoriteHotelDataEntity(basicHotelData, cityInfo));
            postHotelAddedEvent(basicHotelData.id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(BasicHotelData basicHotelData, CityInfo cityInfo, SearchParams searchParams) {
        try {
            this.dbHelper.getFavoriteHotelDataDao().update(new FavoriteHotelDataEntity(basicHotelData, cityInfo, searchParams));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(HotelData hotelData, CityInfo cityInfo, SearchParams searchParams) {
        try {
            this.dbHelper.getFavoriteHotelDataDao().update(new FavoriteHotelDataEntity(hotelData, cityInfo, searchParams));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeFromFavorites(long id) {
        try {
            this.dbHelper.getFavoriteHotelDataDao().deleteById(Long.valueOf(id));
            postHotelRemovedEvent(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long size() {
        try {
            return this.dbHelper.getFavoriteHotelDataDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Deprecated
    public List<FavoriteHotelDataEntity> getAll() {
        try {
            return this.dbHelper.getFavoriteHotelDataDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    public Observable<List<FavoriteHotelDataEntity>> all() {
        return Observable.create(new C11961());
    }

    public List<FavoriteHotelDataEntity> getAllInLocation(int locationId) {
        try {
            QueryBuilder<FavoriteHotelDataEntity, Long> queryBuilder = this.dbHelper.getFavoriteHotelDataDao().queryBuilder();
            queryBuilder.where().eq(FIELD_LOCATION_ID, Integer.valueOf(locationId));
            return this.dbHelper.getFavoriteHotelDataDao().query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Set<Long> getAllFavoriteHotelIdsInLocation(int locationId) {
        return extractFavoriteHotelsIds(getAllInLocation(locationId));
    }

    private Set<Long> extractFavoriteHotelsIds(List<FavoriteHotelDataEntity> favoritesInLocation) {
        Set<Long> favoriteHotelsIds = new HashSet(favoritesInLocation.size());
        for (FavoriteHotelDataEntity favorite : favoritesInLocation) {
            favoriteHotelsIds.add(Long.valueOf(favorite.getHotelId()));
        }
        return favoriteHotelsIds;
    }
}
