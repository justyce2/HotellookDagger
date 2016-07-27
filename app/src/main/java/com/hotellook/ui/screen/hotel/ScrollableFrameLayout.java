package com.hotellook.ui.screen.hotel;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollViewWithFixedFling;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import com.hotellook.HotellookApplication;
import com.hotellook.common.reflection.ReflectionUtils;
import com.hotellook.events.HotelScreenScrollEvent;

public class ScrollableFrameLayout extends FrameLayout implements NestedScrollingParent {
    private static final Interpolator quinticInterpolator;
    private boolean flingPositiveDirection;
    private boolean flinging;
    private Object lastFlingDispatcherView;
    private int maxTranslationAbs;
    private ScrollerCompat nestedScroller;
    private final NestedScrollingParentHelper parentHelper;
    private ScrollerCompat scroller;

    /* renamed from: com.hotellook.ui.screen.hotel.ScrollableFrameLayout.1 */
    class C13121 implements Runnable {
        final /* synthetic */ View val$target;
        final /* synthetic */ float val$velocityY;

        C13121(float f, View view) {
            this.val$velocityY = f;
            this.val$target = view;
        }

        public void run() {
            if (ScrollableFrameLayout.this.nestedScroller != null) {
                if (this.val$velocityY < 0.0f && !ScrollableFrameLayout.this.canScrollTop(this.val$target)) {
                    ScrollableFrameLayout.this.flingPositiveDirection = false;
                    ScrollableFrameLayout.this.fling((int) ScrollableFrameLayout.this.nestedScroller.getCurrVelocity());
                    ScrollableFrameLayout.this.removeCallbacks(this);
                    ScrollableFrameLayout.this.nestedScroller = null;
                } else if (ScrollableFrameLayout.this.nestedScroller.computeScrollOffset()) {
                    ScrollableFrameLayout.this.removeCallbacks(this);
                    ViewCompat.postOnAnimation(ScrollableFrameLayout.this, this);
                } else if (ScrollableFrameLayout.this.lastFlingDispatcherView instanceof RecyclerView) {
                    ((RecyclerView) ScrollableFrameLayout.this.lastFlingDispatcherView).stopScroll();
                }
            }
        }
    }

    static {
        quinticInterpolator = ScrollableFrameLayout$$Lambda$1.lambdaFactory$();
    }

