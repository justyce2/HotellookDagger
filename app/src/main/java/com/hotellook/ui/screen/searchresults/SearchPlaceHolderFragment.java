package com.hotellook.ui.screen.searchresults;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.search.Search;
import com.hotellook.search.SearchParams;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.ui.view.placeholder.PlaceHolder;
import com.hotellook.utils.AndroidUtils;

public class SearchPlaceHolderFragment extends BaseFragment {
    public static final String TAG;
    private Search search;

    /* renamed from: com.hotellook.ui.screen.searchresults.SearchPlaceHolderFragment.1 */
    class C13581 extends MonkeySafeClickListener {
        C13581() {
        }

        public void onSafeClick(View v) {
            if (SearchPlaceHolderFragment.this.getMainActivity() != null) {
                SearchPlaceHolderFragment.this.getMainActivity().returnToSearchForm();
            }
        }
    }

    static {
        TAG = SearchPlaceHolderFragment.class.getSimpleName();
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.search = getComponent().searchKeeper().lastSearchOrThrowException();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PlaceHolder v = (PlaceHolder) inflater.inflate(C1178R.layout.placeholder_search_results, container, false);
        v.setOnButtonClickListener(new C13581());
        AndroidUtils.addPaddingToOffsetStatusBar(v);
        AndroidUtils.addMarginToPlaceViewBelowToolbar(v);
        return v;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar();
    }

    private void setUpToolbar() {
        String cityName;
        SearchParams searchParams = this.search.searchParams();
        if (searchParams.isDestinationTypeUserLocation()) {
            cityName = getString(C1178R.string.my_location);
        } else {
            cityName = this.search.searchData().locations().getById(searchParams.locationId()).getCity();
        }
        TextView title = (TextView) LayoutInflater.from(getActivity()).inflate(C1178R.layout.toolbar_title, getMainActivity().getToolbarManager().getToolbar(), false);
        title.setText(cityName);
        title.setTextColor(getResources().getColor(17170443));
        MainActivity activity = getMainActivity();
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), new ToolbarSettings().withShadow().navigationMode(1).bkgColor(getResources().getColor(C1178R.color.toolbar_green_bkg)).statusBarColor(getResources().getColor(C1178R.color.spf_statusbar_bkg)).toggleColor(getResources().getColor(C1178R.color.sr_toggle)).withCustomView(title));
    }
}
