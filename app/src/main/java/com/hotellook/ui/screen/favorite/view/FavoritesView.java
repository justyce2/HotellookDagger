package com.hotellook.ui.screen.favorite.view;

import android.content.res.Resources;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hotellook.db.data.FavoriteHotelDataEntity;
import com.hotellook.interactor.favorites.FavoriteCityData;
import java.util.List;

public interface FavoritesView extends MvpView {
    Resources getResources();

    void hideCitySelector();

    void showCitiesSelector(List<FavoriteCityData> list, int i);

    void showHotels(List<FavoriteHotelDataEntity> list);

    void showPlaceHolderAddFirstFromResults();

    void showPlaceHolderAddFirstHotelAndStartSearch();

    void showPlaceholderSwitchCity();

    void showProgress();
}
