package com.hotellook.ui.view.bottomnavigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.hotellook.C1178R;
import java.util.HashMap;
import java.util.Map;

public class BottomNavigationView extends LinearLayout {
    public static final int UNDEFINED_ID = -1;
    private int itemColor;
    private int itemColorSelected;
    private final Map<Integer, BottomNavigationItemView> itemViews;
    private OnNavigationItemSelectedListener listener;
    private int selectedItemId;

    public interface OnNavigationItemSelectedListener {
        void onNavigationItemSelected(@IdRes int i);
    }

    public BottomNavigationView(Context context) {
        super(context);
        this.itemViews = new HashMap();
        this.selectedItemId = UNDEFINED_ID;
        init(context, null);
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.itemViews = new HashMap();
        this.selectedItemId = UNDEFINED_ID;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(0);
        setGravity(1);
        TypedArray a = context.obtainStyledAttributes(attrs, C1178R.styleable.BottomNavigationView);
        this.itemColor = a.getColor(1, 0);
        this.itemColorSelected = a.getColor(2, 0);
        NavigationMenu menu = NavigationMenuInflater.from(context).inflate(a.getResourceId(0, 0));
        if (menu != null) {
            populate(menu);
        }
        a.recycle();
    }

    private void populate(@NonNull NavigationMenu menu) {
        int menuItemsCount = menu.items.size();
        for (int i = 0; i < menuItemsCount; i++) {
            Item menuItem = (Item) menu.items.get(i);
            BottomNavigationItemView itemView = createItemView(menuItem);
            this.itemViews.put(Integer.valueOf(menuItem.id), itemView);
            addView(itemView);
        }
    }

    @NonNull
    private BottomNavigationItemView createItemView(@NonNull Item menuItem) {
        BottomNavigationItemView itemView = (BottomNavigationItemView) LayoutInflater.from(getContext()).inflate(C1178R.layout.bottom_nav_item, this, false);
        itemView.bindTo(menuItem);
        itemView.setColors(this.itemColor, this.itemColorSelected);
        itemView.unselect();
        itemView.setOnClickListener(BottomNavigationView$$Lambda$1.lambdaFactory$(this, menuItem));
        return itemView;
    }

    /* synthetic */ void lambda$createItemView$0(@NonNull Item menuItem, View v) {
        select(menuItem.id);
    }

    public void select(@IdRes int itemId) {
        select(itemId, true);
    }

    public void select(@IdRes int itemId, boolean notify) {
        if (this.selectedItemId != UNDEFINED_ID) {
            ((BottomNavigationItemView) this.itemViews.get(Integer.valueOf(this.selectedItemId))).unselect();
        }
        this.selectedItemId = itemId;
        ((BottomNavigationItemView) this.itemViews.get(Integer.valueOf(itemId))).select();
        if (this.listener != null && notify) {
            this.listener.onNavigationItemSelected(itemId);
        }
    }

    public void setNavigationItemSelectedListener(@Nullable OnNavigationItemSelectedListener listener) {
        this.listener = listener;
    }
}
