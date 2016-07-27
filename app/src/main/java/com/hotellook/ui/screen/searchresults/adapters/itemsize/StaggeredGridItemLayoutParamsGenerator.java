package com.hotellook.ui.screen.searchresults.adapters.itemsize;

import android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams;
import android.view.View;
import com.hotellook.utils.Size;
import pl.charmas.android.reactivelocation.C1822R;

public class StaggeredGridItemLayoutParamsGenerator implements ItemLayoutParamsGenerator {
    public static final int PERIOD = 5;
    private final int additionalHeightForSmallItem;
    private final Size imageSize;

    public StaggeredGridItemLayoutParamsGenerator(Size imageSize, int additionalHeightForSmallItem) {
        this.imageSize = imageSize;
        this.additionalHeightForSmallItem = additionalHeightForSmallItem;
    }

    public void generateFullWidthItemsParams(View view) {
        LayoutParams layoutParams = new LayoutParams(view.getLayoutParams());
        layoutParams.setFullSpan(true);
        view.setLayoutParams(layoutParams);
    }

    public ListItemImageParams generateHotelItemParams(int index, int count) {
        int type = index % PERIOD;
        boolean isLast = isLast(index, count);
        switch (type) {
            case C1822R.styleable.SignInButton_buttonSize /*0*/:
                if (isLast) {
                    return createFullLayoutParams();
                }
                return createQuarterLayoutParams();
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                return createQuarterLayoutParams();
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
                if (isLast) {
                    return createFullLayoutParams();
                }
                return createQuarterLayoutParams();
            case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                return createQuarterLayoutParams();
            case C1822R.styleable.MapAttrs_cameraTilt /*4*/:
                return createFullLayoutParams();
            default:
                return createFullLayoutParams();
        }
    }

    private boolean isLast(int index, int count) {
        return index == count + -1;
    }

    private ListItemImageParams createFullLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-2, this.imageSize.getHeight());
        layoutParams.setFullSpan(true);
        return new ListItemImageParams(layoutParams, this.imageSize);
    }

    private ListItemImageParams createQuarterLayoutParams() {
        int height = (this.imageSize.getHeight() / 2) + this.additionalHeightForSmallItem;
        LayoutParams layoutParams = new LayoutParams(-2, height);
        layoutParams.setFullSpan(false);
        return new ListItemImageParams(layoutParams, new Size(this.imageSize.getWidth() / 2, height));
    }
}
