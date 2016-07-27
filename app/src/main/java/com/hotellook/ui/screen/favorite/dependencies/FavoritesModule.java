package com.hotellook.ui.screen.favorite.dependencies;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import com.hotellook.ui.screen.gallery.ViewSizeCalculator;
import com.hotellook.ui.screen.searchresults.adapters.GridItemLayoutParamsGenerator;
import com.hotellook.ui.screen.searchresults.adapters.PhoneGridItemsParamsGenerator;
import com.hotellook.ui.screen.searchresults.adapters.animations.HotelListItemAnimator;
import com.hotellook.ui.screen.searchresults.adapters.itemsize.ItemLayoutParamsGenerator;
import com.hotellook.ui.screen.searchresults.adapters.layoutmanager.LayoutManagerWrapper;
import com.hotellook.ui.screen.searchresults.adapters.layoutmanager.LinearManagerWrapper;
import com.hotellook.ui.screen.searchresults.adapters.layoutmanager.StaggeredGridWrapper;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.Size;
import dagger.Module;
import dagger.Provides;

@Module
public class FavoritesModule {
    @Nullable
    @Provides
    public ItemLayoutParamsGenerator provideItemLayoutParamsGenerator(Context context) {
        Size imageSize = new ViewSizeCalculator(context).calculatePagerImageSize();
        if (isLandscapeLargePhone(context)) {
            return new PhoneGridItemsParamsGenerator(imageSize, 2, AndroidUtils.getDisplaySize(context));
        }
        if (AndroidUtils.isPhone(context)) {
            return null;
        }
        if (AndroidUtils.isPortrait(context)) {
            return new GridItemLayoutParamsGenerator(imageSize, 2);
        }
        return new GridItemLayoutParamsGenerator(imageSize, 3);
    }

    @Nullable
    @Provides
    public ItemAnimator provideListItemAnimator(Application context) {
        return new HotelListItemAnimator();
    }

    @Provides
    public LayoutManagerWrapper provideLayoutManager(Application context) {
        if (isLandscapeLargePhone(context)) {
            return new StaggeredGridWrapper(2);
        }
        if (AndroidUtils.isPhone(context)) {
            return new LinearManagerWrapper(context);
        }
        if (AndroidUtils.isPortrait(context)) {
            return new StaggeredGridWrapper(2);
        }
        return new StaggeredGridWrapper(3);
    }

    private boolean isLandscapeLargePhone(Context context) {
        return AndroidUtils.isLandscape(context) && AndroidUtils.isLargePhone(context);
    }
}
