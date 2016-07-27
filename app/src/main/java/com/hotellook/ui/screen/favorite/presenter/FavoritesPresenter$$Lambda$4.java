package com.hotellook.ui.screen.favorite.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class FavoritesPresenter$$Lambda$4 implements Action1 {
    private final FavoritesPresenter arg$1;

    private FavoritesPresenter$$Lambda$4(FavoritesPresenter favoritesPresenter) {
        this.arg$1 = favoritesPresenter;
    }

    public static Action1 lambdaFactory$(FavoritesPresenter favoritesPresenter) {
        return new FavoritesPresenter$$Lambda$4(favoritesPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.onLoadError((Throwable) obj);
    }
}
