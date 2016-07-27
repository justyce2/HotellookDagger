package com.hotellook.ui.toolbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import com.hotellook.C1178R;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.gallery.DrawableAnimator;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.evaluator.ArgbEvaluator;
import java.util.ArrayList;
import java.util.List;
import pl.charmas.android.reactivelocation.C1822R;

public class ToolbarManager {
    public static final int DEFAULT_ANIMATOR_DURATION = 400;
    private MainActivity activity;
    private Drawable arrowDrawable;
    private View background;
    private Drawable crossDrawable;
    private ToolbarSettings currentSettings;
    private CurrentValues currentValues;
    private View dummyStatusBarBack;
    private View shadow;
    private Toolbar toolbar;
    private AnimatorSet toolbarTransitionAnimator;

    /* renamed from: com.hotellook.ui.toolbar.ToolbarManager.1 */
    class C13951 extends MonkeySafeClickListener {
        final /* synthetic */ MainActivity val$activity;

        C13951(MainActivity mainActivity) {
            this.val$activity = mainActivity;
        }

        public void onSafeClick(View v) {
            ToolbarManager.this.toolbar.getChildAt(0).clearFocus();
            if (ToolbarManager.this.toolbarTransitionAnimator == null || !ToolbarManager.this.toolbarTransitionAnimator.isRunning()) {
                int navigationMode = ToolbarManager.this.currentValues.getNavigationMode();
                if (navigationMode == 1 || navigationMode == 2) {
                    AndroidUtils.hideKeyboard(this.val$activity);
                    ToolbarManager.this.activity.onBackPressed();
                }
            }
        }
    }

    /* renamed from: com.hotellook.ui.toolbar.ToolbarManager.2 */
    class C13962 extends AnimatorListenerAdapter {
        final /* synthetic */ ToolbarSettings val$newSettings;

        C13962(ToolbarSettings toolbarSettings) {
            this.val$newSettings = toolbarSettings;
        }

        public void onAnimationEnd(Animator animation) {
        }

        public void onAnimationCancel(Animator animation) {
            if (this.val$newSettings.getCustomView() != null && ToolbarManager.this.toolbar != null) {
                ToolbarManager.this.toolbar.removeView(this.val$newSettings.getCustomView());
            }
        }
    }

    /* renamed from: com.hotellook.ui.toolbar.ToolbarManager.3 */
    class C13973 extends AnimatorListenerAdapter {
        final /* synthetic */ View val$oldCustomView;

        C13973(View view) {
            this.val$oldCustomView = view;
        }

        public void onAnimationEnd(Animator animation) {
            if (ToolbarManager.this.toolbar != null) {
                ToolbarManager.this.toolbar.removeView(this.val$oldCustomView);
            }
        }
    }

    private static class CurrentValues {
        private int bkgColorStatusBar;
        private View customView;
        private int navigationMode;
        private int toggleColor;

        public CurrentValues(ToolbarSettings settings) {
            this.navigationMode = settings.getNavigationMode();
            this.toggleColor = settings.getToggleColor();
            this.customView = settings.getCustomView();
            this.bkgColorStatusBar = settings.getBkgColorStatusBar();
        }

        public int getNavigationMode() {
            return this.navigationMode;
        }

        public void setNavigationMode(int navigationMode) {
            this.navigationMode = navigationMode;
        }

        public int getToggleColor() {
            return this.toggleColor;
        }

        public void setToggleColor(int toggleColor) {
            this.toggleColor = toggleColor;
        }

        public View getCustomView() {
            return this.customView;
        }

        public void setCustomView(View customView) {
            this.customView = customView;
        }

        public int getBkgColorStatusBar() {
            return this.bkgColorStatusBar;
        }

        public void setBkgColorStatusBar(int bkgColorStatusBar) {
            this.bkgColorStatusBar = bkgColorStatusBar;
        }
    }

    public ToolbarManager(Toolbar toolbar, MainActivity activity) {
        this.toolbar = toolbar;
        this.activity = activity;
        this.shadow = activity.findViewById(C1178R.id.toolbar_shadow);
        this.background = activity.findViewById(C1178R.id.toolbar_container);
        this.arrowDrawable = arrowDrawable();
        this.crossDrawable = activity.getResources().getDrawable(C1178R.drawable.ic_close);
        this.dummyStatusBarBack = activity.findViewById(C1178R.id.dummy_status_bar_background);
        this.dummyStatusBarBack.getLayoutParams().height = VERSION.SDK_INT >= 21 ? AndroidUtils.getStatusBarHeight((Context) activity) : 0;
        setUpToggle(null);
        toolbar.setNavigationOnClickListener(new C13951(activity));
    }

    public void release() {
        this.activity = null;
        this.toolbar = null;
        this.background = null;
        this.shadow = null;
        this.arrowDrawable = null;
        this.crossDrawable = null;
        if (this.toolbarTransitionAnimator != null) {
            this.toolbarTransitionAnimator.cancel();
            this.toolbarTransitionAnimator = null;
        }
        this.currentValues = null;
        this.currentSettings = null;
        this.dummyStatusBarBack = null;
    }

