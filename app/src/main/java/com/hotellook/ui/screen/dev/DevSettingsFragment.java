package com.hotellook.ui.screen.dev;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.OverlayClosedEvent;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.screen.guide.AppGuideFragment;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CommonPreferences;
import com.squareup.otto.Subscribe;

public class DevSettingsFragment extends BaseFragment {
    private CommonPreferences prefs;

    /* renamed from: com.hotellook.ui.screen.dev.DevSettingsFragment.1 */
    class C12401 extends MonkeySafeClickListener {
        C12401() {
        }

        public void onSafeClick(View v) {
            DevSettingsFragment.this.getMainActivity().showOverlay(new AppGuideFragment());
        }
    }

    /* renamed from: com.hotellook.ui.screen.dev.DevSettingsFragment.2 */
    class C12412 extends MonkeySafeClickListener {
        C12412() {
        }

        public void onSafeClick(View v) {
            DevSettingsFragment.this.getMainActivity().showFragment(new DialogsShowroomFragment());
        }
    }

    /* renamed from: com.hotellook.ui.screen.dev.DevSettingsFragment.3 */
    class C12423 extends MonkeySafeClickListener {
        C12423() {
        }

        public void onSafeClick(View v) {
            DevSettingsFragment.this.getMainActivity().showFragment(new ToastShowroomFragment());
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        this.prefs = new CommonPreferences(getContext());
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(C1178R.layout.dev_settings, container, false);
        root.findViewById(C1178R.id.tutorial).setOnClickListener(new C12401());
        root.findViewById(C1178R.id.to_dialogs).setOnClickListener(new C12412());
        root.findViewById(C1178R.id.to_toasts).setOnClickListener(new C12423());
        CheckBox shortPredictedTime = (CheckBox) root.findViewById(C1178R.id.short_time);
        shortPredictedTime.setChecked(this.prefs.isShortBrowserPredictedTimeouts());
        shortPredictedTime.setOnCheckedChangeListener(DevSettingsFragment$$Lambda$1.lambdaFactory$(this));
        setUpToolbar();
        AndroidUtils.setUpViewBelowStatusBar(root);
        AndroidUtils.addPaddingToOffsetToolbar(root);
        HotellookApplication.eventBus().register(this);
        return root;
    }

    /* synthetic */ void lambda$onCreateView$0(CompoundButton buttonView, boolean isChecked) {
        this.prefs.setShortBrowserPredictedTimeouts(isChecked);
    }

    public void onDestroyView() {
        HotellookApplication.eventBus().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void onOverlayClosed(OverlayClosedEvent event) {
        setUpToolbar();
    }

    private void setUpToolbar() {
        TextView title = (TextView) LayoutInflater.from(getActivity()).inflate(C1178R.layout.toolbar_title, getMainActivity().getToolbarManager().getToolbar(), false);
        title.setText(getString(C1178R.string.drawer_item_dev));
        title.setTextColor(getResources().getColor(17170443));
        MainActivity activity = getMainActivity();
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), new ToolbarSettings().withShadow().navigationMode(0).bkgColor(getResources().getColor(C1178R.color.toolbar_green_bkg)).statusBarColor(getResources().getColor(C1178R.color.spf_statusbar_bkg)).toggleColor(getResources().getColor(C1178R.color.sr_toggle)).withCustomView(title));
    }
}
