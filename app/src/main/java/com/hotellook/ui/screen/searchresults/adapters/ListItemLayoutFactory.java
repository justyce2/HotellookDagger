package com.hotellook.ui.screen.searchresults.adapters;

import android.content.Context;
import com.hotellook.C1178R;
import com.hotellook.ui.screen.searchresults.adapters.itemsize.ItemLayoutParamsGenerator;
import com.hotellook.ui.screen.searchresults.adapters.itemsize.StaggeredGridItemLayoutParamsGenerator;
import com.hotellook.ui.screen.searchresults.adapters.layoutmanager.LayoutManagerWrapper;
import com.hotellook.ui.screen.searchresults.adapters.layoutmanager.LinearManagerWrapper;
import com.hotellook.ui.screen.searchresults.adapters.layoutmanager.StaggeredGridWrapper;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.Size;

public class ListItemLayoutFactory {
    public static LayoutManagerWrapper createLayoutManager(Context context) {
        if (!AndroidUtils.isPhone(context) || isLandscapeLargePhone(context)) {
            return new StaggeredGridWrapper();
        }
        return new LinearManagerWrapper(context);
    }

    public static ItemLayoutParamsGenerator createHotelImageSize(Context context, Size imageSize) {
        if (AndroidUtils.isTablet(context)) {
            return new StaggeredGridItemLayoutParamsGenerator(imageSize, context.getResources().getDimensionPixelSize(C1178R.dimen.tablet_small_card_additional_height));
        }
        if (isLandscapeLargePhone(context)) {
            return new PhoneGridItemsParamsGenerator(imageSize, AndroidUtils.getDisplaySize(context));
        }
        return null;
    }

    private static boolean isLandscapeLargePhone(Context context) {
        return AndroidUtils.isLandscape(context) && AndroidUtils.isLargePhone(context);
    }
}
