package com.hotellook.ui.screen.searchresults.adapters;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.ui.screen.searchresults.HotelImageController;
import com.hotellook.ui.view.DeactivatableViewPager;
import com.hotellook.ui.view.image.imagehierarchy.ImageHierarchyFactory;
import com.hotellook.ui.view.image.imagehierarchy.PortraitDefaultHierarchyFactory;
import com.hotellook.utils.Size;
import java.util.HashMap;
import java.util.Map;

public class ImagePagerAdapter extends PagerAdapter {
    private final Context context;
    private long hotelId;
    private ImageHierarchyFactory imageHierarchyFactory;
    private boolean imageLoadingEnabled;
    private final Size imageSize;
    private Map<Long, HotelImageController> imagesWaitingToLoad;
    private OnItemClickListener onItemClickListener;
    private final DeactivatableViewPager pager;
    private boolean pagingDisabled;
    private int photoCount;

    public interface OnItemClickListener {
        void onItemClicked(int i);
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.adapters.ImagePagerAdapter.1 */
    class C13681 extends MonkeySafeClickListener {
        final /* synthetic */ int val$position;

        C13681(int i) {
            this.val$position = i;
        }

        public void onSafeClick(View v) {
            if (ImagePagerAdapter.this.onItemClickListener != null) {
                ImagePagerAdapter.this.onItemClickListener.onItemClicked(this.val$position);
            }
        }
    }

    private class ImageControllerListener extends BaseControllerListener<ImageInfo> {
        private ImageControllerListener() {
        }

        public void onFinalImageSet(String s, ImageInfo imageInfo, Animatable animatable) {
            ImagePagerAdapter.this.onFinalImageSet();
        }
    }

    public ImagePagerAdapter(@NonNull Context context, @NonNull Size imageSize, @NonNull DeactivatableViewPager pager, ImageHierarchyFactory imageHierarchyFactory) {
        this.imageLoadingEnabled = false;
        this.imagesWaitingToLoad = new HashMap();
        this.context = context;
        this.imageSize = imageSize;
        this.pager = pager;
        this.imageHierarchyFactory = imageHierarchyFactory;
    }

    public ImagePagerAdapter(@NonNull Context context, @NonNull Size imageSize, @NonNull DeactivatableViewPager pager) {
        this(context, imageSize, pager, new PortraitDefaultHierarchyFactory(context.getResources()));
    }

    public Object instantiateItem(ViewGroup container, int position) {
        OnClickListener onClickListener;
        HotelImageController imageController = new HotelImageController(position, this.imageSize, this.imageHierarchyFactory);
        if (this.onItemClickListener == null) {
            onClickListener = null;
        } else {
            onClickListener = new C13681(position);
        }
        imageController.createView(this.context, onClickListener);
        View v = imageController.getView();
        container.addView(v, 0);
        if (position == 0 || this.imageLoadingEnabled) {
            ControllerListener<ImageInfo> controllerListener;
            if (this.pager.isPageable()) {
                controllerListener = null;
            } else {
                controllerListener = new ImageControllerListener();
            }
            imageController.loadImage(this.hotelId, controllerListener);
        } else {
            this.imagesWaitingToLoad.put(Long.valueOf(this.hotelId), imageController);
        }
        return v;
    }

    public int getItemPosition(Object object) {
        return -2;
    }

    public void setImageLoadingEnabled(boolean imageLoadingEnabled) {
        this.imageLoadingEnabled = imageLoadingEnabled;
        if (!this.imagesWaitingToLoad.isEmpty()) {
            for (HotelImageController controller : this.imagesWaitingToLoad.values()) {
                controller.loadImage(this.hotelId, null);
            }
            this.imagesWaitingToLoad.clear();
        }
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public int getCount() {
        if (this.pagingDisabled) {
            return Math.min(this.photoCount, 1);
        }
        return this.photoCount;
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void onItemSelected(int position, boolean movingRight) {
        if (position >= 1) {
            int targetPosition = position;
            if (!movingRight) {
                targetPosition--;
            } else if (position < this.photoCount - 1) {
                targetPosition++;
            }
            if (targetPosition != position) {
                Fresco.getImagePipeline().prefetchToBitmapCache(ImageRequest.fromUri(new HotelImageController(targetPosition, this.imageSize, this.imageHierarchyFactory).getImageUrl(this.hotelId)), null);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.onItemClickListener = clickListener;
    }

    public void disablePaging() {
        this.pager.setPagingEnabled(false);
        this.pagingDisabled = true;
        notifyDataSetChanged();
    }

    public void setActualData(long hotelId, int photoCount) {
        this.hotelId = hotelId;
        this.photoCount = photoCount;
    }

    protected void onFinalImageSet() {
        if (!this.pagingDisabled) {
            this.pager.setPagingEnabled(true);
        }
    }
}