    public void setUpToolbar(ActionBar supportActionBar, ToolbarSettings newSettings) {
        if (this.activity != null) {
            finishRunningAnimation();
            resetShadowState();
            supportActionBar.setDisplayShowTitleEnabled(false);
            if (this.currentValues == null) {
                applySettingsInstantly(newSettings);
            } else {
                animateToSettings(newSettings);
            }
            this.currentSettings = newSettings;
        }
    }

    public int toggleDrawableWidth() {
        Drawable navigationIcon = this.toolbar.getNavigationIcon();
        return navigationIcon == null ? 0 : navigationIcon.getIntrinsicWidth();
    }

    private void resetShadowState() {
        this.shadow.setTranslationY(0.0f);
        this.shadow.setVisibility(0);
    }

    private void animateToSettings(ToolbarSettings newSettings) {
        List<Animator> animators = new ArrayList();
        addShadowAnimator(animators, newSettings);
        addToggleAnimator(animators, newSettings);
        addBackgroundAnimator(animators, newSettings);
        addToggleColorAnimator(animators, newSettings);
        addCustomViewAnimator(animators, newSettings);
        addToolbarPositionAnimator(animators);
        addStatusBarColorAnimator(animators, newSettings);
        addToolbarAlphaAnimator(animators, newSettings);
        this.toolbarTransitionAnimator = new AnimatorSet();
        this.toolbarTransitionAnimator.playTogether(animators);
        this.toolbarTransitionAnimator.addListener(new C13962(newSettings));
        this.toolbarTransitionAnimator.start();
    }

    private void applySettingsInstantly(ToolbarSettings newSettings) {
        this.currentValues = new CurrentValues(newSettings);
        this.shadow.setAlpha(newSettings.isWithShadow() ? 1.0f : 0.0f);
        setUpToggle(toggleByNavMode(this.currentValues.getNavigationMode()));
        this.background.setBackgroundColor(newSettings.getBkgColor());
        this.dummyStatusBarBack.setBackgroundColor(newSettings.getBkgColor());
        setStatusBarColor(newSettings.getBkgColorStatusBar());
        this.arrowDrawable.setAlpha(255);
        this.crossDrawable.setAlpha(255);
        setToggleColor(this.currentValues.getToggleColor());
        if (this.currentValues.getCustomView() != null) {
            this.toolbar.addView(this.currentValues.getCustomView());
        }
        this.toolbar.setAlpha(1.0f);
    }

    private void finishRunningAnimation() {
        if (this.toolbarTransitionAnimator != null && this.toolbarTransitionAnimator.isRunning()) {
            this.toolbarTransitionAnimator.end();
        }
    }

    private void addToolbarAlphaAnimator(List<Animator> animators, ToolbarSettings newSettings) {
        if (this.toolbar.getAlpha() != 1.0f) {
            ObjectAnimator toolbarAlphaAnimator = ObjectAnimator.ofFloat(this.toolbar, View.ALPHA, new float[]{1.0f});
            toolbarAlphaAnimator.setDuration(400);
            animators.add(toolbarAlphaAnimator);
        }
    }

