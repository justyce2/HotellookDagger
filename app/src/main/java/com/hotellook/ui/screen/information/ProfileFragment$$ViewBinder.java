package com.hotellook.ui.screen.information;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import com.hotellook.C1178R;
import com.hotellook.ui.screen.information.ProfileFragment$$ViewBinder$ProfileFragment$.ViewBinder;

public class ProfileFragment$$ViewBinder<T extends ProfileFragment> implements butterknife.internal.ViewBinder<T> {

    /* renamed from: com.hotellook.ui.screen.information.ProfileFragment$.ViewBinder.1 */
    class ViewBinder extends DebouncingOnClickListener {
        final /* synthetic */ ProfileFragment val$target;

        ViewBinder(ProfileFragment profileFragment) {
            this.val$target = profileFragment;
        }

        public void doClick(View p0) {
            this.val$target.onRateBtnClick();
        }
    }

    /* renamed from: com.hotellook.ui.screen.information.ProfileFragment$.ViewBinder.2 */
    class ViewBinder extends DebouncingOnClickListener {
        final /* synthetic */ ProfileFragment val$target;

        ViewBinder(ProfileFragment profileFragment) {
            this.val$target = profileFragment;
        }

        public void doClick(View p0) {
            this.val$target.onDevBtnClick();
        }
    }

    /* renamed from: com.hotellook.ui.screen.information.ProfileFragment$.ViewBinder.3 */
    class ViewBinder extends DebouncingOnClickListener {
        final /* synthetic */ ProfileFragment val$target;

        ViewBinder(ProfileFragment profileFragment) {
            this.val$target = profileFragment;
        }

        public void doClick(View p0) {
            this.val$target.onEnGatesAllowCheckedChanged();
        }
    }

    /* renamed from: com.hotellook.ui.screen.information.ProfileFragment$.ViewBinder.4 */
    class ViewBinder extends DebouncingOnClickListener {
        final /* synthetic */ ProfileFragment val$target;

        ViewBinder(ProfileFragment profileFragment) {
            this.val$target = profileFragment;
        }

        public void doClick(View p0) {
            this.val$target.onSocialBtnClick();
        }
    }

    /* renamed from: com.hotellook.ui.screen.information.ProfileFragment$.ViewBinder.5 */
    class ViewBinder extends DebouncingOnClickListener {
        final /* synthetic */ ProfileFragment val$target;

        ViewBinder(ProfileFragment profileFragment) {
            this.val$target = profileFragment;
        }

        public void doClick(View p0) {
            this.val$target.onFeedbackBtnClick();
        }
    }

    /* renamed from: com.hotellook.ui.screen.information.ProfileFragment$.ViewBinder.6 */
    class ViewBinder extends DebouncingOnClickListener {
        final /* synthetic */ ProfileFragment val$target;

        ViewBinder(ProfileFragment profileFragment) {
            this.val$target = profileFragment;
        }

        public void doClick(View p0) {
            this.val$target.onLicenseBtnClick();
        }
    }

    /* renamed from: com.hotellook.ui.screen.information.ProfileFragment$.ViewBinder.7 */
    class ViewBinder extends DebouncingOnClickListener {
        final /* synthetic */ ProfileFragment val$target;

        ViewBinder(ProfileFragment profileFragment) {
            this.val$target = profileFragment;
        }

        public void doClick(View p0) {
            this.val$target.onAboutUsClick();
        }
    }

    /* renamed from: com.hotellook.ui.screen.information.ProfileFragment$.ViewBinder.8 */
    class ViewBinder extends DebouncingOnClickListener {
        final /* synthetic */ ProfileFragment val$target;

        ViewBinder(ProfileFragment profileFragment) {
            this.val$target = profileFragment;
        }

        public void doClick(View p0) {
            this.val$target.onCurrencyChangeClick();
        }
    }

