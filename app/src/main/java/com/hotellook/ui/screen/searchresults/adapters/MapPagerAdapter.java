package com.hotellook.ui.screen.searchresults.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import java.util.List;

public class MapPagerAdapter extends PagerAdapter {
    private final List<String> mTitles;

    public MapPagerAdapter(List<String> titles) {
        this.mTitles = titles;
    }

    public int getCount() {
        return this.mTitles.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        return new Space(container.getContext());
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public CharSequence getPageTitle(int position) {
        return (CharSequence) this.mTitles.get(position);
    }
}