    private void addStatusBarColorAnimator(List<Animator> animators, ToolbarSettings newSettings) {
        if (VERSION.SDK_INT >= 21 && this.currentValues.getBkgColorStatusBar() != newSettings.getBkgColorStatusBar()) {
            ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), new Object[]{Integer.valueOf(this.currentValues.getBkgColorStatusBar()), Integer.valueOf(newSettings.getBkgColorStatusBar())});
            animator.setDuration(400);
            animator.addUpdateListener(ToolbarManager$$Lambda$1.lambdaFactory$(this));
            animators.add(animator);
        }
    }

    /* synthetic */ void lambda$addStatusBarColorAnimator$0(ValueAnimator animation) {
        setStatusBarColor(((Integer) animation.getAnimatedValue()).intValue());
    }

    private void addToolbarPositionAnimator(List<Animator> animators) {
        View mToolbarParent = (View) this.toolbar.getParent();
        if (mToolbarParent.getTranslationY() != 0.0f) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(mToolbarParent, View.TRANSLATION_Y, new float[]{0.0f});
            animator.setDuration(400);
            animators.add(animator);
        }
    }

    private void addCustomViewAnimator(List<Animator> animators, ToolbarSettings newSettings) {
        View oldCustomView = this.currentValues.getCustomView();
        View newCustomView = newSettings.getCustomView();
        long delay = 0;
        if (oldCustomView != null) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(oldCustomView, View.ALPHA, new float[]{0.0f});
            animator.setDuration(200);
            animator.addListener(new C13973(oldCustomView));
            animators.add(animator);
            delay = 200;
        }
        if (newCustomView != null) {
            this.currentValues.setCustomView(newCustomView);
            newCustomView.setAlpha(0.0f);
            this.toolbar.addView(newCustomView);
            animator = ObjectAnimator.ofFloat(newCustomView, View.ALPHA, new float[]{1.0f});
            animator.setDuration(400 - delay);
            animator.setStartDelay(delay);
            animators.add(animator);
        }
    }

    private void setStatusBarColor(int color) {
        if (VERSION.SDK_INT >= 21) {
            this.currentValues.setBkgColorStatusBar(color);
            Window window = this.activity.getWindow();
            window.addFlags(RtlSpacingHelper.UNDEFINED);
            window.clearFlags(67108864);
            window.setStatusBarColor(color);
        }
    }

    private void addToggleColorAnimator(List<Animator> animators, ToolbarSettings newSettings) {
        if (this.currentValues.getToggleColor() != newSettings.getToggleColor()) {
            ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), new Object[]{Integer.valueOf(this.currentValues.getToggleColor()), Integer.valueOf(newSettings.getToggleColor())});
            animator.setDuration(400);
            animator.addUpdateListener(ToolbarManager$$Lambda$2.lambdaFactory$(this));
            animators.add(animator);
        }
    }

    /* synthetic */ void lambda$addToggleColorAnimator$1(ValueAnimator animation) {
        setToggleColor(((Integer) animation.getAnimatedValue()).intValue());
    }

    private void setToggleColor(int toggleColor) {
        this.currentValues.setToggleColor(toggleColor);
        this.arrowDrawable.setColorFilter(toggleColor, Mode.SRC_ATOP);
        this.arrowDrawable.invalidateSelf();
        this.crossDrawable.setColorFilter(toggleColor, Mode.SRC_ATOP);
        this.crossDrawable.invalidateSelf();
    }

    private void addBackgroundAnimator(List<Animator> animators, ToolbarSettings newSettings) {
        if (((ColorDrawable) this.background.getBackground()).getColor() != newSettings.getBkgColor()) {
            ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), new Object[]{Integer.valueOf(currentColor), Integer.valueOf(newSettings.getBkgColor())});
            animator.setDuration(400);
            animator.addUpdateListener(ToolbarManager$$Lambda$3.lambdaFactory$(this));
            animators.add(animator);
        }
    }

    /* synthetic */ void lambda$addBackgroundAnimator$2(ValueAnimator animation) {
        int color = ((Integer) animation.getAnimatedValue()).intValue();
        this.background.setBackgroundColor(color);
        this.dummyStatusBarBack.setBackgroundColor(color);
    }

    private void addToggleAnimator(List<Animator> animators, ToolbarSettings newSettings) {
        int navigationMode = newSettings.getNavigationMode();
        this.currentValues.setNavigationMode(navigationMode);
        int prevNavigationMode = this.currentSettings == null ? -1 : this.currentSettings.getNavigationMode();
        if (navigationMode != prevNavigationMode) {
            animators.add(toggleAnimator(toggleByNavMode(prevNavigationMode), toggleByNavMode(navigationMode)));
        }
    }

    private Animator toggleAnimator(@Nullable Drawable from, @Nullable Drawable to) {
        if (from == null && to == null) {
            throw new IllegalArgumentException();
        } else if (from == null) {
            return DrawableAnimator.showAnimator(to, ToolbarManager$$Lambda$4.lambdaFactory$(this, to)).setDuration(200);
        } else {
            if (to == null) {
                return DrawableAnimator.hideAnimator(from, ToolbarManager$$Lambda$5.lambdaFactory$(this)).setDuration(200);
            }
            return DrawableAnimator.crossAnimator(from, to, 400, ToolbarManager$$Lambda$6.lambdaFactory$(this, to));
        }
    }

    /* synthetic */ void lambda$toggleAnimator$3(@Nullable Drawable to) {
        setUpToggle(to);
    }

    /* synthetic */ void lambda$toggleAnimator$4() {
        setUpToggle(null);
    }

    /* synthetic */ void lambda$toggleAnimator$5(@Nullable Drawable to) {
        setUpToggle(to);
    }

    private void setUpToggle(@Nullable Drawable toggle) {
        if (this.toolbar != null) {
            this.toolbar.setNavigationIcon(toggle);
        }
    }

    @Nullable
    private Drawable toggleByNavMode(int navMode) {
        switch (navMode) {
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                return this.arrowDrawable;
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
                return this.crossDrawable;
            default:
                return null;
        }
    }

    @NonNull
    private Drawable arrowDrawable() {
        DrawerArrowDrawable drawerArrowDrawable = new DrawerArrowDrawable(this.toolbar.getContext());
        drawerArrowDrawable.setProgress(1.0f);
        return drawerArrowDrawable.getCurrent();
    }

    private void addShadowAnimator(List<Animator> animators, ToolbarSettings newSettings) {
        if ((newSettings.isWithShadow() ? 1.0f : 0.0f) != this.shadow.getAlpha()) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(this.shadow, View.ALPHA, new float[]{newSettings.isWithShadow() ? 1.0f : 0.0f});
            animator.setDuration(400);
            animators.add(animator);
        }
    }

    public Toolbar getToolbar() {
        return this.toolbar;
    }

    public ToolbarSettings getCurrentSettings() {
        return this.currentSettings;
    }

    public View shadow() {
        return this.shadow;
    }
}
