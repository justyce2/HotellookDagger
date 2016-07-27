package com.hotellook.ui.screen.filters.presenters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.filters.items.StarsFilterItem;
import com.hotellook.utils.ViewUtils;

public class StarsFilterPresenter extends BoolFilterPresenter {
    private final StarsFilterItem filterItem;
    private RatingBar ratingBar;
    private RatingBar ratingBarDisabled;

    public StarsFilterPresenter(StarsFilterItem filterItem) {
        super(filterItem);
        this.filterItem = filterItem;
    }

    public View createView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(C1178R.layout.filter_stars, parent, false);
        int stars = this.filterItem.getStars();
        this.ratingBar = (RatingBar) view.findViewById(C1178R.id.rating);
        this.ratingBar.setMax(5);
        this.ratingBar.setRating((float) stars);
        this.ratingBarDisabled = (RatingBar) view.findViewById(C1178R.id.rating_disabled);
        this.ratingBarDisabled.setMax(5);
        this.ratingBarDisabled.setRating((float) stars);
        setUpRatingBar(false);
        ((TextView) view.findViewById(C1178R.id.count)).setText(String.valueOf(this.filterItem.getCount()));
        return view;
    }

    public void onClick(View view) {
        super.onClick(view);
        setUpRatingBar(true);
    }

    public void onFiltersUpdated() {
        super.onFiltersUpdated();
        setUpRatingBar(true);
    }

    private void setUpRatingBar(boolean animate) {
        int i = 8;
        boolean isChecked = this.filterItem.isChecked();
        if (!animate) {
            this.ratingBar.setVisibility(isChecked ? 0 : 8);
        } else if (isChecked) {
            ViewUtils.showView(this.ratingBar);
        } else {
            ViewUtils.goneView(this.ratingBar);
        }
        if (!animate) {
            RatingBar ratingBar = this.ratingBarDisabled;
            if (!isChecked) {
                i = 0;
            }
            ratingBar.setVisibility(i);
        } else if (isChecked) {
            ViewUtils.goneView(this.ratingBarDisabled);
        } else {
            ViewUtils.showView(this.ratingBarDisabled);
        }
    }
}
