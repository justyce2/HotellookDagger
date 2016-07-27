package com.hotellook.ui.screen.gallery;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.common.view.OnScreenLocation;
import com.hotellook.core.api.HotellookImageUrlProvider;
import com.hotellook.core.api.params.HotelImageParams;
import com.hotellook.events.GalleryGridItemClickEvent;
import com.hotellook.ui.view.image.imagehierarchy.GridHierarchyFactory;
import com.hotellook.ui.view.image.imagehierarchy.ImageHierarchyFactory;
import com.hotellook.utils.Size;
import me.zhanghai.android.materialprogressbar.C1759R;

public class GalleryGridAdapter extends Adapter<PhotoViewHolder> {
    private final int count;
    private final ImageHierarchyFactory hotelImageHierarchyFactory;
    private final long id;
    private final HotellookImageUrlProvider imageService;
    private final Size imageSize;
    private final Size pagerImageSize;

    /* renamed from: com.hotellook.ui.screen.gallery.GalleryGridAdapter.1 */
    class C12901 extends MonkeySafeClickListener {
        final /* synthetic */ PhotoViewHolder val$holder;
        final /* synthetic */ int val$position;

        C12901(PhotoViewHolder photoViewHolder, int i) {
            this.val$holder = photoViewHolder;
            this.val$position = i;
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new GalleryGridItemClickEvent(this.val$position, GalleryGridAdapter.this.getTransitionData(this.val$holder)));
        }
    }

    public static class PhotoViewHolder extends ViewHolder {
        public final View clickable;
        public final SimpleDraweeView image;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            this.image = (SimpleDraweeView) itemView.findViewById(C1759R.id.image);
            this.clickable = itemView.findViewById(C1178R.id.clickable);
        }
    }

    public GalleryGridAdapter(HotellookImageUrlProvider imageService, long id, int count, Size imageSize, Size pagerImageSize) {
        this.imageService = imageService;
        this.id = id;
        this.count = count;
        this.imageSize = imageSize;
        this.hotelImageHierarchyFactory = new GridHierarchyFactory(HotellookApplication.getApp().getResources());
        this.pagerImageSize = pagerImageSize;
    }

    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.gallery_item, parent, false);
        PhotoViewHolder holder = new PhotoViewHolder(itemView);
        itemView.setLayoutParams(new LayoutParams(-1, this.imageSize.getHeight()));
        holder.image.setHierarchy(this.hotelImageHierarchyFactory.create());
        return holder;
    }

    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        applyImage(holder, position);
        holder.clickable.setOnClickListener(new C12901(holder, position));
    }

    @NonNull
    public TransitionData getTransitionData(PhotoViewHolder holder) {
        return new TransitionData(OnScreenLocation.create(holder.image), new Size(this.imageSize.getHeight(), this.imageSize.getHeight()), this.imageSize);
    }

    private void applyImage(PhotoViewHolder holder, int position) {
        Uri uri = Uri.parse(getImageUrl(position));
        if (Fresco.getImagePipeline().isInBitmapMemoryCache(uri)) {
            holder.image.setImageURI(uri);
            return;
        }
        Uri pagerUri = Uri.parse(getPagerImageUrl(position));
        if (Fresco.getImagePipeline().isInBitmapMemoryCache(pagerUri)) {
            holder.image.setController(((PipelineDraweeControllerBuilder) ((PipelineDraweeControllerBuilder) ((PipelineDraweeControllerBuilder) Fresco.newDraweeControllerBuilder().setLowResImageRequest(ImageRequest.fromUri(pagerUri))).setImageRequest(ImageRequest.fromUri(uri))).setOldController(holder.image.getController())).build());
            return;
        }
        holder.image.setImageURI(uri);
    }

    public int getItemCount() {
        return this.count;
    }

    private String getImageUrl(int index) {
        return getImageUrl(index, this.imageSize);
    }

    private String getPagerImageUrl(int index) {
        return getImageUrl(index, this.pagerImageSize);
    }

    private String getImageUrl(int index, Size imageSize) {
        HotelImageParams params = new HotelImageParams();
        params.setFitType(HotelImageParams.FIT_CROP);
        params.setHeight(imageSize.getHeight());
        params.setWidth(imageSize.getWidth());
        params.setHotelId(this.id);
        params.setPhotoIndex(index);
        return this.imageService.createHotelPhotoUrl(params);
    }
}
