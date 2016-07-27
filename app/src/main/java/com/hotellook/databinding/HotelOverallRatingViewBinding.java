package com.hotellook.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.devspark.robototextview.widget.RobotoTextView;
import com.hotellook.C1178R;
import com.hotellook.ui.screen.hotel.rating.HotelOverallRatingView;
import me.zhanghai.android.materialprogressbar.C1759R;

public class HotelOverallRatingViewBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final HotelOverallRatingView mboundView0;
    public final RobotoTextView rating;
    public final RobotoTextView text;
    public final RobotoTextView title;

    static {
        sIncludes = null;
        sViewsWithIds = new SparseIntArray();
        sViewsWithIds.put(C1178R.id.rating, 1);
        sViewsWithIds.put(C1759R.id.title, 2);
        sViewsWithIds.put(C1759R.id.text, 3);
    }

    public HotelOverallRatingViewBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        this.mDirtyFlags = -1;
        Object[] bindings = ViewDataBinding.mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds);
        this.mboundView0 = (HotelOverallRatingView) bindings[0];
        this.mboundView0.setTag(null);
        this.rating = (RobotoTextView) bindings[1];
        this.text = (RobotoTextView) bindings[3];
        this.title = (RobotoTextView) bindings[2];
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

    public static HotelOverallRatingViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static HotelOverallRatingViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (HotelOverallRatingViewBinding) DataBindingUtil.inflate(inflater, C1178R.layout.hotel_overall_rating_view, root, attachToRoot, bindingComponent);
    }

    public static HotelOverallRatingViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static HotelOverallRatingViewBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(C1178R.layout.hotel_overall_rating_view, null, false), bindingComponent);
    }

    public static HotelOverallRatingViewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static HotelOverallRatingViewBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/hotel_overall_rating_view_0".equals(view.getTag())) {
            return new HotelOverallRatingViewBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
