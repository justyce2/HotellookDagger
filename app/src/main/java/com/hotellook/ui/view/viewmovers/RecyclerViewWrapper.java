package com.hotellook.ui.view.viewmovers;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewWrapper implements OnTouchListener, ScrollableWrapper {
    private final RecyclerView recyclerView;
    private final List<OnTouchListener> touchListenerList;

    /* renamed from: com.hotellook.ui.view.viewmovers.RecyclerViewWrapper.1 */
    class C14531 extends OnScrollListener {
        final /* synthetic */ ScrollListener val$scrollListener;

        C14531(ScrollListener scrollListener) {
            this.val$scrollListener = scrollListener;
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            this.val$scrollListener.onScroll((float) (-dy));
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == 0) {
                this.val$scrollListener.onScrollFinished();
            }
        }
    }

    public RecyclerViewWrapper(RecyclerView recyclerView) {
        this.touchListenerList = new ArrayList();
        this.recyclerView = recyclerView;
    }

    public void listenScrolls(ScrollListener scrollListener) {
        this.recyclerView.addOnScrollListener(new C14531(scrollListener));
        this.recyclerView.setOnTouchListener(this);
    }

    public int scrollY() {
        return this.recyclerView.computeVerticalScrollOffset();
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
