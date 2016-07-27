package com.hotellook.ui.screen.guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.GuideExitEvent;
import com.hotellook.statistics.mixpanel.AppGuideStat;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.utils.AndroidUtils;

public class AppGuideFragment extends BaseFragment {
    private AppGuideStat appStat;

    /* renamed from: com.hotellook.ui.screen.guide.AppGuideFragment.1 */
    class C12971 extends MonkeySafeClickListener {
        C12971() {
        }

        public void onSafeClick(View v) {
            AppGuideFragment.this.finish();
        }
    }

    public AppGuideFragment() {
        this.appStat = new AppGuideStat();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1178R.layout.fragment_app_guide, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adjustOffsets(view);
        setUpToolbar();
        view.findViewById(C1178R.id.start_btn).setOnClickListener(new C12971());
    }

    public void onPause() {
        this.appStat.onScreenFinishedOrPaused();
        super.onPause();
    }

    public void onResume() {
        this.appStat.onScreenStartedOrResumed();
        super.onResume();
    }

    public void onDestroyView() {
        super.onDestroyView();
        HotellookApplication.eventBus().post(new GuideExitEvent(this.appStat));
    }

    private void adjustOffsets(View layout) {
        AndroidUtils.addPaddingToOffsetStatusBar(layout);
        AndroidUtils.addPaddingToOffsetNavBarBottom(layout);
    }

    private void setUpToolbar() {
        getMainActivity().getToolbarManager().setUpToolbar(getSupportActionBar(), new ToolbarSettings());
    }
}
