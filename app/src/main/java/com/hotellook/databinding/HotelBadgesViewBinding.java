package com.hotellook.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.ui.screen.hotel.features.HotelBadgesView;
import com.hotellook.ui.view.FlowLayout;

public class HotelBadgesViewBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    public final FlowLayout badges;
    private long mDirtyFlags;
    private final HotelBadgesView mboundView0;

    static {
        sIncludes = null;
        sViewsWithIds = new SparseIntArray();
        sViewsWithIds.put(C1178R.id.badges, 1);
    }

    public HotelBadgesViewBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        this.mDirtyFlags = -1;
        Object[] bindings = ViewDataBinding.mapBindings(bindingComponent, root, 2, sIncludes, sViewsWithIds);
        this.badges = (FlowLayout) bindings[1];
        this.mboundView0 = (HotelBadgesView) bindings[0];
        this.mboundView0.setTag(null);
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

    public static HotelBadgesViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static HotelBadgesViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (HotelBadgesViewBinding) DataBindingUtil.inflate(inflater, C1178R.layout.hotel_badges_view, root, attachToRoot, bindingComponent);
    }

    public static HotelBadgesViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static HotelBadgesViewBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(C1178R.layout.hotel_badges_view, null, false), bindingComponent);
    }

    public static HotelBadgesViewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static HotelBadgesViewBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/hotel_badges_view_0".equals(view.getTag())) {
            return new HotelBadgesViewBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
