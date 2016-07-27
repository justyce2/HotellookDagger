package com.hotellook.interactor.favorites;

public class FavoriteCityData {
    public final int count;
    public final int id;
    public final String name;

    public FavoriteCityData(int id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public FavoriteCityData withCount(int count) {
        return new FavoriteCityData(this.id, this.name, count);
    }
}
