package com.hotellook.ui.screen.hotel.features;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.badges.Badge;
import com.hotellook.common.locale.Constants.Language;
import com.hotellook.core.api.pojo.hoteldetail.AmenityData;
import com.hotellook.ui.screen.hotel.amenity.FreeableAmenityTitleDelegate;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.StringUtils;
import java.util.Calendar;
import java.util.List;

public class HotelFeaturesView extends LinearLayout {
    private HotelAccommodationTypeView accommodationTypeView;
    private HotelBadgesView badgesView;
    private HotelCheckInOutView checkInOutView;
    private View parkingView;
    private HotelStaffLanguageView staffLanguageView;

    public HotelFeaturesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.parkingView = findViewById(C1178R.id.parking);
        this.badgesView = (HotelBadgesView) findViewById(C1178R.id.badges);
        this.checkInOutView = (HotelCheckInOutView) findViewById(C1178R.id.check_in_out);
        this.staffLanguageView = (HotelStaffLanguageView) findViewById(C1178R.id.staff_language);
        this.accommodationTypeView = (HotelAccommodationTypeView) findViewById(C1178R.id.accommodation_type);
        if (!isInEditMode()) {
            setVisibility(8);
        }
    }

    public void setUpBadges(@Nullable List<Badge> badges) {
        if (CollectionUtils.isEmpty(badges)) {
            this.badgesView.setVisibility(8);
            return;
        }
        this.badgesView.bindTo(badges);
        this.badgesView.setVisibility(0);
        setVisibility(0);
    }

    public void setUpCheckInOut(@Nullable String checkInTime, @Nullable String checkOutTime, @Nullable Calendar checkInDate, @Nullable Calendar checkOutDate) {
        if (StringUtils.isBlank(checkInTime) && StringUtils.isBlank(checkOutTime) && (checkInDate == null || checkOutDate == null)) {
            this.checkInOutView.setVisibility(8);
            return;
        }
        this.checkInOutView.bindTo(checkInTime, checkOutTime, checkInDate, checkOutDate);
        this.checkInOutView.setVisibility(0);
        setVisibility(0);
    }

    public void setUpStaffLanguage(@Nullable List<AmenityData> list) {
        this.staffLanguageView.setVisibility(8);
    }

    @Nullable
    private String staffLanguage(@Nullable List<AmenityData> languages) {
        if (!CollectionUtils.isNotEmpty(languages)) {
            return null;
        }
        String userLanguage = AndroidUtils.getLanguage();
        if (userLanguage.equals(Language.ENGLISH)) {
            return null;
        }
        for (AmenityData language : languages) {
            if (userLanguage.equals(language.getSlug())) {
                return language.getSlug();
            }
        }
        return null;
    }

    public void setUpAccommodationType(@Nullable String type, int adultsCount, int childrenCount) {
        if (StringUtils.isBlank(type)) {
            this.accommodationTypeView.setVisibility(8);
            return;
        }
        this.accommodationTypeView.bindTo(type, adultsCount, childrenCount);
        this.accommodationTypeView.setVisibility(0);
        setVisibility(0);
    }

    public void setUpParking(@Nullable List<AmenityData> hotelAmenities) {
        AmenityData amenityData = parkingAmenity(hotelAmenities);
        if (amenityData == null || !amenityData.isFree()) {
            this.parkingView.setVisibility(8);
            return;
        }
        this.parkingView.setVisibility(0);
        ((TextView) this.parkingView.findViewById(C1178R.id.parking_title)).setText(new FreeableAmenityTitleDelegate(C1178R.string.free_parking, C1178R.string.parking).title(this.parkingView.getContext(), amenityData));
        setVisibility(0);
    }

    @Nullable
    private AmenityData parkingAmenity(@Nullable List<AmenityData> hotelAmenities) {
        if (CollectionUtils.isNotEmpty(hotelAmenities)) {
            for (AmenityData hotelAmenity : hotelAmenities) {
                if (AmenityData.AMENITY_PARKING.equals(hotelAmenity.getSlug())) {
                    return hotelAmenity;
                }
            }
        }
        return null;
    }
}
