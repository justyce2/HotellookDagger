package com.hotellook.api;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class LoadDrawable extends Drawable {
    private static final float BACK_ALPHA = 76.5f;
    private static final int MAX_LEVEL = 10000;
    private static final int MIN_LEVEL = 1000;
    private int mAlpha;
    private RectF mArcBounds;
    private int mArcColor;
    private Paint mArcPaint;
    private int mArcWidth;
    private RectF mBackBounds;
    private int mBackColor;
    private Paint mBackPaint;
    private int mLevel;
    private int mSize;

    public LoadDrawable() {
        this.mBackBounds = new RectF();
        this.mArcBounds = new RectF();
        this.mLevel = MIN_LEVEL;
        this.mArcWidth = 20;
        this.mSize = 64;
        this.mBackColor = -16777216;
        this.mArcColor = -1;
        this.mAlpha = -1;
        init();
    }

    private void init() {
        this.mBackPaint = new Paint();
        this.mBackPaint.setStyle(Style.FILL);
        this.mBackPaint.setAntiAlias(true);
        this.mArcPaint = new Paint();
        this.mArcPaint.setStyle(Style.STROKE);
        this.mArcPaint.setAntiAlias(true);
    }

    public void draw(Canvas canvas) {
        int wHalf = this.mSize / 2;
        int hHalf = this.mSize / 2;
        Rect bounds = getBounds();
        int left = bounds.centerX() - wHalf;
        int top = bounds.centerY() - hHalf;
        int bottom = bounds.centerY() + hHalf;
        int right = bounds.centerX() + wHalf;
        this.mBackBounds = new RectF((float) left, (float) top, (float) right, (float) bottom);
        int halfOfArcWidth = this.mArcWidth / 2;
        this.mArcBounds = new RectF((float) (left + halfOfArcWidth), (float) (top + halfOfArcWidth), (float) (right - halfOfArcWidth), (float) (bottom - halfOfArcWidth));
        this.mBackPaint.setColor(this.mBackColor);
        this.mBackPaint.setAlpha((int) ((((float) this.mAlpha) / 255.0f) * BACK_ALPHA));
        this.mArcPaint.setStrokeWidth((float) this.mArcWidth);
        this.mArcPaint.setColor(this.mArcColor);
        this.mArcPaint.setAlpha(this.mAlpha);
        canvas.drawOval(this.mBackBounds, this.mBackPaint);
        Canvas canvas2 = canvas;
        canvas2.drawArc(this.mArcBounds, -90.0f, 360.0f * (((float) this.mLevel) / 10000.0f), false, this.mArcPaint);
    }

    public void setArcColor(int arcColor) {
        this.mArcColor = arcColor;
    }

    public void setSize(int size) {
        this.mSize = size;
    }

    public void setBackColor(int backColor) {
        this.mBackColor = backColor;
    }

    public void setArcWidth(int arcWidth) {
        this.mArcWidth = arcWidth;
    }

    public void setAlpha(int alpha) {
        this.mAlpha = alpha;
    }

    public void setColorFilter(ColorFilter cf) {
        this.mBackPaint.setColorFilter(cf);
        this.mArcPaint.setColorFilter(cf);
    }

    public int getOpacity() {
        return 0;
    }

    protected boolean onLevelChange(int level) {
        if (level < MIN_LEVEL) {
            level = MIN_LEVEL;
        }
        this.mLevel = level;
        invalidateSelf();
        return true;
    }
}
