package com.hotellook.ui.screen.favorite.presenter;

import com.hotellook.interactor.favorites.HotelsByCities;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class FavoritesPresenter$$Lambda$1 implements Action1 {
    private final FavoritesPresenter arg$1;

    private FavoritesPresenter$$Lambda$1(FavoritesPresenter favoritesPresenter) {
        this.arg$1 = favoritesPresenter;
    }

    public static Action1 lambdaFactory$(FavoritesPresenter favoritesPresenter) {
        return new FavoritesPresenter$$Lambda$1(favoritesPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.addCurrentSearch((HotelsByCities) obj);
    }
}
