package com.hotellook.api.dataloaders;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import com.facebook.drawee.drawable.ScalingUtils.ScaleType;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hotellook.C1178R;
import com.hotellook.ui.view.image.imagehierarchy.ImageHierarchyFactory;

public class SearchFormCityImageLoader extends CityImageLoader {
    public SearchFormCityImageLoader(SimpleDraweeView draweeView) {
        super(draweeView);
    }

    public void setUpHierarchy(SimpleDraweeView draweeView) {
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(draweeView.getResources());
        builder.setFadeDuration(ImageHierarchyFactory.FADE_DURATION);
        builder.setPlaceholderImage(ContextCompat.getDrawable(draweeView.getContext(), C1178R.drawable.img_city_default), ScaleType.CENTER_CROP);
        builder.setOverlay(new ColorDrawable(ContextCompat.getColor(draweeView.getContext(), C1178R.color.sf_image_overlay)));
        draweeView.setHierarchy(builder.build());
    }
}
