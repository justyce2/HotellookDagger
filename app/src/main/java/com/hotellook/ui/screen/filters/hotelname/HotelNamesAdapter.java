package com.hotellook.ui.screen.filters.hotelname;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.events.HotelNameSelectedEvent;
import com.hotellook.ui.screen.filters.pois.listitems.TitleHolder;
import com.hotellook.utils.EventBus;
import java.util.ArrayList;
import java.util.List;

public class HotelNamesAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_TITLE = 0;
    private List<HotelData> data;
    private final EventBus eventBus;

    /* renamed from: com.hotellook.ui.screen.filters.hotelname.HotelNamesAdapter.1 */
    class C12701 extends MonkeySafeClickListener {
        final /* synthetic */ HotelData val$data;

        C12701(HotelData hotelData) {
            this.val$data = hotelData;
        }

        public void onSafeClick(View v) {
            HotelNamesAdapter.this.eventBus.post(new HotelNameSelectedEvent(this.val$data.getName()));
        }
    }

    public HotelNamesAdapter(EventBus eventBus) {
        this.data = new ArrayList();
        this.eventBus = eventBus;
    }

    public void setData(List<HotelData> data) {
        this.data = data;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new TitleHolder(inflateTitle(parent));
        }
        return new HotelNameHolder(inflateHotelView(parent));
    }

    private View inflateHotelView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.hotel_name_list_item, parent, false);
    }

    private View inflateTitle(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.view_hotel_name_title, parent, false);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) != 0) {
            HotelData data = getItem(position);
            HotelNameHolder nameHolder = (HotelNameHolder) holder;
            nameHolder.destination.setText(data.getName());
            nameHolder.item.setOnClickListener(new C12701(data));
            nameHolder.bottomDivider.setVisibility(position == getItemCount() + -1 ? 8 : 0);
        }
    }

    public long getItemId(int position) {
        return getItem(position).getId();
    }

    private HotelData getItem(int position) {
        return (HotelData) this.data.get(position - 1);
    }

    public int getItemViewType(int position) {
        return position == 0 ? 0 : TYPE_ITEM;
    }

    public int getItemCount() {
        if (this.data.size() == 0) {
            return 0;
        }
        return this.data.size() + TYPE_ITEM;
    }

    public void clearData() {
        this.data.clear();
        notifyDataSetChanged();
    }
}
