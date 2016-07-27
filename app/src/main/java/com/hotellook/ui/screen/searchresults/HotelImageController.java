package com.hotellook.ui.screen.searchresults;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.core.api.HotellookImageUrlProvider;
import com.hotellook.core.api.params.HotelImageParams;
import com.hotellook.ui.view.image.imagehierarchy.ImageHierarchyFactory;
import com.hotellook.utils.Size;

public class HotelImageController {
    private final ImageHierarchyFactory hierarchyFactory;
    private SimpleDraweeView image;
    private final Size imageSize;
    private final HotellookImageUrlProvider imageUrlProvider;
    private final int index;
    private View view;

    public HotelImageController(int index, Size imageSize, ImageHierarchyFactory hierarchyFactory) {
        this.index = index;
        this.imageSize = imageSize;
        this.imageUrlProvider = HotellookApplication.getApp().getComponent().getHotelImageUrlProvider();
        this.hierarchyFactory = hierarchyFactory;
    }

    public void createView(Context context, OnClickListener onClickListener) {
        FrameLayout view = new FrameLayout(context);
        view.setLayoutParams(new LayoutParams(-1, -1));
        View touchable = new View(context);
        touchable.setBackgroundResource(C1178R.drawable.bkg_selectable_rect_simple);
        if (onClickListener != null) {
            touchable.setOnClickListener(onClickListener);
        }
        this.image = new SimpleDraweeView(context, this.hierarchyFactory.create());
        view.addView(this.image, new ViewGroup.LayoutParams(-1, -1));
        view.addView(touchable, new ViewGroup.LayoutParams(-1, -1));
        this.view = view;
    }

    public void loadImage(long hotelId, ControllerListener<ImageInfo> listener) {
        ImageRequest request = ImageRequest.fromUri(getImageUrl(hotelId, this.imageSize));
        ((GenericDraweeHierarchy) this.image.getHierarchy()).reset();
        this.image.setController(((PipelineDraweeControllerBuilder) ((PipelineDraweeControllerBuilder) ((PipelineDraweeControllerBuilder) Fresco.newDraweeControllerBuilder().setImageRequest(request)).setControllerListener(listener)).setOldController(this.image.getController())).build());
    }

    private String getImageUrl(long hotelId, Size size) {
        HotelImageParams params = new HotelImageParams();
        params.setFitType(HotelImageParams.FIT_CROP);
        params.setHeight(size.getHeight());
        params.setWidth(size.getWidth());
        params.setHotelId(hotelId);
        params.setPhotoIndex(this.index);
        return this.imageUrlProvider.createHotelPhotoUrl(params);
    }

    public View getView() {
        return this.view;
    }

    public String getImageUrl(long hotelId) {
        return getImageUrl(hotelId, this.imageSize);
    }
}
