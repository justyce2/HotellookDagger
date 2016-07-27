package com.hotellook.ui.screen.hotel.reviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hotellook.C1178R;
import com.hotellook.core.api.pojo.hoteldetail.Highlight;
import com.hotellook.core.api.pojo.hoteldetail.ScoreData;
import com.hotellook.core.api.pojo.hoteldetail.SummarySentence;
import com.hotellook.ui.screen.hotel.ratingcolorizer.RatingColorizer;
import com.hotellook.ui.screen.hotel.ratingcolorizer.TextColorApplier;
import com.hotellook.ui.view.TwoColumnsLayout;
import com.hotellook.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.List;

public class HotelReviewsHighlightsItemView extends LinearLayout {
    @BindView
    TwoColumnsLayout quotesLayout;
    @BindView
    TextView titleView;

    @NonNull
    public static HotelReviewsHighlightsItemView create(@NonNull ViewGroup parent) {
        return (HotelReviewsHighlightsItemView) LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.hotel_reviews_highlights_item_view, parent, false);
    }

    public HotelReviewsHighlightsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void bindTo(@NonNull ScoreData scoreData) {
        setUpTitle(scoreData);
        setUpQuotes(scoreData.getHighlights());
    }

    private void setUpTitle(@NonNull ScoreData scoreData) {
        this.titleView.setText(CollectionUtils.isNotEmpty(scoreData.getSummarySentences()) ? buildTitle(scoreData.getSummarySentences()) : scoreData.getShortText());
        new RatingColorizer(new TextColorApplier(), getResources()).apply(this.titleView, scoreData.getScore());
    }

    private void setUpQuotes(@NonNull List<Highlight> highlights) {
        List<View> views = new ArrayList(highlights.size());
        for (Highlight highlight : highlights) {
            HotelReviewsHighlightsQuoteView quoteView = HotelReviewsHighlightsQuoteView.create(this.quotesLayout);
            quoteView.bindTo(highlight);
            views.add(quoteView);
        }
        this.quotesLayout.addAdaptively(views);
    }

    @NonNull
    private String buildTitle(@NonNull List<SummarySentence> summarySentences) {
        StringBuilder builder = new StringBuilder();
        for (SummarySentence sentence : summarySentences) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(sentence.getText());
        }
        return builder.toString();
    }
}
