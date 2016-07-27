package com.hotellook.ui.screen.hotel.reviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hotellook.C1178R;
import com.hotellook.core.api.pojo.hoteldetail.Highlight;

public class HotelReviewsHighlightsQuoteView extends LinearLayout {
    @BindView
    TextView quoteView;

    @NonNull
    public static HotelReviewsHighlightsQuoteView create(@NonNull ViewGroup parent) {
        return (HotelReviewsHighlightsQuoteView) LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.hotel_reviews_highlights_quote_view, parent, false);
    }

    public HotelReviewsHighlightsQuoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void bindTo(@NonNull Highlight highlight) {
        this.quoteView.setText(highlight.getText());
    }
}
