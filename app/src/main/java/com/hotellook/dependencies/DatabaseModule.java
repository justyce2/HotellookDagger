package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.db.DatabaseHelper;
import com.hotellook.db.FavoritesRepository;
import com.hotellook.db.PoiHistoryDBHelper;
import com.hotellook.db.SearchDestinationFavoritesCache;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import timber.log.Timber;

@Module
public class DatabaseModule {
    @Singleton
    @Provides
    public DatabaseHelper provideDbHelper(Application app) {
        return new DatabaseHelper(app);
    }

    @Provides
    public FavoritesRepository provideFavoritesRepository(DatabaseHelper databaseHelper) {
        Timber.tag("FavoritesRepository").m707e("Got original favorites helper", new Object[0]);
        return new FavoritesRepository(databaseHelper);
    }

    @Singleton
    @Provides
    public SearchDestinationFavoritesCache provideLocationFavoritesCache(FavoritesRepository repository) {
        return new SearchDestinationFavoritesCache(repository);
    }

    @Provides
    public PoiHistoryDBHelper providePoiHistoryHelper(DatabaseHelper databaseHelper) {
        return new PoiHistoryDBHelper(databaseHelper);
    }
}
