package com.hotellook.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.devspark.robototextview.widget.RobotoTextView;
import com.hotellook.C1178R;
import com.hotellook.ui.screen.hotel.reviews.HotelReviewsItemView;
import me.zhanghai.android.materialprogressbar.C1759R;

public class HotelReviewsItemViewBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    public final RobotoTextView date;
    private long mDirtyFlags;
    private final HotelReviewsItemView mboundView0;
    public final RobotoTextView name;
    public final RobotoTextView text;
    public final FrameLayout title;

    static {
        sIncludes = null;
        sViewsWithIds = new SparseIntArray();
        sViewsWithIds.put(C1759R.id.title, 1);
        sViewsWithIds.put(C1178R.id.name, 2);
        sViewsWithIds.put(C1178R.id.date, 3);
        sViewsWithIds.put(C1759R.id.text, 4);
    }

    public HotelReviewsItemViewBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        this.mDirtyFlags = -1;
        Object[] bindings = ViewDataBinding.mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.date = (RobotoTextView) bindings[3];
        this.mboundView0 = (HotelReviewsItemView) bindings[0];
        this.mboundView0.setTag(null);
        this.name = (RobotoTextView) bindings[2];
        this.text = (RobotoTextView) bindings[4];
        this.title = (FrameLayout) bindings[1];
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

    public static HotelReviewsItemViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static HotelReviewsItemViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (HotelReviewsItemViewBinding) DataBindingUtil.inflate(inflater, C1178R.layout.hotel_reviews_item_view, root, attachToRoot, bindingComponent);
    }

    public static HotelReviewsItemViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static HotelReviewsItemViewBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(C1178R.layout.hotel_reviews_item_view, null, false), bindingComponent);
    }

    public static HotelReviewsItemViewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static HotelReviewsItemViewBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/hotel_reviews_item_view_0".equals(view.getTag())) {
            return new HotelReviewsItemViewBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
