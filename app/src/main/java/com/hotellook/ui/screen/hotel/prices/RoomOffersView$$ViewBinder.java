package com.hotellook.ui.screen.hotel.prices;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import com.hotellook.C1178R;

public class RoomOffersView$$ViewBinder<T extends RoomOffersView> implements ViewBinder<T> {

    /* compiled from: RoomOffersView$$ViewBinder */
    /* renamed from: com.hotellook.ui.screen.hotel.prices.RoomOffersView$.ViewBinder.InnerUnbinder */
    protected static class InnerUnbinder<T extends RoomOffersView> implements Unbinder {
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
            target.roomName = null;
            target.offersContainer = null;
            target.expandBtnContainer = null;
            target.expandBtn = null;
        }
    }

    public Unbinder bind(Finder finder, T target, Object source) {
        InnerUnbinder unbinder = createUnbinder(target);
        target.roomName = (TextView) finder.castView((View) finder.findRequiredView(source, C1178R.id.room_name, "field 'roomName'"), C1178R.id.room_name, "field 'roomName'");
        target.offersContainer = (LinearLayout) finder.castView((View) finder.findRequiredView(source, C1178R.id.offers_container, "field 'offersContainer'"), C1178R.id.offers_container, "field 'offersContainer'");
        target.expandBtnContainer = (FrameLayout) finder.castView((View) finder.findRequiredView(source, C1178R.id.expand_btn_container, "field 'expandBtnContainer'"), C1178R.id.expand_btn_container, "field 'expandBtnContainer'");
        target.expandBtn = (TextView) finder.castView((View) finder.findRequiredView(source, C1178R.id.expand_btn, "field 'expandBtn'"), C1178R.id.expand_btn, "field 'expandBtn'");
        return unbinder;
    }

    protected InnerUnbinder<T> createUnbinder(T target) {
        return new InnerUnbinder(target);
    }
}
