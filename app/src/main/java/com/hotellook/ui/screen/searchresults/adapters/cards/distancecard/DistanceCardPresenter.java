package com.hotellook.ui.screen.searchresults.adapters.cards.distancecard;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.filters.distancetarget.DistanceTarget;
import com.hotellook.filters.distancetarget.DistanceTarget.DestinationType;
import com.hotellook.filters.distancetarget.SeasonLocationDistanceTarget;
import com.hotellook.filters.items.DistanceFilterItem;
import com.hotellook.search.SearchData;
import com.hotellook.ui.screen.filters.presenters.FilterPresenter;
import com.hotellook.utils.AndroidUtils;
import com.jetradar.android.rangeseekbar.RangeSeekBar;
import com.jetradar.android.rangeseekbar.RangeSeekBar.OnRangeSeekBarChangeListener;
import java.text.DecimalFormat;
import me.zhanghai.android.materialprogressbar.BuildConfig;
import me.zhanghai.android.materialprogressbar.C1759R;
import pl.charmas.android.reactivelocation.C1822R;

public class DistanceCardPresenter implements FilterPresenter, OnRangeSeekBarChangeListener {
    public static final float KM_IN_MI = 0.621f;
    private final CardsSeasonTitles cardsSeasonTitles;
    private final Context context;
    private final DistanceCardUserActionDelegate distanceCardUserActionDelegate;
    private final DistanceFilterItem distanceFilterItem;
    private final DistanceTarget distanceTarget;
    private TextView distanceTextView;
    private final boolean isMetricUnits;
    private final String metricsText;
    private final DecimalFormat rateFormatter;
    private final SearchData searchData;
    private RangeSeekBar seekBar;
    private TextView titleTextView;

    /* renamed from: com.hotellook.ui.screen.searchresults.adapters.cards.distancecard.DistanceCardPresenter.1 */
    class C13791 extends MonkeySafeClickListener {
        C13791() {
        }

        public void onSafeClick(View v) {
            DistanceCardPresenter.this.distanceCardUserActionDelegate.onCloseClick();
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.adapters.cards.distancecard.DistanceCardPresenter.2 */
    class C13802 extends MonkeySafeClickListener {
        C13802() {
        }

        public void onSafeClick(View v) {
            DistanceCardPresenter.this.distanceCardUserActionDelegate.onApplyClick();
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.adapters.cards.distancecard.DistanceCardPresenter.3 */
    static /* synthetic */ class C13813 {
        static final /* synthetic */ int[] f733x723e49cb;

        static {
            f733x723e49cb = new int[DestinationType.values().length];
            try {
                f733x723e49cb[DestinationType.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f733x723e49cb[DestinationType.USER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f733x723e49cb[DestinationType.MAP_POINT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f733x723e49cb[DestinationType.SEARCH_POINT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f733x723e49cb[DestinationType.SIGHT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f733x723e49cb[DestinationType.SEASON.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public DistanceCardPresenter(Context context, DistanceFilterItem localDistanceFilterItem, DistanceCardUserActionDelegate distanceCardUserActionDelegate) {
        this.rateFormatter = new DecimalFormat("0.0");
        this.distanceCardUserActionDelegate = distanceCardUserActionDelegate;
        this.cardsSeasonTitles = new CardsSeasonTitles(context);
        this.distanceFilterItem = localDistanceFilterItem;
        this.distanceTarget = localDistanceFilterItem.getDistanceTarget();
        this.isMetricUnits = AndroidUtils.isMetricUnits();
        this.metricsText = context.getString(this.isMetricUnits ? C1178R.string.kilometers : C1178R.string.miles);
        this.context = context;
        this.searchData = HotellookApplication.from(context).getComponent().searchKeeper().lastSearchOrThrowException().searchData();
    }

    public void onFiltersUpdated() {
        setUpTitle();
        setUpSeekbar();
        setUpDistanceTextView();
    }

    private void setUpTitle() {
        if (this.titleTextView != null) {
            this.titleTextView.setText(getTitle());
        }
    }

    private String getTitle() {
        switch (C13813.f733x723e49cb[this.distanceTarget.getType().ordinal()]) {
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                if (this.searchData.searchParams().isDestinationTypeNearbyCities()) {
                    return this.context.getString(C1178R.string.distance_to) + " " + this.distanceTarget.getTitle();
                }
                return this.context.getString(C1178R.string.search_hotels_near_city_center);
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
                return this.context.getString(C1178R.string.search_hotels_near_your_location);
            case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                return this.context.getString(C1178R.string.search_hotels_near_location);
            case C1822R.styleable.MapAttrs_cameraTilt /*4*/:
                return this.context.getString(C1178R.string.search_hotels_near_location_search);
            case C1822R.styleable.MapAttrs_cameraZoom /*5*/:
                return this.distanceTarget.getTitle();
            case C1822R.styleable.MapAttrs_liteMode /*6*/:
                return this.cardsSeasonTitles.title(((SeasonLocationDistanceTarget) this.distanceTarget).getSeason());
            default:
                return BuildConfig.FLAVOR;
        }
    }

    private void setUpDistanceTextView() {
        double dist = this.distanceFilterItem.getInterpolatedValue() / 1000.0d;
        if (!this.isMetricUnits) {
            dist *= 0.6209999918937683d;
        }
        if (this.distanceTextView != null) {
            this.distanceTextView.setText(this.rateFormatter.format(dist) + " " + this.metricsText);
        }
    }

    private void setUpSeekbar() {
        if (this.seekBar != null) {
            this.seekBar.setOnRangeSeekBarChangeListener(null);
            this.seekBar.setRangeValues(this.distanceFilterItem.getMinDistance(), this.distanceFilterItem.getMaxDistance());
            this.seekBar.setSelectedMaxValue(this.distanceFilterItem.getValue());
            this.seekBar.setSelectedMinValue(this.distanceFilterItem.getMinDistance());
            this.seekBar.setOnRangeSeekBarChangeListener(this);
        }
    }

    public void addView(LayoutInflater inflater, ViewGroup container) {
        container.addView(inflateView(inflater, C1178R.layout.filter_distance_card, container));
    }

    @NonNull
    public View inflateView(LayoutInflater inflater, @LayoutRes int layoutId, ViewGroup container) {
        View v = inflater.inflate(layoutId, container, false);
        this.titleTextView = (TextView) v.findViewById(C1759R.id.title);
        this.distanceTextView = (TextView) v.findViewById(C1178R.id.value);
        this.seekBar = (RangeSeekBar) v.findViewById(C1178R.id.seekbar);
        this.seekBar.setOnRangeSeekBarChangeListener(this);
        View closeBtn = v.findViewById(C1178R.id.close);
        if (closeBtn != null) {
            closeBtn.setOnClickListener(new C13791());
        }
        View applyBtn = v.findViewById(C1178R.id.apply_btn);
        if (applyBtn != null) {
            applyBtn.setOnClickListener(new C13802());
        }
        setUpDistanceTextView();
        setUpSeekbar();
        setUpTitle();
        return v;
    }

    public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Double minValue, Double maxValue) {
    }

    public void onStopTrackingTouch() {
        this.distanceCardUserActionDelegate.onStopTrackingTouch(this.seekBar.getSelectedMaxValue());
    }

    public void onRangeSeekBarTracking(RangeSeekBar tRangeSeekBar, Double selectedMinValue, Double selectedMaxValue) {
        this.distanceFilterItem.setValue(selectedMaxValue.doubleValue());
        setUpDistanceTextView();
    }
}
