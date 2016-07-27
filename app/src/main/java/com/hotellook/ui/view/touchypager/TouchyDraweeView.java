package com.hotellook.ui.view.touchypager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import timber.log.Timber;

public class TouchyDraweeView extends DraweeView<GenericDraweeHierarchy> implements TouchHandler, TouchListener {
    private final ControllerListener controllerListener;
    private final RectF imageBounds;
    @Nullable
    private ImageFormController imageFormController;
    private final RectF viewBounds;

    /* renamed from: com.hotellook.ui.view.touchypager.TouchyDraweeView.1 */
    class C14511 extends BaseControllerListener<Object> {
        C14511() {
        }

        public void onFinalImageSet(String id, @Nullable Object imageInfo, @Nullable Animatable animatable) {
            TouchyDraweeView.this.updateZoomableControllerBounds();
            TouchyDraweeView.this.onFinalImageSet();
        }

        public void onRelease(String id) {
            TouchyDraweeView.this.onRelease();
        }
    }

    public TouchyDraweeView(Context context) {
        super(context);
        this.imageBounds = new RectF();
        this.viewBounds = new RectF();
        this.controllerListener = new C14511();
    }

    public TouchyDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.imageBounds = new RectF();
        this.viewBounds = new RectF();
        this.controllerListener = new C14511();
    }

    public TouchyDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.imageBounds = new RectF();
        this.viewBounds = new RectF();
        this.controllerListener = new C14511();
    }

    public void setImageFormController(@NonNull ImageFormController controller) {
        this.imageFormController = controller;
        this.imageFormController.setListener(this);
    }

    public void computeScroll() {
        super.computeScroll();
        Timber.m751d("Compute scroll called", new Object[0]);
    }

    protected void onDraw(Canvas canvas) {
        int saveCount = canvas.save();
        if (this.imageFormController != null) {
            canvas.concat(this.imageFormController.getMatrix());
        }
        super.onDraw(canvas);
        canvas.restoreToCount(saveCount);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public boolean handleTouch(MotionEvent event) {
        boolean handled;
        if ((this.imageFormController == null || !this.imageFormController.onTouchEvent(event)) && !super.onTouchEvent(event)) {
            handled = false;
        } else {
            handled = true;
        }
        Timber.m751d("Result of drawee view %b", Boolean.valueOf(handled));
        return handled;
    }

    public boolean interceptTouch(MotionEvent event) {
        return this.imageFormController != null && this.imageFormController.onInterceptTouchEvent(event);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        updateZoomableControllerBounds();
    }

    private void onFinalImageSet() {
        if (this.imageFormController != null) {
            this.imageFormController.setEnabled(true);
        }
    }

    private void onRelease() {
        if (this.imageFormController != null) {
            this.imageFormController.setEnabled(false);
        }
    }

    public void setController(@Nullable DraweeController draweeController) {
        super.setController(draweeController);
        if (draweeController instanceof AbstractDraweeController) {
            ((AbstractDraweeController) draweeController).addControllerListener(this.controllerListener);
        }
    }

    public void onTransformChanged() {
        invalidate();
    }

    public void onAnimationUpdate() {
        postInvalidateOnAnimation();
    }

    private void updateZoomableControllerBounds() {
        ((GenericDraweeHierarchy) getHierarchy()).getActualImageBounds(this.imageBounds);
        this.viewBounds.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        if (this.imageFormController != null) {
            this.imageFormController.updateBounds(this.viewBounds, this.imageBounds);
        }
    }

    public void resetZoom() {
        if (this.imageFormController != null) {
            this.imageFormController.reset();
        }
    }

    public float getTranslationY() {
        if (this.imageFormController == null) {
            return super.getTranslationY();
        }
        return this.imageFormController.getCenterTranslationY();
    }

    public float getTranslationX() {
        if (this.imageFormController == null) {
            return super.getTranslationX();
        }
        return this.imageFormController.getCenterTranslationX();
    }

    public void getCenterPosition(int[] position) {
        if (this.imageFormController == null) {
            super.getLocationOnScreen(position);
        }
        this.imageFormController.getCenterPosition(position);
    }
}
