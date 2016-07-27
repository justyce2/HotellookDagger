package com.hotellook.ui.screen.gallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hotellook.HotellookApplication;
import com.hotellook.core.api.HotellookImageUrlProvider;
import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;
import com.hotellook.events.GalleryGridItemClickEvent;
import com.hotellook.events.GalleryOutTransitionFinishedEvent;
import com.hotellook.events.GalleryTransitionAnimationStartedEvent;
import com.hotellook.events.ImageShowedEvent;
import com.hotellook.events.TransitionDataUpdateEvent;
import com.hotellook.ui.screen.gallery.GalleryGridAdapter.PhotoViewHolder;
import com.hotellook.ui.screen.hotel.HotelInfoBaseListFragment;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.Size;
import com.squareup.otto.Subscribe;

public class PhotosGridFragment extends HotelInfoBaseListFragment {
    private int contentWidth;
    private int currentHiddenPosition;
    private GalleryGridAdapter galleryGridAdapter;
    private GallerySwitcherListener gallerySwitcherListener;
    private Size gridImageSize;
    private GridLayoutManager gridLayoutManager;
    private int recyclerViewWidth;
    private ViewSizeCalculator sizeCalculator;
    private int spanCount;

    private class GallerySwitcherListener {
        private GallerySwitcherListener() {
        }

        @Subscribe
        public void onGalleryAnimationStarted(GalleryTransitionAnimationStartedEvent event) {
            PhotosGridFragment.this.hideGridItemAtPosition(event.index);
        }

        @Subscribe
        public void onItemShowedInGallery(ImageShowedEvent event) {
            int position = event.position;
            if (PhotosGridFragment.this.itemIsNotVisible(position)) {
                PhotosGridFragment.this.gridLayoutManager.scrollToPosition(position);
            }
            PhotosGridFragment.this.showHiddenItem();
            PhotosGridFragment.this.hideGridItemAtPosition(position);
            HotellookApplication.eventBus().post(new TransitionDataUpdateEvent(PhotosGridFragment.this.galleryGridAdapter.getTransitionData(PhotosGridFragment.this.getPhotoViewHolder(position))));
        }

        @Subscribe
        public void onGalleryOutAnimationFinished(GalleryOutTransitionFinishedEvent event) {
            HotellookApplication.eventBus().unregister(PhotosGridFragment.this.gallerySwitcherListener);
            PhotosGridFragment.this.showHiddenItem();
        }
    }

    public static PhotosGridFragment create(HotelDataSource hotelDataSource, Size gridImageSize, int contentWidth) {
        PhotosGridFragment fragment = new PhotosGridFragment();
        fragment.setHotelDataSource(hotelDataSource);
        fragment.setGridImageSize(gridImageSize);
        fragment.setContentWidth(contentWidth);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.gallerySwitcherListener = new GallerySwitcherListener();
        this.sizeCalculator = new ViewSizeCalculator(getContext());
        this.spanCount = this.sizeCalculator.calculateGridSpanCount();
        this.recyclerViewWidth = this.sizeCalculator.calculateGridWidth(this.contentWidth);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onDestroyView() {
        HotellookApplication.eventBus().unregister(this.gallerySwitcherListener);
        super.onDestroyView();
    }

    @Subscribe
    public void onGridItemClicked(GalleryGridItemClickEvent event) {
        HotellookApplication.eventBus().register(this.gallerySwitcherListener);
    }

    private boolean itemIsNotVisible(int position) {
        return position < this.gridLayoutManager.findFirstVisibleItemPosition() || position > this.gridLayoutManager.findLastVisibleItemPosition();
    }

    private void showHiddenItem() {
        PhotoViewHolder prevHolder = getPhotoViewHolder(this.currentHiddenPosition);
        if (prevHolder != null && prevHolder.image != null) {
            prevHolder.image.setVisibility(0);
        }
    }

    private void hideGridItemAtPosition(int position) {
        PhotoViewHolder holder = getPhotoViewHolder(position);
        if (!(holder == null || holder.image == null)) {
            holder.image.setVisibility(4);
        }
        this.currentHiddenPosition = position;
    }

    private PhotoViewHolder getPhotoViewHolder(int position) {
        return (PhotoViewHolder) getRecyclerView().findViewHolderForAdapterPosition(position);
    }

    protected Adapter createAdapter(HotelDataSource hotelDataSource) {
        HotellookImageUrlProvider imageService = getApplication().getComponent().getHotelImageUrlProvider();
        HotelDetailData hotelData = hotelDataSource.detailHotelData();
        this.galleryGridAdapter = new GalleryGridAdapter(imageService, hotelData.getId(), hotelData.getPhotoCount(), this.gridImageSize, this.sizeCalculator.calculatePagerImageSize());
        return this.galleryGridAdapter;
    }

    protected void setUpRecyclerViewPaddings() {
        RecyclerView recyclerView = getRecyclerView();
        AndroidUtils.addBottomPadding(recyclerView, getStandardOffset());
        recyclerView.addItemDecoration(new GridItemDecoration(this.recyclerViewWidth, this.gridImageSize.getHeight(), this.spanCount));
    }

    @NonNull
    protected LinearLayoutManager createLayoutManager() {
        this.gridLayoutManager = new GridLayoutManager(getActivity(), this.spanCount);
        return this.gridLayoutManager;
    }

    public void setGridImageSize(Size gridImageSize) {
        this.gridImageSize = gridImageSize;
    }

    public void setContentWidth(int contentWidth) {
        this.contentWidth = contentWidth;
    }
}
