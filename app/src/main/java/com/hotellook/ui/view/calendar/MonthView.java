package com.hotellook.ui.view.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.common.view.MonkeySafeClickListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import me.zhanghai.android.materialprogressbar.C1759R;
import timber.log.Timber;

public class MonthView extends LinearLayout {
    LinearLayout grid;
    private Listener listener;
    private OnClickListener onClickListener;
    TextView title;

    public interface Listener {
        void handleClick(MonthCellDescriptor monthCellDescriptor);
    }

    /* renamed from: com.hotellook.ui.view.calendar.MonthView.1 */
    class C14231 extends MonkeySafeClickListener {
        C14231() {
        }

        public void onSafeClick(View v) {
            MonthView.this.listener.handleClick((MonthCellDescriptor) v.getTag());
        }
    }

    public MonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.onClickListener = new C14231();
    }

    public static MonthView create(ViewGroup parent, LayoutInflater inflater, DateFormat weekdayNameFormat, Listener listener, Calendar today) {
        MonthView view = (MonthView) inflater.inflate(C1178R.layout.view_calendar_month, parent, false);
        int originalDayOfWeek = today.get(7);
        int firstDayOfWeek = today.getFirstDayOfWeek();
        LinearLayout headerRow = (LinearLayout) view.grid.getChildAt(0);
        for (int offset = 0; offset < 7; offset++) {
            today.set(7, firstDayOfWeek + offset);
            ((TextView) headerRow.getChildAt(offset)).setText(weekdayNameFormat.format(today.getTime()));
        }
        today.set(7, originalDayOfWeek);
        view.listener = listener;
        return view;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.title = (TextView) findViewById(C1759R.id.title);
        this.grid = (LinearLayout) findViewById(C1178R.id.calendar_grid);
    }

    public void init(MonthDescriptor month, List<List<MonthCellDescriptor>> cells) {
        Timber.m751d("Initializing MonthView (%d) for %s", Integer.valueOf(System.identityHashCode(this)), month);
        long start = System.currentTimeMillis();
        this.title.setText(month.getLabel());
        int numRows = cells.size();
        for (int i = 0; i < 6; i++) {
            LinearLayout weekRow = (LinearLayout) this.grid.getChildAt(i + 1);
            if (i < numRows) {
                weekRow.setVisibility(0);
                List<MonthCellDescriptor> week = (List) cells.get(i);
                for (int c = 0; c < week.size(); c++) {
                    MonthCellDescriptor cell = (MonthCellDescriptor) week.get(c);
                    CalendarCellView cellView = (CalendarCellView) weekRow.getChildAt(c);
                    cellView.setOnClickListener(this.onClickListener);
                    String cellDate = Integer.toString(cell.getValue());
                    if (!((TextView) cellView.getChildAt(0)).getText().equals(cellDate)) {
                        ((TextView) cellView.getChildAt(0)).setText(cellDate);
                    }
                    cellView.setEnabled(cell.isCurrentMonth());
                    cellView.setSelectable(cell.isSelectable());
                    cellView.setSelected(cell.isSelected());
                    cellView.setCurrentMonth(cell.isCurrentMonth());
                    cellView.setRangeState(cell.getRangeState());
                    cellView.setRangeValid(cell.isRangeValid());
                    cellView.setHighlighted(cell.isHighlighted());
                    cellView.setTag(cell);
                    cellView.setPriceGroupType(cell.getPriceGroupType());
                }
            } else {
                weekRow.setVisibility(8);
            }
        }
        Timber.m751d("MonthView.init took %d ms", Long.valueOf(System.currentTimeMillis() - start));
    }
}