    /* compiled from: ProfileFragment$$ViewBinder */
    /* renamed from: com.hotellook.ui.screen.information.ProfileFragment$.ViewBinder.InnerUnbinder */
    protected static class InnerUnbinder<T extends ProfileFragment> implements Unbinder {
        private T target;
        View view2131689900;
        View view2131689903;
        View view2131689904;
        View view2131689905;
        View view2131689906;
        View view2131689907;
        View view2131689909;
        View view2131689912;

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
            target.scrollContent = null;
            target.content = null;
            target.persistentFiltersContainer = null;
            this.view2131689903.setOnClickListener(null);
            target.rateBtn = null;
            target.allowEnGatesContainer = null;
            target.versionText = null;
            target.enGatesTip = null;
            target.allowEnGatesCheckbox = null;
            target.currencyText = null;
            this.view2131689912.setOnClickListener(null);
            target.devBtn = null;
            this.view2131689909.setOnClickListener(null);
            this.view2131689904.setOnClickListener(null);
            this.view2131689905.setOnClickListener(null);
            this.view2131689906.setOnClickListener(null);
            this.view2131689907.setOnClickListener(null);
            this.view2131689900.setOnClickListener(null);
        }
    }

    public Unbinder bind(Finder finder, T target, Object source) {
        InnerUnbinder unbinder = createUnbinder(target);
        target.scrollContent = (View) finder.findRequiredView(source, C1178R.id.scroll_content, "field 'scrollContent'");
        target.content = (View) finder.findRequiredView(source, C1178R.id.content, "field 'content'");
        target.persistentFiltersContainer = (ViewGroup) finder.castView((View) finder.findRequiredView(source, C1178R.id.persistent_filters_container, "field 'persistentFiltersContainer'"), C1178R.id.persistent_filters_container, "field 'persistentFiltersContainer'");
        View view = (View) finder.findRequiredView(source, C1178R.id.rate_btn, "field 'rateBtn' and method 'onRateBtnClick'");
        target.rateBtn = view;
        unbinder.view2131689903 = view;
        view.setOnClickListener(new ViewBinder(target));
        target.allowEnGatesContainer = (View) finder.findRequiredView(source, C1178R.id.allow_en_gates_container, "field 'allowEnGatesContainer'");
        target.versionText = (TextView) finder.castView((View) finder.findRequiredView(source, C1178R.id.versionText, "field 'versionText'"), C1178R.id.versionText, "field 'versionText'");
        target.enGatesTip = (View) finder.findRequiredView(source, C1178R.id.en_gates_tip, "field 'enGatesTip'");
        target.allowEnGatesCheckbox = (CheckBox) finder.castView((View) finder.findRequiredView(source, C1178R.id.allow_en_gates_cb, "field 'allowEnGatesCheckbox'"), C1178R.id.allow_en_gates_cb, "field 'allowEnGatesCheckbox'");
        target.currencyText = (TextView) finder.castView((View) finder.findRequiredView(source, C1178R.id.currency, "field 'currencyText'"), C1178R.id.currency, "field 'currencyText'");
        view = (View) finder.findRequiredView(source, C1178R.id.dev_btn, "field 'devBtn' and method 'onDevBtnClick'");
        target.devBtn = view;
        unbinder.view2131689912 = view;
        view.setOnClickListener(new ViewBinder(target));
        view = (View) finder.findRequiredView(source, C1178R.id.allow_en_gates_btn, "method 'onEnGatesAllowCheckedChanged'");
        unbinder.view2131689909 = view;
        view.setOnClickListener(new ViewBinder(target));
        view = (View) finder.findRequiredView(source, C1178R.id.social_btn, "method 'onSocialBtnClick'");
        unbinder.view2131689904 = view;
        view.setOnClickListener(new ViewBinder(target));
        view = (View) finder.findRequiredView(source, C1178R.id.feedback_btn, "method 'onFeedbackBtnClick'");
        unbinder.view2131689905 = view;
        view.setOnClickListener(new ViewBinder(target));
        view = (View) finder.findRequiredView(source, C1178R.id.license_btn, "method 'onLicenseBtnClick'");
        unbinder.view2131689906 = view;
        view.setOnClickListener(new ViewBinder(target));
        view = (View) finder.findRequiredView(source, C1178R.id.about_us_btn, "method 'onAboutUsClick'");
        unbinder.view2131689907 = view;
        view.setOnClickListener(new ViewBinder(target));
        view = (View) finder.findRequiredView(source, C1178R.id.select_currency_btn, "method 'onCurrencyChangeClick'");
        unbinder.view2131689900 = view;
        view.setOnClickListener(new ViewBinder(target));
        return unbinder;
    }

    protected InnerUnbinder<T> createUnbinder(T target) {
        return new InnerUnbinder(target);
    }
}
