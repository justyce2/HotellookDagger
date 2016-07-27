package com.hotellook.ui.screen.searchresults.adapters.layoutmanager;

import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import com.hotellook.utils.Size;

public interface LayoutManagerWrapper {
    int findFirstVisibleItemPosition();

    int findLastVisibleItemPosition();

    LayoutManager get();

    Size getListSize(Size size);

    void onRestoreInstanceState(Parcelable parcelable, RecyclerView recyclerView);

    Parcelable onSaveInstanceState();

    void scrollTo(int i);

    void scrollToTop();
}
