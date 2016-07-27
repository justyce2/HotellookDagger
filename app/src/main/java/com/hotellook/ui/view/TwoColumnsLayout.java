package com.hotellook.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.GridLayout.LayoutParams;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.hotellook.utils.AndroidUtils;
import java.util.ArrayList;
import java.util.List;

public class TwoColumnsLayout extends GridLayout {
    public static final int ONE_COLUMN = 1;
    public static final int TWO_COLUMNS = 2;

    /* renamed from: com.hotellook.ui.view.TwoColumnsLayout.1 */
    class C14131 implements OnGlobalLayoutListener {
        final /* synthetic */ List val$views;

        C14131(List list) {
            this.val$views = list;
        }

        public void onGlobalLayout() {
            if (TwoColumnsLayout.this.getWidth() != 0) {
                TwoColumnsLayout.this.removeOnGlobalLayoutListener(this);
                for (View view : this.val$views) {
                    TwoColumnsLayout.this.add(view, TwoColumnsLayout.this.longerThanHalf(view) ? TwoColumnsLayout.TWO_COLUMNS : TwoColumnsLayout.ONE_COLUMN);
                }
            }
        }
    }

    /* renamed from: com.hotellook.ui.view.TwoColumnsLayout.2 */
    class C14142 implements OnGlobalLayoutListener {
        final /* synthetic */ List val$views;

        C14142(List list) {
            this.val$views = list;
        }

        public void onGlobalLayout() {
            if (TwoColumnsLayout.this.getWidth() != 0) {
                TwoColumnsLayout.this.removeOnGlobalLayoutListener(this);
                TwoColumnsLayout.this.performAddAdaptively(this.val$views);
            }
        }
    }

    public @interface ColumnSpan {
    }

    public TwoColumnsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColumnCount(TWO_COLUMNS);
    }

    public void add(@NonNull View view) {
        add(view, ONE_COLUMN);
    }

    public void add(@NonNull View view, @ColumnSpan int columnSpan) {
        LayoutParams params = new LayoutParams();
        params.width = 0;
        params.height = -2;
        params.columnSpec = columnSpan == ONE_COLUMN ? GridLayout.spec((int) RtlSpacingHelper.UNDEFINED, 1.0f) : GridLayout.spec(0, (int) TWO_COLUMNS, 1.0f);
        view.setLayoutParams(params);
        addView(view);
    }

    public void add(@NonNull List<View> views) {
        addOnGlobalLayoutListener(new C14131(views));
    }

    public void addAdaptively(@NonNull List<View> views) {
        addOnGlobalLayoutListener(new C14142(views));
    }

    private void performAddAdaptively(@NonNull List<View> views) {
        List<View> longViews = new ArrayList();
        for (View view : views) {
            if (longerThanHalf(view)) {
                longViews.add(view);
            } else {
                add(view, ONE_COLUMN);
            }
        }
        for (View view2 : longViews) {
            add(view2, TWO_COLUMNS);
        }
    }

    private boolean longerThanHalf(@NonNull View view) {
        int halfWidth = (int) (((float) ((getWidth() - getPaddingLeft()) - getPaddingRight())) / 2.0f);
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        if (view.getMeasuredWidth() >= halfWidth) {
            return true;
        }
        return false;
    }

    private void addOnGlobalLayoutListener(@NonNull OnGlobalLayoutListener listener) {
        getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    private void removeOnGlobalLayoutListener(@NonNull OnGlobalLayoutListener listener) {
        AndroidUtils.removeOnGlobalLayoutListener(this, listener);
    }
}
