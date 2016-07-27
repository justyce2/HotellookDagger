package com.hotellook.ui.view.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.hotellook.C1178R;
import com.hotellook.api.data.MinPricesCalendarUtils;
import com.hotellook.api.data.MinPricesCalendarUtils.PriceGroup;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.core.api.pojo.minprice.ColoredMinPriceCalendar;
import com.hotellook.ui.view.calendar.MonthCellDescriptor.RangeState;
import com.hotellook.ui.view.calendar.MonthView.Listener;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.DateUtils;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import pl.charmas.android.reactivelocation.C1822R;
import timber.log.Timber;

public class CalendarPickerView extends ListView {
    public static final int MAX_RANGE = 30;
    private final MonthAdapter adapter;
    private CellClickInterceptor cellClickInterceptor;
    private final List<List<List<MonthCellDescriptor>>> cells;
    private DateSelectableFilter dateConfiguredListener;
    private OnDateSelectedListener dateListener;
    private boolean disableClicks;
    private DateFormat fullDateFormat;
    final List<Calendar> highlightedCals;
    final List<MonthCellDescriptor> highlightedCells;
    private OnInvalidDateSelectedListener invalidDateListener;
    final Listener listener;
    private Locale locale;
    private Calendar maxCal;
    private Calendar minCal;
    private ColoredMinPriceCalendar minPricesCalendar;
    private Calendar monthCounter;
    private DateFormat monthNameFormat;
    final List<MonthDescriptor> months;
    private ListViewObserver observer;
    private OnRangeSelectedListener onRangeSelectedListener;
    final List<Calendar> selectedCals;
    final List<MonthCellDescriptor> selectedCells;
    private SelectionMode selectionMode;
    private Calendar today;
    private View trackedChild;
    private int trackedChildPrevPosition;
    private int trackedChildPrevTop;
    private DateFormat weekdayNameFormat;

    public interface OnRangeSelectedListener {
        void onRangeSelected(Date date, Date date2, boolean z);
    }

    /* renamed from: com.hotellook.ui.view.calendar.CalendarPickerView.1 */
    class C14191 implements Runnable {
        final /* synthetic */ int val$selectedIndex;
        final /* synthetic */ boolean val$smoothScroll;

        C14191(int i, boolean z) {
            this.val$selectedIndex = i;
            this.val$smoothScroll = z;
        }

        public void run() {
            Timber.m751d("Scrolling to position %d", Integer.valueOf(this.val$selectedIndex));
            if (this.val$smoothScroll) {
                CalendarPickerView.this.smoothScrollToPosition(this.val$selectedIndex);
            } else {
                CalendarPickerView.this.setSelection(this.val$selectedIndex);
            }
        }
    }

    /* renamed from: com.hotellook.ui.view.calendar.CalendarPickerView.2 */
    class C14202 implements Runnable {
        C14202() {
        }

        public void run() {
            Timber.m751d("Dimens are fixed: now scroll to the selected date", new Object[0]);
            CalendarPickerView.this.scrollToSelectedDates();
        }
    }

