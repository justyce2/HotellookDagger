package com.hotellook.ui.view.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.hotellook.C1178R;

public class LoadCircularProgressBar extends View {
    private static final int DEFAULT_ARC_WIDTH = 10;
    private RectF mArcBounds;
    private Paint mArcPaint;
    private int mArcRotation;
    private int mArcWidth;
    private RectF mBackBounds;
    private Paint mBackPaint;
    private Paint mCirclePaint;
    private int mDefaultArcWidth;
    private float mMaxValue;
    private float mProgress;

    public LoadCircularProgressBar(Context context) {
        super(context);
        this.mBackBounds = new RectF();
        this.mArcBounds = new RectF();
        this.mProgress = 0.5f;
        this.mMaxValue = 1.0f;
        this.mArcWidth = 20;
        this.mDefaultArcWidth = 5;
        init(context, null);
    }

    public LoadCircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mBackBounds = new RectF();
        this.mArcBounds = new RectF();
        this.mProgress = 0.5f;
        this.mMaxValue = 1.0f;
        this.mArcWidth = 20;
        this.mDefaultArcWidth = 5;
        init(context, attrs);
    }

    public LoadCircularProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBackBounds = new RectF();
        this.mArcBounds = new RectF();
        this.mProgress = 0.5f;
        this.mMaxValue = 1.0f;
        this.mArcWidth = 20;
        this.mDefaultArcWidth = 5;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, C1178R.styleable.LoadCircularProgressBar, 0, 0);
        int arcColor = attributes.getColor(1, -1);
        int circleColor = attributes.getColor(0, -16777216);
        int arcDefaultColor = attributes.getColor(6, 0);
        this.mArcWidth = attributes.getDimensionPixelSize(2, (int) TypedValue.applyDimension(1, 10.0f, context.getResources().getDisplayMetrics()));
        this.mDefaultArcWidth = attributes.getDimensionPixelSize(7, 0);
        this.mMaxValue = attributes.getFloat(4, 1.0f);
        this.mProgress = attributes.getFloat(5, 0.0f);
        this.mArcRotation = attributes.getInt(3, 0);
        this.mBackPaint = new Paint();
        this.mBackPaint.setStyle(Style.FILL);
        this.mBackPaint.setColor(circleColor);
        this.mBackPaint.setAntiAlias(true);
        this.mArcPaint = new Paint();
        this.mArcPaint.setStyle(Style.STROKE);
        this.mArcPaint.setStrokeWidth((float) this.mArcWidth);
        this.mArcPaint.setColor(arcColor);
        this.mArcPaint.setAntiAlias(true);
        this.mArcPaint.setStrokeCap(Cap.ROUND);
        this.mCirclePaint = new Paint();
        this.mCirclePaint.setStyle(Style.STROKE);
        this.mCirclePaint.setColor(arcDefaultColor);
        this.mCirclePaint.setStrokeWidth((float) this.mDefaultArcWidth);
        this.mCirclePaint.setAntiAlias(true);
        setWillNotDraw(false);
        attributes.recycle();
    }

    public void setColor(int color) {
        this.mArcPaint.setColor(color);
    }

    public float getProgress() {
        return this.mProgress;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        invalidate();
    }

    public int getArcRotation() {
        return this.mArcRotation;
    }

    public void setArcRotation(int rotation) {
        this.mArcRotation = rotation % 360;
        invalidate();
    }

    public float getMaxValue() {
        return this.mMaxValue;
    }

    public void setMaxValue(float maxValue) {
        this.mMaxValue = maxValue;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mBackBounds = new RectF(0.0f, 0.0f, (float) w, (float) h);
        int halfOfArcWidth = this.mArcWidth;
        this.mArcBounds = new RectF((float) halfOfArcWidth, (float) halfOfArcWidth, (float) (w - halfOfArcWidth), (float) (h - halfOfArcWidth));
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawOval(this.mBackBounds, this.mBackPaint);
        canvas.drawArc(this.mArcBounds, 0.0f, 360.0f, false, this.mCirclePaint);
        canvas.drawArc(this.mArcBounds, (float) (this.mArcRotation + 90), 360.0f * (this.mProgress / this.mMaxValue), false, this.mArcPaint);
    }
}
