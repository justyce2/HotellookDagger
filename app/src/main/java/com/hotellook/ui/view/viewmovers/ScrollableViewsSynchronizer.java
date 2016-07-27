package com.hotellook.ui.view.viewmovers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.hotellook.ui.view.calendar.CalendarPickerView;
import java.util.WeakHashMap;

public class ScrollableViewsSynchronizer {
    private static final WeakHashMap<View, ScrollableWrapper> wrappersCache;
    private final AsyncScrollerDelegate asyncScrollerDelegate;
    private final SyncScrollerDelegate syncScrollerDelegate;

    static {
        wrappersCache = new WeakHashMap();
    }

    private ScrollableViewsSynchronizer(ScrollableWrapper scrollable) {
        this.syncScrollerDelegate = new SyncScrollerDelegate(scrollable);
        this.asyncScrollerDelegate = new AsyncScrollerDelegate(scrollable);
    }

    public static ScrollableViewsSynchronizer with(RecyclerView recyclerView) {
        ScrollableWrapper wrapper = (ScrollableWrapper) wrappersCache.get(recyclerView);
        if (wrapper == null) {
            wrapper = new RecyclerViewWrapper(recyclerView);
            wrappersCache.put(recyclerView, wrapper);
        }
        return new ScrollableViewsSynchronizer(wrapper);
    }

    public static ScrollableViewsSynchronizer with(CalendarPickerView calendarPickerView) {
        ScrollableWrapper wrapper = (ScrollableWrapper) wrappersCache.get(calendarPickerView);
        if (wrapper == null) {
            wrapper = new CalendarPickerViewWrapper(calendarPickerView);
            wrappersCache.put(calendarPickerView, wrapper);
        }
        return new ScrollableViewsSynchronizer(wrapper);
    }

    public ScrollableViewsSynchronizer addViewToTranslate(View view, int translation) {
        this.syncScrollerDelegate.addViewToTranslate(view, translation);
        return this;
    }

    public ScrollableViewsSynchronizer addViewToTranslateAndHide(View view, View hideView, int translation) {
        this.syncScrollerDelegate.addViewToTranslateAndHide(view, hideView, translation);
        return this;
    }

    public ScrollableViewsSynchronizer addViewToTranslateAndHide(View view, int translation) {
        this.syncScrollerDelegate.addViewToTranslateAndHide(view, translation);
        return this;
    }

    public ScrollableViewsSynchronizer addViewToTranslateAsync(View view, View hideView, int translation) {
        this.asyncScrollerDelegate.addViewToTranslateAsync(view, hideView, translation);
        return this;
    }

    public void moveBack() {
        this.syncScrollerDelegate.moveBack();
        this.asyncScrollerDelegate.moveBack();
    }

    public void cancelAnimation() {
        this.syncScrollerDelegate.cancelAllAnimations();
        this.asyncScrollerDelegate.cancelAllAnimatiors();
    }
}
