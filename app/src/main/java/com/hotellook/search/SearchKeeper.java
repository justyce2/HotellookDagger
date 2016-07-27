package com.hotellook.search;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.hotellook.utils.Preconditions;

public class SearchKeeper {
    @Nullable
    private Search lastSearch;

    @Nullable
    public Search lastSearch() {
        return this.lastSearch;
    }

    @NonNull
    public Search lastSearchOrThrowException() {
        Preconditions.checkNotNull(this.lastSearch, "Search is not available.");
        return this.lastSearch;
    }

    public void clear() {
        if (this.lastSearch != null) {
            this.lastSearch.filters().unsubscribeFromEvents();
        }
        this.lastSearch = null;
    }

    public void add(Search search) {
        clear();
        this.lastSearch = search;
        this.lastSearch.filters().subscribeToEvents();
    }
}
