package com.hotellook.ui.screen.filters.pois.listitems;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.db.data.PoiHistoryItem;
import com.hotellook.events.DistanceTargetSelectedEvent;
import com.hotellook.filters.distancetarget.PoiHistoryDistanceTarget;
import com.hotellook.ui.screen.filters.pois.adapter.ListItemBinder;

public class PoiHistoryItemBinder implements ListItemBinder {
    private final PoiHistoryItem poiHistoryItem;

    /* renamed from: com.hotellook.ui.screen.filters.pois.listitems.PoiHistoryItemBinder.1 */
    class C12801 extends MonkeySafeClickListener {
        C12801() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new DistanceTargetSelectedEvent(new PoiHistoryDistanceTarget(PoiHistoryItemBinder.this.poiHistoryItem)));
        }
    }

    public PoiHistoryItemBinder(PoiHistoryItem poiHistoryItem) {
        this.poiHistoryItem = poiHistoryItem;
    }

    public int getType() {
        return 1;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        SimpleItemHolder h = (SimpleItemHolder) holder;
        h.clickable.setOnClickListener(new C12801());
        h.title.setText(this.poiHistoryItem.getName());
    }
}
