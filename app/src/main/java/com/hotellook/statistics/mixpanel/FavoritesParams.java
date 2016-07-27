package com.hotellook.statistics.mixpanel;

import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.ui.screen.hotel.Source;
import java.util.HashMap;
import java.util.Map;
import pl.charmas.android.reactivelocation.C1822R;

public class FavoritesParams implements MixPanelParamsBuilder {
    private final Source source;

    /* renamed from: com.hotellook.statistics.mixpanel.FavoritesParams.1 */
    static /* synthetic */ class C12061 {
        static final /* synthetic */ int[] $SwitchMap$com$hotellook$ui$screen$hotel$Source;

        static {
            $SwitchMap$com$hotellook$ui$screen$hotel$Source = new int[Source.values().length];
            try {
                $SwitchMap$com$hotellook$ui$screen$hotel$Source[Source.FAVORITES.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hotellook$ui$screen$hotel$Source[Source.FAVORITES_SEARCH_RESULTS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hotellook$ui$screen$hotel$Source[Source.CITY_SEARCH_RESULTS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$hotellook$ui$screen$hotel$Source[Source.BEST_HOTELS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hotellook$ui$screen$hotel$Source[Source.CITY_SEARCH_MAP.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hotellook$ui$screen$hotel$Source[Source.HOTEL_SEARCH.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$hotellook$ui$screen$hotel$Source[Source.INTENT.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public FavoritesParams(Source source) {
        this.source = source;
    }

    public Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.HOTEL_LIKED_REFERRER, getReferrer());
        return params;
    }

    private String getReferrer() {
        switch (C12061.$SwitchMap$com$hotellook$ui$screen$hotel$Source[this.source.ordinal()]) {
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
                return MixPanelParams.HOTEL;
            case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
            case C1822R.styleable.MapAttrs_cameraTilt /*4*/:
            case C1822R.styleable.MapAttrs_cameraZoom /*5*/:
            case C1822R.styleable.MapAttrs_liteMode /*6*/:
            case C1822R.styleable.MapAttrs_uiCompass /*7*/:
                return MixPanelParams.RESULTS;
            default:
                return SortingChangedParams.UNKNOWN;
        }
    }
}
