package com.hotellook.ui.screen.locationchooser;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import com.google.android.gms.maps.MapView;
import com.hotellook.C1178R;

public class LocationChooserFragment$$ViewBinder<T extends LocationChooserFragment> implements ViewBinder<T> {

    /* compiled from: LocationChooserFragment$$ViewBinder */
    /* renamed from: com.hotellook.ui.screen.locationchooser.LocationChooserFragment$.ViewBinder.InnerUnbinder */
    protected static class InnerUnbinder<T extends LocationChooserFragment> implements Unbinder {
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
            target.mapView = null;
            target.placeHolder = null;
            target.locationIcon = null;
            target.applyBtn = null;
        }
    }

    public Unbinder bind(Finder finder, T target, Object source) {
        InnerUnbinder unbinder = createUnbinder(target);
        target.mapView = (MapView) finder.castView((View) finder.findRequiredView(source, C1178R.id.map, "field 'mapView'"), C1178R.id.map, "field 'mapView'");
        target.placeHolder = (View) finder.findRequiredView(source, C1178R.id.place_holder, "field 'placeHolder'");
        target.locationIcon = (View) finder.findRequiredView(source, C1178R.id.ic_location, "field 'locationIcon'");
        target.applyBtn = (View) finder.findRequiredView(source, C1178R.id.apply_btn, "field 'applyBtn'");
        return unbinder;
    }

    protected InnerUnbinder<T> createUnbinder(T target) {
        return new InnerUnbinder(target);
    }
}
