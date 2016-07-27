package com.hotellook.ui.view.bottomnavigation;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import com.hotellook.ui.view.LimitedWidthFrameLayout;
import me.zhanghai.android.materialprogressbar.C1759R;

public class BottomNavigationItemView extends LimitedWidthFrameLayout {
    private ImageView iconView;
    private int selectedColor;
    private TextView titleView;
    private int unselectedColor;

    public BottomNavigationItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.iconView = (ImageView) findViewById(C1759R.id.icon);
        this.titleView = (TextView) findViewById(C1759R.id.title);
    }

    public void bindTo(Item item) {
        this.iconView.setImageDrawable(item.icon);
        this.titleView.setText(item.title);
        setId(item.id);
    }

    public void setColors(int unselectedColor, int selectedColor) {
        this.unselectedColor = unselectedColor;
        this.selectedColor = selectedColor;
    }

    public void select() {
        this.iconView.setColorFilter(this.selectedColor);
        this.titleView.setTextColor(this.selectedColor);
    }

    public void unselect() {
        this.iconView.setColorFilter(this.unselectedColor);
        this.titleView.setTextColor(this.unselectedColor);
    }
}
