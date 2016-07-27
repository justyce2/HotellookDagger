package com.hotellook.ui.screen.hotel.rating;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.backstack.Savable;
import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.screen.hotel.HotelInfoLoadableLayout;
import com.hotellook.ui.screen.hotel.HotelInfoLoadableLayout.LCE;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.ui.screen.hotel.reviews.HotelReviewsHighlightsView;
import com.hotellook.ui.screen.hotel.reviews.HotelReviewsView;
import com.hotellook.ui.screen.hotel.usefulInfo.HotelUsefulInfoView;
import com.hotellook.ui.screen.hotel.visitors.HotelVisitorsView;
import com.hotellook.utils.AndroidUtils;
import pl.charmas.android.reactivelocation.C1822R;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HotelRatingsFragment extends BaseFragment implements Savable {
    private View contentView;
    private HotelDataSource hotelDataSource;
    private HotelInfoLoadableLayout loadableLayout;
    private View loadingView;
    private HotelOverallRatingView overallRatingView;
    private HotelRatingsView ratingsView;
    private HotelReviewsHighlightsView reviewsHighlightsView;
    private HotelReviewsView reviewsView;
    private HotelUsefulInfoView usefulInfoView;
    private HotelVisitorsView visitorsView;

    /* renamed from: com.hotellook.ui.screen.hotel.rating.HotelRatingsFragment.1 */
    static /* synthetic */ class C13361 {
        static final /* synthetic */ int[] f732x491420ea;

        static {
            f732x491420ea = new int[LCE.values().length];
            try {
                f732x491420ea[LCE.CONTENT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    static class Snapshot {
        final HotelDataSource hotelDataSource;

        Snapshot(HotelDataSource hotelDataSource) {
            this.hotelDataSource = hotelDataSource;
        }
    }

    public static Fragment create(HotelDataSource hotelDataSource) {
        HotelRatingsFragment fragment = new HotelRatingsFragment();
        fragment.setHotelDataSource(hotelDataSource);
        return fragment;
    }

    private void setHotelDataSource(HotelDataSource hotelDataSource) {
        this.hotelDataSource = hotelDataSource;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1178R.layout.fragment_hotel_ratings, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.loadingView = view.findViewById(C1178R.id.loading);
        this.loadableLayout = (HotelInfoLoadableLayout) view.findViewById(C1178R.id.loadable_layout);
        this.contentView = view.findViewById(C1178R.id.content);
        this.ratingsView = (HotelRatingsView) view.findViewById(C1178R.id.ratings);
        this.reviewsView = (HotelReviewsView) view.findViewById(C1178R.id.reviews);
        this.visitorsView = (HotelVisitorsView) view.findViewById(C1178R.id.visitors);
        this.usefulInfoView = (HotelUsefulInfoView) view.findViewById(C1178R.id.useful_info);
        this.overallRatingView = (HotelOverallRatingView) view.findViewById(C1178R.id.overall_rating);
        this.reviewsHighlightsView = (HotelReviewsHighlightsView) view.findViewById(C1178R.id.reviews_highlights);
        adjustLayoutOffsets();
        subscribeToLoadableLayoutStates();
        HotellookApplication.eventBus().register(this);
    }

    @SuppressLint({"RxSubscribeOnError"})
    private void subscribeToLoadableLayoutStates() {
        keepUntilDestroyView(this.loadableLayout.observeState(this.hotelDataSource).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(HotelRatingsFragment$$Lambda$1.lambdaFactory$(this)));
    }

    /* synthetic */ void lambda$subscribeToLoadableLayoutStates$0(LCE lce) {
        switch (C13361.f732x491420ea[lce.ordinal()]) {
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                setUpContent();
                onShowContent();
            default:
        }
    }

    public void onDestroyView() {
        HotellookApplication.eventBus().unregister(this);
        super.onDestroyView();
    }

    private void adjustLayoutOffsets() {
        AndroidUtils.addBottomPadding(this.contentView, getStandardOffset());
    }

    private void onShowContent() {
        this.loadingView.setVisibility(8);
        this.contentView.setVisibility(0);
    }

    private void setUpContent() {
        HotelDetailData data = this.hotelDataSource.detailHotelData();
        if (data != null) {
            this.overallRatingView.bindTo(data.getTrustyou());
            this.ratingsView.bindTo(data.getTrustyou().getSentimentScoreList());
            this.usefulInfoView.bindTo(data.getTrustyou().getGoodToKnow());
            this.visitorsView.bindTo(data.getTrustyou());
            this.reviewsHighlightsView.bindTo(data.getTrustyou().getSentimentScoreList());
            this.reviewsView.bindTo(data.getFoursquare());
        }
    }

    @NonNull
    public Object saveState() {
        return new Snapshot(this.hotelDataSource);
    }

    public void restoreState(@NonNull Object state) {
        this.hotelDataSource = ((Snapshot) state).hotelDataSource;
    }
}
