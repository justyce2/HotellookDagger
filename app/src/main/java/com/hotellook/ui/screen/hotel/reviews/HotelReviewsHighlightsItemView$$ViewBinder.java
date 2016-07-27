package com.hotellook.ui.screen.hotel.reviews;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import com.hotellook.C1178R;
import com.hotellook.ui.view.TwoColumnsLayout;
import me.zhanghai.android.materialprogressbar.C1759R;

public class HotelReviewsHighlightsItemView$$ViewBinder<T extends HotelReviewsHighlightsItemView> implements ViewBinder<T> {

    /* compiled from: HotelReviewsHighlightsItemView$$ViewBinder */
    /* renamed from: com.hotellook.ui.screen.hotel.reviews.HotelReviewsHighlightsItemView$.ViewBinder.InnerUnbinder */
    protected static class InnerUnbinder<T extends HotelReviewsHighlightsItemView> implements Unbinder {
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
            target.titleView = null;
            target.quotesLayout = null;
        }
    }

    public Unbinder bind(Finder finder, T target, Object source) {
        InnerUnbinder unbinder = createUnbinder(target);
        target.titleView = (TextView) finder.castView((View) finder.findRequiredView(source, C1759R.id.title, "field 'titleView'"), C1759R.id.title, "field 'titleView'");
        target.quotesLayout = (TwoColumnsLayout) finder.castView((View) finder.findRequiredView(source, C1178R.id.quotes, "field 'quotesLayout'"), C1178R.id.quotes, "field 'quotesLayout'");
        return unbinder;
    }

    protected InnerUnbinder<T> createUnbinder(T target) {
        return new InnerUnbinder(target);
    }
}
