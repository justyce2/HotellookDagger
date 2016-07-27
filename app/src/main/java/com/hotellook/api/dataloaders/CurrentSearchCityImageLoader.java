package com.hotellook.api.dataloaders;

import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hotellook.ui.view.image.imagehierarchy.ImageHierarchyFactory;

public class CurrentSearchCityImageLoader extends CityImageLoader {
    public CurrentSearchCityImageLoader(SimpleDraweeView draweeView) {
        super(draweeView);
    }

    public void setUpHierarchy(SimpleDraweeView draweeView) {
        draweeView.setHierarchy(new GenericDraweeHierarchyBuilder(draweeView.getResources()).setFadeDuration(ImageHierarchyFactory.FADE_DURATION).build());
    }
}
