package com.hotellook.ui.screen.filters.pois.listitems;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.DistanceTargetSelectedEvent;
import com.hotellook.filters.distancetarget.DistanceTarget.DestinationType;
import com.hotellook.filters.distancetarget.LocationDistanceTarget;
import com.hotellook.ui.screen.filters.pois.adapter.ListItemBinder;

public class CenterItemBinder implements ListItemBinder {
    private final Location cityCenter;
    private final String title;

    /* renamed from: com.hotellook.ui.screen.filters.pois.listitems.CenterItemBinder.1 */
    class C12781 extends MonkeySafeClickListener {
        C12781() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new DistanceTargetSelectedEvent(new LocationDistanceTarget(CenterItemBinder.this.cityCenter, CenterItemBinder.this.title, DestinationType.CENTER)));
        }
    }

    public CenterItemBinder(Location cityCenter, Context context) {
        this.cityCenter = cityCenter;
        this.title = context.getString(C1178R.string.city_center);
    }

    public CenterItemBinder(Location cityCenter, String title) {
        this.cityCenter = cityCenter;
        this.title = title;
    }

    public int getType() {
        return 2;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        SimpleItemHolder h = (SimpleItemHolder) holder;
        h.clickable.setOnClickListener(new C12781());
        h.title.setText(this.title);
    }
}
