package com.hotellook.ui.screen.searchprogress;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import com.hotellook.C1178R;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class SearchProgressFragment$$ViewBinder<T extends SearchProgressFragment> implements ViewBinder<T> {

    /* compiled from: SearchProgressFragment$$ViewBinder */
    /* renamed from: com.hotellook.ui.screen.searchprogress.SearchProgressFragment$.ViewBinder.InnerUnbinder */
    protected static class InnerUnbinder<T extends SearchProgressFragment> implements Unbinder {
        private T target;

        protected InnerUnbinder(T target) {
            this.target = target;
        }

        public final void unbind() {
            if (this.target == null) {
                throw new IllegalStateException("Bindings already cleared.");
            }
            unbind(this.target);
            this.target = null;
        }

        protected void unbind(T target) {
            target.recyclerView = null;
            target.progressBar = null;
            target.gatesListWaiter = null;
        }
    }

    public Unbinder bind(Finder finder, T target, Object source) {
        InnerUnbinder unbinder = createUnbinder(target);
        target.recyclerView = (RecyclerView) finder.castView((View) finder.findRequiredView(source, C1178R.id.recycler_view, "field 'recyclerView'"), C1178R.id.recycler_view, "field 'recyclerView'");
        target.progressBar = (ProgressBar) finder.castView((View) finder.findRequiredView(source, C1178R.id.progressbar, "field 'progressBar'"), C1178R.id.progressbar, "field 'progressBar'");
        target.gatesListWaiter = (MaterialProgressBar) finder.castView((View) finder.findRequiredView(source, C1178R.id.gates_list_waiter, "field 'gatesListWaiter'"), C1178R.id.gates_list_waiter, "field 'gatesListWaiter'");
        return unbinder;
    }

    protected InnerUnbinder<T> createUnbinder(T target) {
        return new InnerUnbinder(target);
    }
}
