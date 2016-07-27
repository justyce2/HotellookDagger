package com.hotellook.ui.screen.information;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.utils.AndroidUtils;

public class AboutUsFragment extends BaseFragment {
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(C1178R.layout.fragment_about_us, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        adjustOffsets(view);
        setUpToolbar();
    }

    private void adjustOffsets(@NonNull View layout) {
        AndroidUtils.addMarginToPlaceViewBelowStatusBar(layout);
        AndroidUtils.addMarginToPlaceViewBelowToolbar(layout);
    }

    private void setUpToolbar() {
        TextView title = (TextView) LayoutInflater.from(getContext()).inflate(C1178R.layout.toolbar_title, getMainActivity().getToolbarManager().getToolbar(), false);
        title.setText(getString(C1178R.string.title_about_us));
        title.setTextColor(getResources().getColor(17170443));
        MainActivity activity = getMainActivity();
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), new ToolbarSettings().withShadow().navigationMode(1).bkgColor(getResources().getColor(C1178R.color.toolbar_green_bkg)).statusBarColor(getResources().getColor(C1178R.color.spf_statusbar_bkg)).toggleColor(getResources().getColor(C1178R.color.sr_toggle)).withCustomView(title));
    }
}