    public ScrollableFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.parentHelper = new NestedScrollingParentHelper(this);
        initScrollView();
    }

    private void initScrollView() {
        this.scroller = ScrollerCompat.create(getContext(), quinticInterpolator);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.maxTranslationAbs == 0) {
            Rect visibleRect = new Rect();
            getGlobalVisibleRect(visibleRect);
            this.maxTranslationAbs = (getHeight() - visibleRect.bottom) + visibleRect.top;
        }
    }

    public void fling(int velocityY) {
        this.flinging = true;
        this.scroller.fling(0, (int) (-getTranslationY()), 0, this.flingPositiveDirection ? Math.abs(velocityY) : -Math.abs(velocityY), 0, 0, 0, this.maxTranslationAbs);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & 2) != 0;
    }

    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        this.parentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    public void onStopNestedScroll(View target) {
        this.parentHelper.onStopNestedScroll(target);
    }

    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        this.scroller.abortAnimation();
        Rect visibleRect = new Rect();
        getGlobalVisibleRect(visibleRect);
        int availableTopTranslation = (int) (((((float) getHeight()) + getTranslationY()) - ((float) visibleRect.bottom)) + ((float) visibleRect.top));
        int oldTranslation = (int) getTranslationY();
        int newTranslation = oldTranslation;
        if (dy <= 0 || availableTopTranslation <= 0) {
            if (!canScrollTop(target) && dy < 0 && oldTranslation < 0) {
                newTranslation = Math.min(0, oldTranslation - dy);
                consumed[1] = oldTranslation - newTranslation;
            }
        } else if (availableTopTranslation < dy) {
            newTranslation = oldTranslation - availableTopTranslation;
            consumed[1] = availableTopTranslation;
        } else {
            newTranslation = oldTranslation - dy;
            consumed[1] = dy;
        }
        if (oldTranslation != newTranslation) {
            setTranslationY((float) newTranslation);
        }
    }

    private boolean canScrollTop(View target) {
        if (target instanceof RecyclerView) {
            if (isTopOfRecyclerView((RecyclerView) target)) {
                return false;
            }
            return true;
        } else if (target.getScrollY() == 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isTopOfRecyclerView(RecyclerView recyclerView) {
        boolean noItems;
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        if (firstVisibleItemPosition == -1) {
            noItems = true;
        } else {
            noItems = false;
        }
        boolean firstItemFullyVisible;
        if (firstVisibleItemPosition == 0 && layoutManager.findViewByPosition(firstVisibleItemPosition).getTop() - recyclerView.getPaddingTop() == 0) {
            firstItemFullyVisible = true;
        } else {
            firstItemFullyVisible = false;
        }
        if (noItems || firstItemFullyVisible) {
            return true;
        }
        return false;
    }

    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        this.flinging = false;
        this.nestedScroller = ScrollerCompat.create(getContext(), quinticInterpolator);
        implantCustomScroller(target, this.nestedScroller);
        ViewCompat.postOnAnimation(this, new C13121(velocityY, target));
        return false;
    }

    private void implantCustomScroller(View target, ScrollerCompat flingingScroller) {
        try {
            if (target instanceof RecyclerView) {
                Object viewFlinger = ReflectionUtils.findDeclaredFieldAndMakeItAccessible(target.getClass(), "mViewFlinger").get(target);
                ReflectionUtils.findDeclaredFieldAndMakeItAccessible(viewFlinger.getClass(), "mScroller").set(viewFlinger, flingingScroller);
                return;
            }
            ReflectionUtils.findDeclaredFieldAndMakeItAccessible(target.getClass(), "mScroller").set(target, flingingScroller);
        } catch (IllegalAccessException e) {
        }
    }

    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        this.lastFlingDispatcherView = target;
        this.scroller.abortAnimation();
        if (velocityY > 0.0f && getTranslationY() != ((float) (-this.maxTranslationAbs))) {
            fling((int) velocityY);
            return true;
        } else if (velocityY >= 0.0f || Math.abs(getTranslationY()) >= ((float) this.maxTranslationAbs) || canScrollTop(target)) {
            return false;
        } else {
            fling((int) velocityY);
            return true;
        }
    }

    public int getNestedScrollAxes() {
        return this.parentHelper.getNestedScrollAxes();
    }

    public void computeScroll() {
        if (this.scroller.computeScrollOffset() && this.flinging) {
            int newTranslationY = -this.scroller.getCurrY();
            if (((int) getTranslationY()) != newTranslationY) {
                if (newTranslationY >= 0) {
                    setTranslationY(0.0f);
                    this.flinging = false;
                    this.scroller.abortAnimation();
                } else if (Math.abs(newTranslationY) >= this.maxTranslationAbs) {
                    if (this.lastFlingDispatcherView != null) {
                        if (this.lastFlingDispatcherView instanceof NestedScrollViewWithFixedFling) {
                            ((NestedScrollViewWithFixedFling) this.lastFlingDispatcherView).fling((int) this.scroller.getCurrVelocity());
                        } else if (this.lastFlingDispatcherView instanceof RecyclerView) {
                            this.lastFlingDispatcherView.fling(0, (int) this.scroller.getCurrVelocity());
                        }
                    }
                    setTranslationY((float) (-this.maxTranslationAbs));
                    this.flinging = false;
                    this.scroller.abortAnimation();
                } else {
                    setTranslationY((float) newTranslationY);
                }
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setTranslationY(float translationY) {
        this.flingPositiveDirection = translationY < getTranslationY();
        super.setTranslationY(translationY);
        HotellookApplication.eventBus().post(new HotelScreenScrollEvent((int) (-translationY)));
    }
}
