package com.hotellook.ui.screen.filters.pois.listitems;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.events.DistanceTargetSelectedEvent;
import com.hotellook.ui.screen.filters.pois.adapter.ListItemBinder;

public class PoiListItemBinder implements ListItemBinder {
    private final int cityId;
    private final Poi poi;

    /* renamed from: com.hotellook.ui.screen.filters.pois.listitems.PoiListItemBinder.1 */
    class C12811 extends MonkeySafeClickListener {
        C12811() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new DistanceTargetSelectedEvent(PoiListItemBinder.this.cityId, PoiListItemBinder.this.poi));
        }
    }

    public PoiListItemBinder(int cityId, Poi poi) {
        this.cityId = cityId;
        this.poi = poi;
    }

    public int getType() {
        return 1;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        SimpleItemHolder h = (SimpleItemHolder) holder;
        h.clickable.setOnClickListener(new C12811());
        h.title.setText(this.poi.getName());
    }
}
