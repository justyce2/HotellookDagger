package com.hotellook.ui.view.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.hotellook.C1178R;
import com.hotellook.api.data.MinPricesCalendarUtils.PriceGroup;
import com.hotellook.ui.view.calendar.MonthCellDescriptor.RangeState;
import pl.charmas.android.reactivelocation.C1822R;

public class CalendarCellView extends FrameLayout {
    private static final int[] STATE_CURRENT_MONTH;
    private static final int[] STATE_HIGHLIGHTED;
    private static final int[] STATE_PRICE_GROUP_HIGH;
    private static final int[] STATE_PRICE_GROUP_LOW;
    private static final int[] STATE_PRICE_GROUP_NORMAL;
    private static final int[] STATE_SELECTABLE;
    private static final int[] STATE_TODAY;
    private RectF finishRangeBkgRect;
    private Paint invalidRangeDotPaint;
    private Paint invalidRangePaint;
    private boolean isCurrentMonth;
    private boolean isHighlighted;
    private boolean isRangeValid;
    private boolean isSelectable;
    private boolean isToday;
    private RectF middleRangeBkgRect;
    private PriceGroup priceGroupType;
    private int rangeBkgHeight;
    private int rangeBkgRadius;
    private int rangeDotSize;
    private RectF rangeLeftDotRect;
    private RectF rangeRightDotRect;
    private RangeState rangeState;
    private RectF singleRangeBkgRect;
    private RectF startRangeBkgRect;
    private Paint validRangeDotPaint;
    private Paint validRangePaint;

