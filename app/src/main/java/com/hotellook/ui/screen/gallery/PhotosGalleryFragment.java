package com.hotellook.ui.screen.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.core.api.HotellookImageUrlProvider;
import com.hotellook.events.GalleryOutTransitionFinishedEvent;
import com.hotellook.events.GalleryTransitionAnimationStartedEvent;
import com.hotellook.events.ImageShowedEvent;
import com.hotellook.events.TransitionDataUpdateEvent;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.OnBackPressHandler;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.ui.view.touchypager.NullVerticalScrollListener;
import com.hotellook.ui.view.touchypager.TouchyDraweeView;
import com.hotellook.ui.view.touchypager.TouchyViewPager;
import com.hotellook.ui.view.touchypager.VerticalScrollListener;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.Size;
import com.squareup.otto.Subscribe;

public class PhotosGalleryFragment extends BaseFragment implements OnBackPressHandler {
    private TextView counter;
    private GalleryPagerAdapter galleryAdapter;
    private long hotelId;
    private int imageCount;
    private HotellookImageUrlProvider imageService;
    private int initPage;
    private Size itemSize;
    private TouchyViewPager pager;
    private View pagerBackground;
    private Size pagerImageSize;
    private ViewSizeCalculator sizeCalculator;
    private GalleryTransitionAnimator transitionAnimator;
    private TransitionData transitionData;

    /* renamed from: com.hotellook.ui.screen.gallery.PhotosGalleryFragment.1 */
    class C12941 implements OnPageChangeListener {
        C12941() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            PhotosGalleryFragment.this.counter.setText((position + 1) + "/" + PhotosGalleryFragment.this.imageCount);
            HotellookApplication.eventBus().post(new ImageShowedEvent(PhotosGalleryFragment.this.hotelId, position));
        }

