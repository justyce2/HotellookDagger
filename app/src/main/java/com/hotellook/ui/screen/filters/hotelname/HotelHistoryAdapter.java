package com.hotellook.ui.screen.filters.hotelname;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.db.data.HotelNameHistory;
import com.hotellook.events.HistoryHotelNameSelectedEvent;
import com.hotellook.ui.adapters.CursorRecyclerViewAdapter;
import com.hotellook.ui.screen.filters.pois.listitems.TitleHolder;
import me.zhanghai.android.materialprogressbar.C1759R;

public class HotelHistoryAdapter extends CursorRecyclerViewAdapter<ViewHolder> {
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_TITLE = 0;

    /* renamed from: com.hotellook.ui.screen.filters.hotelname.HotelHistoryAdapter.1 */
    class C12621 extends MonkeySafeClickListener {
        final /* synthetic */ String val$title;

        C12621(String str) {
            this.val$title = str;
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new HistoryHotelNameSelectedEvent(this.val$title));
        }
    }

    public HotelHistoryAdapter(Cursor cursor) {
        super(cursor);
    }

    public int getItemViewType(int position) {
        return position == 0 ? 0 : TYPE_ITEM;
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position, Cursor cursor) {
        if (getItemViewType(position) != 0) {
            cursor.moveToPosition(position - 1);
            String title = cursor.getString(cursor.getColumnIndex(HotelNameHistory.HOTEL_NAME));
            HotelNameHolder nameHolder = (HotelNameHolder) viewHolder;
            nameHolder.destination.setText(title);
            nameHolder.icon.setImageResource(C1178R.drawable.ic_history);
            nameHolder.item.setOnClickListener(new C12621(title));
            nameHolder.bottomDivider.setVisibility(position == getItemCount() + -1 ? 8 : 0);
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new TitleHolder(inflateTitle(parent));
        }
        return new HotelNameHolder(inflateHotelView(parent));
    }

    private View inflateTitle(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.view_hotel_name_title, parent, false);
        ((TextView) v.findViewById(C1759R.id.title)).setText(C1178R.string.recent_search);
        return v;
    }

    private View inflateHotelView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.hotel_name_list_item, parent, false);
    }

    public int getItemCount() {
        int count = super.getItemCount();
        return count == 0 ? 0 : count + TYPE_ITEM;
    }
}
