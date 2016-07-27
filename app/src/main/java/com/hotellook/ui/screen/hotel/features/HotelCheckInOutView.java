package com.hotellook.ui.screen.hotel.features;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.hotellook.C1178R;
import com.hotellook.databinding.HotelCheckInOutViewBinding;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HotelCheckInOutView extends LinearLayout {
    private static final String CHECK_IN_OUT_DATE_FORMAT = "dd MMMM";
    private HotelCheckInOutViewBinding binding;

    public HotelCheckInOutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            this.binding = (HotelCheckInOutViewBinding) DataBindingUtil.bind(this);
        }
    }

    public void bindTo(@Nullable String checkInTime, @Nullable String checkOutTime, @Nullable Calendar checkInDate, @Nullable Calendar checkOutDate) {
        String times = buildTimes(checkInTime, checkOutTime);
        if (StringUtils.isNotBlank(times)) {
            this.binding.time.setText(times);
        }
        String dates = buildDates(checkInDate, checkOutDate);
        if (!StringUtils.isNotBlank(dates)) {
            this.binding.date.setVisibility(8);
        } else if (StringUtils.isBlank(times)) {
            this.binding.time.setText(dates);
            this.binding.date.setVisibility(8);
        } else {
            this.binding.date.setText(dates);
            this.binding.date.setVisibility(0);
        }
    }

    @NonNull
    private String buildTimes(@Nullable String checkInTime, @Nullable String checkOutTime) {
        StringBuilder timesBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(checkInTime)) {
            timesBuilder.append(String.format(getContext().getString(C1178R.string.hotel_general_check_in_after), new Object[]{checkInTime}));
        }
        if (StringUtils.isNotBlank(checkOutTime)) {
            String checkOut = String.format(getContext().getString(C1178R.string.hotel_general_check_out_before), new Object[]{checkOutTime});
            if (StringUtils.isBlank(checkInTime)) {
                timesBuilder.append(StringUtils.capitalize(checkOut));
            } else {
                timesBuilder.append(", ").append(checkOut);
            }
        }
        return timesBuilder.toString();
    }

    @NonNull
    private String buildDates(@Nullable Calendar checkInDate, @Nullable Calendar checkOutDate) {
        StringBuilder datesBuilder = new StringBuilder();
        if (!(checkInDate == null || checkOutDate == null)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(CHECK_IN_OUT_DATE_FORMAT, AndroidUtils.getLocale());
            datesBuilder.append(dateFormat.format(checkInDate.getTime())).append(" \u2014 ").append(dateFormat.format(checkOutDate.getTime()));
        }
        return datesBuilder.toString();
    }
}
