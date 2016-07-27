package com.hotellook.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;
import com.hotellook.C1178R;

public class ViewUtils {
    private static final int VIEW_ANIMATION_DURATION = 200;

    /* renamed from: com.hotellook.utils.ViewUtils.1 */
    static class C14571 extends AnimatorListenerAdapter {
        final /* synthetic */ View val$view;

        C14571(View view) {
            this.val$view = view;
        }

        public void onAnimationEnd(Animator animation) {
            this.val$view.setVisibility(4);
        }
    }

    /* renamed from: com.hotellook.utils.ViewUtils.2 */
    static class C14582 extends AnimatorListenerAdapter {
        final /* synthetic */ View val$view;

        C14582(View view) {
            this.val$view = view;
        }

        public void onAnimationEnd(Animator animation) {
            this.val$view.setVisibility(8);
        }
    }

    public static void hideView(View view) {
        if (view.getVisibility() == 0) {
            view.animate().alpha(0.0f).setDuration(200).setListener(new C14571(view));
        }
    }

    public static void goneView(View view) {
        if (view.getVisibility() != 8) {
            view.animate().alpha(0.0f).setDuration(200).setListener(new C14582(view));
        }
    }

    public static void showView(View view) {
        if (view.getVisibility() != 0 || view.getAlpha() < 1.0f) {
            view.setVisibility(0);
            view.animate().alpha(1.0f).setDuration(200).setListener(null);
        }
    }

    public static void showView(View view, boolean instantly) {
        if (instantly) {
            view.setVisibility(0);
        } else {
            showView(view);
        }
    }

    public static void goneView(View view, boolean instantly) {
        if (instantly) {
            view.setVisibility(8);
        } else {
            goneView(view);
        }
    }

    public static void hideView(View view, boolean instantly) {
        if (instantly) {
            view.setVisibility(4);
        } else {
            hideView(view);
        }
    }

    public static void addLeftAndRightPaddingsForWideScreen(View view) {
        int padding = calculateSideOffsetForWideScreens(view.getContext());
        if (padding > 0) {
            view.setPadding(padding, view.getPaddingTop(), padding, view.getPaddingBottom());
        }
    }

    private static int calculateSideOffsetForWideScreens(Context context) {
        int maxWidth = getContentMaxWidth(context);
        int screenWidth = AndroidUtils.getDisplaySize(context).x;
        if (maxWidth > screenWidth) {
            return 0;
        }
        return (screenWidth - maxWidth) / 2;
    }

    public static int getContentMaxWidth(Context context) {
        return context.getResources().getDimensionPixelSize(C1178R.dimen.wide_screen_content_width);
    }

    public static void addTopPadding(View view, int offset) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + offset, view.getPaddingRight(), view.getPaddingBottom());
    }

    public static void addBottomPadding(View view, int offset) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom() + offset);
    }
}
