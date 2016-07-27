package com.hotellook.ui.screen.favorite.presenter;

import com.hotellook.interactor.favorites.HotelsByCities;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class FavoritesPresenter$$Lambda$2 implements Func1 {
    private final FavoritesPresenter arg$1;

    private FavoritesPresenter$$Lambda$2(FavoritesPresenter favoritesPresenter) {
        this.arg$1 = favoritesPresenter;
    }

    public static Func1 lambdaFactory$(FavoritesPresenter favoritesPresenter) {
        return new FavoritesPresenter$$Lambda$2(favoritesPresenter);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.toParsedData((HotelsByCities) obj);
    }
}
