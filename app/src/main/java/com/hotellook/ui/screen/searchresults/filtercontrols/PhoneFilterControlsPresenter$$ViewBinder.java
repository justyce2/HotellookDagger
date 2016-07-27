package com.hotellook.ui.screen.searchresults.filtercontrols;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import com.hotellook.C1178R;
import com.hotellook.ui.screen.searchresults.filtercontrols.PhoneFilterControlsPresenter$$ViewBinder$PhoneFilterControlsPresenter$.ViewBinder;

public class PhoneFilterControlsPresenter$$ViewBinder<T extends PhoneFilterControlsPresenter> implements butterknife.internal.ViewBinder<T> {

    /* renamed from: com.hotellook.ui.screen.searchresults.filtercontrols.PhoneFilterControlsPresenter$.ViewBinder.1 */
    class ViewBinder extends DebouncingOnClickListener {
        final /* synthetic */ PhoneFilterControlsPresenter val$target;

        ViewBinder(PhoneFilterControlsPresenter phoneFilterControlsPresenter) {
            this.val$target = phoneFilterControlsPresenter;
        }

        public void doClick(View p0) {
            this.val$target.onSortBtnClicked();
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.filtercontrols.PhoneFilterControlsPresenter$.ViewBinder.2 */
    class ViewBinder extends DebouncingOnClickListener {
        final /* synthetic */ PhoneFilterControlsPresenter val$target;

        ViewBinder(PhoneFilterControlsPresenter phoneFilterControlsPresenter) {
            this.val$target = phoneFilterControlsPresenter;
        }

        public void doClick(View p0) {
            this.val$target.onResetBtnClicked();
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.filtercontrols.PhoneFilterControlsPresenter$.ViewBinder.3 */
    class ViewBinder extends DebouncingOnClickListener {
        final /* synthetic */ PhoneFilterControlsPresenter val$target;

        ViewBinder(PhoneFilterControlsPresenter phoneFilterControlsPresenter) {
            this.val$target = phoneFilterControlsPresenter;
        }

        public void doClick(View p0) {
            this.val$target.onFilterBtnMapClicked();
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.filtercontrols.PhoneFilterControlsPresenter$.ViewBinder.4 */
    class ViewBinder extends DebouncingOnClickListener {
        final /* synthetic */ PhoneFilterControlsPresenter val$target;

        ViewBinder(PhoneFilterControlsPresenter phoneFilterControlsPresenter) {
            this.val$target = phoneFilterControlsPresenter;
        }

        public void doClick(View p0) {
            this.val$target.onFilterBtnClicked();
        }
    }

    /* compiled from: PhoneFilterControlsPresenter$$ViewBinder */
    /* renamed from: com.hotellook.ui.screen.searchresults.filtercontrols.PhoneFilterControlsPresenter$.ViewBinder.InnerUnbinder */
    protected static class InnerUnbinder<T extends PhoneFilterControlsPresenter> implements Unbinder {
        private T target;
        View view2131689787;
        View view2131689788;
        View view2131689789;
        View view2131689979;

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
            this.view2131689788.setOnClickListener(null);
            target.btnSorting = null;
            target.filtersOnIcon = null;
            target.mapFiltersOnIcon = null;
            this.view2131689789.setOnClickListener(null);
            target.btnReset = null;
            target.listControlsRoot = null;
            this.view2131689979.setOnClickListener(null);
            target.btnMapFilter = null;
            target.quickFiltersContainer = null;
            target.quickFiltersShadow = null;
            this.view2131689787.setOnClickListener(null);
        }
    }

    public Unbinder bind(Finder finder, T target, Object source) {
        InnerUnbinder unbinder = createUnbinder(target);
        View view = (View) finder.findRequiredView(source, C1178R.id.btn_sorting, "field 'btnSorting' and method 'onSortBtnClicked'");
        target.btnSorting = view;
        unbinder.view2131689788 = view;
        view.setOnClickListener(new ViewBinder(target));
        target.filtersOnIcon = (View) finder.findRequiredView(source, C1178R.id.filters_on, "field 'filtersOnIcon'");
        target.mapFiltersOnIcon = (View) finder.findRequiredView(source, C1178R.id.map_filters_on, "field 'mapFiltersOnIcon'");
        view = (View) finder.findRequiredView(source, C1178R.id.reset_btn, "field 'btnReset' and method 'onResetBtnClicked'");
        target.btnReset = view;
        unbinder.view2131689789 = view;
        view.setOnClickListener(new ViewBinder(target));
        target.listControlsRoot = (View) finder.findRequiredView(source, C1178R.id.filter_sorting_btn_container, "field 'listControlsRoot'");
        view = (View) finder.findRequiredView(source, C1178R.id.btn_map_filter, "field 'btnMapFilter' and method 'onFilterBtnMapClicked'");
        target.btnMapFilter = view;
        unbinder.view2131689979 = view;
        view.setOnClickListener(new ViewBinder(target));
        target.quickFiltersContainer = (View) finder.findOptionalView(source, C1178R.id.quick_filters_container, null);
        target.quickFiltersShadow = (View) finder.findOptionalView(source, C1178R.id.quick_filters_shadow, null);
        view = (View) finder.findRequiredView(source, C1178R.id.btn_filter, "method 'onFilterBtnClicked'");
        unbinder.view2131689787 = view;
        view.setOnClickListener(new ViewBinder(target));
        return unbinder;
    }

    protected InnerUnbinder<T> createUnbinder(T target) {
        return new InnerUnbinder(target);
    }
}
