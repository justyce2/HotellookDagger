package com.hotellook.statistics.mixpanel;

import com.hotellook.C1178R;
import com.hotellook.filters.distancetarget.DistanceTarget;
import com.hotellook.filters.distancetarget.DistanceTarget.DestinationType;
import com.hotellook.statistics.Constants.MixPanelParams;
import pl.charmas.android.reactivelocation.C1822R;

public class SortingTypeConverter {
    private final int algoId;
    private final DistanceTarget distanceTarget;

    /* renamed from: com.hotellook.statistics.mixpanel.SortingTypeConverter.1 */
    static /* synthetic */ class C12071 {
        static final /* synthetic */ int[] f727x723e49cb;

        static {
            f727x723e49cb = new int[DestinationType.values().length];
            try {
                f727x723e49cb[DestinationType.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f727x723e49cb[DestinationType.USER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f727x723e49cb[DestinationType.MAP_POINT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f727x723e49cb[DestinationType.SIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f727x723e49cb[DestinationType.SEASON.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public SortingTypeConverter(int algoId, DistanceTarget distanceTarget) {
        this.algoId = algoId;
        this.distanceTarget = distanceTarget;
    }

    public String convert() {
        String sortType = SortingChangedParams.UNKNOWN;
        switch (this.algoId) {
            case C1178R.id.sorting_popularity /*2131689811*/:
                return MixPanelParams.POPULARITY;
            case C1178R.id.sorting_ratings /*2131689812*/:
                return MixPanelParams.MARKS;
            case C1178R.id.sorting_price /*2131689813*/:
                return MixPanelParams.PRICE;
            case C1178R.id.sorting_distance /*2131689814*/:
                switch (C12071.f727x723e49cb[this.distanceTarget.getType().ordinal()]) {
                    case C1822R.styleable.SignInButton_colorScheme /*1*/:
                        return MixPanelParams.DISTANCE_TO_CENTRE;
                    case C1822R.styleable.SignInButton_scopeUris /*2*/:
                        return MixPanelParams.DISTANCE_TO_ME;
                    case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                        return MixPanelParams.DISTANCE_TO_LOCATION;
                    case C1822R.styleable.MapAttrs_cameraTilt /*4*/:
                    case C1822R.styleable.MapAttrs_cameraZoom /*5*/:
                        return MixPanelParams.DISTANCE_TO_POI;
                    default:
                        return sortType;
                }
            case C1178R.id.sorting_discount /*2131689815*/:
                return MixPanelParams.DISCOUNT;
            default:
                return sortType;
        }
    }
}
