package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.badges.Badges;
import com.hotellook.db.FavoritesRepository;
import com.hotellook.filters.Filters;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.search.DumpCacher;
import com.hotellook.search.SearchEngine;
import com.hotellook.search.SearchKeeper;
import com.hotellook.ui.screen.searchresults.adapters.cards.controller.Cards;
import com.hotellook.ui.screen.searchresults.adapters.cards.controller.PhoneCards;
import com.hotellook.ui.screen.searchresults.adapters.cards.controller.TabletCards;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CommonPreferences;
import com.hotellook.utils.EventBus;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class SearchModule {
    @Singleton
    @Provides
    public SearchKeeper provideSearch() {
        return new SearchKeeper();
    }

    @Provides
    public SearchEngine provideSearchEngine(Application app, DumpCacher dumpCacher, EventBus eventBus, FavoritesRepository favoritesRepository) {
        return new SearchEngine(app, dumpCacher, eventBus, favoritesRepository);
    }

    @Provides
    public Badges provideBadges(Application app) {
        return new Badges(app);
    }

    @Provides
    public Filters provideFilters(PersistentFilters persistentFilters) {
        return new Filters(persistentFilters);
    }

    @Provides
    public PersistentFilters provideGlobalFilters(Application app) {
        return new PersistentFilters(app);
    }

    @Provides
    public Cards provideCards(Application app, CommonPreferences commonPreferences, PersistentFilters persistentFilters) {
        if (AndroidUtils.isPhone(app)) {
            return new PhoneCards(app, commonPreferences, persistentFilters);
        }
        return new TabletCards(persistentFilters);
    }
}
