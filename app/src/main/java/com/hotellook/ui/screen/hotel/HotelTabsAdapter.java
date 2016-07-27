package com.hotellook.ui.screen.hotel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.hotellook.C1178R;
import com.hotellook.backstack.Savable;
import com.hotellook.ui.adapters.AdvancedPagerAdapter;
import com.hotellook.ui.screen.gallery.PhotosGridFragment;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.ui.screen.hotel.general.HotelGeneralFragment;
import com.hotellook.ui.screen.hotel.prices.OffersFragment;
import com.hotellook.ui.screen.hotel.rating.HotelRatingsFragment;
import com.hotellook.utils.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelTabsAdapter extends AdvancedPagerAdapter {
    public static final int TAB_GENERAL = 10;
    public static final int TAB_PHOTOS = 14;
    public static final int TAB_PRICES = 11;
    public static final int TAB_RATINGS = 12;
    public static final int TAB_REVIEWS = 13;
    private final List<Fragment> fragments;
    private final Size gridItemSize;
    private final int gridViewWidth;
    private HotelDataSource hotelDataSource;
    private final Source source;
    private final List<HotelTab> tabs;

    public static class FragmentsStates {
        private final Map<String, Object> states;

        public FragmentsStates() {
            this.states = new HashMap();
        }

        private void saveState(@NonNull Fragment fragment) {
            if (fragment instanceof Savable) {
                this.states.put(fragmentTag(fragment), ((Savable) fragment).saveState());
            }
        }

        private void restoreState(@NonNull Fragment fragment) {
            if (fragment instanceof Savable) {
                Savable savableFragment = (Savable) fragment;
                Object state = this.states.get(fragmentTag(fragment));
                if (state != null) {
                    savableFragment.restoreState(state);
                }
            }
        }

        @NonNull
        private String fragmentTag(@NonNull Fragment fragment) {
            return fragment.getClass().getSimpleName();
        }
    }

    private static class HotelTab {
        final int tab;
        final String title;

        public HotelTab(int tab, String title) {
            this.tab = tab;
            this.title = title;
        }
    }

    public HotelTabsAdapter(Context context, FragmentManager fm, int pagerId, Source source, HotelDataSource hotelDataSource, Size gridItemSize, int gridViewWidth) {
        super(fm, (long) pagerId);
        this.tabs = new ArrayList();
        this.fragments = new ArrayList();
        this.source = source;
        this.gridItemSize = gridItemSize;
        this.gridViewWidth = gridViewWidth;
        this.hotelDataSource = hotelDataSource;
        prepareTabsAndFragments(context, fm, hotelDataSource, source);
    }

    private void prepareTabsAndFragments(Context context, FragmentManager fm, HotelDataSource hotelDataSource, Source source) {
        this.tabs.add(new HotelTab(TAB_GENERAL, context.getString(C1178R.string.hotel_tab_general)));
        this.fragments.add(prepareHotelGeneralFragment(fm, this.tabs.size() - 1));
        if (canShowPriceTab(hotelDataSource, source)) {
            this.tabs.add(new HotelTab(TAB_PRICES, context.getString(C1178R.string.hotel_tab_prices)));
            this.fragments.add(prepareHotelPricesFragment(fm, this.tabs.size() - 1));
        }
        if (hotelDataSource.basicHotelData().hasRatingsData()) {
            this.tabs.add(new HotelTab(TAB_RATINGS, context.getString(C1178R.string.hotel_tab_ratings)));
            this.fragments.add(prepareHotelRatingsFragment(fm, this.tabs.size() - 1));
        }
        if (hotelDataSource.basicHotelData().photoCount() > 1) {
            this.tabs.add(new HotelTab(TAB_PHOTOS, context.getString(C1178R.string.hotel_tab_photos)));
            this.fragments.add(prepareGalleryFragment(fm, this.tabs.size() - 1));
        }
    }

    private boolean canShowPriceTab(HotelDataSource hotelDataSource, Source source) {
        return (source.isCitySearch() && hotelDataSource.offers() == null) ? false : true;
    }

    public CharSequence getPageTitle(int position) {
        return ((HotelTab) this.tabs.get(position)).title;
    }

    private Fragment prepareHotelGeneralFragment(@NonNull FragmentManager fm, int position) {
        Fragment fragment = findFragmentInFMByIndex(fm, position);
        if (fragment == null) {
            return HotelGeneralFragment.create(this.hotelDataSource, this.source);
        }
        return fragment;
    }

    private Fragment prepareHotelPricesFragment(@NonNull FragmentManager fm, int position) {
        Fragment fragment = findFragmentInFMByIndex(fm, position);
        if (fragment == null) {
            return OffersFragment.create(this.hotelDataSource, this.source);
        }
        return fragment;
    }

    private Fragment prepareHotelRatingsFragment(@NonNull FragmentManager fm, int position) {
        Fragment fragment = findFragmentInFMByIndex(fm, position);
        if (fragment == null) {
            return HotelRatingsFragment.create(this.hotelDataSource);
        }
        return fragment;
    }

    private Fragment prepareGalleryFragment(FragmentManager fm, int position) {
        PhotosGridFragment fragment = (PhotosGridFragment) findFragmentInFMByIndex(fm, position);
        if (fragment == null) {
            return PhotosGridFragment.create(this.hotelDataSource, this.gridItemSize, this.gridViewWidth);
        }
        fragment.setGridImageSize(this.gridItemSize);
        fragment.setContentWidth(this.gridViewWidth);
        return fragment;
    }

    public Fragment getItem(int position) {
        return (Fragment) this.fragments.get(position);
    }

    public int getCount() {
        return this.tabs.size();
    }

    public boolean hasRatingTab() {
        for (HotelTab tab : this.tabs) {
            if (tab.tab == TAB_RATINGS) {
                return true;
            }
        }
        return false;
    }

    public boolean isRatingTab(int position) {
        return ((HotelTab) this.tabs.get(position)).tab == TAB_RATINGS;
    }

    public int getTabId(int position) {
        return ((HotelTab) this.tabs.get(position)).tab;
    }

    public int getPricesTabPosition() {
        for (int i = 0; i < this.fragments.size(); i++) {
            if (((Fragment) this.fragments.get(i)) instanceof OffersFragment) {
                return i;
            }
        }
        return -1;
    }

    @NonNull
    public FragmentsStates saveFragmentsStates() {
        FragmentsStates fragmentStates = new FragmentsStates();
        for (Fragment fragment : this.fragments) {
            fragmentStates.saveState(fragment);
        }
        return fragmentStates;
    }

    public void restoreFragmentsStates(@NonNull FragmentsStates fragmentStates) {
        for (Fragment fragment : this.fragments) {
            fragmentStates.restoreState(fragment);
        }
    }
}
