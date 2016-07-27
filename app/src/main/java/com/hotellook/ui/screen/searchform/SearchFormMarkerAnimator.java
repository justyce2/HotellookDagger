package com.hotellook.ui.screen.searchform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.OvershootInterpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.hotellook.C1178R;

public class SearchFormMarkerAnimator {
    private ValueAnimator animator;
    @Nullable
    private Marker marker;
    private Bitmap userPinBitmap;

    /* renamed from: com.hotellook.ui.screen.searchform.SearchFormMarkerAnimator.1 */
    class C13511 extends AnimatorListenerAdapter {
        C13511() {
        }

        public void onAnimationStart(Animator animation) {
            if (SearchFormMarkerAnimator.this.marker != null) {
                SearchFormMarkerAnimator.this.marker.setAlpha(1.0f);
            }
        }
    }

    public SearchFormMarkerAnimator(@NonNull Resources resources, @Nullable Marker marker) {
        this.marker = marker;
        this.userPinBitmap = BitmapFactory.decodeResource(resources, C1178R.drawable.user_map_pin);
    }

    public void animateAppearing(int delay) {
        cancelRunningAnimations();
        this.animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.animator.addUpdateListener(SearchFormMarkerAnimator$$Lambda$1.lambdaFactory$(this));
        this.animator.addListener(new C13511());
        this.animator.setStartDelay((long) delay);
        this.animator.setInterpolator(new OvershootInterpolator());
        this.animator.start();
    }

    /* synthetic */ void lambda$animateAppearing$0(ValueAnimator animation) {
        setScaledMarkerIcon(animation.getAnimatedFraction());
    }

    public void cancelRunningAnimations() {
        if (this.animator != null && this.animator.isRunning()) {
            this.animator.cancel();
        }
    }

    public void animatePinBounce() {
        cancelRunningAnimations();
        this.animator = ValueAnimator.ofFloat(new float[]{0.5f, 1.0f});
        this.animator.addUpdateListener(SearchFormMarkerAnimator$$Lambda$2.lambdaFactory$(this));
        this.animator.setInterpolator(new OvershootInterpolator());
        this.animator.start();
    }

    /* synthetic */ void lambda$animatePinBounce$1(ValueAnimator animation) {
        setScaledMarkerIcon(animation.getAnimatedFraction());
    }

    private void setScaledMarkerIcon(float animatedFraction) {
        if (this.marker != null) {
            int dstWidth = (int) (((float) this.userPinBitmap.getWidth()) * animatedFraction);
            int dstHeight = (int) (((float) this.userPinBitmap.getHeight()) * animatedFraction);
            if (dstWidth == 0 || dstHeight == 0) {
                dstWidth = 1;
                dstHeight = 1;
            }
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(this.userPinBitmap, dstWidth, dstHeight, false);
            BitmapDescriptorFactory.fromBitmap(scaledBitmap);
            this.marker.setIcon(BitmapDescriptorFactory.fromBitmap(scaledBitmap));
            this.marker.setAnchor(0.5f, 0.5f);
        }
    }

    public void release() {
        cancelRunningAnimations();
        this.marker = null;
    }
}
