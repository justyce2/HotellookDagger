package com.hotellook.ui.screen.filters.pois.listitems;

import android.location.Location;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.DistanceTargetSelectedEvent;
import com.hotellook.filters.distancetarget.SeasonLocationDistanceTarget;
import com.hotellook.ui.screen.filters.pois.adapter.ListItemBinder;
import java.util.List;

public class SeasonLocationItemBinder implements ListItemBinder {
    private final List<Location> locationList;
    private final String season;
    private final String title;

    /* renamed from: com.hotellook.ui.screen.filters.pois.listitems.SeasonLocationItemBinder.1 */
    class C12821 extends MonkeySafeClickListener {
        C12821() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new DistanceTargetSelectedEvent(new SeasonLocationDistanceTarget(SeasonLocationItemBinder.this.locationList, SeasonLocationItemBinder.this.title, SeasonLocationItemBinder.this.season)));
        }
    }

    public SeasonLocationItemBinder(List<Location> locationList, String title, String season) {
        this.locationList = locationList;
        this.title = title;
        this.season = season;
    }

    public int getType() {
        return 3;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        SimpleItemHolder h = (SimpleItemHolder) holder;
        h.clickable.setOnClickListener(new C12821());
        h.title.setText(this.title);
    }
}
