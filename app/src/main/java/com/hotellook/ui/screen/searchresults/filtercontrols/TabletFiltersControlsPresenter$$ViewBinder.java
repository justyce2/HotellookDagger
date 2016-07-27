package com.hotellook.ui.screen.searchresults.filtercontrols;

import android.view.View;
import android.widget.Spinner;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import com.hotellook.C1178R;

public class TabletFiltersControlsPresenter$$ViewBinder<T extends TabletFiltersControlsPresenter> implements ViewBinder<T> {

    /* compiled from: TabletFiltersControlsPresenter$$ViewBinder */
    /* renamed from: com.hotellook.ui.screen.searchresults.filtercontrols.TabletFiltersControlsPresenter$.ViewBinder.InnerUnbinder */
    protected static class InnerUnbinder<T extends TabletFiltersControlsPresenter> implements Unbinder {
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
            target.filtersOnIcon = null;
            target.container = null;
            target.spinnerContainer = null;
            target.spinner = null;
            target.sortingShadow = null;
            target.toolbarSortingShadow = null;
        }
    }

    public Unbinder bind(Finder finder, T target, Object source) {
        InnerUnbinder unbinder = createUnbinder(target);
        target.filtersOnIcon = (View) finder.findOptionalView(source, C1178R.id.map_filters_on, null);
        target.container = (View) finder.findRequiredView(source, C1178R.id.filter_sorting_btn_container, "field 'container'");
        target.spinnerContainer = (View) finder.findRequiredView(source, C1178R.id.spinner_container, "field 'spinnerContainer'");
        target.spinner = (Spinner) finder.castView((View) finder.findRequiredView(source, C1178R.id.sorting_spinner, "field 'spinner'"), C1178R.id.sorting_spinner, "field 'spinner'");
        target.sortingShadow = (View) finder.findRequiredView(source, C1178R.id.sorting_shadow, "field 'sortingShadow'");
        target.toolbarSortingShadow = (View) finder.findOptionalView(source, C1178R.id.toolbar_sorting_shadow, null);
        return unbinder;
    }

    protected InnerUnbinder<T> createUnbinder(T target) {
        return new InnerUnbinder(target);
    }
}
