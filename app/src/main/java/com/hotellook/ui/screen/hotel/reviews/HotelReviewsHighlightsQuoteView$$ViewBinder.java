package com.hotellook.ui.screen.hotel.reviews;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import com.hotellook.C1178R;

public class HotelReviewsHighlightsQuoteView$$ViewBinder<T extends HotelReviewsHighlightsQuoteView> implements ViewBinder<T> {

    /* compiled from: HotelReviewsHighlightsQuoteView$$ViewBinder */
    /* renamed from: com.hotellook.ui.screen.hotel.reviews.HotelReviewsHighlightsQuoteView$.ViewBinder.InnerUnbinder */
    protected static class InnerUnbinder<T extends HotelReviewsHighlightsQuoteView> implements Unbinder {
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
            target.quoteView = null;
        }
    }

    public Unbinder bind(Finder finder, T target, Object source) {
        InnerUnbinder unbinder = createUnbinder(target);
        target.quoteView = (TextView) finder.castView((View) finder.findRequiredView(source, C1178R.id.quote, "field 'quoteView'"), C1178R.id.quote, "field 'quoteView'");
        return unbinder;
    }

    protected InnerUnbinder<T> createUnbinder(T target) {
        return new InnerUnbinder(target);
    }
}
