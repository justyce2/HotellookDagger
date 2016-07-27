package com.hotellook.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.View;
import com.hotellook.C1178R;
import com.hotellook.badges.Badge;
import java.util.Collections;
import java.util.List;

public class MarkerBadgesView extends View {
    public static final boolean MULTI_HOTEL_MARKER = false;
    public static final boolean SINGLE_HOTEL_MARKER = true;
    private List<Badge> mBadges;
    private Paint mColoredPaint;
    private Bitmap mMultiStar;
    private Paint mPaint;
    private boolean mSingleHotelMarker;
    private Bitmap mStar;

    public MarkerBadgesView(Context context) {
        super(context);
        this.mBadges = Collections.EMPTY_LIST;
        init();
    }

    public MarkerBadgesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mBadges = Collections.EMPTY_LIST;
        init();
    }

    public MarkerBadgesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBadges = Collections.EMPTY_LIST;
        init();
    }

    private void init() {
        this.mColoredPaint = new Paint();
        this.mColoredPaint.setAntiAlias(SINGLE_HOTEL_MARKER);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(SINGLE_HOTEL_MARKER);
        this.mStar = BitmapFactory.decodeResource(getResources(), C1178R.drawable.ic_star_badge);
        this.mMultiStar = BitmapFactory.decodeResource(getResources(), C1178R.drawable.ic_multi_star_badge);
    }

    protected void onDraw(Canvas canvas) {
        if (this.mBadges.size() != 0) {
            Paint paint;
            if (this.mSingleHotelMarker && this.mBadges.size() == 1) {
                paint = this.mColoredPaint;
                this.mColoredPaint.setColorFilter(new PorterDuffColorFilter(((Badge) this.mBadges.get(0)).color, Mode.SRC_IN));
            } else {
                paint = this.mPaint;
            }
            Bitmap icon = (!this.mSingleHotelMarker || this.mBadges.size() <= 1) ? this.mStar : this.mMultiStar;
            canvas.drawBitmap(icon, 0.0f, 0.0f, paint);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mBadges.size() == 0) {
            setMeasuredDimension(0, 0);
        } else if (!this.mSingleHotelMarker || this.mBadges.size() <= 1) {
            setMeasuredDimension(this.mStar.getWidth(), this.mStar.getHeight());
        } else {
            setMeasuredDimension(this.mMultiStar.getWidth(), this.mMultiStar.getHeight());
        }
    }

    public void setData(boolean markerWithOneHotel, List<Badge> badges) {
        this.mSingleHotelMarker = markerWithOneHotel;
        this.mBadges = badges;
        requestLayout();
    }
}
