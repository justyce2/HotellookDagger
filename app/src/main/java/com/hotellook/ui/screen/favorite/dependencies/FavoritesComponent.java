package com.hotellook.ui.screen.favorite.dependencies;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import com.hotellook.dependencies.scope.MvpScope;
import com.hotellook.ui.screen.favorite.presenter.FavoritesPresenter;
import com.hotellook.ui.screen.favorite.view.FavoriteHotelsAdapter;
import com.hotellook.ui.screen.searchresults.adapters.layoutmanager.LayoutManagerWrapper;
import dagger.Component;

@MvpScope
@Component
public interface FavoritesComponent {
    FavoriteHotelsAdapter hotelsAdapter();

    LayoutManagerWrapper layoutManager();

    @Nullable
    ItemAnimator listItemAnimator();

    FavoritesPresenter presenter();
}
