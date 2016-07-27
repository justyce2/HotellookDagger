package com.hotellook.ui.screen;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.api.data.SearchFormData;
import com.hotellook.core.api.pojo.minprice.ColoredMinPriceCalendar;
import com.hotellook.events.MoreThan30DaysBookingEvent;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.ui.view.calendar.CalendarPickerView;
import com.hotellook.ui.view.calendar.CalendarPickerView.SelectionMode;
import com.hotellook.ui.view.viewmovers.ScrollableViewsSynchronizer;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.DateUtils;
import com.hotellook.utils.ViewUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatesPickerFragment extends BaseFragment {
    public static final int DEFAULT_POST_DELAY = 300;
    private CalendarPickerView calendar;
    private ColoredMinPriceCalendar minPricesCalendar;
    private SearchFormData searchFormData;
    private List<Date> selectedDates;

    /* renamed from: com.hotellook.ui.screen.DatesPickerFragment.1 */
    class C12141 implements OnGlobalLayoutListener {
        final /* synthetic */ CalendarPickerView val$calendar;
        final /* synthetic */ ViewGroup val$layout;
        final /* synthetic */ View val$toolbarShadow;

        C12141(ViewGroup viewGroup, CalendarPickerView calendarPickerView, View view) {
            this.val$layout = viewGroup;
            this.val$calendar = calendarPickerView;
            this.val$toolbarShadow = view;
        }

        public void onGlobalLayout() {
            if (DatesPickerFragment.this.getActivity() != null) {
                AndroidUtils.removeOnGlobalLayoutListener(this.val$layout, this);
                int translation = -DatesPickerFragment.this.getToolbar().getHeight();
                ScrollableViewsSynchronizer.with(this.val$calendar).addViewToTranslateAndHide(DatesPickerFragment.this.getToolbarParent(), DatesPickerFragment.this.getToolbar(), translation).addViewToTranslate(this.val$toolbarShadow, translation);
            }
        }
    }

    static class Snapshot {
        final ColoredMinPriceCalendar minPricesCalendar;
        final SearchFormData searchFormData;
        public List<Date> selectedDates;

        Snapshot(SearchFormData searchFormData, ColoredMinPriceCalendar minPricesCalendar, List<Date> selectedDates) {
            this.searchFormData = searchFormData;
            this.minPricesCalendar = minPricesCalendar;
            this.selectedDates = selectedDates;
        }
    }

    public static DatesPickerFragment create(SearchFormData searchFormData, ColoredMinPriceCalendar minPricesCalendar) {
        DatesPickerFragment fragment = new DatesPickerFragment();
        fragment.setSearchFormData(searchFormData);
        fragment.setMinPricesCalendar(minPricesCalendar);
        return fragment;
    }

    private void setSearchFormData(SearchFormData searchFormData) {
        this.searchFormData = searchFormData;
    }

    private void setMinPricesCalendar(ColoredMinPriceCalendar minPricesCalendar) {
        this.minPricesCalendar = minPricesCalendar;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (hasInitialSnapshot()) {
            restoreFromSnapshot((Snapshot) initialSnapshot());
        }
        setUpToolbar();
        ViewGroup layout = (ViewGroup) inflater.inflate(C1178R.layout.fragment_date_picker, container, false);
        this.calendar = (CalendarPickerView) layout.findViewById(C1178R.id.calendar_view);
        AndroidUtils.addMarginToPlaceViewBelowToolbar(this.calendar);
        AndroidUtils.addMarginToPlaceViewBelowStatusBar(this.calendar);
        ViewUtils.addLeftAndRightPaddingsForWideScreen(this.calendar);
        if (VERSION.SDK_INT >= 21) {
            this.calendar.setNestedScrollingEnabled(true);
        }
        Calendar today = Calendar.getInstance();
        if (DateUtils.isPreviousDayActualAnywhereOnThePlanet()) {
            today.add(5, -1);
        }
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(1, 1);
        List<Date> selectedDates = new ArrayList();
        if (hasInitialSnapshot()) {
            selectedDates.addAll(this.selectedDates);
        } else {
            selectedDates.add(this.searchFormData.getCheckInDate());
            selectedDates.add(this.searchFormData.getCheckOutDate());
        }
        this.calendar.setMinPricesCalendar(this.minPricesCalendar);
        this.calendar.init(today.getTime(), nextYear.getTime()).inMode(SelectionMode.RANGE).withSelectedDates(selectedDates);
        this.calendar.setOnRangeSelectedListener(DatesPickerFragment$$Lambda$1.lambdaFactory$(this));
        if (this.minPricesCalendar != null) {
            setUpMovingUIs(layout, this.calendar, getMainActivity().getToolbarManager().shadow());
        }
        return layout;
    }

    /* synthetic */ void lambda$onCreateView$3(Date startDate, Date finishDate, boolean validRange) {
        if (getActivity() != null) {
            if (validRange) {
                this.calendar.setOnTouchListener(DatesPickerFragment$$Lambda$3.lambdaFactory$());
                new Handler().postDelayed(DatesPickerFragment$$Lambda$4.lambdaFactory$(this, startDate, finishDate), 300);
                return;
            }
            new Handler().postDelayed(DatesPickerFragment$$Lambda$2.lambdaFactory$(this), 300);
        }
    }

    /* synthetic */ void lambda$null$0() {
        if (getActivity() != null) {
            Toasts.showCalendarInvalidRange(getMainActivity());
            HotellookApplication.eventBus().post(new MoreThan30DaysBookingEvent());
            this.calendar.reset();
        }
    }

    /* synthetic */ void lambda$null$2(Date startDate, Date finishDate) {
        if (getActivity() != null) {
            this.searchFormData.setCheckInDate(startDate);
            this.searchFormData.setCheckOutDate(finishDate);
            this.searchFormData.saveData();
            getActivity().onBackPressed();
        }
    }

    private void setUpMovingUIs(ViewGroup layout, CalendarPickerView calendar, View toolbarShadow) {
        layout.getViewTreeObserver().addOnGlobalLayoutListener(new C12141(layout, calendar, toolbarShadow));
    }

    private View getToolbarParent() {
        return (View) getToolbar().getParent();
    }

    private void setUpToolbar() {
        TextView title = (TextView) LayoutInflater.from(getActivity()).inflate(C1178R.layout.toolbar_title, getMainActivity().getToolbarManager().getToolbar(), false);
        title.setText(getString(C1178R.string.title_dates));
        ToolbarSettings toolbarSettings = new ToolbarSettings().navigationMode(1).bkgColor(getResources().getColor(C1178R.color.date_picker_toolbar_bkg)).toggleColor(getResources().getColor(C1178R.color.dp_toggle)).withCustomView(title).withShadow();
        if (this.minPricesCalendar == null) {
            toolbarSettings.withShadow();
        }
        getMainActivity().getToolbarManager().setUpToolbar(getSupportActionBar(), toolbarSettings);
    }

    @Nullable
    public Snapshot takeSnapshot() {
        return new Snapshot(this.searchFormData, this.minPricesCalendar, this.calendar.getSelectedDates());
    }

    private void restoreFromSnapshot(@NonNull Snapshot snapshot) {
        this.searchFormData = snapshot.searchFormData;
        this.minPricesCalendar = snapshot.minPricesCalendar;
        this.selectedDates = snapshot.selectedDates;
    }
}
