package com.hotellook.ui.screen.favorite.view;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class FavoritesFragment$$Lambda$1 implements Runnable {
    private final FavoritesFragment arg$1;

    private FavoritesFragment$$Lambda$1(FavoritesFragment favoritesFragment) {
        this.arg$1 = favoritesFragment;
    }

    public static Runnable lambdaFactory$(FavoritesFragment favoritesFragment) {
        return new FavoritesFragment$$Lambda$1(favoritesFragment);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$showCitiesSelector$0();
    }
}
