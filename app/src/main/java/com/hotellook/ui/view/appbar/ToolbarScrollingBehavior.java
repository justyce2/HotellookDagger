package com.hotellook.ui.view.appbar;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.support.annotation.NonNull;
import android.view.View;
import com.hotellook.HotellookApplication;
import com.hotellook.events.ExpandAppBarEvent;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CollectionUtils;
import com.squareup.otto.Subscribe;
import java.util.Arrays;
import java.util.List;

public class ToolbarScrollingBehavior {
    public static final int ANIMATION_DURATION = 150;
    private ValueAnimator animator;
    private List<View> movingViews;
    private View toolbar;
    private View toolbarContainer;
    private final int toolbarHeight;

    /* renamed from: com.hotellook.ui.view.appbar.ToolbarScrollingBehavior.1 */
    class C14161 implements OnNestedScrollListener {
        C14161() {
        }

        public int onNestedPreScroll(int dy) {
            if (ToolbarScrollingBehavior.this.nothingToMove()) {
                return 0;
            }
            ToolbarScrollingBehavior.this.cancelRunningAnimation();
            float initialTranslation = ToolbarScrollingBehavior.this.getTranslationY();
            ToolbarScrollingBehavior.this.transformUi(ToolbarScrollingBehavior.this.ensureCollapseTranslationRange(initialTranslation - ((float) dy)));
            return (int) (initialTranslation - ToolbarScrollingBehavior.this.getTranslationY());
        }

        public void onStopNestedScroll() {
            if (!ToolbarScrollingBehavior.this.nothingToMove()) {
                if (ToolbarScrollingBehavior.this.getTranslationY() < ((float) ((-ToolbarScrollingBehavior.this.toolbarHeight) / 2))) {
                    ToolbarScrollingBehavior.this.animateTranslate(-ToolbarScrollingBehavior.this.toolbarHeight);
                } else {
                    ToolbarScrollingBehavior.this.animateTranslate(0);
                }
            }
        }
    }

    /* renamed from: com.hotellook.ui.view.appbar.ToolbarScrollingBehavior.2 */
    class C14172 implements AnimatorUpdateListener {
        C14172() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            ToolbarScrollingBehavior.this.transformUi(((Float) animation.getAnimatedValue()).floatValue());
        }
    }

    public ToolbarScrollingBehavior(View toolbar, NestedParent nestedParent) {
        this.toolbar = toolbar;
        this.toolbarContainer = (View) toolbar.getParent();
        this.toolbarHeight = AndroidUtils.getToolbarHeight(toolbar.getContext());
        nestedParent.setOnNestedScrollListener(createOnNestedScrollListener());
        HotellookApplication.eventBus().register(this);
    }

    public void setMovingViews(View... views) {
        this.movingViews = Arrays.asList(views);
    }

    @NonNull
    private OnNestedScrollListener createOnNestedScrollListener() {
        return new C14161();
    }

    private void transformUi(float uiTranslation) {
        translateViews(uiTranslation);
        setToolbarTransparency(convertTranslationToAlpha(uiTranslation));
    }

    public float getTranslationY() {
        if (CollectionUtils.isEmpty(this.movingViews) || this.movingViews.get(0) == null) {
            return 0.0f;
        }
        return ((View) this.movingViews.get(0)).getTranslationY();
    }

    private boolean nothingToMove() {
        return this.movingViews == null || this.movingViews.size() == 0;
    }

    private void translateViews(float uiTranslation) {
        for (View view : this.movingViews) {
            view.setTranslationY(uiTranslation);
        }
        this.toolbarContainer.setTranslationY(uiTranslation);
    }

    private float ensureCollapseTranslationRange(float translation) {
        return Math.min((float) 0, Math.max((float) (-this.toolbarHeight), translation));
    }

    private void animateTranslate(int translate) {
        cancelRunningAnimation();
        this.animator = ValueAnimator.ofFloat(new float[]{getTranslationY(), (float) translate});
        this.animator.setDuration(150);
        this.animator.addUpdateListener(new C14172());
        this.animator.start();
    }

    private void setToolbarTransparency(float alpha) {
        this.toolbar.setAlpha(alpha);
    }

    private float convertTranslationToAlpha(float newTranslation) {
        return 1.0f - (newTranslation / ((float) (-this.toolbarHeight)));
    }

    private void cancelRunningAnimation() {
        if (this.animator != null && this.animator.isRunning()) {
            this.animator.cancel();
        }
    }

    @Subscribe
    public void onExpandAppBarEvent(ExpandAppBarEvent event) {
        if (getTranslationY() != 0.0f) {
            animateTranslate(0);
        }
    }

    public void clean() {
        cancelRunningAnimation();
        this.toolbar = null;
        this.toolbarContainer = null;
        this.movingViews = null;
        HotellookApplication.eventBus().unregister(this);
    }

    public boolean isCollapsed() {
        return getTranslationY() == ((float) (-this.toolbarHeight));
    }

    public void collapse() {
        transformUi((float) (-this.toolbarHeight));
    }
}
