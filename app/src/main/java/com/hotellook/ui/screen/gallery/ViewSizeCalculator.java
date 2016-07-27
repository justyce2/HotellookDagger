package com.hotellook.ui.screen.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.Display;
import com.hotellook.C1178R;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.Size;

public class ViewSizeCalculator {
    public static final int GRID_SPAN_COUNT = 4;
    private static final float IMAGE_RATION = 1.78f;
    private final Context context;
    private final Display display;
    private final Resources resources;

    public ViewSizeCalculator(Context context) {
        this((Activity) context);
    }

    public ViewSizeCalculator(Activity activity) {
        this.display = activity.getWindowManager().getDefaultDisplay();
        this.resources = activity.getResources();
        this.context = activity;
    }

    public Size calculateHotelScreenPhotoSize(Context context) {
        if (AndroidUtils.isLandscape(context)) {
            return calculateHotelScreenPhotoSizeForWideScreens();
        }
        return calculatePagerImageSize();
    }

    @NonNull
    public Size calculateHotelScreenPhotoSizeForWideScreens() {
        return new Size(getScreenWidth(), getScreenHeight());
    }

    public Size calculatePagerImageSize() {
        int screenWidth = AndroidUtils.isPortrait(this.context) ? getScreenWidth() : getScreenHeight();
        return new Size(screenWidth, (int) (((float) screenWidth) / IMAGE_RATION));
    }

    public int calculateListWidth() {
        if (AndroidUtils.isPortrait(this.context)) {
            return getScreenWidth();
        }
        if (AndroidUtils.isTablet(this.context)) {
            return getScreenHeight();
        }
        if (AndroidUtils.isLarge(this.context)) {
            return getScreenWidth();
        }
        return getScreenHeight();
    }

    public int calculateControlsWidth() {
        int width;
        if (AndroidUtils.isPortrait(this.context)) {
            width = getScreenWidth();
        } else {
            width = getScreenHeight();
        }
        if (AndroidUtils.isTablet(this.context)) {
            return width / 2;
        }
        return width;
    }

    public int calculateGridWidth(int contentWidth) {
        int maxHotelItemFragmentWidth = this.resources.getDimensionPixelSize(C1178R.dimen.max_hotel_item_fragment_width);
        return contentWidth > maxHotelItemFragmentWidth ? maxHotelItemFragmentWidth : contentWidth;
    }

    public Size calculateGridImageSize(int contentWidth) {
        int itemsPadding = this.resources.getDimensionPixelOffset(C1178R.dimen.image_grid_item_padding) * 3;
        int imageSize = (calculateGridWidth(contentWidth) - itemsPadding) / calculateGridSpanCount();
        return new Size((int) (((float) imageSize) * IMAGE_RATION), imageSize);
    }

    public int calculateGridSpanCount() {
        return GRID_SPAN_COUNT;
    }

    public int getScreenWidth() {
        Point size = new Point();
        this.display.getSize(size);
        return size.x;
    }

    public int getScreenHeight() {
        Point size = new Point();
        this.display.getSize(size);
        return size.y + AndroidUtils.getNavBarBottomHeight();
    }

    public Size calculateGalleryImageSize() {
        int screenWidth = getScreenWidth();
        return new Size(screenWidth, (int) (((float) screenWidth) / IMAGE_RATION));
    }
}
