package com.hotellook.ui.screen.gallery;

import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.drawable.ScalingUtils.ScaleType;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.hotellook.HotellookApplication;
import com.hotellook.core.api.HotellookImageUrlProvider;
import com.hotellook.core.api.params.HotelImageParams;
import com.hotellook.ui.view.image.imagehierarchy.GalleryHierarchyFactory;
import com.hotellook.ui.view.image.imagehierarchy.ImageHierarchyFactory;
import com.hotellook.ui.view.touchypager.ImageFormController;
import com.hotellook.ui.view.touchypager.TouchyDraweeView;
import com.hotellook.ui.view.touchypager.TouchyViewPager;
import com.hotellook.ui.view.touchypager.VerticalScrollListener;
import com.hotellook.utils.Size;
import io.fabric.sdk.android.services.events.EventsFilesManager;

public class GalleryPagerAdapter extends PagerAdapter {
    public static final String TAG;
    private final int count;
    private final long hotelId;
    private final ImageHierarchyFactory hotelImageHierarchyFactory;
    private final HotellookImageUrlProvider imageService;
    private final Size imageSize;
    private final Size preloadImageSize;
    private final VerticalScrollListener verticalScrollListener;

    static {
        TAG = GalleryPagerAdapter.class.getSimpleName();
    }

    public GalleryPagerAdapter(HotellookImageUrlProvider imageService, long hotelId, int count, Size imageSize, Size preloadImageSize, VerticalScrollListener verticalScrollListener) {
        this.imageService = imageService;
        this.count = count;
        this.hotelId = hotelId;
        this.imageSize = imageSize;
        this.preloadImageSize = preloadImageSize;
        this.verticalScrollListener = verticalScrollListener;
        this.hotelImageHierarchyFactory = new GalleryHierarchyFactory(HotellookApplication.getApp().getResources());
    }

    public Object instantiateItem(ViewGroup container, int position) {
        TouchyDraweeView v = new TouchyDraweeView(container.getContext());
        ImageFormController imageFormController = new ImageFormController(container.getContext(), v);
        imageFormController.setPagerMode(true);
        imageFormController.setVerticalScrollListener(this.verticalScrollListener);
        if (position == 0) {
            imageFormController.setFirstInPager(true);
        }
        if (position == getCount() - 1) {
            imageFormController.setLastInPager(true);
        }
        v.setImageFormController(imageFormController);
        String lowQualityUrl = getImageUrl(position, this.preloadImageSize);
        String url = getImageUrl(position, this.imageSize);
        ImageRequest lowQualityRequest = ImageRequest.fromUri(lowQualityUrl);
        if (ImagePipelineFactory.getInstance().getMainDiskStorageCache().hasKey(DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(lowQualityRequest))) {
            GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(container.getResources());
            builder.setActualImageScaleType(ScaleType.FIT_CENTER);
            v.setHierarchy(builder.build());
        } else {
            GenericDraweeHierarchy hierarchy = this.hotelImageHierarchyFactory.create();
            hierarchy.setActualImageScaleType(ScaleType.FIT_CENTER);
            v.setHierarchy(hierarchy);
        }
        v.setController(((PipelineDraweeControllerBuilder) Fresco.newDraweeControllerBuilder().setLowResImageRequest(lowQualityRequest)).setUri(Uri.parse(url)).build());
        container.addView(v);
        v.setTag(getTagForPosition(position));
        return v;
    }

    private String getImageUrl(int index, Size size) {
        HotelImageParams params = new HotelImageParams();
        params.setFitType(HotelImageParams.FIT_CROP);
        params.setHeight(size.getHeight());
        params.setWidth(size.getWidth());
        params.setHotelId(this.hotelId);
        params.setPhotoIndex(index);
        return this.imageService.createHotelPhotoUrl(params);
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public int getCount() {
        return this.count;
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public String getTagForPosition(int position) {
        return TAG + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + position;
    }

    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (container instanceof TouchyViewPager) {
            ((TouchyViewPager) container).setCurrentView((TouchyDraweeView) object);
        }
    }
}
