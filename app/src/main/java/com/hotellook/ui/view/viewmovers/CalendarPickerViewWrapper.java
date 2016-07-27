package com.hotellook.ui.view.viewmovers;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.hotellook.ui.view.calendar.CalendarPickerView;
import com.hotellook.ui.view.calendar.CalendarPickerView.ListViewObserver;
import java.util.ArrayList;
import java.util.List;

public class CalendarPickerViewWrapper implements OnTouchListener, ScrollableWrapper {
    private final CalendarPickerView calendarPickerView;
    private final List<OnTouchListener> touchListenerList;

    /* renamed from: com.hotellook.ui.view.viewmovers.CalendarPickerViewWrapper.1 */
    class C14521 implements ListViewObserver {
        boolean passFirst;
        final /* synthetic */ ScrollListener val$listener;

        C14521(ScrollListener scrollListener) {
            this.val$listener = scrollListener;
            this.passFirst = false;
        }

        public void onScroll(float deltaY) {
            if (this.passFirst) {
                this.val$listener.onScroll(deltaY);
            } else {
                this.passFirst = true;
            }
        }
    }

    public CalendarPickerViewWrapper(CalendarPickerView calendarPickerView) {
        this.touchListenerList = new ArrayList();
        this.calendarPickerView = calendarPickerView;
    }

    public void listenScrolls(ScrollListener listener) {
        this.calendarPickerView.setObserver(new C14521(listener));
        this.calendarPickerView.setOnTouchListener(this);
    }

    public int scrollY() {
        View c = this.calendarPickerView.getChildAt(0);
        if (c == null) {
            return 0;
        }
        return ((-c.getTop()) + (this.calendarPickerView.getFirstVisiblePosition() * c.getHeight())) + this.calendarPickerView.getPaddingTop();
    }

    public void listenTouches(OnTouchListener touchListener) {
        this.touchListenerList.add(touchListener);
    }

    public boolean onTouch(View v, MotionEvent event) {
        for (OnTouchListener listener : this.touchListenerList) {
            listener.onTouch(v, event);
        }
        return false;
    }
}
