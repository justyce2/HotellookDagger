package com.hotellook.ui.toolbar;

import android.graphics.Color;
import android.view.View;

public class ToolbarSettings {
    public static final int ARROW = 1;
    public static final int CROSS = 2;
    public static final int NONE = 0;
    private int bkgColor;
    private int bkgColorStatusBar;
    private View customView;
    private int navigation;
    private boolean shadow;
    private int toggleColor;

    public ToolbarSettings() {
        this.shadow = false;
        this.navigation = 0;
        this.bkgColor = Color.argb(0, 255, 255, 255);
        this.bkgColorStatusBar = Color.argb(51, 0, 0, 0);
        this.toggleColor = -1;
    }

    public ToolbarSettings withShadow() {
        this.shadow = true;
        return this;
    }

    public ToolbarSettings statusBarColor(int color) {
        this.bkgColorStatusBar = color;
        return this;
    }

    public ToolbarSettings navigationMode(int mode) {
        this.navigation = mode;
        return this;
    }

    public ToolbarSettings bkgColor(int color) {
        this.bkgColor = color;
        return this;
    }

    public ToolbarSettings toggleColor(int color) {
        this.toggleColor = color;
        return this;
    }

    public ToolbarSettings withCustomView(View customView) {
        this.customView = customView;
        return this;
    }

    public int getNavigationMode() {
        return this.navigation;
    }

    public boolean isWithShadow() {
        return this.shadow;
    }

    public int getBkgColor() {
        return this.bkgColor;
    }

    public int getToggleColor() {
        return this.toggleColor;
    }

    public View getCustomView() {
        return this.customView;
    }

    public int getBkgColorStatusBar() {
        return this.bkgColorStatusBar;
    }
}
