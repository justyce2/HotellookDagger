package com.hotellook.ui.screen.hotel;

import android.content.Context;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.events.GalleryOutTransitionFinishedEvent;
import com.hotellook.events.GalleryTransitionAnimationStartedEvent;
import com.hotellook.events.ImageShowedEvent;
import com.hotellook.events.OnNextPhotoBtnClick;
import com.hotellook.events.OnPhotoClickEvent;
import com.hotellook.events.OnPhotoSelectedEvent;
import com.hotellook.events.OnPreviousPhotoBtnClick;
import com.hotellook.ui.screen.gallery.ImageUrlCreator;
import com.hotellook.ui.screen.hotel.data.BasicHotelData;
import com.hotellook.ui.screen.hotel.ratingcolorizer.BackgroundColorApplier;
import com.hotellook.ui.screen.hotel.ratingcolorizer.RatingColorizer;
import com.hotellook.ui.screen.searchresults.adapters.ImagePagerAdapter;
import com.hotellook.ui.view.DeactivatableViewPager;
import com.hotellook.ui.view.image.imagehierarchy.ImageHierarchyFactory;
import com.hotellook.ui.view.image.imagehierarchy.LandscapeHotelCardHierarchyFactory;
import com.hotellook.ui.view.image.imagehierarchy.PortraitHotelCardHierarchyFactory;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.Size;
import com.hotellook.utils.ValueFormat;
import com.hotellook.utils.sdk.OnPageChangeListenerAdapter;
import com.squareup.otto.Subscribe;

public class HotelScreenCardView extends FrameLayout {
    private BasicHotelData basicHotelData;
    private View contentFrame;
    private HotelCardContentHider contentHider;
    private View galleryClickableView;
    private final GalleryTransitionListener galleryTransitionListener;
    private View hideableContent;
    private Size hotelCardSize;
    private View hotelNameLayout;
    private TextView hotelNameTxt;
    private RatingBar hotelRatingBar;
    private TextView hotelRatingTxt;
    private ImagePagerAdapter imageAdapter;
    private DeactivatableViewPager imagePager;
    private int initImageIdx;
    private PhotoChangeListener photoChangeListener;
    private TextView photoCounterTxt;
    private View photoPlaceHolder;

    /* renamed from: com.hotellook.ui.screen.hotel.HotelScreenCardView.1 */
    class C13101 extends ImagePagerAdapter {
        C13101(Context context, Size imageSize, DeactivatableViewPager pager, ImageHierarchyFactory imageHierarchyFactory) {
            super(context, imageSize, pager, imageHierarchyFactory);
        }