    /* renamed from: com.hotellook.ui.view.calendar.CalendarPickerView.3 */
    static /* synthetic */ class C14213 {
        static final /* synthetic */ int[] f735x7f5dc9ff;

        static {
            f735x7f5dc9ff = new int[SelectionMode.values().length];
            try {
                f735x7f5dc9ff[SelectionMode.RANGE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f735x7f5dc9ff[SelectionMode.MULTIPLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f735x7f5dc9ff[SelectionMode.SINGLE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public interface CellClickInterceptor {
        boolean onCellClicked(Date date);
    }

    private class CellClickedListener implements Listener {
        private CellClickedListener() {
        }

        public void handleClick(MonthCellDescriptor cell) {
            Date clickedDate = cell.getDate();
            if (CalendarPickerView.this.cellClickInterceptor != null && CalendarPickerView.this.cellClickInterceptor.onCellClicked(clickedDate)) {
                return;
            }
            if (CalendarPickerView.betweenDates(clickedDate, CalendarPickerView.this.minCal, CalendarPickerView.this.maxCal) && CalendarPickerView.this.isDateSelectable(clickedDate)) {
                boolean wasSelected = CalendarPickerView.this.doSelectDate(clickedDate, cell);
                if (wasSelected && CalendarPickerView.this.selectedCals.size() > 1) {
                    CalendarPickerView.this.onRangeSelectedListener.onRangeSelected(((MonthCellDescriptor) CalendarPickerView.this.selectedCells.get(0)).getDate(), ((MonthCellDescriptor) CalendarPickerView.this.selectedCells.get(CalendarPickerView.this.selectedCells.size() - 1)).getDate(), CalendarPickerView.this.isRangeValid());
                }
                if (CalendarPickerView.this.dateListener == null) {
                    return;
                }
                if (wasSelected) {
                    CalendarPickerView.this.dateListener.onDateSelected(clickedDate);
                } else {
                    CalendarPickerView.this.dateListener.onDateUnselected(clickedDate);
                }
            } else if (CalendarPickerView.this.invalidDateListener != null) {
                CalendarPickerView.this.invalidDateListener.onInvalidDateSelected(clickedDate);
            }
        }
    }

    public interface DateSelectableFilter {
        boolean isDateSelectable(Date date);
    }

    public interface OnInvalidDateSelectedListener {
        void onInvalidDateSelected(Date date);
    }

    private class DefaultOnInvalidDateSelectedListener implements OnInvalidDateSelectedListener {
        private DefaultOnInvalidDateSelectedListener() {
        }

        public void onInvalidDateSelected(Date date) {
        }
    }

    public class FluentInitializer {
        public FluentInitializer inMode(SelectionMode mode) {
            CalendarPickerView.this.selectionMode = mode;
            CalendarPickerView.this.validateAndUpdate();
            return this;
        }

        public FluentInitializer withSelectedDate(Date selectedDates) {
            return withSelectedDates(Arrays.asList(new Date[]{selectedDates}));
        }

        public FluentInitializer withSelectedDates(Collection<Date> selectedDates) {
            if (CalendarPickerView.this.selectionMode != SelectionMode.SINGLE || selectedDates.size() <= 1) {
                if (selectedDates != null) {
                    for (Date date : selectedDates) {
                        CalendarPickerView.this.selectDate(date);
                    }
                }
                CalendarPickerView.this.scrollToSelectedDates();
                CalendarPickerView.this.validateAndUpdate();
                return this;
            }
            throw new IllegalArgumentException("SINGLE mode can't be used with multiple selectedDates");
        }

        public FluentInitializer withHighlightedDates(Collection<Date> dates) {
            CalendarPickerView.this.highlightDates(dates);
            return this;
        }

        @SuppressLint({"SimpleDateFormat"})
        public FluentInitializer setShortWeekdays(String[] newShortWeekdays) {
            DateFormatSymbols symbols = new DateFormatSymbols(CalendarPickerView.this.locale);
            symbols.setShortWeekdays(newShortWeekdays);
            CalendarPickerView.this.weekdayNameFormat = new SimpleDateFormat(CalendarPickerView.this.getContext().getString(C1178R.string.calendar_day_name_format), symbols);
            return this;
        }
    }

    public interface ListViewObserver {
        void onScroll(float f);
    }

    private class MonthAdapter extends BaseAdapter {
        private final LayoutInflater inflater;

        /* renamed from: com.hotellook.ui.view.calendar.CalendarPickerView.MonthAdapter.1 */
        class C14221 extends MonkeySafeClickListener {
            C14221() {
            }

            public void onSafeClick(View v) {
            }
        }

        private MonthAdapter() {
            this.inflater = LayoutInflater.from(CalendarPickerView.this.getContext());
        }

        public boolean isEnabled(int position) {
            return true;
        }

        public int getCount() {
            return CalendarPickerView.this.months.size();
        }

        public Object getItem(int position) {
            return CalendarPickerView.this.months.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            MonthView monthView = (MonthView) convertView;
            if (monthView == null) {
                monthView = MonthView.create(parent, this.inflater, CalendarPickerView.this.weekdayNameFormat, CalendarPickerView.this.listener, CalendarPickerView.this.today);
                monthView.setOnClickListener(new C14221());
            }
            monthView.init((MonthDescriptor) CalendarPickerView.this.months.get(position), (List) CalendarPickerView.this.cells.get(position));
            return monthView;
        }
    }

    private static class MonthCellWithMonthIndex {
        public MonthCellDescriptor cell;
        public int monthIndex;

        public MonthCellWithMonthIndex(MonthCellDescriptor cell, int monthIndex) {
            this.cell = cell;
            this.monthIndex = monthIndex;
        }
    }

    public interface OnDateSelectedListener {
        void onDateSelected(Date date);

        void onDateUnselected(Date date);
    }

    public enum SelectionMode {
        SINGLE,
        MULTIPLE,
        RANGE
    }

    static /* synthetic */ void lambda$new$0(Date startDate, Date finishDate, boolean validRange) {
    }

    public CalendarPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.listener = new CellClickedListener();
        this.months = new ArrayList();
        this.selectedCells = new ArrayList();
        this.highlightedCells = new ArrayList();
        this.selectedCals = new ArrayList();
        this.highlightedCals = new ArrayList();
        this.cells = new ArrayList();
        this.invalidDateListener = new DefaultOnInvalidDateSelectedListener();
        this.onRangeSelectedListener = CalendarPickerView$$Lambda$1.lambdaFactory$();
        this.disableClicks = false;
        this.adapter = new MonthAdapter();
        this.locale = AndroidUtils.getLocale();
        this.today = Calendar.getInstance(this.locale);
        this.minCal = Calendar.getInstance(this.locale);
        this.maxCal = Calendar.getInstance(this.locale);
        this.monthCounter = Calendar.getInstance(this.locale);
        this.monthNameFormat = DateUtils.getSimpleDateFormatWithNominativeRuMonths(context, context.getString(C1178R.string.calendar_month_name_format), this.locale);
        this.weekdayNameFormat = new SimpleDateFormat(context.getString(C1178R.string.calendar_day_name_format), this.locale);
        this.fullDateFormat = DateFormat.getDateInstance(2, this.locale);
        if (isInEditMode()) {
            Calendar nextYear = Calendar.getInstance(this.locale);
            nextYear.add(1, 1);
            init(new Date(), nextYear.getTime()).withSelectedDate(new Date());
        }
    }

    private static String dbg(Date minDate, Date maxDate) {
        return "minDate: " + minDate + "\nmaxDate: " + maxDate;
    }

    static void setMidnight(Calendar cal) {
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
    }

    private static boolean containsDate(List<Calendar> selectedCals, Calendar cal) {
        for (Calendar selectedCal : selectedCals) {
            if (sameDate(cal, selectedCal)) {
                return true;
            }
        }
        return false;
    }

    private static Calendar minDate(List<Calendar> selectedCals) {
        if (selectedCals == null || selectedCals.size() == 0) {
            return null;
        }
        Collections.sort(selectedCals);
        return (Calendar) selectedCals.get(0);
    }

    private static Calendar maxDate(List<Calendar> selectedCals) {
        if (selectedCals == null || selectedCals.size() == 0) {
            return null;
        }
        Collections.sort(selectedCals);
        return (Calendar) selectedCals.get(selectedCals.size() - 1);
    }

    private static boolean sameDate(Calendar cal, Calendar selectedDate) {
        if (cal.get(2) == selectedDate.get(2) && cal.get(1) == selectedDate.get(1) && cal.get(5) == selectedDate.get(5)) {
            return true;
        }
        return false;
    }

    private static boolean betweenDates(Calendar cal, Calendar minCal, Calendar maxCal) {
        return betweenDates(cal.getTime(), minCal, maxCal);
    }

    static boolean betweenDates(Date date, Calendar minCal, Calendar maxCal) {
        Date min = minCal.getTime();
        return (date.equals(min) || date.after(min)) && date.before(maxCal.getTime());
    }

    private static boolean sameMonth(Calendar cal, MonthDescriptor month) {
        return cal.get(2) == month.getMonth() && cal.get(1) == month.getYear();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.hotellook.ui.view.calendar.CalendarPickerView.FluentInitializer init(java.util.Date r13, java.util.Date r14, java.util.Locale r15) {
        /*
        r12 = this;
        r10 = 0;
        r8 = 2;
        r7 = 1;
        if (r13 == 0) goto L_0x0008;
    L_0x0006:
        if (r14 != 0) goto L_0x0025;
    L_0x0008:
        r4 = new java.lang.IllegalArgumentException;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "minDate and maxDate must be non-null.  ";
        r5 = r5.append(r6);
        r6 = dbg(r13, r14);
        r5 = r5.append(r6);
        r5 = r5.toString();
        r4.<init>(r5);
        throw r4;
    L_0x0025:
        r4 = r13.after(r14);
        if (r4 == 0) goto L_0x0048;
    L_0x002b:
        r4 = new java.lang.IllegalArgumentException;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "minDate must be before maxDate.  ";
        r5 = r5.append(r6);
        r6 = dbg(r13, r14);
        r5 = r5.append(r6);
        r5 = r5.toString();
        r4.<init>(r5);
        throw r4;
    L_0x0048:
        r4 = r13.getTime();
        r4 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1));
        if (r4 == 0) goto L_0x0058;
    L_0x0050:
        r4 = r14.getTime();
        r4 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1));
        if (r4 != 0) goto L_0x0075;
    L_0x0058:
        r4 = new java.lang.IllegalArgumentException;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "minDate and maxDate must be non-zero.  ";
        r5 = r5.append(r6);
        r6 = dbg(r13, r14);
        r5 = r5.append(r6);
        r5 = r5.toString();
        r4.<init>(r5);
        throw r4;
    L_0x0075:
        if (r15 != 0) goto L_0x007f;
    L_0x0077:
        r4 = new java.lang.IllegalArgumentException;
        r5 = "Locale is null.";
        r4.<init>(r5);
        throw r4;
    L_0x007f:
        r12.locale = r15;
        r4 = java.util.Calendar.getInstance(r15);
        r12.today = r4;
        r4 = java.util.Calendar.getInstance(r15);
        r12.minCal = r4;
        r4 = java.util.Calendar.getInstance(r15);
        r12.maxCal = r4;
        r4 = java.util.Calendar.getInstance(r15);
        r12.monthCounter = r4;
        r4 = r12.getContext();
        r5 = r12.getContext();
        r6 = 2131165291; // 0x7f07006b float:1.7944795E38 double:1.052935556E-314;
        r5 = r5.getString(r6);
        r4 = com.hotellook.utils.DateUtils.getSimpleDateFormatWithNominativeRuMonths(r4, r5, r15);
        r12.monthNameFormat = r4;
        r4 = r12.months;
        r4 = r4.iterator();
    L_0x00b4:
        r5 = r4.hasNext();
        if (r5 == 0) goto L_0x00ce;
    L_0x00ba:
        r3 = r4.next();
        r3 = (com.hotellook.ui.view.calendar.MonthDescriptor) r3;
        r5 = r12.monthNameFormat;
        r6 = r3.getDate();
        r5 = r5.format(r6);
        r3.setLabel(r5);
        goto L_0x00b4;
    L_0x00ce:
        r4 = new java.text.SimpleDateFormat;
        r5 = r12.getContext();
        r6 = 2131165289; // 0x7f070069 float:1.794479E38 double:1.052935555E-314;
        r5 = r5.getString(r6);
        r4.<init>(r5, r15);
        r12.weekdayNameFormat = r4;
        r4 = java.text.DateFormat.getDateInstance(r8, r15);
        r12.fullDateFormat = r4;
        r4 = com.hotellook.ui.view.calendar.CalendarPickerView.SelectionMode.SINGLE;
        r12.selectionMode = r4;
        r4 = r12.selectedCals;
        r4.clear();
        r4 = r12.selectedCells;
        r4.clear();
        r4 = r12.highlightedCals;
        r4.clear();
        r4 = r12.highlightedCells;
        r4.clear();
        r4 = r12.cells;
        r4.clear();
        r4 = r12.months;
        r4.clear();
        r4 = r12.minCal;
        r4.setTime(r13);
        r4 = r12.maxCal;
        r4.setTime(r14);
        r4 = r12.minCal;
        setMidnight(r4);
        r4 = r12.maxCal;
        setMidnight(r4);
        r4 = r12.maxCal;
        r5 = 12;
        r6 = -1;
        r4.add(r5, r6);
        r4 = r12.monthCounter;
        r5 = r12.minCal;
        r5 = r5.getTime();
        r4.setTime(r5);
        r4 = r12.maxCal;
        r1 = r4.get(r8);
        r4 = r12.maxCal;
        r2 = r4.get(r7);
    L_0x013b:
        r4 = r12.monthCounter;
        r4 = r4.get(r8);
        if (r4 <= r1) goto L_0x014b;
    L_0x0143:
        r4 = r12.monthCounter;
        r4 = r4.get(r7);
        if (r4 >= r2) goto L_0x0192;
    L_0x014b:
        r4 = r12.monthCounter;
        r4 = r4.get(r7);
        r5 = r2 + 1;
        if (r4 >= r5) goto L_0x0192;
    L_0x0155:
        r4 = r12.monthCounter;
        r0 = r4.getTime();
        r3 = new com.hotellook.ui.view.calendar.MonthDescriptor;
        r4 = r12.monthCounter;
        r4 = r4.get(r8);
        r5 = r12.monthCounter;
        r5 = r5.get(r7);
        r6 = r12.monthNameFormat;
        r6 = r6.format(r0);
        r3.<init>(r4, r5, r0, r6);
        r4 = r12.cells;
        r5 = r12.monthCounter;
        r5 = r12.getMonthCells(r3, r5);
        r4.add(r5);
        r4 = "Adding month %s";
        r5 = new java.lang.Object[r7];
        r6 = 0;
        r5[r6] = r3;
        timber.log.Timber.m751d(r4, r5);
        r4 = r12.months;
        r4.add(r3);
        r4 = r12.monthCounter;
        r4.add(r8, r7);
        goto L_0x013b;
    L_0x0192:
        r12.validateAndUpdate();
        r4 = new com.hotellook.ui.view.calendar.CalendarPickerView$FluentInitializer;
        r4.<init>();
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hotellook.ui.view.calendar.CalendarPickerView.init(java.util.Date, java.util.Date, java.util.Locale):com.hotellook.ui.view.calendar.CalendarPickerView$FluentInitializer");
    }

    public FluentInitializer init(Date minDate, Date maxDate) {
        return init(minDate, maxDate, AndroidUtils.getLocale());
    }

    private void validateAndUpdate() {
        if (getAdapter() == null) {
            setAdapter(this.adapter);
        }
        this.adapter.notifyDataSetChanged();
    }

    private void scrollToSelectedMonth(int selectedIndex) {
        scrollToSelectedMonth(selectedIndex, false);
    }

    private void scrollToSelectedMonth(int selectedIndex, boolean smoothScroll) {
        post(new C14191(selectedIndex, smoothScroll));
    }

    private void scrollToSelectedDates() {
        Integer selectedIndex = null;
        Integer todayIndex = null;
        Calendar today = Calendar.getInstance(this.locale);
        for (int c = 0; c < this.months.size(); c++) {
            MonthDescriptor month = (MonthDescriptor) this.months.get(c);
            if (selectedIndex == null) {
                for (Calendar selectedCal : this.selectedCals) {
                    if (sameMonth(selectedCal, month)) {
                        selectedIndex = Integer.valueOf(c);
                        break;
                    }
                }
                if (selectedIndex == null && todayIndex == null && sameMonth(today, month)) {
                    todayIndex = Integer.valueOf(c);
                }
            }
        }
        if (selectedIndex != null) {
            scrollToSelectedMonth(selectedIndex.intValue());
        } else if (todayIndex != null) {
            scrollToSelectedMonth(todayIndex.intValue());
        }
    }

    public boolean scrollToDate(Date date) {
        Integer selectedIndex = null;
        Calendar cal = Calendar.getInstance(this.locale);
        cal.setTime(date);
        for (int c = 0; c < this.months.size(); c++) {
            if (sameMonth(cal, (MonthDescriptor) this.months.get(c))) {
                selectedIndex = Integer.valueOf(c);
                break;
            }
        }
        if (selectedIndex == null) {
            return false;
        }
        scrollToSelectedMonth(selectedIndex.intValue());
        return true;
    }

    public void fixDialogDimens() {
        Timber.m751d("Fixing dimensions to h = %d / w = %d", Integer.valueOf(getMeasuredHeight()), Integer.valueOf(getMeasuredWidth()));
        getLayoutParams().height = getMeasuredHeight();
        getLayoutParams().width = getMeasuredWidth();
        post(new C14202());
    }

    public void unfixDialogDimens() {
        Timber.m751d("Reset the fixed dimensions to allow for re-measurement", new Object[0]);
        getLayoutParams().height = -1;
        getLayoutParams().width = -1;
        requestLayout();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.months.isEmpty()) {
            throw new IllegalStateException("Must have at least one month to display.  Did you forget to call init()?");
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public Date getSelectedDate() {
        return this.selectedCals.size() > 0 ? ((Calendar) this.selectedCals.get(0)).getTime() : null;
    }

    public List<Date> getSelectedDates() {
        List<Date> selectedDates = new ArrayList();
        for (MonthCellDescriptor cal : this.selectedCells) {
            selectedDates.add(cal.getDate());
        }
        Collections.sort(selectedDates);
        return selectedDates;
    }

    public boolean selectDate(Date date) {
        return selectDate(date, false);
    }

    public boolean selectDate(Date date, boolean smoothScroll) {
        MonthCellWithMonthIndex monthCellWithMonthIndex = getMonthCellWithIndexByDate(date);
        if (monthCellWithMonthIndex == null || !isDateSelectable(date)) {
            return false;
        }
        boolean wasSelected = doSelectDate(date, monthCellWithMonthIndex.cell);
        if (!wasSelected) {
            return wasSelected;
        }
        scrollToSelectedMonth(monthCellWithMonthIndex.monthIndex, smoothScroll);
        return wasSelected;
    }

    private boolean doSelectDate(Date date, MonthCellDescriptor cell) {
        Calendar newlySelectedCal = Calendar.getInstance(this.locale);
        newlySelectedCal.setTime(date);
        setMidnight(newlySelectedCal);
        clearRangeState();
        switch (C14213.f735x7f5dc9ff[this.selectionMode.ordinal()]) {
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                if (this.selectedCals.size() <= 1) {
                    if (this.selectedCals.size() == 1 && newlySelectedCal.before(this.selectedCals.get(0))) {
                        clearOldSelections();
                        break;
                    }
                }
                clearOldSelections();
                break;
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
                date = applyMultiSelect(date, newlySelectedCal);
                break;
            case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                clearOldSelections();
                break;
            default:
                throw new IllegalStateException("Unknown selectionMode " + this.selectionMode);
        }
        if (date != null) {
            int i;
            if (this.selectedCells.size() == 0 || !((MonthCellDescriptor) this.selectedCells.get(0)).equals(cell)) {
                this.selectedCells.add(cell);
                cell.setSelected(true);
                this.selectedCals.add(newlySelectedCal);
            }
            if (this.selectionMode == SelectionMode.RANGE && this.selectedCells.size() > 1) {
                Date start = ((MonthCellDescriptor) this.selectedCells.get(0)).getDate();
                Date end = ((MonthCellDescriptor) this.selectedCells.get(1)).getDate();
                ((MonthCellDescriptor) this.selectedCells.get(0)).setRangeState(RangeState.FIRST);
                ((MonthCellDescriptor) this.selectedCells.get(1)).setRangeState(RangeState.LAST);
                this.selectedCells.clear();
                for (List<List<MonthCellDescriptor>> month : this.cells) {
                    for (List<MonthCellDescriptor> week : month) {
                        for (i = 0; i < week.size(); i++) {
                            MonthCellDescriptor singleCell = (MonthCellDescriptor) week.get(i);
                            if ((singleCell.getDate().after(start) || singleCell.getDate().compareTo(start) == 0) && ((singleCell.getDate().before(end) || singleCell.getDate().compareTo(end) == 0) && singleCell.isSelectable())) {
                                singleCell.setSelected(true);
                                Calendar c = Calendar.getInstance(this.locale);
                                c.setTime(singleCell.getDate());
                                int dayOfMonth = c.get(5);
                                if (i == 0 || dayOfMonth == c.getActualMinimum(5) || singleCell.getDate().compareTo(start) == 0) {
                                    if (singleCell.getRangeState() == RangeState.LAST) {
                                        singleCell.setRangeState(RangeState.SINGLE);
                                    } else {
                                        singleCell.setRangeState(RangeState.FIRST);
                                    }
                                }
                                if ((i == week.size() - 1 || dayOfMonth == c.getActualMaximum(5) || singleCell.getDate().compareTo(end) == 0) && singleCell.getRangeState() != RangeState.SINGLE) {
                                    if (singleCell.getRangeState() == RangeState.FIRST) {
                                        singleCell.setRangeState(RangeState.SINGLE);
                                    } else {
                                        singleCell.setRangeState(RangeState.LAST);
                                    }
                                }
                                if (singleCell.getRangeState() == RangeState.NONE) {
                                    singleCell.setRangeState(RangeState.MIDDLE);
                                }
                                this.selectedCells.add(singleCell);
                            }
                        }
                    }
                }
            } else if (this.selectionMode == SelectionMode.RANGE && this.selectedCells.size() == 1) {
                ((MonthCellDescriptor) this.selectedCells.get(0)).setRangeState(RangeState.SINGLE);
            }
            int counter = 0;
            i = 0;
            while (i < this.selectedCells.size()) {
                if (counter >= MAX_RANGE) {
                    ((MonthCellDescriptor) this.selectedCells.get(i)).setRangeValid(false);
                    if (((MonthCellDescriptor) this.selectedCells.get(i - 1)).isRangeValid()) {
                        if (((MonthCellDescriptor) this.selectedCells.get(i - 1)).getRangeState() == RangeState.FIRST || ((MonthCellDescriptor) this.selectedCells.get(i - 1)).getRangeState() == RangeState.SINGLE) {
                            ((MonthCellDescriptor) this.selectedCells.get(i - 1)).setRangeState(RangeState.SINGLE);
                        } else {
                            ((MonthCellDescriptor) this.selectedCells.get(i - 1)).setRangeState(RangeState.LAST);
                        }
                        if (((MonthCellDescriptor) this.selectedCells.get(i)).getRangeState() == RangeState.LAST || ((MonthCellDescriptor) this.selectedCells.get(i)).getRangeState() == RangeState.SINGLE) {
                            ((MonthCellDescriptor) this.selectedCells.get(i)).setRangeState(RangeState.SINGLE);
                        } else {
                            ((MonthCellDescriptor) this.selectedCells.get(i)).setRangeState(RangeState.FIRST);
                        }
                    }
                } else {
                    ((MonthCellDescriptor) this.selectedCells.get(i)).setRangeValid(true);
                }
                if (((MonthCellDescriptor) this.selectedCells.get(i)).getRangeState() != RangeState.NONE) {
                    counter++;
                }
                i++;
            }
        }
        validateAndUpdate();
        return date != null;
    }

    private void clearRangeState() {
        for (MonthCellDescriptor selectedCell : this.selectedCells) {
            selectedCell.setRangeState(RangeState.NONE);
        }
    }

    public void reset() {
        clearRangeState();
        clearOldSelections();
        validateAndUpdate();
    }

    private void clearOldSelections() {
        for (MonthCellDescriptor selectedCell : this.selectedCells) {
            selectedCell.setSelected(false);
            if (this.dateListener != null) {
                Date selectedDate = selectedCell.getDate();
                if (this.selectionMode == SelectionMode.RANGE) {
                    int index = this.selectedCells.indexOf(selectedCell);
                    if (index == 0 || index == this.selectedCells.size() - 1) {
                        this.dateListener.onDateUnselected(selectedDate);
                    }
                } else {
                    this.dateListener.onDateUnselected(selectedDate);
                }
            }
        }
        this.selectedCells.clear();
        this.selectedCals.clear();
    }

    private Date applyMultiSelect(Date date, Calendar selectedCal) {
        for (MonthCellDescriptor selectedCell : this.selectedCells) {
            if (selectedCell.getDate().equals(date)) {
                selectedCell.setSelected(false);
                this.selectedCells.remove(selectedCell);
                date = null;
                break;
            }
        }
        for (Calendar cal : this.selectedCals) {
            if (sameDate(cal, selectedCal)) {
                this.selectedCals.remove(cal);
                break;
            }
        }
        return date;
    }

    public void highlightDates(Collection<Date> dates) {
        for (Date date : dates) {
            MonthCellWithMonthIndex monthCellWithMonthIndex = getMonthCellWithIndexByDate(date);
            if (monthCellWithMonthIndex != null) {
                Calendar newlyHighlightedCal = Calendar.getInstance();
                newlyHighlightedCal.setTime(date);
                MonthCellDescriptor cell = monthCellWithMonthIndex.cell;
                this.highlightedCells.add(cell);
                this.highlightedCals.add(newlyHighlightedCal);
                cell.setHighlighted(true);
            }
        }
        validateAndUpdate();
    }

    public void clearHighlightedDates() {
        for (MonthCellDescriptor cal : this.highlightedCells) {
            cal.setHighlighted(false);
        }
        this.highlightedCells.clear();
        this.highlightedCals.clear();
        validateAndUpdate();
    }

    private MonthCellWithMonthIndex getMonthCellWithIndexByDate(Date date) {
        int index = 0;
        Calendar searchCal = Calendar.getInstance(this.locale);
        searchCal.setTime(date);
        Calendar actCal = Calendar.getInstance(this.locale);
        for (List<List<MonthCellDescriptor>> monthCells : this.cells) {
            for (List<MonthCellDescriptor> weekCells : monthCells) {
                for (MonthCellDescriptor actCell : weekCells) {
                    actCal.setTime(actCell.getDate());
                    if (sameDate(actCal, searchCal) && actCell.isSelectable()) {
                        return new MonthCellWithMonthIndex(actCell, index);
                    }
                }
            }
            index++;
        }
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    java.util.List<java.util.List<com.hotellook.ui.view.calendar.MonthCellDescriptor>> getMonthCells(com.hotellook.ui.view.calendar.MonthDescriptor r23, java.util.Calendar r24) {
        /*
        r22 = this;
        r0 = r22;
        r2 = r0.locale;
        r14 = java.util.Calendar.getInstance(r2);
        r2 = r24.getTime();
        r14.setTime(r2);
        r15 = new java.util.ArrayList;
        r15.<init>();
        r2 = 5;
        r11 = 1;
        r14.set(r2, r11);
        r2 = 7;
        r16 = r14.get(r2);
        r2 = r14.getFirstDayOfWeek();
        r19 = r2 - r16;
        if (r19 <= 0) goto L_0x0028;
    L_0x0026:
        r19 = r19 + -7;
    L_0x0028:
        r2 = 5;
        r0 = r19;
        r14.add(r2, r0);
        r0 = r22;
        r2 = r0.selectedCals;
        r18 = minDate(r2);
        r0 = r22;
        r2 = r0.selectedCals;
        r17 = maxDate(r2);
    L_0x003e:
        r2 = 2;
        r2 = r14.get(r2);
        r11 = r23.getMonth();
        r11 = r11 + 1;
        if (r2 < r11) goto L_0x0056;
    L_0x004b:
        r2 = 1;
        r2 = r14.get(r2);
        r11 = r23.getYear();
        if (r2 >= r11) goto L_0x011d;
    L_0x0056:
        r2 = 1;
        r2 = r14.get(r2);
        r11 = r23.getYear();
        if (r2 > r11) goto L_0x011d;
    L_0x0061:
        r2 = "Building week row starting at %s";
        r11 = 1;
        r11 = new java.lang.Object[r11];
        r12 = 0;
        r21 = r14.getTime();
        r11[r12] = r21;
        timber.log.Timber.m751d(r2, r11);
        r20 = new java.util.ArrayList;
        r20.<init>();
        r0 = r20;
        r15.add(r0);
        r13 = 0;
    L_0x007b:
        r2 = 7;
        if (r13 >= r2) goto L_0x003e;
    L_0x007e:
        r3 = r14.getTime();
        r2 = 2;
        r2 = r14.get(r2);
        r11 = r23.getMonth();
        if (r2 != r11) goto L_0x00f9;
    L_0x008d:
        r4 = 1;
    L_0x008e:
        if (r4 == 0) goto L_0x00fb;
    L_0x0090:
        r0 = r22;
        r2 = r0.selectedCals;
        r2 = containsDate(r2, r14);
        if (r2 == 0) goto L_0x00fb;
    L_0x009a:
        r6 = 1;
    L_0x009b:
        if (r4 == 0) goto L_0x00fd;
    L_0x009d:
        r0 = r22;
        r2 = r0.minCal;
        r0 = r22;
        r11 = r0.maxCal;
        r2 = betweenDates(r14, r2, r11);
        if (r2 == 0) goto L_0x00fd;
    L_0x00ab:
        r0 = r22;
        r2 = r0.isDateSelectable(r3);
        if (r2 == 0) goto L_0x00fd;
    L_0x00b3:
        r5 = 1;
    L_0x00b4:
        r0 = r22;
        r2 = r0.today;
        r7 = sameDate(r14, r2);
        r0 = r22;
        r2 = r0.highlightedCals;
        r8 = containsDate(r2, r14);
        r2 = 5;
        r9 = r14.get(r2);
        r10 = com.hotellook.ui.view.calendar.MonthCellDescriptor.RangeState.NONE;
        r0 = r22;
        r2 = r0.selectedCals;
        r2 = r2.size();
        r11 = 1;
        if (r2 <= r11) goto L_0x00e0;
    L_0x00d6:
        r0 = r18;
        r2 = sameDate(r0, r14);
        if (r2 == 0) goto L_0x00ff;
    L_0x00de:
        r10 = com.hotellook.ui.view.calendar.MonthCellDescriptor.RangeState.FIRST;
    L_0x00e0:
        r2 = new com.hotellook.ui.view.calendar.MonthCellDescriptor;
        r11 = 1;
        r0 = r22;
        r12 = r0.detectPriceGroup(r3);
        r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12);
        r0 = r20;
        r0.add(r2);
        r2 = 5;
        r11 = 1;
        r14.add(r2, r11);
        r13 = r13 + 1;
        goto L_0x007b;
    L_0x00f9:
        r4 = 0;
        goto L_0x008e;
    L_0x00fb:
        r6 = 0;
        goto L_0x009b;
    L_0x00fd:
        r5 = 0;
        goto L_0x00b4;
    L_0x00ff:
        r0 = r22;
        r2 = r0.selectedCals;
        r2 = maxDate(r2);
        r2 = sameDate(r2, r14);
        if (r2 == 0) goto L_0x0110;
    L_0x010d:
        r10 = com.hotellook.ui.view.calendar.MonthCellDescriptor.RangeState.LAST;
        goto L_0x00e0;
    L_0x0110:
        r0 = r18;
        r1 = r17;
        r2 = betweenDates(r14, r0, r1);
        if (r2 == 0) goto L_0x00e0;
    L_0x011a:
        r10 = com.hotellook.ui.view.calendar.MonthCellDescriptor.RangeState.MIDDLE;
        goto L_0x00e0;
    L_0x011d:
        return r15;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hotellook.ui.view.calendar.CalendarPickerView.getMonthCells(com.hotellook.ui.view.calendar.MonthDescriptor, java.util.Calendar):java.util.List<java.util.List<com.hotellook.ui.view.calendar.MonthCellDescriptor>>");
    }

    private PriceGroup detectPriceGroup(Date date) {
        return this.minPricesCalendar == null ? PriceGroup.NONE : MinPricesCalendarUtils.getPriceGroup(this.minPricesCalendar, date);
    }

    private boolean containsDate(List<Calendar> selectedCals, Date date) {
        Calendar cal = Calendar.getInstance(this.locale);
        cal.setTime(date);
        return containsDate((List) selectedCals, cal);
    }

    private boolean isDateSelectable(Date date) {
        return this.dateConfiguredListener == null || this.dateConfiguredListener.isDateSelectable(date);
    }

    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        this.dateListener = listener;
    }

    public void setOnInvalidDateSelectedListener(OnInvalidDateSelectedListener listener) {
        this.invalidDateListener = listener;
    }

    public void setDateSelectableFilter(DateSelectableFilter listener) {
        this.dateConfiguredListener = listener;
    }

    public void setCellClickInterceptor(CellClickInterceptor listener) {
        this.cellClickInterceptor = listener;
    }

    public void setOnRangeSelectedListener(OnRangeSelectedListener onRangeSelectedListener) {
        this.onRangeSelectedListener = onRangeSelectedListener;
    }

    public void setMinPricesCalendar(ColoredMinPriceCalendar minPricesCalendar) {
        this.minPricesCalendar = minPricesCalendar;
    }

    private boolean isRangeValid() {
        return this.selectedCells.size() <= MAX_RANGE;
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.trackedChild != null) {
            boolean childIsSafeToTrack = this.trackedChild.getParent() == this && getPositionForView(this.trackedChild) == this.trackedChildPrevPosition;
            if (childIsSafeToTrack) {
                int top = this.trackedChild.getTop();
                if (this.observer != null) {
                    this.observer.onScroll((float) (top - this.trackedChildPrevTop));
                }
                this.trackedChildPrevTop = top;
                return;
            }
            this.trackedChild = null;
        } else if (getChildCount() > 0) {
            this.trackedChild = getChildInTheMiddle();
            this.trackedChildPrevTop = this.trackedChild.getTop();
            this.trackedChildPrevPosition = getPositionForView(this.trackedChild);
        }
    }

    private View getChildInTheMiddle() {
        return getChildAt(getChildCount() / 2);
    }

    public void setObserver(ListViewObserver observer) {
        this.observer = observer;
    }
}
