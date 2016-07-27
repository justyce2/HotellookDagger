package com.hotellook.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.hotellook.C1178R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FlowLayout extends ViewGroup {
    public static final int DEFAULT_HORIZONTAL_SPACING = 5;
    public static final int DEFAULT_VERTICAL_SPACING = 5;
    private List<RowMeasurement> currentRows;
    private final int horizontalSpacing;
    private final int verticalSpacing;

    private final class RowMeasurement {
        private int height;
        private final int maxWidth;
        private int width;
        private final int widthMode;

        public RowMeasurement(int maxWidth, int widthMode) {
            this.maxWidth = maxWidth;
            this.widthMode = widthMode;
        }

        public int getHeight() {
            return this.height;
        }

        public int getWidth() {
            return this.width;
        }

        public boolean wouldExceedMax(int childWidth) {
            return this.widthMode != 0 && getNewWidth(childWidth) > this.maxWidth;
        }

        public void addChildDimensions(int childWidth, int childHeight) {
            this.width = getNewWidth(childWidth);
            this.height = Math.max(this.height, childHeight);
        }

        private int getNewWidth(int childWidth) {
            return this.width == 0 ? childWidth : childWidth + (this.width + FlowLayout.this.horizontalSpacing);
        }
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.currentRows = Collections.emptyList();
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, C1178R.styleable.FlowLayout);
        this.horizontalSpacing = styledAttributes.getDimensionPixelSize(0, DEFAULT_VERTICAL_SPACING);
        this.verticalSpacing = styledAttributes.getDimensionPixelSize(1, DEFAULT_VERTICAL_SPACING);
        styledAttributes.recycle();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size;
        int size2;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int maxInternalWidth = MeasureSpec.getSize(widthMeasureSpec) - getHorizontalPadding();
        int maxInternalHeight = MeasureSpec.getSize(heightMeasureSpec) - getVerticalPadding();
        List<RowMeasurement> rows = new ArrayList();
        RowMeasurement currentRow = new RowMeasurement(maxInternalWidth, widthMode);
        rows.add(currentRow);
        for (View child : getLayoutChildren()) {
            LayoutParams childLayoutParams = child.getLayoutParams();
            child.measure(createChildMeasureSpec(childLayoutParams.width, maxInternalWidth, widthMode), createChildMeasureSpec(childLayoutParams.height, maxInternalHeight, heightMode));
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if (currentRow.wouldExceedMax(childWidth)) {
                currentRow = new RowMeasurement(maxInternalWidth, widthMode);
                rows.add(currentRow);
            }
            currentRow.addChildDimensions(childWidth, childHeight);
        }
        int longestRowWidth = 0;
        int totalRowHeight = 0;
        for (int index = 0; index < rows.size(); index++) {
            RowMeasurement row = (RowMeasurement) rows.get(index);
            totalRowHeight += row.getHeight();
            if (index < rows.size() - 1) {
                totalRowHeight += this.verticalSpacing;
            }
            longestRowWidth = Math.max(longestRowWidth, row.getWidth());
        }
        if (widthMode == 1073741824) {
            size = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            size = getHorizontalPadding() + longestRowWidth;
        }
        if (heightMode == 1073741824) {
            size2 = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            size2 = getVerticalPadding() + totalRowHeight;
        }
        setMeasuredDimension(size, size2);
        this.currentRows = Collections.unmodifiableList(rows);
    }

    private int createChildMeasureSpec(int childLayoutParam, int max, int parentMode) {
        if (childLayoutParam == -1) {
            return MeasureSpec.makeMeasureSpec(max, 1073741824);
        }
        if (childLayoutParam != -2) {
            return MeasureSpec.makeMeasureSpec(childLayoutParam, 1073741824);
        }
        return MeasureSpec.makeMeasureSpec(max, parentMode == 0 ? 0 : RtlSpacingHelper.UNDEFINED);
    }

    protected void onLayout(boolean changed, int leftPosition, int topPosition, int rightPosition, int bottomPosition) {
        int widthOffset = getMeasuredWidth() - getPaddingRight();
        int x = getPaddingLeft();
        int y = getPaddingTop();
        Iterator<RowMeasurement> rowIterator = this.currentRows.iterator();
        RowMeasurement currentRow = (RowMeasurement) rowIterator.next();
        for (View child : getLayoutChildren()) {
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if (x + childWidth > widthOffset) {
                x = getPaddingLeft();
                y += currentRow.height + this.verticalSpacing;
                if (rowIterator.hasNext()) {
                    currentRow = (RowMeasurement) rowIterator.next();
                }
            }
            child.layout(x, y, x + childWidth, y + childHeight);
            x += this.horizontalSpacing + childWidth;
        }
    }

    private List<View> getLayoutChildren() {
        List<View> children = new ArrayList();
        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);
            if (child.getVisibility() != 8) {
                children.add(child);
            }
        }
        return children;
    }

    protected int getVerticalPadding() {
        return getPaddingTop() + getPaddingBottom();
    }

    protected int getHorizontalPadding() {
        return getPaddingLeft() + getPaddingRight();
    }
}