        protected void onFinalImageSet() {
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.HotelScreenCardView.2 */
    class C13112 extends OnPageChangeListenerAdapter {
        C13112() {
        }

        public void onPageSelected(int position) {
            HotellookApplication.eventBus().post(new ImageShowedEvent(HotelScreenCardView.this.basicHotelData.id(), position));
        }
    }

    private class GalleryTransitionListener {
        private GalleryTransitionListener() {
        }

        @Subscribe
        public void onPhotoPagedTo(ImageShowedEvent event) {
            HotelScreenCardView.this.imagePager.setCurrentItem(event.position);
        }

        @Subscribe
        public void onGalleryAnimationStarted(GalleryTransitionAnimationStartedEvent event) {
            HotelScreenCardView.this.imagePager.setVisibility(4);
        }

        @Subscribe
        public void onGalleryOutAnimationFinished(GalleryOutTransitionFinishedEvent event) {
            HotelScreenCardView.this.imagePager.setVisibility(0);
            HotelScreenCardView.this.imagePager.setCurrentItem(HotelScreenCardView.this.photoChangeListener.prevPosition, false);
            HotelScreenCardView.this.contentHider.show();
            HotelScreenCardView.this.photoCounterTxt.animate().alpha(1.0f);
            HotellookApplication.eventBus().unregister(this);
        }
    }

    private class PhotoChangeListener implements OnPageChangeListener {
        private int prevPosition;

        private PhotoChangeListener() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (HotelScreenCardView.this.contentHider != null && positionOffsetPixels != 0) {
                HotelScreenCardView.this.contentHider.hideAndShowWithDelay();
            }
        }

        public void onPageSelected(int position) {
            HotelScreenCardView.this.updateCounter(position, HotelScreenCardView.this.basicHotelData.photoCount());
            HotelScreenCardView.this.imageAdapter.onItemSelected(position, position > this.prevPosition);
            this.prevPosition = position;
            HotelScreenCardView.this.onPhotoSelected(position);
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    public HotelScreenCardView(Context context) {
        super(context);
        this.galleryTransitionListener = new GalleryTransitionListener();
    }

    public HotelScreenCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.galleryTransitionListener = new GalleryTransitionListener();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.imagePager = (DeactivatableViewPager) findViewById(C1178R.id.images_pager);
        this.photoPlaceHolder = findViewById(C1178R.id.view_no_images);
        this.hotelNameTxt = (TextView) findViewById(C1178R.id.hotel_name);
        this.hotelRatingTxt = (TextView) findViewById(C1178R.id.hotel_rating);
        this.hotelRatingBar = (RatingBar) findViewById(C1178R.id.hotel_stars);
        this.photoCounterTxt = (TextView) findViewById(C1178R.id.photo_counter);
        this.hideableContent = findViewById(C1178R.id.hideable);
        this.hotelNameLayout = findViewById(C1178R.id.hotel_name_line);
        this.galleryClickableView = findViewById(C1178R.id.clickable);
        this.contentFrame = findViewById(C1178R.id.content);
        this.hotelRatingBar.setVisibility(4);
        this.hotelRatingTxt.setVisibility(4);
        setUpInfoHider();
        if (!isInEditMode()) {
            this.photoCounterTxt.setAlpha(0.0f);
        }
        hideBottomShadowIfNeed();
    }

    private void hideBottomShadowIfNeed() {
        Context context = getContext();
        if (AndroidUtils.isTablet(context) && AndroidUtils.isLandscape(context)) {
            findViewById(C1178R.id.bottom_shadow).setVisibility(8);
        }
    }

    public void setUpData(BasicHotelData basicHotelData, Size hotelCardSize, int initImageIdx) {
        this.basicHotelData = basicHotelData;
        this.hotelCardSize = hotelCardSize;
        this.initImageIdx = initImageIdx;
        setUpImagePager();
        setUpHotelName();
        setUpRating();
        setUpStars();
    }

    private void setUpInfoHider() {
        if (AndroidUtils.isLandscape(getContext())) {
            this.contentHider = new FakeHotelCardContentHider(this.hideableContent);
        } else {
            this.contentHider = new HotelCardContentHider(this.hideableContent);
        }
    }

    public void setContentPadding(int left, int top, int right, int bottom) {
        MarginLayoutParams contentLayoutParams = (MarginLayoutParams) this.contentFrame.getLayoutParams();
        contentLayoutParams.leftMargin += left;
        contentLayoutParams.topMargin += top;
        contentLayoutParams.rightMargin += right;
        contentLayoutParams.bottomMargin += bottom;
    }

    public int getContentHeight() {
        return this.contentFrame.getHeight();
    }

    private void setUpHotelName() {
        this.hotelNameTxt.setText(this.basicHotelData.name());
    }

    private void setUpRating() {
        RatingColorizer mRateColorized = new RatingColorizer(new BackgroundColorApplier(), getResources());
        int rating = this.basicHotelData.rating();
        if (rating > 0) {
            this.hotelRatingTxt.setVisibility(0);
            this.hotelRatingTxt.setText(ValueFormat.ratingToString(rating));
            mRateColorized.apply(this.hotelRatingTxt, rating);
            return;
        }
        this.hotelRatingTxt.setVisibility(8);
    }

    private void setUpStars() {
        this.hotelRatingBar.setVisibility(0);
        this.hotelRatingBar.setProgress(this.basicHotelData.stars());
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        HotellookApplication.eventBus().register(this);
    }

    protected void onDetachedFromWindow() {
        HotellookApplication.eventBus().unregister(this);
        HotellookApplication.eventBus().unregister(this.galleryTransitionListener);
        super.onDetachedFromWindow();
    }

    @Subscribe
    public void onPhotoClick(OnPhotoClickEvent event) {
        HotellookApplication.eventBus().register(this.galleryTransitionListener);
    }

    @Subscribe
    public void onPreviousPhotoRequest(OnPreviousPhotoBtnClick event) {
        this.imagePager.setCurrentItem(this.imagePager.getCurrentItem() - 1);
    }

    @Subscribe
    public void oNextPhotoRequest(OnNextPhotoBtnClick event) {
        this.imagePager.setCurrentItem(this.imagePager.getCurrentItem() + 1);
    }

    public void showContent() {
        this.contentHider.show();
    }

    private void setUpImagePager() {
        int photoCount = this.basicHotelData.photoCount();
        if (photoCount > 0) {
            this.imageAdapter = new C13101(getContext().getApplicationContext(), this.hotelCardSize, this.imagePager, getImageHierarchyFactory());
            this.imageAdapter.setImageLoadingEnabled(true);
            this.imageAdapter.setActualData(this.basicHotelData.id(), this.basicHotelData.photoCount());
            this.imagePager.setAdapter(this.imageAdapter);
            this.imagePager.setCurrentItem(this.initImageIdx, false);
            informAboutInitialPhotoSelection();
            this.photoChangeListener = new PhotoChangeListener();
            this.imagePager.addOnPageChangeListener(this.photoChangeListener);
            this.imagePager.addOnPageChangeListener(new C13112());
            this.imageAdapter.setOnItemClickListener(HotelScreenCardView$$Lambda$1.lambdaFactory$());
            this.imagePager.setPagingEnabled(true);
            updateCounter(this.initImageIdx, photoCount);
            return;
        }
        this.imagePager.setVisibility(8);
        this.photoCounterTxt.setVisibility(8);
        this.photoPlaceHolder.setVisibility(0);
        this.galleryClickableView.setVisibility(8);
    }

    private ImageHierarchyFactory getImageHierarchyFactory() {
        if (AndroidUtils.isPortrait(getContext())) {
            return new PortraitHotelCardHierarchyFactory(getContext().getResources());
        }
        return new LandscapeHotelCardHierarchyFactory(getContext().getResources());
    }

    private void informAboutInitialPhotoSelection() {
        post(HotelScreenCardView$$Lambda$2.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$informAboutInitialPhotoSelection$1() {
        onPhotoSelected(this.initImageIdx);
    }

    public void onPhotoSelected(int position) {
        HotellookApplication.eventBus().post(new OnPhotoSelectedEvent(position));
    }

    public void hideContentInstantly() {
        this.contentHider.hideInstantly();
    }

    public DeactivatableViewPager getPhotoPager() {
        return this.imagePager;
    }

    public void hidePhotoCounter() {
        this.photoCounterTxt.setAlpha(0.0f);
    }

    public int getCurrentPhotoIdx() {
        return this.imagePager.getCurrentItem();
    }

    public int getHotelNameYLocationOnScreen() {
        return AndroidUtils.getViewYLocationOnScreen(this.hotelNameTxt);
    }

    public View getPhotoPlaceHolder() {
        return this.photoPlaceHolder;
    }

    public View getHideableContent() {
        return this.hideableContent;
    }

    public View getHotelNameLine() {
        return this.hotelNameLayout;
    }

    public View getRatingBar() {
        return this.hotelRatingBar;
    }

    private void updateCounter(int position, int photoCount) {
        this.photoCounterTxt.setAlpha(1.0f);
        this.photoCounterTxt.setText((position + 1) + "/" + photoCount);
    }

    public String getImageUrl() {
        return new ImageUrlCreator(this.basicHotelData.id()).getImageUrl(this.initImageIdx, this.hotelCardSize);
    }
}
