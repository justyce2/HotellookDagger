package com.hotellook.interactor.favorites;

import android.support.annotation.NonNull;
import com.hotellook.db.FavoritesRepository;
import com.hotellook.db.data.FavoriteHotelDataEntity;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

public class FavoritesInteractor {
    private final FavoritesRepository favoritesRepository;

    @Inject
    public FavoritesInteractor(FavoritesRepository favoritesRepository) {
        this.favoritesRepository = favoritesRepository;
    }

    public Observable<HotelsByCities> favoriteData() {
        return this.favoritesRepository.all().map(FavoritesInteractor$$Lambda$1.lambdaFactory$(this));
    }

    @NonNull
    private HotelsByCities convert(List<FavoriteHotelDataEntity> favoriteHotelDataEntities) {
        HotelsByCities hotels = new HotelsByCities();
        for (FavoriteHotelDataEntity hotel : favoriteHotelDataEntities) {
            hotels.add(Integer.valueOf(hotel.getLocationId()), hotel);
        }
        return hotels;
    }
}
