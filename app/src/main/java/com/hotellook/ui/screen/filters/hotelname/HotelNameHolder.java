package com.hotellook.ui.screen.filters.hotelname;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.hotellook.C1178R;

public class HotelNameHolder extends ViewHolder {
    public final View bottomDivider;
    public final TextView destination;
    public final ImageView icon;
    public final View item;

    public HotelNameHolder(View itemView) {
        super(itemView);
        this.item = itemView;
        this.destination = (TextView) itemView.findViewById(C1178R.id.hotel_name);
        this.icon = (ImageView) itemView.findViewById(C1178R.id.iv_hotel_icon);
        this.bottomDivider = itemView.findViewById(C1178R.id.bottom_divider);
    }
}
