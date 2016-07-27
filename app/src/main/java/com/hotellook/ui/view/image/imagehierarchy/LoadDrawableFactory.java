package com.hotellook.ui.view.image.imagehierarchy;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.api.LoadDrawable;

public class LoadDrawableFactory {
    private static final int PROGRESS_ROTATE_INTERVAL = 1500;

    public static Drawable create() {
        Resources res = HotellookApplication.getApp().getResources();
        int progressSize = res.getDimensionPixelSize(C1178R.dimen.sr_progress_size);
        int progressArcWidth = res.getDimensionPixelOffset(C1178R.dimen.sr_arc_width);
        int progressBackColor = res.getColor(C1178R.color.black_pct_18);
        LoadDrawable progressDrawable = new LoadDrawable();
        progressDrawable.setSize(progressSize);
        progressDrawable.setArcWidth(progressArcWidth);
        progressDrawable.setBackColor(progressBackColor);
        return new AutoRotateDrawable(progressDrawable, PROGRESS_ROTATE_INTERVAL);
    }
}
