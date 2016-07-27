package com.hotellook.ui.screen.searchresults.map;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.events.MapPinOpenedEvent;
import com.hotellook.search.Discounts;
import com.hotellook.search.Highlights;
import com.hotellook.search.SearchParams;
import com.hotellook.ui.screen.searchresults.map.clustering.HotelCluster;
import com.hotellook.utils.Size;
import java.util.List;
import java.util.Map;

public class ResultsMapOverlay extends FrameLayout {
    public static final String PROPERTY_NAME_HEIGHT = "height";
    public static final String PROPERTY_NAME_WIDTH = "width";
    private boolean animating;
    private ValueAnimator animator;
    private HotelMapCardView hotelCard;
    private Point markerLocation;
    private Size markerSize;
    private Marker revealedMarker;
    private SearchParams searchParams;

    public interface MarkerAnimationListener {
        void onAnimationFinished();
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.map.ResultsMapOverlay.1 */
    class C13871 implements AnimatorUpdateListener {
        C13871() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            int newWidth = ((Integer) animation.getAnimatedValue(ResultsMapOverlay.PROPERTY_NAME_WIDTH)).intValue();
            int newHeight = ((Integer) animation.getAnimatedValue(ResultsMapOverlay.PROPERTY_NAME_HEIGHT)).intValue();
            ResultsMapOverlay.this.hotelCard.getLayoutParams().width = newWidth;
            ResultsMapOverlay.this.hotelCard.getLayoutParams().height = newHeight;
            ResultsMapOverlay.this.hotelCard.setX((float) (ResultsMapOverlay.this.markerLocation.x - (newWidth / 2)));
            ResultsMapOverlay.this.hotelCard.setY((float) (ResultsMapOverlay.this.markerLocation.y - newHeight));
            ResultsMapOverlay.this.hotelCard.requestLayout();
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.map.ResultsMapOverlay.2 */
    class C13882 extends AnimatorListenerAdapter {
        C13882() {
        }

        public void onAnimationStart(Animator animation) {
            ResultsMapOverlay.this.animating = true;
        }

        public void onAnimationEnd(Animator animation) {
            ResultsMapOverlay.this.animating = false;
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.map.ResultsMapOverlay.3 */
    class C13913 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$collapseToInfinity;
        final /* synthetic */ MarkerAnimationListener val$listener;

        /* renamed from: com.hotellook.ui.screen.searchresults.map.ResultsMapOverlay.3.1 */
        class C13901 implements Runnable {

            /* renamed from: com.hotellook.ui.screen.searchresults.map.ResultsMapOverlay.3.1.1 */
            class C13891 extends AnimatorListenerAdapter {
                C13891() {
                }

                public void onAnimationEnd(Animator animation) {
                    ResultsMapOverlay.this.removeView(ResultsMapOverlay.this.hotelCard);
                    C13913.this.val$listener.onAnimationFinished();
                }
            }

            C13901() {
            }

            public void run() {
                ResultsMapOverlay.this.hotelCard.animate().alpha(0.0f).setDuration(100).setListener(new C13891());
            }
        }

        C13913(boolean z, MarkerAnimationListener markerAnimationListener) {
            this.val$collapseToInfinity = z;
            this.val$listener = markerAnimationListener;
        }

        public void onAnimationEnd(Animator animation) {
            ResultsMapOverlay.this.revealedMarker.setAlpha(1.0f);
            if (!this.val$collapseToInfinity) {
                ResultsMapOverlay.this.postDelayed(new C13901(), 16);
            }
        }
    }

    public ResultsMapOverlay(Context context) {
        super(context);
        this.animating = false;
    }

    public ResultsMapOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.animating = false;
    }

    public void revealMarker(GoogleMap googleMap, Map<Long, List<Offer>> searchResults, Marker marker, HotelCluster cluster, String currency, Discounts discounts, Highlights highlights) {
        this.revealedMarker = marker;
        this.markerLocation = googleMap.getProjection().toScreenLocation(cluster.getPosition());
        this.hotelCard = (HotelMapCardView) LayoutInflater.from(getContext()).inflate(C1178R.layout.hotel_map_card, this, false);
        this.hotelCard.setData(cluster, searchResults, currency, discounts, highlights);
        this.markerSize = getMarkerSize(cluster, searchResults);
        addView(this.hotelCard, new LayoutParams(this.markerSize.getWidth(), this.markerSize.getHeight()));
        this.hotelCard.setX((float) (this.markerLocation.x - (this.markerSize.getWidth() / 2)));
        this.hotelCard.setY((float) (this.markerLocation.y - this.markerSize.getHeight()));
        getHotelCardAnimator(this.hotelCard.getLayoutParams().width, this.hotelCard.getRevealedWidth(), this.hotelCard.getLayoutParams().height, this.hotelCard.getRevealedHeight()).start();
        this.hotelCard.getContent().setAlpha(0.0f);
        this.hotelCard.getContent().animate().alpha(1.0f).setDuration(300).setStartDelay(100);
        marker.setAlpha(0.0f);
        HotellookApplication.eventBus().post(new MapPinOpenedEvent());
    }

    @NonNull
    private ValueAnimator getHotelCardAnimator(int width, int newWidth, int height, int newHeight) {
        PropertyValuesHolder pvhWidth = PropertyValuesHolder.ofInt(PROPERTY_NAME_WIDTH, new int[]{width, newWidth});
        PropertyValuesHolder pvhHeight = PropertyValuesHolder.ofInt(PROPERTY_NAME_HEIGHT, new int[]{height, newHeight});
        this.animator = ValueAnimator.ofPropertyValuesHolder(new PropertyValuesHolder[]{pvhWidth, pvhHeight});
        this.animator.addUpdateListener(new C13871());
        this.animator.addListener(new C13882());
        return this.animator;
    }

    @NonNull
    private Size getMarkerSize(HotelCluster cluster, Map<Long, List<Offer>> results) {
        ClusterView marker = (ClusterView) LayoutInflater.from(getContext()).inflate(C1178R.layout.results_map_marker, null);
        marker.setCurrency(this.searchParams.currency());
        marker.setHotel((HotelData) cluster.getHotels().get(0), cluster.findClusterOffers(results));
        marker.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        return new Size(marker.getMeasuredWidth(), marker.getMeasuredHeight());
    }

    public void collapseMarker(MarkerAnimationListener listener, boolean collapseToInfinity) {
        int i = 0;
        if (this.revealedMarker != null) {
            int i2 = this.hotelCard.getLayoutParams().width;
            int width = collapseToInfinity ? 0 : this.markerSize.getWidth();
            int i3 = this.hotelCard.getLayoutParams().height;
            if (!collapseToInfinity) {
                i = this.markerSize.getHeight();
            }
            this.animator = getHotelCardAnimator(i2, width, i3, i);
            this.animator.addListener(new C13913(collapseToInfinity, listener));
            this.animator.start();
            this.hotelCard.getContent().animate().alpha(0.0f).setDuration(150).setStartDelay(0);
        }
    }

    public boolean isAnimating() {
        return this.animating;
    }

    public Marker getRevealedMarker() {
        return this.revealedMarker;
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isAnimating()) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void clean() {
        if (this.animator != null && this.animator.isRunning()) {
            this.animator.cancel();
        }
        removeAllViews();
    }

    public void setSearchParams(SearchParams searchParams) {
        this.searchParams = searchParams;
    }
}
