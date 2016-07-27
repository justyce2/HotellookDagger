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
import com.hotellook.ui.screen.hotel.distance.DistanceItemView;
import me.zhanghai.android.materialprogressbar.C1759R;

public class DistanceItemViewBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    public final RobotoTextView distance;
    public final ImageView icon;
    private long mDirtyFlags;
    private final DistanceItemView mboundView0;
    public final RobotoTextView text;

    static {
        sIncludes = null;
        sViewsWithIds = new SparseIntArray();
        sViewsWithIds.put(C1759R.id.icon, 1);
        sViewsWithIds.put(C1759R.id.text, 2);
        sViewsWithIds.put(C1178R.id.distance, 3);
    }

    public DistanceItemViewBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        this.mDirtyFlags = -1;
        Object[] bindings = ViewDataBinding.mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds);
        this.distance = (RobotoTextView) bindings[3];
        this.icon = (ImageView) bindings[1];
        this.mboundView0 = (DistanceItemView) bindings[0];
        this.mboundView0.setTag(null);
        this.text = (RobotoTextView) bindings[2];
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

    public static DistanceItemViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static DistanceItemViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (DistanceItemViewBinding) DataBindingUtil.inflate(inflater, C1178R.layout.distance_item_view, root, attachToRoot, bindingComponent);
    }

    public static DistanceItemViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static DistanceItemViewBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(C1178R.layout.distance_item_view, null, false), bindingComponent);
    }

    public static DistanceItemViewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static DistanceItemViewBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/distance_item_view_0".equals(view.getTag())) {
            return new DistanceItemViewBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
