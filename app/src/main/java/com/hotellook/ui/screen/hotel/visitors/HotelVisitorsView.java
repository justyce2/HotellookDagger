package com.hotellook.ui.screen.hotel.visitors;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.hotellook.common.locale.Constants.Language;
import com.hotellook.core.api.Constants.TripTypeSplit;
import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.core.api.pojo.hoteldetail.LanguageData;
import com.hotellook.core.api.pojo.hoteldetail.TripType;
import com.hotellook.core.api.pojo.hoteldetail.TrustYouData;
import com.hotellook.databinding.HotelVisitorsViewBinding;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.CompareUtils;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import pl.charmas.android.reactivelocation.C1822R;

public class HotelVisitorsView extends LinearLayout {
    private HotelVisitorsViewBinding binding;

    static class TripTypeComparator implements Comparator<TripType> {
        TripTypeComparator() {
        }

        public int compare(TripType t1, TripType t2) {
            if (t1.getPercentage() == t2.getPercentage()) {
                return CompareUtils.compare(typeToInt(t1.getType()), typeToInt(t2.getType()));
            }
            return CompareUtils.compare(t2.getPercentage(), t1.getPercentage());
        }

        private int typeToInt(String type) {
            int i = -1;
            switch (type.hashCode()) {
                case -1354573888:
                    if (type.equals(TripTypeSplit.COUPLE)) {
                        i = 2;
                        break;
                    }
                    break;
                case -1281860764:
                    if (type.equals(TripTypeSplit.FAMILY)) {
                        i = 3;
                        break;
                    }
                    break;
                case -1146830912:
                    if (type.equals(Poi.CATEGORY_BUSINESS)) {
                        i = 1;
                        break;
                    }
                    break;
                case 3536095:
                    if (type.equals(TripTypeSplit.SOLO)) {
                        i = 0;
                        break;
                    }
                    break;
            }
            switch (i) {
                case C1822R.styleable.SignInButton_buttonSize /*0*/:
                    return 0;
                case C1822R.styleable.SignInButton_colorScheme /*1*/:
                    return 1;
                case C1822R.styleable.SignInButton_scopeUris /*2*/:
                    return 2;
                case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                    return 3;
                default:
                    return 4;
            }
        }
    }

    public HotelVisitorsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            this.binding = (HotelVisitorsViewBinding) DataBindingUtil.bind(this);
        }
    }

    public void bindTo(@NonNull TrustYouData data) {
        List<TripType> tripTypes = tripTypesWithNonZeroPercent(data.getTripTypeSplit());
        if (tripTypes.size() < 2) {
            setVisibility(8);
            return;
        }
        showTripTypes(tripTypes);
        showLanguagePercent(data.getLanguageSplit());
    }

    @NonNull
    private List<TripType> tripTypesWithNonZeroPercent(@Nullable List<TripType> tripTypes) {
        List<TripType> items = new LinkedList();
        if (CollectionUtils.isNotEmpty(tripTypes)) {
            for (TripType tripType : tripTypes) {
                if (tripType.getPercentage() != 0) {
                    items.add(tripType);
                }
            }
        }
        return items;
    }

    private void showTripTypes(@NonNull List<TripType> tripTypes) {
        Collections.sort(tripTypes, new TripTypeComparator());
        for (TripType tripType : tripTypes) {
            View itemView = HotelVisitorItemView.create(this.binding.visitors);
            itemView.bindTo(tripType);
            this.binding.visitors.add(itemView);
        }
    }

    private void showLanguagePercent(@Nullable List<LanguageData> languages) {
        String userLanguage = AndroidUtils.getLanguage();
        if (!userLanguage.equals(Language.ENGLISH)) {
            int percent = percentReviewsInUserLanguage(languages, userLanguage);
            if (percent != 0 && percent != 100) {
                HotelVisitorItemView itemView = HotelVisitorItemView.create(this.binding.visitors);
                itemView.bindTo(percent, userLanguage);
                this.binding.visitors.add(itemView, 2);
            }
        }
    }

    private int percentReviewsInUserLanguage(@Nullable List<LanguageData> languages, @NonNull String userLanguage) {
        if (!CollectionUtils.isNotEmpty(languages)) {
            return 0;
        }
        for (LanguageData language : languages) {
            if (userLanguage.equals(language.getName())) {
                return language.getPercentage();
            }
        }
        return 0;
    }
}
