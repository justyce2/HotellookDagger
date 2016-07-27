package com.hotellook.ui.screen.searchprogress;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hotellook.C1178R;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class GatesAdapter$GateViewHolder$$ViewBinder<T extends GateViewHolder> implements ViewBinder<T> {

    /* compiled from: GatesAdapter$GateViewHolder$$ViewBinder */
    /* renamed from: com.hotellook.ui.screen.searchprogress.GatesAdapter$GateViewHolder$.ViewBinder.InnerUnbinder */
    protected static class InnerUnbinder<T extends GateViewHolder> implements Unbinder {
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
            target.progressBar = null;
            target.hotelsWebsites = null;
            target.checkMark = null;
            target.logo = null;
        }
    }

    public Unbinder bind(Finder finder, T target, Object source) {
        InnerUnbinder unbinder = createUnbinder(target);
        target.progressBar = (MaterialProgressBar) finder.castView((View) finder.findRequiredView(source, C1178R.id.progressbar, "field 'progressBar'"), C1178R.id.progressbar, "field 'progressBar'");
        target.hotelsWebsites = (TextView) finder.castView((View) finder.findRequiredView(source, C1178R.id.hotels_websites, "field 'hotelsWebsites'"), C1178R.id.hotels_websites, "field 'hotelsWebsites'");
        target.checkMark = (ImageView) finder.castView((View) finder.findRequiredView(source, C1178R.id.check_mark, "field 'checkMark'"), C1178R.id.check_mark, "field 'checkMark'");
        target.logo = (SimpleDraweeView) finder.castView((View) finder.findRequiredView(source, C1178R.id.logo, "field 'logo'"), C1178R.id.logo, "field 'logo'");
        return unbinder;
    }

    protected InnerUnbinder<T> createUnbinder(T target) {
        return new InnerUnbinder(target);
    }
}
