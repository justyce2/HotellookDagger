package com.hotellook.ui.screen.hotel.reviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hotellook.core.api.pojo.hoteldetail.ScoreData;
import com.hotellook.utils.CollectionUtils;
import java.util.List;

public class HotelReviewsHighlightsView extends LinearLayout {
    @BindView
    ViewGroup reviewsHighlightsLayout;

    public HotelReviewsHighlightsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void bindTo(@Nullable List<ScoreData> sentimentScoreList) {
        if (CollectionUtils.isNotEmpty(sentimentScoreList)) {
            for (ScoreData scoreData : sentimentScoreList) {
                addReviewsHighlightsItem(scoreData);
            }
        }
        if (this.reviewsHighlightsLayout.getChildCount() == 0) {
            setVisibility(8);
        }
    }

    private void addReviewsHighlightsItem(@NonNull ScoreData scoreData) {
        if (!CollectionUtils.isEmpty(scoreData.getHighlights())) {
            HotelReviewsHighlightsItemView itemView = HotelReviewsHighlightsItemView.create(this.reviewsHighlightsLayout);
            itemView.bindTo(scoreData);
            this.reviewsHighlightsLayout.addView(itemView);
        }
    }
}
