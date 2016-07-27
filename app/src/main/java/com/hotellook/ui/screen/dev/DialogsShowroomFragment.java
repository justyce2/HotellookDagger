package com.hotellook.ui.screen.dev;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.dialog.DismissDialogListener;
import com.hotellook.ui.screen.Dialogs;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.screen.information.RateDialogFragment.Source;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.utils.AndroidUtils;
import java.io.IOException;
import retrofit.RetrofitError;

public class DialogsShowroomFragment extends BaseFragment {
    private OnClickListener clickListener;

    /* renamed from: com.hotellook.ui.screen.dev.DialogsShowroomFragment.1 */
    class C12431 extends MonkeySafeClickListener {
        C12431() {
        }

        public void onSafeClick(View v) {
            switch (v.getId()) {
                case C1178R.id.en_gates /*2131689763*/:
                    Dialogs.showEnGatesDialog(DialogsShowroomFragment.this.getMainActivity(), new DismissDialogListener());
                case C1178R.id.rate_us /*2131689764*/:
                    Dialogs.showRateDialog(DialogsShowroomFragment.this.getMainActivity(), Source.MANUAL);
                case C1178R.id.currency_changed /*2131689765*/:
                    Dialogs.showCurrencyChangedDialog(DialogsShowroomFragment.this.getContext());
                case C1178R.id.location_error /*2131689766*/:
                    Dialogs.showLocationErrorDialog(DialogsShowroomFragment.this.getMainActivity(), new DismissDialogListener());
                case C1178R.id.location_permission /*2131689767*/:
                    Dialogs.showLocationPermissionDialog(DialogsShowroomFragment.this.getMainActivity(), new DismissDialogListener(), new DismissDialogListener());
                case C1178R.id.loading_nearest_location /*2131689768*/:
                    Dialogs.showLoadingNearestCityDialog(DialogsShowroomFragment.this.getMainActivity(), new EmptyOnDismissListener());
                case C1178R.id.loading_location /*2131689769*/:
                    Dialogs.showDestinationLoadingDialog(DialogsShowroomFragment.this.getMainActivity());
                case C1178R.id.network_error /*2131689770*/:
                    Dialogs.showNetworkErrorDialog(DialogsShowroomFragment.this.getMainActivity(), RetrofitError.networkError("test.com", new IOException("Test error message")), new DismissDialogListener(), new BackKeyListener());
                case C1178R.id.server_error /*2131689771*/:
                    Dialogs.showNetworkErrorDialog(DialogsShowroomFragment.this.getMainActivity(), RetrofitError.unexpectedError("test.com", new IOException("Test error message")), new DismissDialogListener(), new BackKeyListener());
                case C1178R.id.browser_loading /*2131689772*/:
                    Dialogs.showBrowserLoadingDialog(DialogsShowroomFragment.this.getMainActivity(), "Booking.com").setKeyListener(new BackKeyListener());
                case C1178R.id.out_of_date /*2131689773*/:
                    Dialogs.showOutOfDateDialog(DialogsShowroomFragment.this.getMainActivity(), new DismissDialogListener());
                case C1178R.id.deeplink_error /*2131689774*/:
                    Dialogs.showLoadingIntentErrorDialog(DialogsShowroomFragment.this.getMainActivity(), new DismissDialogListener(), new DismissDialogListener());
                default:
            }
        }
    }

    private static class BackKeyListener implements OnKeyListener {
        private BackKeyListener() {
        }

        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (event.getKeyCode() != 4) {
                return false;
            }
            dialog.dismiss();
            return true;
        }
    }

    private static class EmptyOnDismissListener implements OnDismissListener {
        private EmptyOnDismissListener() {
        }

        public void onDismiss(DialogInterface dialog) {
        }
    }

    public DialogsShowroomFragment() {
        this.clickListener = new C12431();
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(C1178R.layout.dialogs_showroom, container, false);
        setUpOnClickListenerToViews((ViewGroup) view.findViewById(C1178R.id.dialog_btns_container));
        setUpToolbar();
        AndroidUtils.setUpViewBelowStatusBar(view);
        AndroidUtils.addPaddingToOffsetToolbar(view);
        return view;
    }

    private void setUpOnClickListenerToViews(ViewGroup container) {
        int childCount = container.getChildCount();
        for (int i = 0; i < childCount; i++) {
            container.getChildAt(i).setOnClickListener(this.clickListener);
        }
    }

    private void setUpToolbar() {
        TextView title = (TextView) LayoutInflater.from(getActivity()).inflate(C1178R.layout.toolbar_title, getMainActivity().getToolbarManager().getToolbar(), false);
        title.setText(getString(C1178R.string.to_dialogs));
        title.setTextColor(getResources().getColor(17170443));
        MainActivity activity = getMainActivity();
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), new ToolbarSettings().withShadow().navigationMode(1).bkgColor(getResources().getColor(C1178R.color.toolbar_green_bkg)).statusBarColor(getResources().getColor(C1178R.color.spf_statusbar_bkg)).toggleColor(getResources().getColor(C1178R.color.sr_toggle)).withCustomView(title));
    }
}
