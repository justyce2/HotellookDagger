package com.hotellook.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.widget.NestedScrollViewWithFixedFling;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.hotellook.C1178R;
import com.hotellook.ui.view.LimitedWidthFrameLayout;

public class FragmentHotelPricesBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    public final LimitedWidthFrameLayout contentContainer;
    private long mDirtyFlags;
    private final FrameLayout mboundView0;
    public final RecyclerView recyclerView;
    public final NestedScrollViewWithFixedFling scrollView;

    static {
        sIncludes = null;
        sViewsWithIds = new SparseIntArray();
        sViewsWithIds.put(C1178R.id.scroll_view, 1);
        sViewsWithIds.put(C1178R.id.content_container, 2);
        sViewsWithIds.put(C1178R.id.recycler_view, 3);
    }

    public FragmentHotelPricesBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        this.mDirtyFlags = -1;
        Object[] bindings = ViewDataBinding.mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds);
        this.contentContainer = (LimitedWidthFrameLayout) bindings[2];
        this.mboundView0 = (FrameLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.recyclerView = (RecyclerView) bindings[3];
        this.scrollView = (NestedScrollViewWithFixedFling) bindings[1];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1;
        }
        requestRebind();
    }

    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return false;
        }
    }

    public boolean setVariable(int variableId, Object variable) {
        return false;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    protected void executeBindings() {
        synchronized (this) {
            long dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
    }

    public static FragmentHotelPricesBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static FragmentHotelPricesBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (FragmentHotelPricesBinding) DataBindingUtil.inflate(inflater, C1178R.layout.fragment_hotel_prices, root, attachToRoot, bindingComponent);
    }

    public static FragmentHotelPricesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static FragmentHotelPricesBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(C1178R.layout.fragment_hotel_prices, null, false), bindingComponent);
    }

    public static FragmentHotelPricesBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static FragmentHotelPricesBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/fragment_hotel_prices_0".equals(view.getTag())) {
            return new FragmentHotelPricesBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
