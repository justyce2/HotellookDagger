package com.hotellook.ui.screen.dev;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.Toasts;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.utils.AndroidUtils;

public class ToastShowroomFragment extends BaseFragment {
    private OnClickListener clickListener;

    /* renamed from: com.hotellook.ui.screen.dev.ToastShowroomFragment.1 */
    class C12441 extends MonkeySafeClickListener {
        C12441() {
        }

        public void onSafeClick(View v) {
            Context context = v.getContext();
            switch (v.getId()) {
                case C1178R.id.currency /*2131689901*/:
                    Toasts.showCurrencyAlertToast(context);
                case C1178R.id.cal_range /*2131690057*/:
                    Toasts.showCalendarInvalidRange(context);
                case C1178R.id.error_location /*2131690058*/:
                    Toasts.showErrorFindLocation(context);
                case C1178R.id.error_deeplink /*2131690059*/:
                    Toasts.showDeeplinkError(context);
                case C1178R.id.removed_from_favs /*2131690060*/:
                    Toasts.showHotelRemovedFromFavorites(context);
                case C1178R.id.added_to_favs /*2131690061*/:
                    Toasts.showHotelAddedToFavorites(context);
                case C1178R.id.search_failed /*2131690062*/:
                    Toasts.showHotelSearchFailed(context);
                case C1178R.id.hotel_today_no_gps /*2131690063*/:
                    Toasts.showHotelForTodayNoGps(context);
                case C1178R.id.error_update_location /*2131690064*/:
                    Toasts.showErrorUpdatingLocation(context);
                case C1178R.id.location_permission_denied /*2131690065*/:
                    Toasts.showErrorUpdatingLocation(context);
                case C1178R.id.location_settings_denied /*2131690066*/:
                    Toasts.showInvalidLocationSettingsToast(context);
                case C1178R.id.no_play_services /*2131690067*/:
                    Toasts.showNoPlayServicesToast(context);
                default:
            }
        }
    }

    public ToastShowroomFragment() {
        this.clickListener = new C12441();
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(C1178R.layout.toasts_showroom, container, false);
        setUpClickListenerToViews((ViewGroup) view.findViewById(C1178R.id.toasts_btns_container));
        setUpToolbar();
        AndroidUtils.setUpViewBelowStatusBar(view);
        AndroidUtils.addPaddingToOffsetToolbar(view);
        return view;
    }

    private void setUpClickListenerToViews(ViewGroup container) {
        int childCount = container.getChildCount();
        for (int i = 0; i < childCount; i++) {
            container.getChildAt(i).setOnClickListener(this.clickListener);
        }
    }

    private void setUpToolbar() {
        TextView title = (TextView) LayoutInflater.from(getActivity()).inflate(C1178R.layout.toolbar_title, getMainActivity().getToolbarManager().getToolbar(), false);
        title.setText(getString(C1178R.string.to_toasts));
        title.setTextColor(getResources().getColor(17170443));
        MainActivity activity = getMainActivity();
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), new ToolbarSettings().withShadow().navigationMode(1).bkgColor(getResources().getColor(C1178R.color.toolbar_green_bkg)).statusBarColor(getResources().getColor(C1178R.color.spf_statusbar_bkg)).toggleColor(getResources().getColor(C1178R.color.sr_toggle)).withCustomView(title));
    }
}
