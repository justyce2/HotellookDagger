package com.hotellook.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.devspark.robototextview.widget.RobotoTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hotellook.C1178R;
import com.hotellook.ui.screen.hotel.prices.OfferView;
import com.hotellook.ui.view.FlowLayout;

public class OfferViewBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    public final RobotoTextView beforeDiscountPrice;
    public final RobotoTextView bookingBtn;
    public final LinearLayout bottomSegment;
    public final View clickable;
    public final RobotoTextView discountPrice;
    public final SimpleDraweeView gateLogo;
    public final RobotoTextView gateName;
    public final FlowLayout gateOptionsContainer;
    public final FrameLayout logoContainer;
    private long mDirtyFlags;
    private final OfferView mboundView0;
    public final RobotoTextView price;
    public final FrameLayout prices;
    public final RobotoTextView roomName;

    static {
        sIncludes = null;
        sViewsWithIds = new SparseIntArray();
        sViewsWithIds.put(C1178R.id.clickable, 1);
        sViewsWithIds.put(C1178R.id.logo_container, 2);
        sViewsWithIds.put(C1178R.id.gate_logo, 3);
        sViewsWithIds.put(C1178R.id.gate_name, 4);
        sViewsWithIds.put(C1178R.id.prices, 5);
        sViewsWithIds.put(C1178R.id.price, 6);
        sViewsWithIds.put(C1178R.id.discount_price, 7);
        sViewsWithIds.put(C1178R.id.before_discount_price, 8);
        sViewsWithIds.put(C1178R.id.bottom_segment, 9);
        sViewsWithIds.put(C1178R.id.room_name, 10);
        sViewsWithIds.put(C1178R.id.gate_options_container, 11);
        sViewsWithIds.put(C1178R.id.booking_btn, 12);
    }

    public OfferViewBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        this.mDirtyFlags = -1;
        Object[] bindings = ViewDataBinding.mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds);
        this.beforeDiscountPrice = (RobotoTextView) bindings[8];
        this.bookingBtn = (RobotoTextView) bindings[12];
        this.bottomSegment = (LinearLayout) bindings[9];
        this.clickable = (View) bindings[1];
        this.discountPrice = (RobotoTextView) bindings[7];
        this.gateLogo = (SimpleDraweeView) bindings[3];
        this.gateName = (RobotoTextView) bindings[4];
        this.gateOptionsContainer = (FlowLayout) bindings[11];
        this.logoContainer = (FrameLayout) bindings[2];
        this.mboundView0 = (OfferView) bindings[0];
        this.mboundView0.setTag(null);
        this.price = (RobotoTextView) bindings[6];
        this.prices = (FrameLayout) bindings[5];
        this.roomName = (RobotoTextView) bindings[10];
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

    public static OfferViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static OfferViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (OfferViewBinding) DataBindingUtil.inflate(inflater, C1178R.layout.offer_view, root, attachToRoot, bindingComponent);
    }

    public static OfferViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static OfferViewBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(C1178R.layout.offer_view, null, false), bindingComponent);
    }

    public static OfferViewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static OfferViewBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/offer_view_0".equals(view.getTag())) {
            return new OfferViewBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
