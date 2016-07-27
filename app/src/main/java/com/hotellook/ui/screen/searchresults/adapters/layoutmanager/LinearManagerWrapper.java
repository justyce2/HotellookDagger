package com.hotellook.ui.screen.searchresults.adapters.layoutmanager;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import com.hotellook.utils.Size;

public class LinearManagerWrapper implements LayoutManagerWrapper {
    private final LinearLayoutManager layoutManager;

    public LinearManagerWrapper(Context context) {
        this.layoutManager = new LinearLayoutManager(context);
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
        return this.layoutManager.findFirstVisibleItemPosition();
    }

    public int findLastVisibleItemPosition() {
        return this.layoutManager.findLastVisibleItemPosition();
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
