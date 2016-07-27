package com.hotellook.ui.screen.filters.radiogroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.OpenDistanceSelectorEvent;
import com.hotellook.filters.items.SortingItem;

public class DistanceSortingFilterRadioButton extends FiltersRadioButton {
    private SortingItem distanceFilterItem;
    private TextView distanceTargetTitle;

    /* renamed from: com.hotellook.ui.screen.filters.radiogroup.DistanceSortingFilterRadioButton.1 */
    class C12871 extends MonkeySafeClickListener {
        C12871() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new OpenDistanceSelectorEvent(0));
        }
    }

    public DistanceSortingFilterRadioButton(Context context) {
        super(context);
    }

    public DistanceSortingFilterRadioButton(Context context, int layoutId) {
        super(context, layoutId);
    }

    public DistanceSortingFilterRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DistanceSortingFilterRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        this.distanceFilterItem = HotellookApplication.from(context).getComponent().searchKeeper().lastSearchOrThrowException().filters().getSortingCategory().getSortingItem();
        this.distanceTargetTitle = (TextView) findViewById(C1178R.id.distance_to);
        this.distanceTargetTitle.setOnClickListener(new C12871());
        onFiltersUpdated();
    }

    public void onFiltersUpdated() {
        this.distanceTargetTitle.setText(this.distanceFilterItem.getDistanceTarget().getTitle());
    }
}
