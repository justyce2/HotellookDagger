package com.hotellook.api;

import android.net.Uri;
import android.net.Uri.Builder;
import com.hotellook.HotellookApplication;
import com.hotellook.api.data.ShortUrlData;
import com.hotellook.events.HotelLinkGeneratedEvent;
import com.hotellook.rx.SubscriberAdapter;
import com.hotellook.search.KidsSerializer;
import com.hotellook.search.SearchParams;
import com.hotellook.search.ServerDateFormatter;
import com.hotellook.utils.AndroidUtils;
import java.util.concurrent.TimeUnit;
import me.zhanghai.android.materialprogressbar.BuildConfig;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HotelLinkGenerator {
    private static final String ENDPOINT = "search.hotellook.com";
    private static final String HLS = "mobilesharing";
    private static final String PARAM_ADULTS = "adults";
    private static final String PARAM_CHECK_IN = "checkIn";
    private static final String PARAM_CHECK_OUT = "checkOut";
    private static final String PARAM_CHILDREN = "children";
    private static final String PARAM_CURRENCY = "currency";
    private static final String PARAM_HLS = "hls";
    private static final String PARAM_HOTEL_ID = "hotelId";
    private static final String PARAM_LANGUAGE = "language";
    private static final String PARAM_UTM_CAMPAIGN = "utm_campaign";
    private static final String PARAM_UTM_MEDIUM = "utm_medium";
    private static final String PARAM_UTM_SOURCE = "utm_source";
    private static final String SCHEME = "http";
    private static final String SHORTEN_SERVICE_ACTION_TO_SHORTEN = "shorturl";
    private static final String SHORTEN_SERVICE_FORMAT_JSON = "json";
    private static final String SHORTEN_SERVICE_PASSWORD = "EPhliOk7";
    private static final String SHORTEN_SERVICE_USERNAME = "sapato";
    private static final int SHORTEN_URL_REQUEST_TIMEOUT_IN_SECONDS = 10;
    private static final String UTM_CAMPAIGN = "mobilesharing";
    private static final String UTM_MEDIUM_PHONE = "androidphone";
    private static final String UTM_MEDIUM_TABLET = "androidtablet";
    private static final String UTM_SOURCE = "mobile";
    private final long hotelId;
    private final boolean isTablet;
    private final SearchParams searchParams;

    /* renamed from: com.hotellook.api.HotelLinkGenerator.1 */
    class C11791 extends SubscriberAdapter<ShortUrlData> {
        C11791() {
        }

        public void onError(Throwable e) {
            HotellookApplication.eventBus().post(new HotelLinkGeneratedEvent(HotelLinkGenerator.this.generateHotelLink().toString(), HotelLinkGenerator.this.hotelId));
        }

        public void onNext(ShortUrlData shortUrlData) {
            HotellookApplication.eventBus().post(new HotelLinkGeneratedEvent(shortUrlData.shorturl, HotelLinkGenerator.this.hotelId));
        }
    }

    public HotelLinkGenerator(SearchParams searchParams, long hotelId, boolean isTablet) {
        this.searchParams = searchParams;
        this.hotelId = hotelId;
        this.isTablet = isTablet;
    }

    public void postShortUrlRequest() {
        createShortUrlObserver().timeout(10, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new C11791());
    }

    private Observable<ShortUrlData> createShortUrlObserver() {
        return HotellookApplication.getApp().getComponent().getShortUrlService().observeShortUrl(SHORTEN_SERVICE_USERNAME, SHORTEN_SERVICE_PASSWORD, SHORTEN_SERVICE_ACTION_TO_SHORTEN, SHORTEN_SERVICE_FORMAT_JSON, generateHotelLink().toString());
    }

    public Uri generateHotelLink() {
        Builder builder = new Builder();
        builder.scheme(SCHEME).authority(ENDPOINT).appendPath(BuildConfig.FLAVOR).appendQueryParameter(PARAM_HOTEL_ID, String.valueOf(this.hotelId)).appendQueryParameter(PARAM_LANGUAGE, AndroidUtils.getLanguage());
        if (this.searchParams != null) {
            ServerDateFormatter serverDateFormatter = new ServerDateFormatter();
            builder.appendQueryParameter(PARAM_CHECK_IN, serverDateFormatter.format(this.searchParams.checkOut())).appendQueryParameter(PARAM_CHECK_OUT, serverDateFormatter.format(this.searchParams.checkOut())).appendQueryParameter(PARAM_ADULTS, String.valueOf(this.searchParams.adults())).appendQueryParameter(PARAM_CHILDREN, KidsSerializer.toString(this.searchParams.kids())).appendQueryParameter(PARAM_CURRENCY, this.searchParams.currency());
        }
        builder.appendQueryParameter(PARAM_UTM_SOURCE, UTM_SOURCE).appendQueryParameter(PARAM_UTM_MEDIUM, this.isTablet ? UTM_MEDIUM_TABLET : UTM_MEDIUM_PHONE).appendQueryParameter(PARAM_UTM_CAMPAIGN, UTM_CAMPAIGN).appendQueryParameter(PARAM_HLS, UTM_CAMPAIGN);
        return builder.build();
    }
}
