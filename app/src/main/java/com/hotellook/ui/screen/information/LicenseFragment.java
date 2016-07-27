package com.hotellook.ui.screen.information;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils.TruncateAt;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.ViewUtils;
import me.zhanghai.android.materialprogressbar.C1759R;

public class LicenseFragment extends BaseFragment {
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1178R.layout.fragment_license, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        adjustOffsets(view);
        setUpToolbar();
        TextView license = (TextView) view.findViewById(C1759R.id.text);
        license.setMovementMethod(LinkMovementMethod.getInstance());
        license.setText(Html.fromHtml(getString(C1178R.string.license)));
        ViewUtils.addLeftAndRightPaddingsForWideScreen(view.findViewById(C1178R.id.scroll_content));
    }

    private void adjustOffsets(@NonNull View layout) {
        AndroidUtils.addMarginToPlaceViewBelowStatusBar(layout);
        AndroidUtils.addMarginToPlaceViewBelowToolbar(layout);
    }

    private void setUpToolbar() {
        TextView title = (TextView) LayoutInflater.from(getActivity()).inflate(C1178R.layout.toolbar_title_white, getMainActivity().getToolbarManager().getToolbar(), false);
        title.setSingleLine();
        title.setEllipsize(TruncateAt.END);
        title.setText(getString(C1178R.string.license_title));
        MainActivity activity = getMainActivity();
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), new ToolbarSettings().navigationMode(1).bkgColor(getResources().getColor(C1178R.color.toolbar_green_bkg)).statusBarColor(getResources().getColor(C1178R.color.spf_statusbar_bkg)).toggleColor(getResources().getColor(C1178R.color.sr_toggle)).withCustomView(title).withShadow());
    }
}
