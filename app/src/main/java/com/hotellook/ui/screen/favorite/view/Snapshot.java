package com.hotellook.ui.screen.favorite.view;

import android.os.Parcelable;
import android.util.SparseIntArray;
import com.hotellook.ui.screen.favorite.presenter.PresenterSnapshot;

public class Snapshot {
    public final Parcelable listState;
    public final SparseIntArray pageIndexes;
    public final PresenterSnapshot presenterSate;

    public Snapshot(Parcelable listState, SparseIntArray pageIndexes, PresenterSnapshot presenterSate) {
        this.listState = listState;
        this.pageIndexes = pageIndexes;
        this.presenterSate = presenterSate;
    }
}
