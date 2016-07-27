package com.hotellook.ui.screen.hotel.amenity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.hotellook.C1178R;
import com.hotellook.utils.AndroidUtils;
import java.util.ArrayList;
import java.util.List;

public class AmenitiesTwoColumnLayout extends LinearLayout {
    private int amenityHeight;
    private LinearLayout leftColumn;
    private LinearLayout rightColumn;
    private int standardOffset;
    private List<View> views;

    /* renamed from: com.hotellook.ui.screen.hotel.amenity.AmenitiesTwoColumnLayout.1 */
    class C13131 implements OnGlobalLayoutListener {
        C13131() {
        }

        public void onGlobalLayout() {
            if (AmenitiesTwoColumnLayout.this.getWidth() != 0) {
                AndroidUtils.removeOnGlobalLayoutListener(AmenitiesTwoColumnLayout.this, this);
                AmenitiesTwoColumnLayout.this.distributeViewsByColumns();
            }
        }
    }

    public AmenitiesTwoColumnLayout(Context context) {
        super(context, null);
    }

    public AmenitiesTwoColumnLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(1);
        this.standardOffset = getResources().getDimensionPixelSize(C1178R.dimen.standard_offset);
        this.amenityHeight = getResources().getDimensionPixelSize(C1178R.dimen.hotel_general_exp_item_height);
        inflate(getContext(), C1178R.layout.smart_two_column_layout, this);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.leftColumn = (LinearLayout) findViewById(C1178R.id.left_column);
        this.rightColumn = (LinearLayout) findViewById(C1178R.id.right_column);
    }

    public void addViews(List<View> views) {
        this.views = views;
        getViewTreeObserver().addOnGlobalLayoutListener(new C13131());
    }

    private void distributeViewsByColumns() {
        int i;
        List<View> smallItems = new ArrayList();
        List<View> bigItems = new ArrayList();
        sortOnLists(smallItems, bigItems);
        for (i = 0; i < smallItems.size(); i++) {
            View view = (View) smallItems.get(i);
            if (i % 2 == 0) {
                addViewToColumn(view, this.leftColumn);
            } else {
                addViewToColumn(view, this.rightColumn);
            }
        }
        i = 0;
        while (i < bigItems.size()) {
            view = (View) bigItems.get(i);
            boolean z = this.leftColumn.getChildCount() > 0 || i > 0;
            addView(view, generateLayoutParams(z));
            i++;
        }
    }

    private void addViewToColumn(View view, LinearLayout column) {
        column.addView(view, generateLayoutParams(column.getChildCount() > 0));
    }

    private void sortOnLists(List<View> smallItems, List<View> bigItems) {
        int smallItemWidthLimit = (((getWidth() - getPaddingLeft()) - getPaddingBottom()) / 2) - (this.standardOffset / 2);
        for (int i = 0; i < this.views.size(); i++) {
            View view = (View) this.views.get(i);
            if (viewFitsLimit(smallItemWidthLimit, view)) {
                smallItems.add(view);
            } else {
                bigItems.add(view);
            }
        }
        transferSmallOddItemToBigsForBetterLook(smallItems, bigItems);
    }

    private boolean viewFitsLimit(int smallItemWidthLimit, View view) {
        measureView(view);
        return view.getMeasuredWidth() < smallItemWidthLimit;
    }

    private void measureView(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
    }

    private void transferSmallOddItemToBigsForBetterLook(List<View> smallItems, List<View> bigItems) {
        if (smallItems.size() % 2 != 0) {
            bigItems.add((View) smallItems.remove(smallItems.size() - 1));
        }
    }

    private LayoutParams generateLayoutParams(boolean addTopOffset) {
        LayoutParams layoutParams = new LayoutParams(-2, this.amenityHeight);
        if (addTopOffset) {
            layoutParams.topMargin = this.standardOffset;
        }
        return layoutParams;
    }

    public int getRowsHeight(int collapsedRows) {
        return (((View) this.views.get(0)).getHeight() * collapsedRows) + (this.standardOffset * (collapsedRows - 1));
    }
}
