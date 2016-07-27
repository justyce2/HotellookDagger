package com.hotellook.ui.view.placeholder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import com.hotellook.C1178R;
import java.util.Random;

public class TumbleweedView extends View {
    private static final float DEFAULT_SPEED = 50.0f;
    private static final int INVALID_TIME = -1;
    private static final float MAX_JUMP_HEIGHT_IN_METERS = 1.0f;
    private static final float MAX_SPEED = 60.000004f;
    private static final float MIN_SPEED = 40.0f;
    private static final float ROTATION_SPEED = 360.0f;
    private static final float SHADOW_SCALE_FACTOR = 0.7f;
    private static final float SPEED_RANDOM_DELTA = 2.5f;
    private static final float START_POSITION_PERCENT_OF_SCREEN = 0.3f;
    @VisibleForTesting
    public static boolean animationEnabled = false;
    private static final float f738g = 4.0f;
    private float angle;
    private float mBottomPosition;
    private float mCurrentSpeed;
    private float mCurrentVerticalSpeed;
    private float mDensity;
    private Matrix mMatrix;
    private float mMeterInDp;
    private Paint mPaint;
    private Random mRandom;
    private Bitmap mShadow;
    private double mTimeStamp;
    private float mTopPosition;
    private Bitmap mTumbleweed;
    private float f739x;
    private float f740y;

    static {
        animationEnabled = true;
    }

    public TumbleweedView(Context context) {
        super(context);
        this.mTimeStamp = -1.0d;
        this.mCurrentSpeed = DEFAULT_SPEED;
        this.mMatrix = new Matrix();
        init(context);
    }

    public TumbleweedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTimeStamp = -1.0d;
        this.mCurrentSpeed = DEFAULT_SPEED;
        this.mMatrix = new Matrix();
        init(context);
    }

    public TumbleweedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mTimeStamp = -1.0d;
        this.mCurrentSpeed = DEFAULT_SPEED;
        this.mMatrix = new Matrix();
        init(context);
    }

    private void init(Context context) {
        Resources res = context.getResources();
        this.mPaint = new Paint();
        this.mTumbleweed = BitmapFactory.decodeResource(res, C1178R.drawable.tumbleweed);
        this.mShadow = BitmapFactory.decodeResource(res, C1178R.drawable.shadow_tumbleweed);
        this.mDensity = context.getResources().getDisplayMetrics().density;
        this.mRandom = new Random();
        this.mMeterInDp = (float) this.mTumbleweed.getHeight();
    }

    protected void onDraw(Canvas canvas) {
        long time = System.currentTimeMillis();
        if (this.mTimeStamp != -1.0d) {
            double delta = (((double) time) - this.mTimeStamp) / 1000.0d;
            updatePosition(delta);
            drawShadow(canvas);
            drawTumbleweed(canvas, delta);
        } else {
            this.mBottomPosition = (float) (getHeight() - this.mTumbleweed.getHeight());
            this.mTopPosition = MAX_JUMP_HEIGHT_IN_METERS * this.mDensity;
            this.f739x = ((float) getWidth()) * START_POSITION_PERCENT_OF_SCREEN;
            this.f740y = this.mBottomPosition;
        }
        this.mTimeStamp = (double) time;
        if (animationEnabled) {
            invalidate();
        }
    }

    private void drawShadow(Canvas canvas) {
        Bitmap toDraw;
        float scale = MAX_JUMP_HEIGHT_IN_METERS - (SHADOW_SCALE_FACTOR * ((this.mBottomPosition - this.f740y) / (this.mBottomPosition - this.mTopPosition)));
        if (scale == MAX_JUMP_HEIGHT_IN_METERS) {
            toDraw = this.mShadow;
        } else {
            toDraw = Bitmap.createScaledBitmap(this.mShadow, (int) (((float) this.mShadow.getWidth()) * scale), (int) (((float) this.mShadow.getHeight()) * scale), true);
        }
        canvas.drawBitmap(toDraw, this.f739x, (float) (getHeight() - this.mShadow.getHeight()), this.mPaint);
    }

    private void drawTumbleweed(Canvas canvas, double delta) {
        updateAngle(delta);
        this.mMatrix.setTranslate(this.f739x, this.f740y);
        this.mMatrix.postRotate(this.angle, this.f739x + ((float) (this.mTumbleweed.getWidth() / 2)), this.f740y + ((float) (this.mTumbleweed.getHeight() / 2)));
        canvas.drawBitmap(this.mTumbleweed, this.mMatrix, this.mPaint);
    }

    private void updateAngle(double delta) {
        this.angle = (float) (((double) this.angle) + (360.0d * delta));
        this.angle %= ROTATION_SPEED;
    }

    private void updatePosition(double delta) {
        recalculateCurrentSpeed();
        this.f739x = (float) (((double) this.f739x) + (((double) (this.mDensity * this.mCurrentSpeed)) * delta));
        if (this.f739x > ((float) (getWidth() + (this.mTumbleweed.getWidth() * 5)))) {
            this.f739x = (float) (-this.mTumbleweed.getWidth());
        }
        if (this.f740y != this.mBottomPosition || this.mCurrentVerticalSpeed > 0.0f) {
            this.mCurrentVerticalSpeed = (float) (((double) this.mCurrentVerticalSpeed) - ((4.0d * delta) * ((double) this.mMeterInDp)));
            this.f740y = (float) (((double) this.f740y) - (((double) this.mCurrentVerticalSpeed) * delta));
            if (this.f740y > this.mBottomPosition) {
                this.f740y = this.mBottomPosition;
                return;
            }
            return;
        }
        calculateJumpSpeed();
    }

    private void calculateJumpSpeed() {
        this.mCurrentVerticalSpeed = (getRandom(0.2f, MAX_JUMP_HEIGHT_IN_METERS) * this.mMeterInDp) * this.mDensity;
    }

    private void recalculateCurrentSpeed() {
        this.mCurrentSpeed += getRandom(-2.5f, SPEED_RANDOM_DELTA);
        if (this.mCurrentSpeed < MIN_SPEED) {
            this.mCurrentSpeed = MIN_SPEED;
        }
        if (this.mCurrentSpeed > MAX_SPEED) {
            this.mCurrentSpeed = MAX_SPEED;
        }
    }

    private float getRandom(float min, float max) {
        return ((max - min) * this.mRandom.nextFloat()) + min;
    }
}
