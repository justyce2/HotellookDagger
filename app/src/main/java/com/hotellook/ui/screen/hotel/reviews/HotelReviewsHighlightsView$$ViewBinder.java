package com.hotellook.ui.screen.hotel.reviews;

import android.view.View;
import android.view.ViewGroup;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import com.hotellook.C1178R;

public class HotelReviewsHighlightsView$$ViewBinder<T extends HotelReviewsHighlightsView> implements ViewBinder<T> {

    /* compiled from: HotelReviewsHighlightsView$$ViewBinder */
    /* renamed from: com.hotellook.ui.screen.hotel.reviews.HotelReviewsHighlightsView$.ViewBinder.InnerUnbinder */
    protected static class InnerUnbinder<T extends HotelReviewsHighlightsView> implements Unbinder {
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
            target.reviewsHighlightsLayout = null;
        }
    }

    public Unbinder bind(Finder finder, T target, Object source) {
        InnerUnbinder unbinder = createUnbinder(target);
        target.reviewsHighlightsLayout = (ViewGroup) finder.castView((View) finder.findRequiredView(source, C1178R.id.reviews_highlights, "field 'reviewsHighlightsLayout'"), C1178R.id.reviews_highlights, "field 'reviewsHighlightsLayout'");
        return unbinder;
    }

    protected InnerUnbinder<T> createUnbinder(T target) {
        return new InnerUnbinder(target);
    }
}
