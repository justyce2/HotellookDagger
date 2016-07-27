package com.hotellook.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.api.data.DestinationType;
import com.hotellook.api.data.SearchFormData;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.events.AdultsUpdatedEvent;
import com.hotellook.events.GateItemExpandAnimationFinished;
import com.hotellook.events.KidsUpdatedEvent;
import com.hotellook.events.LocationButtonClickEvent;
import com.hotellook.events.OpenDestinationPickerEvent;
import com.hotellook.events.SearchButtonClickEvent;
import com.hotellook.events.SearchFormCollapseEvent;
import com.hotellook.events.SearchFormDatesClickEvent;
import com.hotellook.events.SearchFormExpandEvent;
import com.hotellook.events.SearchFormKidsClickEvent;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.Animators;
import com.squareup.otto.Subscribe;

public class SearchFormView extends LimitedSizeLinearLayout {
    private View adultsContainer;
    private Spinner adultsSpinner;
    private ImageView cityIcon;
    private int collapsedHeight;
    private View datesBtn;
    private TextView datesText;
    private TextView datesTitle;
    private View destinationBtn;
    private TextView destinationLocationText;
    private TextView destinationNameText;
    private boolean embedded;
    private AnimatorSet expandCollapseAnimator;
    private View expandIndicator;
    private boolean expandable;
    private View headerContainer;
    private ImageView hotelIcon;
    private boolean interceptTouches;
    private View kidsBtn;
    private TextView kidsText;
    private View locationBtn;
    private CircularProgressBar locationProgress;
    private View searchBtn;
    private SearchFormData searchFormData;
    private CircularProgressBar searchProgress;
    private TextView searchText;
    private View topDivider;

    /* renamed from: com.hotellook.ui.view.SearchFormView.1 */
    class C14041 extends MonkeySafeClickListener {
        C14041() {
        }

        public void onSafeClick(View v) {
            SearchFormView.this.onKidsClick();
        }
    }

    /* renamed from: com.hotellook.ui.view.SearchFormView.2 */
    class C14052 extends MonkeySafeClickListener {
        C14052() {
        }

        public void onSafeClick(View v) {
            SearchFormView.this.onDatesClick();
        }
    }

    /* renamed from: com.hotellook.ui.view.SearchFormView.3 */
    class C14063 extends MonkeySafeClickListener {
        C14063() {
        }

        public void onSafeClick(View v) {
            SearchFormView.this.onDestinationClick();
        }
    }

    /* renamed from: com.hotellook.ui.view.SearchFormView.4 */
    class C14074 extends MonkeySafeClickListener {
        C14074() {
        }

        public void onSafeClick(View v) {
            SearchFormView.this.adultsSpinner.performClick();
        }
    }

    /* renamed from: com.hotellook.ui.view.SearchFormView.5 */
    class C14085 extends MonkeySafeClickListener {
        C14085() {
        }

        public void onSafeClick(View v) {
            SearchFormView.this.cancelAnimators();
            if (SearchFormView.this.isExpanded()) {
                SearchFormView.this.animateCollapse();
            } else {
                SearchFormView.this.animateExpand();
            }
        }
    }

    /* renamed from: com.hotellook.ui.view.SearchFormView.6 */
    class C14096 extends MonkeySafeClickListener {
        C14096() {
        }

        public void onSafeClick(View v) {
            SearchFormView.this.onSearchClick();
        }
    }

