package com.hotellook.interactor.favorites;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func1;

final /* synthetic */ class FavoritesInteractor$$Lambda$1 implements Func1 {
    private final FavoritesInteractor arg$1;

    private FavoritesInteractor$$Lambda$1(FavoritesInteractor favoritesInteractor) {
        this.arg$1 = favoritesInteractor;
    }

    public static Func1 lambdaFactory$(FavoritesInteractor favoritesInteractor) {
        return new FavoritesInteractor$$Lambda$1(favoritesInteractor);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.convert((List) obj);
    }
}
