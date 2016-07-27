package com.hotellook.ui.screen.filters.pois.listitems;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.MapPointClickEvent;
import com.hotellook.events.SearchPointClickEvent;
import com.hotellook.ui.screen.filters.pois.adapter.ListItemBinder;
import com.hotellook.utils.EventBus;

public class MapPointBinder implements ListItemBinder {
    private EventBus eventBus;
    private final int type;

    /* renamed from: com.hotellook.ui.screen.filters.pois.listitems.MapPointBinder.1 */
    class C12791 extends MonkeySafeClickListener {
        C12791() {
        }

        public void onSafeClick(View v) {
            if (MapPointBinder.this.type == 5) {
                MapPointBinder.this.eventBus.post(new MapPointClickEvent());
            } else {
                MapPointBinder.this.eventBus.post(new SearchPointClickEvent());
            }
        }
    }

    public MapPointBinder(EventBus eventBus, int type) {
        this.eventBus = eventBus;
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ((MapPointHolder) holder).clickable.setOnClickListener(new C12791());
    }
}
