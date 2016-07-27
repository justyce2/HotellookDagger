package com.hotellook.ui.screen.hotel.api;

import android.app.Application;
import com.hotellook.BuildConfig;
import com.hotellook.HotellookApplication;
import com.hotellook.api.RequestFlags;
import com.hotellook.api.RequestFlagsGenerator;
import com.hotellook.badges.Badge;
import com.hotellook.booking.SearchInfoForBooking;
import com.hotellook.core.api.HotellookService;
import com.hotellook.core.api.params.DeepLinkParamsBuilder;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.KidsSerializer;
import com.hotellook.search.SearchParams;
import com.hotellook.search.ServerDateFormatter;
import com.hotellook.ui.screen.hotel.Source;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.NetworkUtil;
import java.util.List;
import rx.Observable;
import timber.log.Timber;

public class BookingSource {
    private final HotellookService api;
    private final String host;
    private final String token;

    public BookingSource(HotellookService api, String token, Application app) {
        this.api = api;
        this.token = token;
        this.host = HotellookApplication.from(app).getHost();
    }

    public Observable<Booking> observe(SearchParams searchParams, RequestFlags searchRequestFlags, long hotelId, int locationId, Offer offer, Source source, List<Badge> badges, SearchInfoForBooking searchInfoForBooking) {
        RequestFlags bookingRequestFlags = new RequestFlagsGenerator().bookingFromHotelScreen(searchParams, searchRequestFlags.getSource(), source, badges);
        ServerDateFormatter dateFormatter = new ServerDateFormatter();
        return this.api.deeplink(new DeepLinkParamsBuilder().locationId(locationId).checkIn(dateFormatter.format(searchParams.checkIn())).checkOut(dateFormatter.format(searchParams.checkOut())).adults(searchParams.adults()).children(KidsSerializer.toString(searchParams.kids())).mobileToken(this.token).marker(BuildConfig.GOOGLE_PLAY_BUILD.booleanValue() ? bookingRequestFlags.getMarker() : BuildConfig.STORE_MARKER).currency(searchParams.currency()).language(AndroidUtils.getLanguage()).hotelId(hotelId).gateId(offer.getGateId()).roomId(offer.getRoomId()).host(this.host).flagSource(bookingRequestFlags.getSource()).flagSection(bookingRequestFlags.getSection()).flagFunc(bookingRequestFlags.getFunc()).flagBadge(bookingRequestFlags.getBadge()).hls(bookingRequestFlags.getHls()).utmDetail(bookingRequestFlags.getUtmDetail()).locationIds(searchInfoForBooking.locations()).searchUuid(searchInfoForBooking.searchId()).build()).map(BookingSource$$Lambda$1.lambdaFactory$(offer, searchParams, hotelId)).doOnNext(BookingSource$$Lambda$2.lambdaFactory$(this)).doOnError(BookingSource$$Lambda$3.lambdaFactory$(this, hotelId, offer)).doOnSubscribe(BookingSource$$Lambda$4.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$observe$1(long hotelId, Offer offer, Throwable error) {
        logError(error, hotelId, offer.getGateId(), offer.getRoomId());
    }

    /* synthetic */ void lambda$observe$2() {
        logStart();
    }

    private void logLoadedResult(Booking booking) {
        Timber.m755i("Deeplink loaded: %s", booking.deeplink());
    }

    private void logError(Throwable error, long hotelId, int gateId, int roomId) {
        if (NetworkUtil.isRetrofitNetworkError(error)) {
            Timber.m756w("Connection error while loading deeplink: %s", error.getMessage());
            return;
        }
        Timber.m754e(error, "Error while loading deeplink for hotel id: %s, gate id: %s, room id: %s", Long.valueOf(hotelId), Integer.valueOf(gateId), Integer.valueOf(roomId));
    }

    private void logStart() {
        Timber.m755i("Start loading deeplink", new Object[0]);
    }
}