    /* renamed from: com.hotellook.ui.view.calendar.CalendarCellView.1 */
    static /* synthetic */ class C14181 {
        static final /* synthetic */ int[] f734xee3d31aa;

        static {
            f734xee3d31aa = new int[RangeState.values().length];
            try {
                f734xee3d31aa[RangeState.FIRST.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f734xee3d31aa[RangeState.LAST.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f734xee3d31aa[RangeState.MIDDLE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f734xee3d31aa[RangeState.SINGLE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static {
        STATE_SELECTABLE = new int[]{C1178R.attr.state_selectable};
        STATE_CURRENT_MONTH = new int[]{C1178R.attr.state_current_month};
        STATE_TODAY = new int[]{C1178R.attr.state_today};
        STATE_HIGHLIGHTED = new int[]{C1178R.attr.state_highlighted};
        STATE_PRICE_GROUP_LOW = new int[]{C1178R.attr.state_price_group_low};
        STATE_PRICE_GROUP_NORMAL = new int[]{C1178R.attr.state_price_group_normal};
        STATE_PRICE_GROUP_HIGH = new int[]{C1178R.attr.state_price_group_high};
    }

    public CalendarCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isSelectable = false;
        this.isCurrentMonth = false;
        this.isToday = false;
        this.isHighlighted = false;
        this.isRangeValid = true;
        this.rangeState = RangeState.NONE;
        setWillNotDraw(false);
        this.validRangePaint = getRangePaint(C1178R.color.calendar_range_valid_bkg);
        this.invalidRangePaint = getRangePaint(C1178R.color.calendar_range_invalid_bkg);
        this.rangeBkgHeight = getResources().getDimensionPixelSize(C1178R.dimen.calendar_range_bkg_height);
        this.rangeBkgRadius = getResources().getDimensionPixelSize(C1178R.dimen.calendar_range_bkg_radius);
        this.validRangeDotPaint = getRangeDotPaint(C1178R.color.calendar_range_valid_dot);
        this.invalidRangeDotPaint = getRangeDotPaint(C1178R.color.calendar_range_invalid_dot);
        this.rangeDotSize = getResources().getDimensionPixelSize(C1178R.dimen.calendar_range_dot_size);
    }

    private Paint getRangeDotPaint(int colorId) {
        Paint rangeDotPaint = new Paint();
        rangeDotPaint.setColor(getResources().getColor(colorId));
        rangeDotPaint.setAntiAlias(true);
        return rangeDotPaint;
    }

    private Paint getRangePaint(int colorId) {
        Paint rangePaint = new Paint();
        rangePaint.setColor(getResources().getColor(colorId));
        rangePaint.setAntiAlias(true);
        return rangePaint;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int dotYPos = (h / 2) - (this.rangeDotSize / 2);
        int rightDotXPos = w - (this.rangeDotSize / 2);
        int leftDotXPos = (-this.rangeDotSize) / 2;
        this.rangeLeftDotRect = new RectF((float) leftDotXPos, (float) dotYPos, (float) (this.rangeDotSize + leftDotXPos), (float) (this.rangeDotSize + dotYPos));
        this.rangeRightDotRect = new RectF((float) rightDotXPos, (float) dotYPos, (float) (this.rangeDotSize + rightDotXPos), (float) (this.rangeDotSize + dotYPos));
        int rangeBkgYPosition = (h / 2) - (this.rangeBkgHeight / 2);
        this.startRangeBkgRect = new RectF((float) ((w / 2) - this.rangeBkgRadius), (float) rangeBkgYPosition, (float) (this.rangeBkgRadius + w), (float) (this.rangeBkgHeight + rangeBkgYPosition));
        this.finishRangeBkgRect = new RectF((float) (-this.rangeBkgRadius), (float) rangeBkgYPosition, (float) (w - ((w / 2) - this.rangeBkgRadius)), (float) (this.rangeBkgHeight + rangeBkgYPosition));
        this.middleRangeBkgRect = new RectF(0.0f, (float) rangeBkgYPosition, (float) w, (float) (this.rangeBkgHeight + rangeBkgYPosition));
        this.singleRangeBkgRect = new RectF((float) ((w / 2) - this.rangeBkgRadius), (float) rangeBkgYPosition, (float) ((w / 2) + this.rangeBkgRadius), (float) (this.rangeBkgHeight + rangeBkgYPosition));
    }

    public void setSelectable(boolean isSelectable) {
        this.isSelectable = isSelectable;
        refreshDrawableState();
    }

    public void setCurrentMonth(boolean isCurrentMonth) {
        this.isCurrentMonth = isCurrentMonth;
        if (isCurrentMonth) {
            setVisibility(0);
        } else {
            setVisibility(4);
        }
    }

    public void setToday(boolean isToday) {
        this.isToday = isToday;
        refreshDrawableState();
    }

    public void setRangeState(RangeState rangeState) {
        if (this.rangeState != rangeState) {
            this.rangeState = rangeState;
            invalidate();
        }
    }

    public void setHighlighted(boolean highlighted) {
        this.isHighlighted = highlighted;
        refreshDrawableState();
    }

    protected int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 5);
        if (this.isSelectable) {
            mergeDrawableStates(drawableState, STATE_SELECTABLE);
        }
        if (this.isCurrentMonth) {
            mergeDrawableStates(drawableState, STATE_CURRENT_MONTH);
        }
        if (this.isToday) {
            mergeDrawableStates(drawableState, STATE_TODAY);
        }
        if (this.isHighlighted) {
            mergeDrawableStates(drawableState, STATE_HIGHLIGHTED);
        }
        if (this.priceGroupType == PriceGroup.LOW) {
            mergeDrawableStates(drawableState, STATE_PRICE_GROUP_LOW);
        } else if (this.priceGroupType == PriceGroup.NORMAL) {
            mergeDrawableStates(drawableState, STATE_PRICE_GROUP_NORMAL);
        } else if (this.priceGroupType == PriceGroup.HIGH) {
            mergeDrawableStates(drawableState, STATE_PRICE_GROUP_HIGH);
        }
        return drawableState;
    }

    public boolean isRangeValid() {
        return this.isRangeValid;
    }

    public void setRangeValid(boolean isValid) {
        if (isRangeValid() != isValid) {
            this.isRangeValid = isValid;
            invalidate();
        }
    }

    protected void onDraw(Canvas canvas) {
        switch (C14181.f734xee3d31aa[this.rangeState.ordinal()]) {
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                canvas.drawRoundRect(this.startRangeBkgRect, (float) this.rangeBkgRadius, (float) this.rangeBkgRadius, getRangeBkgPaint());
                canvas.drawOval(this.rangeRightDotRect, getRangeDotPaint());
                break;
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
                canvas.drawRoundRect(this.finishRangeBkgRect, (float) this.rangeBkgRadius, (float) this.rangeBkgRadius, getRangeBkgPaint());
                canvas.drawOval(this.rangeLeftDotRect, getRangeDotPaint());
                break;
            case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                canvas.drawRect(this.middleRangeBkgRect, getRangeBkgPaint());
                canvas.drawOval(this.rangeRightDotRect, getRangeDotPaint());
                canvas.drawOval(this.rangeLeftDotRect, getRangeDotPaint());
                break;
            case C1822R.styleable.MapAttrs_cameraTilt /*4*/:
                canvas.drawOval(this.singleRangeBkgRect, getRangeBkgPaint());
                break;
        }
        super.onDraw(canvas);
    }

    private Paint getRangeBkgPaint() {
        return isRangeValid() ? this.validRangePaint : this.invalidRangePaint;
    }

    private Paint getRangeDotPaint() {
        return isRangeValid() ? this.validRangeDotPaint : this.invalidRangeDotPaint;
    }

    public void setPriceGroupType(PriceGroup priceGroupType) {
        this.priceGroupType = priceGroupType;
        refreshDrawableState();
    }
}
