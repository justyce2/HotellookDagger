package com.hotellook.ui.view.placeholder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class CloudsView extends View {
    private static final float SPEED_DP_PER_SEC = 20.0f;
    @VisibleForTesting
    public static boolean animationEnabled;
    private final List<Cloud> mClouds;
    private float mDensity;
    private Paint mPaint;
    private double mTimeStamp;

    private static class Cloud {
        public final Bitmap bitmap;
        public final float speedMult;
        public float f736x;
        public final int f737y;

        private Cloud(Bitmap bitmap, float speedMult, int y) {
            this.bitmap = bitmap;
            this.speedMult = speedMult;
            this.f737y = y;
        }
    }

    static {
        animationEnabled = true;
    }

    public CloudsView(Context context) {
        super(context);
        this.mClouds = new ArrayList();
        this.mTimeStamp = -1.0d;
        init(context);
    }

    public CloudsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mClouds = new ArrayList();
        this.mTimeStamp = -1.0d;
        init(context);
    }

    public CloudsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mClouds = new ArrayList();
        this.mTimeStamp = -1.0d;
        init(context);
    }

    private void init(Context context) {
        this.mPaint = new Paint();
        Resources res = context.getResources();
        this.mDensity = context.getResources().getDisplayMetrics().density;
        this.mClouds.add(new Cloud(0.3f, 0, null));
        this.mClouds.add(new Cloud(0.6f, (int) (22.0f * this.mDensity), null));
        this.mClouds.add(new Cloud(0.8f, (int) (40.0f * this.mDensity), null));
    }

    protected void onDraw(Canvas canvas) {
        long time = System.currentTimeMillis();
        if (this.mTimeStamp != -1.0d) {
            drawClouds(canvas, time);
        } else {
            placeToStartPositions();
        }
        this.mTimeStamp = (double) time;
        if (animationEnabled) {
            invalidate();
        }
    }

    private void placeToStartPositions() {
        int width = getWidth();
        float percent = 0.1f;
        for (Cloud cloud : this.mClouds) {
            cloud.f736x = ((float) width) * percent;
            percent += 0.25f;
        }
    }

    private void drawClouds(Canvas canvas, long time) {
        for (Cloud cloud : this.mClouds) {
            updatePosition(cloud, (((double) time) - this.mTimeStamp) / 1000.0d);
            canvas.drawBitmap(cloud.bitmap, cloud.f736x, (float) cloud.f737y, this.mPaint);
        }
    }

    private void updatePosition(Cloud cloud, double timeDelta) {
        cloud.f736x = (float) (((double) cloud.f736x) + (((double) ((this.mDensity * SPEED_DP_PER_SEC) * cloud.speedMult)) * timeDelta));
        if (cloud.f736x > ((float) getWidth())) {
            cloud.f736x = (float) (-cloud.bitmap.getWidth());
        }
    }
}
