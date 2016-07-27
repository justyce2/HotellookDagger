package com.hotellook.ui.screen.filters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;
import com.hotellook.C1178R;
import com.hotellook.ui.screen.filters.presenters.FilterPresenter;
import java.util.ArrayList;
import java.util.List;

public class FiltersAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragments;
    private final int pagerId;
    private final List<String> titles;

    public FiltersAdapter(Context context, FragmentManager fm, List<Pair<FilterPresenter, String>> presenters, int pagerId) {
        super(fm);
        this.pagerId = pagerId;
        int tabsHeight = context.getResources().getDimensionPixelSize(C1178R.dimen.results_tabs_height);
        this.titles = new ArrayList(presenters.size());
        this.fragments = new ArrayList(presenters.size());
        for (int i = 0; i < presenters.size(); i++) {
            Pair<FilterPresenter, String> pair = (Pair) presenters.get(i);
            FilterPresenter presenter = pair.first;
            FilterPresenterFragment fragment = (FilterPresenterFragment) findFragmentInFMByIndex(fm, i);
            if (fragment != null) {
                fragment.setFilterPresenter(presenter);
                fragment.setTabsOffset(tabsHeight);
                this.fragments.add(fragment);
            } else {
                this.fragments.add(FilterPresenterFragment.create(presenter, tabsHeight));
            }
            this.titles.add(pair.second);
        }
    }

    private Fragment findFragmentInFMByIndex(FragmentManager fm, int index) {
        return fm.findFragmentByTag("android:switcher:" + this.pagerId + ":" + index);
    }

    public Fragment getItem(int position) {
        return (Fragment) this.fragments.get(position);
    }

    public int getCount() {
        return this.fragments.size();
    }

    public CharSequence getPageTitle(int position) {
        return (CharSequence) this.titles.get(position);
    }
}
