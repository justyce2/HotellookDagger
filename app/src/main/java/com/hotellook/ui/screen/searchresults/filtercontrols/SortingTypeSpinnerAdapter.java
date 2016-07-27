package com.hotellook.ui.screen.searchresults.filtercontrols;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.DestinationInSortingSpinnerClickedEvent;
import java.util.List;

public class SortingTypeSpinnerAdapter extends ArrayAdapter<SortingSpinnerItem> {
    private int selectedPosition;

    /* renamed from: com.hotellook.ui.screen.searchresults.filtercontrols.SortingTypeSpinnerAdapter.1 */
    class C13831 extends MonkeySafeClickListener {
        C13831() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.from(SortingTypeSpinnerAdapter.this.getContext()).getComponent().eventBus().post(new DestinationInSortingSpinnerClickedEvent());
        }
    }

    public SortingTypeSpinnerAdapter(Context context, List<SortingSpinnerItem> data) {
        super(context, C1178R.layout.spinner_item, C1178R.id.item_title, data);
        setDropDownViewResource(C1178R.layout.layout_sorting_radio_btn);
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        boolean z = false;
        SortingSpinnerItem item = (SortingSpinnerItem) getItem(position);
        View view = super.getDropDownView(position, convertView, parent);
        TextView distanceTo = (TextView) view.findViewById(C1178R.id.distance_to);
        if (item.id == C1178R.id.sorting_distance && (item instanceof DistanceSortingSpinnerItem)) {
            distanceTo.setVisibility(0);
            distanceTo.setText(((DistanceSortingSpinnerItem) item).destination);
            distanceTo.setOnClickListener(new C13831());
        } else {
            distanceTo.setVisibility(8);
        }
        RadioButton radioBtn = (RadioButton) view.findViewById(C1178R.id.radio_btn);
        if (position == this.selectedPosition) {
            z = true;
        }
        radioBtn.setChecked(z);
        return view;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        SortingSpinnerItem item = (SortingSpinnerItem) getItem(position);
        if (item.id == C1178R.id.sorting_distance && (item instanceof DistanceSortingSpinnerItem)) {
            ((TextView) view.findViewById(C1178R.id.item_title)).setText(view.getResources().getString(C1178R.string.distance_from_object, new Object[]{((DistanceSortingSpinnerItem) item).destination}));
        }
        return view;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
}
