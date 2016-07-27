package com.hotellook.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.devspark.robototextview.widget.RobotoTextView;
import com.hotellook.C1178R;
import me.zhanghai.android.materialprogressbar.C1759R;

public class FavoritesToolbarBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final RelativeLayout mboundView0;
    public final LinearLayout stateBtn;
    public final RobotoTextView stateBtnText;
    public final ImageView stateIcon;
    public final RobotoTextView subtitle;
    public final RobotoTextView title;

    static {
        sIncludes = null;
        sViewsWithIds = new SparseIntArray();
        sViewsWithIds.put(C1759R.id.title, 1);
        sViewsWithIds.put(C1178R.id.subtitle, 2);
        sViewsWithIds.put(C1178R.id.state_btn, 3);
        sViewsWithIds.put(C1178R.id.state_icon, 4);
        sViewsWithIds.put(C1178R.id.state_btn_text, 5);
    }

    public FavoritesToolbarBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        this.mDirtyFlags = -1;
        Object[] bindings = ViewDataBinding.mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds);
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.stateBtn = (LinearLayout) bindings[3];
        this.stateBtnText = (RobotoTextView) bindings[5];
        this.stateIcon = (ImageView) bindings[4];
        this.subtitle = (RobotoTextView) bindings[2];
        this.title = (RobotoTextView) bindings[1];
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

    public static FavoritesToolbarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static FavoritesToolbarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (FavoritesToolbarBinding) DataBindingUtil.inflate(inflater, C1178R.layout.favorites_toolbar, root, attachToRoot, bindingComponent);
    }

    public static FavoritesToolbarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static FavoritesToolbarBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(C1178R.layout.favorites_toolbar, null, false), bindingComponent);
    }

    public static FavoritesToolbarBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static FavoritesToolbarBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/favorites_toolbar_0".equals(view.getTag())) {
            return new FavoritesToolbarBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
