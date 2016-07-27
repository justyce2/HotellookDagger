package com.hotellook.ui.screen.hotel.general;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaView;
import com.hotellook.C1178R;
import com.hotellook.core.api.pojo.common.Coordinates;
import com.hotellook.ui.screen.OnBackPressHandler;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.LocationUtils;

public class StreetViewFragment extends BaseFragment implements OnStreetViewPanoramaReadyCallback, OnBackPressHandler {
    public static final int STREET_VIEW_RADIUS = 150;
    private Coordinates mLocation;
    private String mName;
    private StreetViewPanoramaView mStreetView;
    private StreetViewPanorama mStreetViewPanorama;
    private TextView mTitle;

    /* renamed from: com.hotellook.ui.screen.hotel.general.StreetViewFragment.1 */
    class C13241 implements Runnable {
        final /* synthetic */ View val$layout;
        final /* synthetic */ View val$overlay;

        C13241(View view, View view2) {
            this.val$layout = view;
            this.val$overlay = view2;
        }

        public void run() {
            LayoutParams layoutParams = StreetViewFragment.this.mStreetView.getLayoutParams();
            layoutParams.width = this.val$layout.getWidth();
            layoutParams.height = this.val$layout.getHeight();
            StreetViewFragment.this.mStreetView.setVisibility(0);
            StreetViewFragment.this.mStreetView.requestLayout();
            this.val$overlay.animate().alpha(0.0f);
        }
    }

    public static Fragment create(Coordinates location, String name) {
        StreetViewFragment fragment = new StreetViewFragment();
        fragment.mLocation = location;
        fragment.mName = name;
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        boolean hasGMS;
        View layout = inflater.inflate(C1178R.layout.fragment_street_view, container, false);
        View overlay = layout.findViewById(C1178R.id.overlay);
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) == 0) {
            hasGMS = true;
        } else {
            hasGMS = false;
        }
        this.mStreetView = (StreetViewPanoramaView) layout.findViewById(C1178R.id.street_view);
        if (hasGMS) {
            this.mStreetView.setVisibility(4);
            this.mStreetView.onCreate(savedInstanceState);
            this.mStreetView.getStreetViewPanoramaAsync(this);
            this.mStreetView.postDelayed(new C13241(layout, overlay), 500);
            ((ViewGroup) this.mStreetView.getChildAt(0)).getChildAt(1).setPadding(0, 0, 0, AndroidUtils.getNavBarHeight(getActivity()));
        } else {
            this.mStreetView.setVisibility(8);
            overlay.setVisibility(8);
            layout.findViewById(C1178R.id.place_holder).setVisibility(0);
        }
        setUpToolbar();
        return layout;
    }

    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        this.mStreetViewPanorama = streetViewPanorama;
        this.mStreetViewPanorama.setPosition(LocationUtils.toLatLng(this.mLocation), STREET_VIEW_RADIUS);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (this.mStreetView != null) {
            this.mStreetView.onCreate(savedInstanceState);
        }
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    public void onResume() {
        if (this.mStreetView != null) {
            this.mStreetView.onResume();
        }
        super.onResume();
    }

    public void onDestroy() {
        if (this.mStreetView != null) {
            this.mStreetView.onDestroy();
        }
        super.onDestroy();
    }

    public void onPause() {
        if (this.mStreetView != null) {
            this.mStreetView.onPause();
        }
        super.onPause();
    }

    public void onLowMemory() {
        if (this.mStreetView != null) {
            this.mStreetView.onLowMemory();
        }
        super.onLowMemory();
    }

    private void setUpToolbar() {
        this.mTitle = (TextView) LayoutInflater.from(getActivity()).inflate(C1178R.layout.toolbar_title_white, getToolbar(), false);
        this.mTitle.setText(this.mName);
        this.mTitle.setMaxLines(2);
        this.mTitle.setEllipsize(TruncateAt.END);
        MarginLayoutParams layoutParams = (MarginLayoutParams) this.mTitle.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, getResources().getDimensionPixelSize(C1178R.dimen.standard_offset), layoutParams.bottomMargin);
        getMainActivity().getToolbarManager().setUpToolbar(getSupportActionBar(), new ToolbarSettings().toggleColor(getResources().getColor(17170443)).navigationMode(1).bkgColor(0).withCustomView(this.mTitle));
    }

    public boolean onBackPressedHandled() {
        if (this.mTitle != null) {
            getMainActivity().getToolbarManager().getToolbar().removeView(this.mTitle);
        }
        return false;
    }
}
