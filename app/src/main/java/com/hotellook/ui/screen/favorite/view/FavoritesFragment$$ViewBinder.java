package com.hotellook.ui.screen.favorite.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import com.hotellook.C1178R;

public class FavoritesFragment$$ViewBinder<T extends FavoritesFragment> implements ViewBinder<T> {

    /* compiled from: FavoritesFragment$$ViewBinder */
    /* renamed from: com.hotellook.ui.screen.favorite.view.FavoritesFragment$.ViewBinder.InnerUnbinder */
    protected static class InnerUnbinder<T extends FavoritesFragment> implements Unbinder {
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
            target.placeHolder = null;
            target.placeHolderMessage = null;
            target.messageArrowUp = null;
            target.messageArrowDown = null;
            target.citiesSpinner = null;
            target.spinnerContainer = null;
            target.progressBar = null;
            target.hotelsList = null;
            target.spinnerShadow = null;
            target.bottomArrowMessage = null;
            target.placeHolderIcon = null;
        }
    }

    public Unbinder bind(Finder finder, T target, Object source) {
        InnerUnbinder unbinder = createUnbinder(target);
        target.placeHolder = (View) finder.findRequiredView(source, C1178R.id.place_holder, "field 'placeHolder'");
        target.placeHolderMessage = (TextView) finder.castView((View) finder.findRequiredView(source, C1178R.id.placeholder_message, "field 'placeHolderMessage'"), C1178R.id.placeholder_message, "field 'placeHolderMessage'");
        target.messageArrowUp = (View) finder.findRequiredView(source, C1178R.id.message_up, "field 'messageArrowUp'");
        target.messageArrowDown = (View) finder.findRequiredView(source, C1178R.id.message_down, "field 'messageArrowDown'");
        target.citiesSpinner = (Spinner) finder.castView((View) finder.findRequiredView(source, C1178R.id.cities_spinner, "field 'citiesSpinner'"), C1178R.id.cities_spinner, "field 'citiesSpinner'");
        target.spinnerContainer = (View) finder.findRequiredView(source, C1178R.id.spinner_container, "field 'spinnerContainer'");
        target.progressBar = (View) finder.findRequiredView(source, C1178R.id.progressbar, "field 'progressBar'");
        target.hotelsList = (RecyclerView) finder.castView((View) finder.findRequiredView(source, C1178R.id.hotels, "field 'hotelsList'"), C1178R.id.hotels, "field 'hotelsList'");
        target.spinnerShadow = (View) finder.findRequiredView(source, C1178R.id.spinner_shadow, "field 'spinnerShadow'");
        target.bottomArrowMessage = (TextView) finder.castView((View) finder.findRequiredView(source, C1178R.id.bottom_arrow_message, "field 'bottomArrowMessage'"), C1178R.id.bottom_arrow_message, "field 'bottomArrowMessage'");
        target.placeHolderIcon = (View) finder.findRequiredView(source, C1178R.id.placeholder_icon, "field 'placeHolderIcon'");
        return unbinder;
    }

    protected InnerUnbinder<T> createUnbinder(T target) {
        return new InnerUnbinder(target);
    }
}
