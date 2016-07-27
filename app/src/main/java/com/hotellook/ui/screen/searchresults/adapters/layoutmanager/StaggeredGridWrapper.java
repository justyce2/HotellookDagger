package com.hotellook.ui.screen.searchresults.adapters.layoutmanager;

import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.hotellook.utils.Size;

public class StaggeredGridWrapper implements LayoutManagerWrapper {
    private final StaggeredGridLayoutManager layoutManager;
    private final int[] temp;

    public StaggeredGridWrapper() {
        this(2);
    }

    public StaggeredGridWrapper(int spanCount) {
        this.temp = new int[spanCount];
        this.layoutManager = new StaggeredGridLayoutManager(spanCount, 1);
    }

    public LayoutManager get() {
        return this.layoutManager;
    }

    public void onRestoreInstanceState(Parcelable listState, RecyclerView view) {
        this.layoutManager.onRestoreInstanceState(listState);
    }

    public Parcelable onSaveInstanceState() {
        return this.layoutManager.onSaveInstanceState();
    }

    public int findFirstVisibleItemPosition() {
        return this.layoutManager.findFirstVisibleItemPositions(this.temp)[0];
    }

    public int findLastVisibleItemPosition() {
        return this.layoutManager.findLastVisibleItemPositions(this.temp)[0];
    }

    public void scrollToTop() {
        this.layoutManager.scrollToPosition(0);
    }

    public Size getListSize(Size imageSize) {
        return imageSize;
    }

    public void scrollTo(int position) {
        this.layoutManager.scrollToPosition(position);
    }
}
