package com.hotellook.api.dataloaders;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.hotellook.HotellookApplication;
import com.hotellook.core.api.HotellookImageUrlProvider;
import com.hotellook.core.api.params.CityImageParams;
import com.hotellook.utils.Size;

public abstract class CityImageLoader {
    private final HotellookImageUrlProvider mUrlProvider;

    public abstract void setUpHierarchy(SimpleDraweeView simpleDraweeView);

    public CityImageLoader(SimpleDraweeView draweeView) {
        this.mUrlProvider = HotellookApplication.getApp().getComponent().getHotelImageUrlProvider();
        setUpHierarchy(draweeView);
    }

    public void loadImage(SimpleDraweeView draweeView, Size size, int cityId) {
        ImageRequest request = ImageRequest.fromUri(getImageUrl(cityId, size));
        ((GenericDraweeHierarchy) draweeView.getHierarchy()).reset();
        draweeView.setController(((PipelineDraweeControllerBuilder) ((PipelineDraweeControllerBuilder) Fresco.newDraweeControllerBuilder().setImageRequest(request)).setOldController(draweeView.getController())).build());
    }

    public String getImageUrl(int cityId, Size size) {
        CityImageParams params = new CityImageParams();
        params.setHeight(size.getHeight());
        params.setWidth(size.getWidth());
        params.setCityId(cityId);
        return this.mUrlProvider.createCityPhotoUrl(params);
    }
}
