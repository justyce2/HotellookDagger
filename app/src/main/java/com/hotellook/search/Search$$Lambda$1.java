package com.hotellook.search;

import com.hotellook.badges.Badges;
import com.hotellook.db.SearchDestinationFavoritesCache;
import com.hotellook.filters.Filters;
import com.hotellook.ui.screen.searchresults.adapters.cards.controller.Cards;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class Search$$Lambda$1 implements Action1 {
    private final Filters arg$1;
    private final Badges arg$2;
    private final Cards arg$3;
    private final SearchDestinationFavoritesCache arg$4;

    private Search$$Lambda$1(Filters filters, Badges badges, Cards cards, SearchDestinationFavoritesCache searchDestinationFavoritesCache) {
        this.arg$1 = filters;
        this.arg$2 = badges;
        this.arg$3 = cards;
        this.arg$4 = searchDestinationFavoritesCache;
    }

    public static Action1 lambdaFactory$(Filters filters, Badges badges, Cards cards, SearchDestinationFavoritesCache searchDestinationFavoritesCache) {
        return new Search$$Lambda$1(filters, badges, cards, searchDestinationFavoritesCache);
    }

    @Hidden
    public void call(Object obj) {
        Search.lambda$new$0(this.arg$1, this.arg$2, this.arg$3, this.arg$4, (SearchData) obj);
    }
}
