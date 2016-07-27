package com.hotellook.db;

import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.events.FavoriteHotelAddedEvent;
import com.hotellook.events.FavoriteHotelRemovedEvent;
import com.squareup.otto.Subscribe;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchDestinationFavoritesCache {
    private final Set<Long> favoriteHotelsIds;
    private final FavoritesRepository repository;

    public SearchDestinationFavoritesCache(FavoritesRepository repository) {
        this.favoriteHotelsIds = new HashSet();
        this.repository = repository;
    }

    public void init(List<CityInfo> locations) {
        this.favoriteHotelsIds.clear();
        for (CityInfo location : locations) {
            this.favoriteHotelsIds.addAll(this.repository.getAllFavoriteHotelIdsInLocation(location.getId()));
        }
    }

    public boolean contains(long hotelId) {
        return this.favoriteHotelsIds.contains(Long.valueOf(hotelId));
    }

    @Subscribe
    public void onFavoriteHotelAdded(FavoriteHotelAddedEvent event) {
        this.favoriteHotelsIds.add(event.id);
    }

    @Subscribe
    public void onFavoriteHotelRemoved(FavoriteHotelRemovedEvent event) {
        this.favoriteHotelsIds.remove(event.id);
    }

    public Set<Long> getAll() {
        return this.favoriteHotelsIds;
    }
}
