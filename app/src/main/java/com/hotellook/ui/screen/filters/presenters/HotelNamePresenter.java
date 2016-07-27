package com.hotellook.ui.screen.filters.presenters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.FiltersChangedEvent;
import com.hotellook.events.OpenHotelNameSelectorEvent;
import com.hotellook.filters.items.HotelNameFilterItem;
import com.hotellook.utils.ViewUtils;

public class HotelNamePresenter implements FilterPresenter {
    private final HotelNameFilterItem hotelNameFilterItem;
    private View nameView;
    private View noNameView;
    private TextView title;

    /* renamed from: com.hotellook.ui.screen.filters.presenters.HotelNamePresenter.1 */
    class C12841 extends MonkeySafeClickListener {
        C12841() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new OpenHotelNameSelectorEvent(HotelNamePresenter.this.hotelNameFilterItem));
        }
    }

    /* renamed from: com.hotellook.ui.screen.filters.presenters.HotelNamePresenter.2 */
    class C12852 extends MonkeySafeClickListener {
        C12852() {
        }

        public void onSafeClick(View v) {
            HotelNamePresenter.this.hotelNameFilterItem.reset();
            HotelNamePresenter.this.setUpTitle();
            HotellookApplication.eventBus().post(new FiltersChangedEvent());
        }
    }

    public HotelNamePresenter(HotelNameFilterItem hotelNameFilterItem) {
        this.hotelNameFilterItem = hotelNameFilterItem;
    }

    public void onFiltersUpdated() {
        setUpTitle();
    }

    public void addView(LayoutInflater inflater, ViewGroup container) {
        View v = inflater.inflate(C1178R.layout.filter_hotelname, container, false);
        container.addView(v);
        this.title = (TextView) v.findViewById(C1178R.id.hotel_name);
        v.setOnClickListener(new C12841());
        v.findViewById(C1178R.id.clear_hotel_name).setOnClickListener(new C12852());
        this.nameView = v.findViewById(C1178R.id.hotel_name_container);
        this.noNameView = v.findViewById(C1178R.id.no_name);
        setUpTitle();
    }

    private void setUpTitle() {
        String hotelName = this.hotelNameFilterItem.getHotelName();
        boolean noTitle = TextUtils.isEmpty(hotelName);
        this.title.setText(hotelName);
        setViewVisibility(this.nameView, !noTitle);
        setViewVisibility(this.noNameView, noTitle);
    }

    private void setViewVisibility(View view, boolean show) {
        view.setAlpha(1.0f);
        if (show) {
            ViewUtils.showView(view);
        } else {
            ViewUtils.hideView(view);
        }
    }
}
