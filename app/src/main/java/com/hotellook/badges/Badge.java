package com.hotellook.badges;

import com.hotellook.badges.selectors.HotelSelector;

public class Badge {
    public final HotelSelector badgeSelector;
    public final int color;
    public final String name;
    public final String text;

    public Badge(String text, int color, HotelSelector badgeSelector, String name) {
        this.text = text;
        this.color = color;
        this.badgeSelector = badgeSelector;
        this.name = name;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Badge badge = (Badge) o;
        if (this.color == badge.color) {
            return this.text.equals(badge.text);
        }
        return false;
    }

    public int hashCode() {
        return (this.text.hashCode() * 31) + this.color;
    }
}
