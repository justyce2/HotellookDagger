package com.hotellook.ui.screen.searchresults;

import android.content.Context;
import android.view.View;
import com.hotellook.ui.screen.gallery.ViewSizeCalculator;
import com.hotellook.utils.AndroidUtils;

public class ViewHorizontalAligner {
    private final int padding;

    public ViewHorizontalAligner(Context context, int width) {
        int screenWidth = AndroidUtils.getDisplaySize(context).x;
        if (screenWidth > width) {
            this.padding = (screenWidth - width) / 2;
        } else {
            this.padding = 0;
        }
    }

    public ViewHorizontalAligner(Context context) {
        this(context, new ViewSizeCalculator(context).calculatePagerImageSize().getWidth());
    }

    public void alignWithPaddings(View v) {
        v.setPadding(v.getPaddingLeft() + this.padding, v.getPaddingTop(), v.getPaddingRight() + this.padding, v.getPaddingBottom());
    }
}