        public void onPageScrollStateChanged(int state) {
            if (state == 0) {
                PhotosGalleryFragment.this.resetZoom();
            }
        }
    }

    private class PagerScrollListener implements VerticalScrollListener {
        private static final int DRAG_DELTA_TO_CLOSE_IN_DP = 50;
        private static final int MIN_DRAG_IN_DP = 10;
        private final int dragDeltaToClose;
        private final int halfScreenHeight;
        private boolean ingnoreDrag;
        private final Interpolator interpolator;
        private final int minDragDelta;

        private PagerScrollListener(ViewSizeCalculator sizeCalculator) {
            this.ingnoreDrag = false;
            this.halfScreenHeight = sizeCalculator.getScreenHeight() / 2;
            this.interpolator = new AccelerateInterpolator();
            float density = PhotosGalleryFragment.this.getResources().getDisplayMetrics().density;
            this.minDragDelta = (int) (10.0f * density);
            this.dragDeltaToClose = (int) (50.0f * density);
        }

        public void onStartScroll() {
            this.ingnoreDrag = false;
        }

        public void onScroll(float translateY) {
            if (!this.ingnoreDrag && Math.abs(translateY) > ((float) this.minDragDelta)) {
                BackgroundAnimatorHelper.setTransitionColor(PhotosGalleryFragment.this.pagerBackground, 1.0f - this.interpolator.getInterpolation(Math.abs(translateY) / ((float) this.halfScreenHeight)));
            }
        }

        public boolean onFinishScroll(View view, float translateY) {
            if (this.ingnoreDrag) {
                return false;
            }
            if (Math.abs(translateY) > ((float) this.dragDeltaToClose)) {
                PhotosGalleryFragment.this.animateOutTransition(view);
                return true;
            }
            BackgroundAnimatorHelper.animateToTargetColor(PhotosGalleryFragment.this.pagerBackground);
            return false;
        }
    }

    public static PhotosGalleryFragment create(long hotelId, int imageCount, int initPage, Size itemSize, TransitionData transitionData) {
        PhotosGalleryFragment fragment = new PhotosGalleryFragment();
        fragment.setHotelId(hotelId);
        fragment.setImageCount(imageCount);
        fragment.setInitPage(initPage);
        fragment.setItemSize(itemSize);
        fragment.setStartTransitionInfo(transitionData);
        return fragment;
    }

    void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    void setInitPage(int initPage) {
        this.initPage = initPage;
    }

    public void setStartTransitionInfo(TransitionData transitionData) {
        this.transitionData = transitionData;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        this.sizeCalculator = new ViewSizeCalculator(getContext());
        setRetainInstance(true);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1178R.layout.fragment_photos_gallery, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMainActivity().lockDrawer();
        this.imageService = getApplication().getComponent().getHotelImageUrlProvider();
        ImageUrlCreator imageUrlCreator = new ImageUrlCreator(this.imageService, this.hotelId);
        this.pagerImageSize = this.sizeCalculator.calculatePagerImageSize();
        this.pagerBackground = view.findViewById(C1178R.id.overlay);
        setUpGallery(view);
        setUpToolbar();
        SimpleDraweeView transitionImage = (SimpleDraweeView) view.findViewById(C1178R.id.transition_view);
        HotellookApplication.eventBus().register(this);
        this.transitionAnimator = new GalleryTransitionAnimator(this.pagerBackground, transitionImage, this.sizeCalculator, this.pager, imageUrlCreator);
        if (savedInstanceState == null) {
            postTransitionAnimation();
            return;
        }
        this.transitionData = null;
        this.pager.setVisibility(0);
        setBackgroundToBlackInstantly();
    }

    private void setBackgroundToBlackInstantly() {
        this.pagerBackground.setVisibility(0);
        BackgroundAnimatorHelper.setTransitionColor(this.pagerBackground, 1.0f);
    }

    public void onDestroyView() {
        HotellookApplication.eventBus().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void onUpdateTransitionData(TransitionDataUpdateEvent event) {
        this.transitionData = event.transitionData;
    }

    private void setUpToolbar() {
        MainActivity activity = getMainActivity();
        View toolbarView = LayoutInflater.from(getActivity()).inflate(C1178R.layout.gallery_toolbar, getMainActivity().getToolbarManager().getToolbar(), false);
        this.counter = (TextView) toolbarView.findViewById(C1178R.id.counter);
        this.counter.setText((this.initPage + 1) + "/" + this.imageCount);
        this.counter.setVisibility(0);
        alignCounterToCenter(activity);
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), new ToolbarSettings().navigationMode(2).bkgColor(getResources().getColor(C1178R.color.black_pct_50)).statusBarColor(getResources().getColor(C1178R.color.black_pct_50)).toggleColor(getResources().getColor(17170443)).withCustomView(toolbarView));
    }

    private void alignCounterToCenter(MainActivity activity) {
        ((MarginLayoutParams) this.counter.getLayoutParams()).rightMargin = activity.getToolbarManager().toggleDrawableWidth();
    }

    private void setUpGallery(View view) {
        VerticalScrollListener verticalScrollListener;
        this.pager = (TouchyViewPager) view.findViewById(C1178R.id.photos_pager);
        if (AndroidUtils.isPortrait(getContext())) {
            verticalScrollListener = new PagerScrollListener(this.sizeCalculator, null);
        } else {
            verticalScrollListener = new NullVerticalScrollListener();
            setBackgroundToBlackInstantly();
        }
        this.galleryAdapter = new GalleryPagerAdapter(this.imageService, this.hotelId, this.imageCount, this.pagerImageSize, this.itemSize, verticalScrollListener);
        this.pager.setAdapter(this.galleryAdapter);
        this.pager.setCurrentItem(this.initPage, false);
        this.pager.setPageMargin(getResources().getDimensionPixelOffset(C1178R.dimen.gallery_page_margin));
        this.pager.addOnPageChangeListener(new C12941());
    }

    private void resetZoom() {
        int childCount = this.pager.getChildCount();
        View currentView = getCurrentPagerView();
        for (int i = 0; i < childCount; i++) {
            View child = this.pager.getChildAt(i);
            if (!(child == null || child.equals(currentView))) {
                ((TouchyDraweeView) child).resetZoom();
            }
        }
    }

    public boolean onBackPressedHandled() {
        animateOutTransition(getCurrentPagerView());
        return true;
    }

    private void animateOutTransition(View view) {
        this.counter.animate().alpha(0.0f);
        this.transitionAnimator.animateToPreviousScreen(this.transitionData, view, this.pager.getCurrentItem(), PhotosGalleryFragment$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$animateOutTransition$0() {
        HotellookApplication.eventBus().post(new GalleryOutTransitionFinishedEvent());
        MainActivity activity = getMainActivity();
        if (activity != null) {
            activity.goBack();
        }
    }

    private View getCurrentPagerView() {
        return this.pager.findViewWithTag(this.galleryAdapter.getTagForPosition(this.pager.getCurrentItem()));
    }

    private void postTransitionAnimation() {
        this.pager.setVisibility(4);
        this.pager.post(PhotosGalleryFragment$$Lambda$2.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$postTransitionAnimation$2() {
        HotellookApplication.eventBus().post(new GalleryTransitionAnimationStartedEvent(this.initPage));
        this.transitionAnimator.animateToScreen(this.transitionData, this.initPage, PhotosGalleryFragment$$Lambda$3.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$null$1() {
        this.pager.setVisibility(0);
        this.pager.setAlpha(1.0f);
    }

    public void setItemSize(Size itemSize) {
        this.itemSize = itemSize;
    }
}