    /* renamed from: com.hotellook.ui.view.SearchFormView.7 */
    class C14107 extends MonkeySafeClickListener {
        C14107() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new LocationButtonClickEvent());
        }
    }

    /* renamed from: com.hotellook.ui.view.SearchFormView.8 */
    class C14118 extends AnimatorListenerAdapter {
        C14118() {
        }

        public void onAnimationEnd(Animator animation) {
            SearchFormView.this.locationBtn.setVisibility(4);
        }
    }

    /* renamed from: com.hotellook.ui.view.SearchFormView.9 */
    class C14129 implements OnItemSelectedListener {
        C14129() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            if (view != null && ((TextView) view).getText() != null) {
                int newAdults = Integer.valueOf(((TextView) view).getText().toString()).intValue();
                if (SearchFormView.this.searchFormData.getAdults() != newAdults) {
                    SearchFormView.this.searchFormData.setAdults(newAdults);
                    SearchFormView.this.searchFormData.saveData();
                    HotellookApplication.eventBus().post(new AdultsUpdatedEvent(newAdults));
                }
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public SearchFormView(Context context) {
        this(context, null);
    }

    public SearchFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setOrientation(1);
        extractAttributes(attrs);
        this.collapsedHeight = getResources().getDimensionPixelSize(C1178R.dimen.hotel_price_collapse_item_height);
    }

    private void extractAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, C1178R.styleable.SearchFormView, 0, 0);
        this.embedded = a.getBoolean(0, false);
        this.expandable = a.getBoolean(1, false);
        a.recycle();
    }

    protected void onFinishInflate() {
        inflateViews();
        if (!isInEditMode()) {
            this.cityIcon = (ImageView) findViewById(C1178R.id.iv_ic_city);
            this.hotelIcon = (ImageView) findViewById(C1178R.id.iv_ic_hotel);
            this.destinationNameText = (TextView) findViewById(C1178R.id.sf_city);
            this.destinationLocationText = (TextView) findViewById(C1178R.id.sf_country);
            this.datesText = (TextView) findViewById(C1178R.id.sf_dates);
            this.adultsSpinner = (Spinner) findViewById(C1178R.id.sf_adults);
            this.locationBtn = findViewById(C1178R.id.gps);
            this.locationProgress = (CircularProgressBar) findViewById(C1178R.id.pb_gps);
            this.destinationBtn = findViewById(C1178R.id.btn_destination);
            this.datesTitle = (TextView) findViewById(C1178R.id.sf_date_title);
            this.datesBtn = findViewById(C1178R.id.btn_dates);
            this.searchBtn = findViewById(C1178R.id.search_btn);
            this.searchText = (TextView) findViewById(C1178R.id.search_txt);
            this.searchProgress = (CircularProgressBar) findViewById(C1178R.id.search_pb);
            this.kidsBtn = findViewById(C1178R.id.btn_kids);
            this.kidsText = (TextView) this.kidsBtn.findViewById(C1178R.id.sf_kids_num);
            this.topDivider = findViewById(C1178R.id.first_divider);
            this.expandIndicator = findViewById(C1178R.id.expand_indicator);
            this.adultsContainer = findViewById(C1178R.id.adults_container);
            setUpDestinationOnClick();
            setUpDatesOnClick();
            setUpKidsOnClick();
            setUpAdultsOnClick();
            setUpSearchViews();
            setUpLocationViews();
            if (this.expandIndicator != null) {
                this.expandIndicator.setVisibility(8);
            }
            if (this.embedded) {
                setUpEmbedded();
            }
            if (this.expandable) {
                setUpExpandable();
            }
            super.onFinishInflate();
        }
    }

    private void setUpKidsOnClick() {
        this.kidsBtn.setOnClickListener(new C14041());
    }

    private void setUpDatesOnClick() {
        this.datesBtn.setOnClickListener(new C14052());
    }

    private void setUpDestinationOnClick() {
        this.destinationBtn.setOnClickListener(new C14063());
    }

    private void setUpAdultsOnClick() {
        this.adultsContainer.setOnClickListener(new C14074());
        this.adultsSpinner.setOnTouchListener(SearchFormView$$Lambda$1.lambdaFactory$());
    }

    private void setUpExpandable() {
        this.expandIndicator.setVisibility(0);
        this.headerContainer.setOnClickListener(new C14085());
    }

    private boolean isExpanded() {
        return this.datesBtn.getVisibility() == 0;
    }

    private void inflateViews() {
        if (this.expandable) {
            this.headerContainer = LayoutInflater.from(getContext()).inflate(C1178R.layout.expandable_search_from_header, this, false);
        } else if (this.embedded) {
            this.headerContainer = LayoutInflater.from(getContext()).inflate(C1178R.layout.embedded_search_from_header, this, false);
        }
        if (this.headerContainer != null) {
            addView(this.headerContainer);
        }
        View.inflate(getContext(), this.embedded ? C1178R.layout.basic_search_form : C1178R.layout.search_form, this);
        View.inflate(getContext(), this.embedded ? C1178R.layout.search_small_btn : C1178R.layout.search_large_btn, this);
        if (this.embedded) {
            setBackgroundColor(ContextCompat.getColor(getContext(), 17170443));
        }
    }

    private void setUpSearchViews() {
        this.searchBtn.setOnClickListener(new C14096());
        setUpTouchInterceptor();
    }

    private void setUpTouchInterceptor() {
        setOnTouchListener(SearchFormView$$Lambda$2.lambdaFactory$(this));
    }

    /* synthetic */ boolean lambda$setUpTouchInterceptor$1(View v, MotionEvent event) {
        return this.interceptTouches;
    }

    public void setUpSearchProgressState(boolean inProgress) {
        int i;
        int i2 = 0;
        this.interceptTouches = inProgress;
        this.searchProgress.reset();
        TextView textView = this.searchText;
        if (inProgress) {
            i = 4;
        } else {
            i = 0;
        }
        textView.setVisibility(i);
        CircularProgressBar circularProgressBar = this.searchProgress;
        if (!inProgress) {
            i2 = 4;
        }
        circularProgressBar.setVisibility(i2);
    }

    private void setUpLocationViews() {
        this.locationProgress.setVisibility(4);
        this.locationBtn.setOnClickListener(new C14107());
    }

    public void showLocationSearchAnimation() {
        this.locationBtn.animate().alpha(0.0f).setDuration(100).setListener(new C14118());
        this.locationProgress.setVisibility(0);
        this.locationProgress.setAlpha(1.0f);
        this.locationProgress.reset();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            HotellookApplication.eventBus().register(this);
        }
    }

    protected void onDetachedFromWindow() {
        HotellookApplication.eventBus().unregister(this);
        AndroidUtils.forceHideSpinners(this.adultsSpinner);
        super.onDetachedFromWindow();
    }

    public void setUpData(SearchFormData searchFormData) {
        this.searchFormData = searchFormData;
        setUpDestinationIcon();
        setUpDestination();
        setUpAdults();
        setUpDates();
        setUpKids();
        setUpSearchButtonText();
    }

    public void setUpSearchButtonText() {
        if (this.searchFormData.getDestinationType() == DestinationType.HOTEL) {
            this.searchText.setText(getResources().getString(C1178R.string.find_prices));
        } else {
            this.searchText.setText(C1178R.string.find_hotels);
        }
    }

    public void setUpDates() {
        String datesTitle = getResources().getString(C1178R.string.sf_dates_title);
        int nightsCount = this.searchFormData.getNightsCount();
        this.datesTitle.setText(datesTitle + " (" + getResources().getQuantityString(C1178R.plurals.nights, nightsCount, new Object[]{Integer.valueOf(nightsCount)}) + ")");
        this.datesText.setText(this.searchFormData.getDateString(getContext()));
    }

    public void setUpAdults() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), C1178R.array.sf_adults_array, C1178R.layout.view_simple_spinner_item);
        adapter.setDropDownViewResource(C1178R.layout.view_simple_spinner_dropdown_item);
        this.adultsSpinner.setAdapter(adapter);
        this.adultsSpinner.setOnItemSelectedListener(null);
        this.adultsSpinner.setSelection(adapter.getPosition(String.valueOf(this.searchFormData.getAdults())));
        setUpAdultsSpinner();
    }

    public void setUpDestination() {
        if (this.searchFormData.isUserCoordinatesType()) {
            this.destinationNameText.setText(C1178R.string.destination_by_location);
            this.destinationLocationText.setText(C1178R.string.search_form_subtitle_for_coordinates_search);
        } else if (this.searchFormData.isLocationType()) {
            this.destinationNameText.setText(C1178R.string.search_type_name_location);
            this.destinationLocationText.setText(C1178R.string.search_form_subtitle_for_coordinates_search);
        } else {
            this.destinationNameText.setText(this.searchFormData.getDestinationName());
            this.destinationLocationText.setText(this.searchFormData.getDestinationLocation());
        }
    }

    public void setUpDestinationIcon() {
        ImageView icToShow;
        ImageView icToHide;
        if (this.searchFormData.isCityType() || this.searchFormData.isMapType()) {
            icToShow = this.cityIcon;
            icToHide = this.hotelIcon;
        } else {
            icToShow = this.hotelIcon;
            icToHide = this.cityIcon;
        }
        if (isDestinationIconNotSet() || iconsAlreadySet(icToShow, icToHide)) {
            icToHide.setVisibility(8);
        } else {
            Animators.createCrossFadeAnimator(icToShow, icToHide, 8).start();
        }
    }

    private boolean iconsAlreadySet(ImageView icToShow, ImageView icToHide) {
        return icToShow.getVisibility() == 0 && icToShow.getAlpha() == 1.0f && icToHide.getVisibility() == 8;
    }

    public boolean isDestinationIconNotSet() {
        return ViewCompat.getAlpha(this.cityIcon) == 1.0f && ViewCompat.getAlpha(this.hotelIcon) == 1.0f;
    }

    public void setUpAdultsSpinner() {
        this.adultsSpinner.setOnItemSelectedListener(new C14129());
    }

    public void setUpKids() {
        this.kidsText.setText(String.valueOf(this.searchFormData.getKidsCount()));
    }

    public void switchToLocationFoundState() {
        this.locationProgress.animate().alpha(0.0f).scaleX(0.0f).scaleY(0.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                SearchFormView.this.locationProgress.setVisibility(8);
                SearchFormView.this.locationProgress.setScaleX(1.0f);
                SearchFormView.this.locationProgress.setScaleY(1.0f);
            }
        });
        this.locationBtn.setVisibility(0);
        this.locationBtn.setScaleX(0.0f);
        this.locationBtn.setScaleY(0.0f);
        this.locationBtn.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(300).setListener(null);
    }

    private void cancelAnimators() {
        if (this.expandCollapseAnimator != null && this.expandCollapseAnimator.isRunning()) {
            this.expandCollapseAnimator.removeAllListeners();
            this.expandCollapseAnimator.cancel();
        }
    }

    private void animateExpand() {
        sendExpandEvent();
        this.datesBtn.setVisibility(0);
        Animator resizeAnimator = getResizeAnimator(getHeight(), getExpandedHeight());
        Animator indicatorAnimator = getIndicatorAnimator(this.expandIndicator.getRotation(), 180.0f);
        this.expandCollapseAnimator = new AnimatorSet();
        this.expandCollapseAnimator.playTogether(new Animator[]{resizeAnimator, indicatorAnimator});
        this.expandCollapseAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                HotellookApplication.eventBus().post(new GateItemExpandAnimationFinished());
            }
        });
        this.expandCollapseAnimator.start();
    }

    private void sendExpandEvent() {
        HotellookApplication.eventBus().post(new SearchFormExpandEvent());
    }

    private void sendCollapseEvent() {
        HotellookApplication.eventBus().post(new SearchFormCollapseEvent());
    }

    private Animator getIndicatorAnimator(float fromRotation, float toRotation) {
        ValueAnimator animator = ObjectAnimator.ofFloat(this.expandIndicator, View.ROTATION, new float[]{fromRotation, toRotation});
        animator.setDuration(200);
        return animator;
    }

    private void animateCollapse() {
        sendCollapseEvent();
        Animator resizeAnimator = getResizeAnimator(getHeight(), this.collapsedHeight);
        Animator indicatorAnimator = getIndicatorAnimator(this.expandIndicator.getRotation(), 0.0f);
        this.expandCollapseAnimator = new AnimatorSet();
        this.expandCollapseAnimator.playTogether(new Animator[]{resizeAnimator, indicatorAnimator});
        this.expandCollapseAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                SearchFormView.this.datesBtn.setVisibility(4);
            }
        });
        this.expandCollapseAnimator.start();
    }

    private Animator getResizeAnimator(int fromHeight, int toHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(new int[]{fromHeight, toHeight});
        animator.setDuration(200);
        animator.addUpdateListener(SearchFormView$$Lambda$3.lambdaFactory$(this));
        return animator;
    }

    /* synthetic */ void lambda$getResizeAnimator$2(ValueAnimator animation) {
        getLayoutParams().height = ((Integer) animation.getAnimatedValue()).intValue();
        requestLayout();
    }

    private void expand() {
        sendExpandEvent();
        getLayoutParams().height = getExpandedHeight();
        expandIndicator();
        requestLayout();
        this.datesBtn.setVisibility(0);
    }

    private void collapse() {
        sendCollapseEvent();
        getLayoutParams().height = this.collapsedHeight;
        collapseIndicator();
        requestLayout();
        this.datesBtn.setVisibility(4);
    }

    private int getExpandedHeight() {
        measure(MeasureSpec.makeMeasureSpec(0, RtlSpacingHelper.UNDEFINED), MeasureSpec.makeMeasureSpec(0, 0));
        return getMeasuredHeight();
    }

    private void setUpEmbedded() {
        this.destinationBtn.setVisibility(8);
        this.locationBtn.setVisibility(8);
        this.locationProgress.setVisibility(8);
        this.topDivider.setVisibility(4);
    }

    public void setUpData(SearchFormData searchFormData, boolean expanded) {
        if (expanded) {
            expand();
        } else {
            collapse();
        }
        setUpData(searchFormData);
    }

    private void expandIndicator() {
        this.expandIndicator.setRotation(180.0f);
    }

    private void collapseIndicator() {
        this.expandIndicator.setRotation(0.0f);
    }

    @Subscribe
    public void onKidsUpdated(KidsUpdatedEvent event) {
        if (getContext() != null) {
            this.searchFormData.setKids(event.getKids());
            this.searchFormData.saveData();
            setUpKids();
        }
    }

    private void onDatesClick() {
        HotellookApplication.eventBus().post(new SearchFormDatesClickEvent(this.searchFormData));
    }

    public void onKidsClick() {
        HotellookApplication.eventBus().post(new SearchFormKidsClickEvent(this.searchFormData.getKids()));
    }

    private void onDestinationClick() {
        HotellookApplication.eventBus().post(new OpenDestinationPickerEvent());
    }

    public void onSearchClick() {
        HotellookComponent component = HotellookApplication.from(getContext()).getComponent();
        component.eventBus().post(new SearchButtonClickEvent(this.searchFormData.toSearchParams(component.currencyRepository().currencyCode(), component.getCommonPreferences().areEnGatesAllowed())));
    }
}
