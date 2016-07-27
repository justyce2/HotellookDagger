package com.hotellook.ui.view.bottomnavigation;

import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import java.util.Collections;
import java.util.List;

class NavigationMenu {
    @NonNull
    public final List<Item> items;

    static class Item {
        public final Drawable icon;
        public final int id;
        public final CharSequence title;

        public Item(@IdRes int id, @NonNull Drawable icon, @NonNull CharSequence title) {
            this.id = id;
            this.icon = icon;
            this.title = title;
        }
    }

    public NavigationMenu(@NonNull List<Item> items) {
        this.items = Collections.unmodifiableList(items);
    }
}
