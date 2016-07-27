package com.hotellook.ui.screen.hotel;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.view.View;

public class HotelCardContentHider {
    private static final int ANIM_DURATION = 300;
    private static final long HIDDEN_TIME = 2000;
    private Animator animator;
    private final Handler handler;
    private final View view;
    private Visibility visibility;

    /* renamed from: com.hotellook.ui.screen.hotel.HotelCardContentHider.1 */
    class C12981 implements Runnable {
        C12981() {
        }

        public void run() {
            HotelCardContentHider.this.show();
        }
    }

    private enum Visibility {
        VISIBLE,
        INVISIBLE
    }

    public HotelCardContentHider(View view) {
        this.visibility = Visibility.VISIBLE;
        this.view = view;
        this.handler = new Handler();
    }

    public void hideInstantly() {
        this.visibility = Visibility.INVISIBLE;
        this.view.setAlpha(0.0f);
    }

    public void hideAndShowWithDelay() {
        this.handler.removeCallbacksAndMessages(null);
        this.handler.postDelayed(new C12981(), HIDDEN_TIME);
        if (!this.visibility.equals(Visibility.INVISIBLE)) {
            this.visibility = Visibility.INVISIBLE;
            this.animator = ObjectAnimator.ofFloat(this.view, View.ALPHA, new float[]{this.view.getAlpha(), 0.0f});
            this.animator.setDuration(300);
            this.animator.start();
        }
    }

    public void show() {
        if (!this.visibility.equals(Visibility.VISIBLE)) {
            this.visibility = Visibility.VISIBLE;
            this.animator = ObjectAnimator.ofFloat(this.view, View.ALPHA, new float[]{this.view.getAlpha(), 1.0f});
            this.animator.setDuration(300);
            this.animator.start();
        }
    }
}
