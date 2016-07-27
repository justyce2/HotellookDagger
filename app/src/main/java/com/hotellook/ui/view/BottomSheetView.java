package com.hotellook.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.BottomSheetOpenedEvent;
import com.hotellook.utils.AndroidUtils;

public class BottomSheetView extends RelativeLayout {
    private View contentView;
    private View dimView;
    private boolean dismissed;

    /* renamed from: com.hotellook.ui.view.BottomSheetView.1 */
    class C13991 extends MonkeySafeClickListener {
        C13991() {
        }

        public void onSafeClick(View v) {
            BottomSheetView.this.dismiss();
        }
    }

    /* renamed from: com.hotellook.ui.view.BottomSheetView.2 */
    class C14002 implements Runnable {
        C14002() {
        }

        public void run() {
            if (AndroidUtils.getNavBarBottomHeight() > 0) {
                View navBarHider = new View(BottomSheetView.this.getContext());
                navBarHider.setBackgroundColor(BottomSheetView.this.getResources().getColor(17170443));
                LayoutParams navBarHiderParams = new LayoutParams(-1, AndroidUtils.getNavBarBottomHeight());
                navBarHiderParams.addRule(12);
                BottomSheetView.this.addView(navBarHider, navBarHiderParams);
            }
        }
    }

    /* renamed from: com.hotellook.ui.view.BottomSheetView.3 */
    class C14023 implements OnGlobalLayoutListener {

        /* renamed from: com.hotellook.ui.view.BottomSheetView.3.1 */
        class C14011 extends AnimatorListenerAdapter {
            C14011() {
            }

            public void onAnimationEnd(Animator animation) {
                HotellookApplication.eventBus().post(new BottomSheetOpenedEvent());
            }
        }

        C14023() {
        }

        public void onGlobalLayout() {
            AndroidUtils.removeOnGlobalLayoutListener(contentView, this);
            Animator animator = ObjectAnimator.ofFloat(contentView, View.Y, (float) contentView.getBottom(), (float) contentView.getTop());
            animator.addListener(new C14011());
            animator.start();
            if (dimView != null) {
                dimView.animate().alpha(1.0f);
            }
        }
    }

    /* renamed from: com.hotellook.ui.view.BottomSheetView.4 */
    class C14034 extends AnimatorListenerAdapter {
        C14034() {
        }

        public void onAnimationEnd(Animator animation) {
            removeView(contentView);
            contentView = null;
            setVisibility(4);
        }
    }

    public BottomSheetView(Context context) {
        super(context);
    }

    public BottomSheetView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.dimView = new View(getContext());
        this.dimView.setBackgroundColor(getResources().getColor(R.color.black_pct_40));
        addView(this.dimView, new LayoutParams(-1, -1));
        this.dimView.setAlpha(0.0f);
        this.dimView.setOnClickListener(new C13991());
        post(new C14002());
        setVisibility(4);
    }

    public void show(View view) {
        if (this.contentView == null) {
            this.contentView = view;
            setVisibility(0);
            this.dismissed = false;
            addContentView();
            animateAfterContentLayout();
        }
    }

    private void addContentView() {
        LayoutParams params = new LayoutParams(getContentWidth(), -2);
        params.addRule(12, this.contentView.getId());
        params.addRule(14, this.contentView.getId());
        AndroidUtils.addPaddingToOffsetNavBarBottom(this.contentView);
        addView(this.contentView, params);
    }

    private int getContentWidth() {
        int widthMult = getResources().getInteger(C1178R.integer.bottom_sheet_width_mult);
        if (widthMult == 0) {
            return -2;
        }
        return getResources().getDimensionPixelSize(C1178R.dimen.default_toolbar_height) * widthMult;
    }

    private void animateAfterContentLayout() {
        this.contentView.getViewTreeObserver().addOnGlobalLayoutListener(new C14023());
    }

    public void dismiss() {
        if (!this.dismissed) {
            this.dismissed = true;
            this.dimView.animate().alpha(0.0f);
            ObjectAnimator animator = ObjectAnimator.ofFloat(this.contentView, View.Y, (float) this.contentView.getTop(), (float) this.contentView.getBottom());
            animator.addListener(new C14034());
            animator.start();
        }
    }

    public boolean isOpen() {
        return this.contentView != null;
    }
}
