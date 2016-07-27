package com.hotellook.ui.screen.information;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.events.InfoTapRateUsEvent;
import com.hotellook.events.InfoTapToLicenceEvent;
import com.hotellook.events.InfoTapToSendFeedback;
import com.hotellook.events.InfoTapToSocialNetworksEvent;
import com.hotellook.events.OnSocialNetworkClickEvent;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.Dialogs;
import com.hotellook.ui.screen.common.BaseMvpFragment;
import com.hotellook.ui.screen.currency.CurrencyFragment;
import com.hotellook.ui.screen.dev.DevSettingsFragment;
import com.hotellook.ui.screen.information.RateDialogFragment.Source;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.ViewUtils;
import com.squareup.otto.Subscribe;

public class ProfileFragment extends BaseMvpFragment<ProfileView, ProfilePresenter> implements ProfileView {
    @BindView
    CheckBox allowEnGatesCheckbox;
    @BindView
    View allowEnGatesContainer;
    private ProfileComponent component;
    @BindView
    View content;
    @BindView
    TextView currencyText;
    @BindView
    View devBtn;
    @BindView
    View enGatesTip;
    @BindView
    ViewGroup persistentFiltersContainer;
    @BindView
    View rateBtn;
    @BindView
    View scrollContent;
    @BindView
    TextView versionText;

    private void createComponent() {
        this.component = DaggerProfileComponent.builder().hotellookComponent(appComponent()).build();
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createComponent();
    }

    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(C1178R.layout.fragment_profile, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adjustLayoutOffsets(view);
        setUpToolbar();
        setUpDevBtn();
        HotellookApplication.eventBus().register(this);
    }

    private void setUpDevBtn() {
        this.devBtn.setVisibility(8);
    }

    public void showAppVersionName(String versionName) {
        this.versionText.setText(versionName);
    }

    public void hideEnGateViews() {
        this.allowEnGatesContainer.setVisibility(8);
        this.enGatesTip.setVisibility(8);
    }

    public void hideRateView() {
        this.rateBtn.setVisibility(8);
    }

    private void adjustLayoutOffsets(View root) {
        AndroidUtils.addMarginToPlaceViewBelowStatusBar(root);
        AndroidUtils.addMarginToPlaceViewBelowToolbar(root);
        ViewUtils.addLeftAndRightPaddingsForWideScreen(this.scrollContent);
    }

    public ProfilePresenter createPresenter() {
        return this.component.presenter();
    }

    public void onDestroyView() {
        HotellookApplication.eventBus().unregister(this);
        super.onDestroyView();
    }

    @OnClick
    public void onEnGatesAllowCheckedChanged() {
        boolean checked = !this.allowEnGatesCheckbox.isChecked();
        ((ProfilePresenter) getPresenter()).onEnGatesAllowCheckedChanged(checked);
        setEnGatesChecked(checked);
    }

    @OnClick
    public void onRateBtnClick() {
        Dialogs.showRateDialog(getActivity(), Source.MANUAL);
        HotellookApplication.eventBus().post(new InfoTapRateUsEvent());
    }

    @OnClick
    public void onSocialBtnClick() {
        mainActivity().showBottomSheet(LayoutInflater.from(getActivity()).inflate(C1178R.layout.social_networks, null));
        HotellookApplication.eventBus().post(new InfoTapToSocialNetworksEvent());
    }

    @OnClick
    public void onFeedbackBtnClick() {
        new EmailComposer().sendFeedbackEmail(getActivity(), 0, null);
        HotellookApplication.eventBus().post(new InfoTapToSendFeedback());
    }

    @OnClick
    public void onLicenseBtnClick() {
        mainActivity().showFragment(new LicenseFragment());
        HotellookApplication.eventBus().post(new InfoTapToLicenceEvent());
    }

    @OnClick
    public void onAboutUsClick() {
        mainActivity().showFragment(new AboutUsFragment());
    }

    @OnClick
    public void onCurrencyChangeClick() {
        mainActivity().showFragment(new CurrencyFragment());
    }

    @OnClick
    public void onDevBtnClick() {
        mainActivity().showFragment(new DevSettingsFragment());
    }

    @Subscribe
    public void onSocialNetworkClickEvent(OnSocialNetworkClickEvent event) {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(event.link)));
    }

    public void addPersistentFilterViews(PersistentFiltersGroupPresenter persistentFiltersGroupPresenter) {
        persistentFiltersGroupPresenter.addViews(this.persistentFiltersContainer);
    }

    public void setEnGatesChecked(boolean checked) {
        this.allowEnGatesCheckbox.setChecked(checked);
    }

    public void showCurrency(String currencyCode) {
        this.currencyText.setText(currencyCode.toUpperCase());
    }

    private void setUpToolbar() {
        TextView title = (TextView) LayoutInflater.from(getActivity()).inflate(C1178R.layout.toolbar_title_white, mainActivity().getToolbarManager().getToolbar(), false);
        title.setText(getString(C1178R.string.information_title));
        MainActivity activity = mainActivity();
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), new ToolbarSettings().navigationMode(0).bkgColor(ContextCompat.getColor(getContext(), C1178R.color.toolbar_green_bkg)).statusBarColor(ContextCompat.getColor(getContext(), C1178R.color.spf_statusbar_bkg)).toggleColor(ContextCompat.getColor(getContext(), C1178R.color.sr_toggle)).withCustomView(title).withShadow());
    }
}
