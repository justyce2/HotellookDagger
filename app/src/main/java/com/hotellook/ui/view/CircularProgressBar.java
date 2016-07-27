package com.hotellook.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.NotificationCompat;
import android.util.AttributeSet;
import com.hotellook.utils.AndroidUtils;

public class CircularProgressBar extends BaseView {
    static final String ANDROIDXML = "http://schemas.android.com/apk/res/android";
    @VisibleForTesting
    public static boolean animationEnabled;
    int arcD;
    int arcO;
    int backgroundColor;
    int cont;
    boolean firstAnimationOver;
    int limite;
    float radius1;
    float radius2;
    float rotateAngle;

    static {
        animationEnabled = true;
    }

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.backgroundColor = Color.parseColor("#1E88E5");
        this.radius1 = 0.0f;
        this.radius2 = 0.0f;
        this.cont = 0;
        this.firstAnimationOver = false;
        this.arcD = 1;
        this.arcO = 0;
        this.rotateAngle = 0.0f;
        this.limite = 0;
        setAttributes(attrs);
    }

    protected void setAttributes(AttributeSet attrs) {
        setMinimumHeight(AndroidUtils.convertDpToPx(getContext(), 32));
        setMinimumWidth(AndroidUtils.convertDpToPx(getContext(), 32));
        int backgroundColor = attrs.getAttributeResourceValue(ANDROIDXML, "background", -1);
        if (backgroundColor != -1) {
            setBackgroundColor(getResources().getColor(backgroundColor));
        } else {
            int background = attrs.getAttributeIntValue(ANDROIDXML, "background", -1);
            if (background != -1) {
                setBackgroundColor(background);
            } else {
                setBackgroundColor(Color.parseColor("#1E88E5"));
            }
        }
        setMinimumHeight(AndroidUtils.convertDpToPx(getContext(), 3));
    }

    protected int makePressColor() {
        return Color.argb(NotificationCompat.FLAG_HIGH_PRIORITY, (this.backgroundColor >> 16) & 255, (this.backgroundColor >> 8) & 255, (this.backgroundColor >> 0) & 255);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.firstAnimationOver) {
            drawFirstAnimation(canvas);
            if (animationEnabled) {
                invalidate();
            }
        }
        if (this.cont > 0) {
            drawSecondAnimation(canvas);
            if (animationEnabled) {
                invalidate();
            }
        }
    }

    private void drawFirstAnimation(Canvas canvas) {
        if (this.radius1 < ((float) (getWidth() / 2))) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(makePressColor());
            this.radius1 = this.radius1 >= ((float) (getWidth() / 2)) ? ((float) getWidth()) / 2.0f : this.radius1 + 2.0f;
            canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), this.radius1, paint);
            return;
        }
        Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Config.ARGB_8888);
        Canvas temp = new Canvas(bitmap);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(makePressColor());
        temp.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), (float) (getHeight() / 2), paint);
        Paint transparentPaint = new Paint();
        transparentPaint.setAntiAlias(true);
        transparentPaint.setColor(getResources().getColor(17170445));
        transparentPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        if (this.cont >= 50) {
            this.radius2 = this.radius2 >= ((float) (getWidth() / 2)) ? ((float) getWidth()) / 2.0f : this.radius2 + 1.0f;
        } else {
            this.radius2 = this.radius2 >= ((float) ((getWidth() / 2) - AndroidUtils.convertDpToPx(getContext(), 4))) ? (((float) getWidth()) / 2.0f) - ((float) AndroidUtils.convertDpToPx(getContext(), 4)) : this.radius2 + 3.0f;
        }
        temp.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), this.radius2, transparentPaint);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, new Paint());
        if (this.radius2 >= ((float) ((getWidth() / 2) - AndroidUtils.convertDpToPx(getContext(), 4)))) {
            this.cont++;
        }
        if (this.radius2 >= ((float) (getWidth() / 2))) {
            this.firstAnimationOver = true;
        }
    }

    private void drawSecondAnimation(Canvas canvas) {
        if (this.arcO == this.limite) {
            this.arcD += 6;
        }
        if (this.arcD >= 290 || this.arcO > this.limite) {
            this.arcO += 6;
            this.arcD -= 6;
        }
        if (this.arcO > this.limite + 290) {
            this.limite = this.arcO;
            this.arcO = this.limite;
            this.arcD = 1;
        }
        this.rotateAngle += 4.0f;
        canvas.rotate(this.rotateAngle, (float) (getWidth() / 2), (float) (getHeight() / 2));
        Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Config.ARGB_8888);
        Canvas temp = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(this.backgroundColor);
        temp.drawArc(new RectF(0.0f, 0.0f, (float) getWidth(), (float) getHeight()), (float) this.arcO, (float) this.arcD, true, paint);
        Paint transparentPaint = new Paint();
        transparentPaint.setAntiAlias(true);
        transparentPaint.setColor(getResources().getColor(17170445));
        transparentPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        temp.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), (float) ((getWidth() / 2) - AndroidUtils.convertDpToPx(getContext(), 4)), transparentPaint);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, new Paint());
    }

    public void setBackgroundColor(int color) {
        super.setBackgroundColor(getResources().getColor(17170445));
        if (isEnabled()) {
            this.beforeBackground = this.backgroundColor;
        }
        this.backgroundColor = color;
    }

    public void reset() {
        this.radius1 = 0.0f;
        this.radius2 = 0.0f;
        this.cont = 0;
        this.firstAnimationOver = false;
        this.arcD = 1;
        this.arcO = 0;
        this.rotateAngle = 0.0f;
        this.limite = 0;
    }
}
