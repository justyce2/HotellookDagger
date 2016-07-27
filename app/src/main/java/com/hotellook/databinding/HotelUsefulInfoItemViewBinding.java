package com.hotellook.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.devspark.robototextview.widget.RobotoTextView;
import com.hotellook.C1178R;
import com.hotellook.ui.screen.hotel.usefulInfo.HotelUsefulInfoItemView;

public class HotelUsefulInfoItemViewBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    public final ImageView indicator;
    private long mDirtyFlags;
    private final HotelUsefulInfoItemView mboundView0;
    public final RobotoTextView name;

    static {
        sIncludes = null;
        sViewsWithIds = new SparseIntArray();
        sViewsWithIds.put(C1178R.id.indicator, 1);
        sViewsWithIds.put(C1178R.id.name, 2);
    }

    public HotelUsefulInfoItemViewBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        this.mDirtyFlags = -1;
        Object[] bindings = ViewDataBinding.mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.indicator = (ImageView) bindings[1];
        this.mboundView0 = (HotelUsefulInfoItemView) bindings[0];
        this.mboundView0.setTag(null);
        this.name = (RobotoTextView) bindings[2];
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

    public static HotelUsefulInfoItemViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static HotelUsefulInfoItemViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (HotelUsefulInfoItemViewBinding) DataBindingUtil.inflate(inflater, C1178R.layout.hotel_useful_info_item_view, root, attachToRoot, bindingComponent);
    }

    public static HotelUsefulInfoItemViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static HotelUsefulInfoItemViewBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(C1178R.layout.hotel_useful_info_item_view, null, false), bindingComponent);
    }

    public static HotelUsefulInfoItemViewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static HotelUsefulInfoItemViewBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/hotel_useful_info_item_view_0".equals(view.getTag())) {
            return new HotelUsefulInfoItemViewBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
