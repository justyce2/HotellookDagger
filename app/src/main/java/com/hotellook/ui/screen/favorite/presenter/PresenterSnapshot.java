package com.hotellook.ui.screen.favorite.presenter;

import com.hotellook.interactor.favorites.FavoritesParsedData;

public class PresenterSnapshot {
    public final FavoritesParsedData favoritesParsedData;
    public final int selectedPosition;

    public PresenterSnapshot(int selectedPosition, FavoritesParsedData favoritesParsedData) {
        this.selectedPosition = selectedPosition;
        this.favoritesParsedData = favoritesParsedData;
    }
}
