package com.hotellook.ui.screen.searchresults;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.events.OpenHotelDetailsEvent;
import com.hotellook.search.Search;
import com.hotellook.search.SearchData;
import com.hotellook.search.SearchParams;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.screen.searchresults.adapters.HotelsGalleryViewPagerAdapter;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.sdk.OnPageChangeListenerAdapter;
import com.squareup.otto.Subscribe;
import java.util.List;

public class HotelsGalleryFragment extends BaseFragment {
    public static final float BASE_ALPHA_FOR_SECONDARY_HOTELS = 0.55f;
    private List<HotelData> mHotels;
    private TextView mHotelsCounter;
    private ViewPager mViewPager;
    private Search search;

    public class GalleryViewPagerListener extends OnPageChangeListenerAdapter {
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            HotelsGalleryViewPagerAdapter adapter = (HotelsGalleryViewPagerAdapter) HotelsGalleryFragment.this.mViewPager.getAdapter();
            View selectedView = adapter.getItemView(position);
            View nextView = adapter.getItemView(position + 1);
            setAlpha(selectedView, 1.0f - (positionOffset * 0.45f));
            setAlpha(nextView, 1.0f - ((1.0f - positionOffset) * 0.45f));
            for (View item : adapter.getItemViews()) {
                if (!(item == selectedView || item == nextView)) {
                    setAlpha(item, HotelsGalleryFragment.BASE_ALPHA_FOR_SECONDARY_HOTELS);
                }
            }
        }

        private void setAlpha(View view, float alpha) {
            if (view != null) {
                view.setAlpha(alpha);
            }
        }

        public void onPageSelected(int position) {
            HotelsGalleryFragment.this.updateHotelsCounter(position);
            HotelsGalleryViewPagerAdapter adapter = (HotelsGalleryViewPagerAdapter) HotelsGalleryFragment.this.mViewPager.getAdapter();
            if (adapter.getItemView(position) != null) {
                adapter.getItemView(position).findViewById(C1178R.id.overlay).setOnTouchListener(null);
                View prevItem = adapter.getItemView(position - 1);
                View nextItem = adapter.getItemView(position + 1);
                setOnClickListener(prevItem, -1);
                setOnClickListener(nextItem, 1);
            }
        }

        private void setOnClickListener(View item, int increment) {
            if (item != null) {
                item.findViewById(C1178R.id.overlay).setOnTouchListener(HotelsGalleryFragment$GalleryViewPagerListener$$Lambda$1.lambdaFactory$(this, increment));
            }
        }

        /* synthetic */ boolean lambda$setOnClickListener$0(int increment, View v, MotionEvent event) {
            if (1 == event.getAction()) {
                HotelsGalleryFragment.this.mViewPager.setCurrentItem(HotelsGalleryFragment.this.mViewPager.getCurrentItem() + increment, true);
            }
            return true;
        }
    }

    public static HotelsGalleryFragment create(List<HotelData> hotels) {
        HotelsGalleryFragment fragment = new HotelsGalleryFragment();
        fragment.setHotels(hotels);
        return fragment;
    }

    public void setHotels(List<HotelData> hotels) {
        this.mHotels = hotels;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        this.search = getComponent().searchKeeper().lastSearchOrThrowException();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1178R.layout.fragment_hotels_gallery, container, false);
    }

    /* synthetic */ void lambda$onViewCreated$0() {
        getMainActivity().lockDrawer();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.post(HotelsGalleryFragment$$Lambda$1.lambdaFactory$(this));
        interceptTouchesThroughFragment(view);
        setUpViewPager(view);
        HotellookApplication.eventBus().register(this);
    }

    public void onStart() {
        super.onStart();
        setUpToolbar();
    }

    private void setUpViewPager(View view) {
        this.mViewPager = (ViewPager) view.findViewById(C1178R.id.viewpager);
        int cardWidth = view.getContext().getResources().getDimensionPixelSize(C1178R.dimen.hotels_gallery_card_view_width);
        this.mViewPager.setPageMargin((-(AndroidUtils.getDisplaySize(view.getContext()).x - cardWidth)) + view.getContext().getResources().getDimensionPixelSize(C1178R.dimen.hotels_gallery_cards_between_margin));
        this.mViewPager.setOffscreenPageLimit(2);
        GalleryViewPagerListener galleryViewPagerListener = new GalleryViewPagerListener();
        this.mViewPager.setOnPageChangeListener(galleryViewPagerListener);
        SearchData searchData = this.search.searchData();
        SearchParams searchParams = this.search.searchParams();
        this.mViewPager.setAdapter(new HotelsGalleryViewPagerAdapter(this.mHotels, searchData.offers().all(), searchParams.nightsCount(), this.search.badges(), searchParams.currency(), searchData.discounts(), searchData.highlights()));
        view.post(HotelsGalleryFragment$$Lambda$2.lambdaFactory$(this, galleryViewPagerListener));
    }

    /* synthetic */ void lambda$setUpViewPager$1(GalleryViewPagerListener galleryViewPagerListener) {
        galleryViewPagerListener.onPageSelected(this.mViewPager.getCurrentItem());
    }

    private void setUpToolbar() {
        MainActivity activity = getMainActivity();
        View toolbarView = LayoutInflater.from(getActivity()).inflate(C1178R.layout.gallery_toolbar, getToolbar(), false);
        this.mHotelsCounter = (TextView) toolbarView.findViewById(C1178R.id.counter);
        alignCounterToCenter(activity);
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), new ToolbarSettings().navigationMode(2).bkgColor(getResources().getColor(C1178R.color.black_pct_20)).statusBarColor(getResources().getColor(C1178R.color.black_pct_20)).toggleColor(getResources().getColor(17170443)).withCustomView(toolbarView));
    }

    private void alignCounterToCenter(MainActivity activity) {
        ((MarginLayoutParams) this.mHotelsCounter.getLayoutParams()).rightMargin = activity.getToolbarManager().toggleDrawableWidth();
    }

    public void onDestroyView() {
        HotellookApplication.eventBus().unregister(this);
        super.onDestroyView();
    }

    private void interceptTouchesThroughFragment(View view) {
        view.setOnTouchListener(HotelsGalleryFragment$$Lambda$3.lambdaFactory$());
    }

    private void updateHotelsCounter(int numOfSelectedHotel) {
        this.mHotelsCounter.setText((numOfSelectedHotel + 1) + "/" + this.mHotels.size());
    }

    @Subscribe
    public void onHotelSelected(OpenHotelDetailsEvent event) {
        getMainActivity().onBackPressed();
    }
}
