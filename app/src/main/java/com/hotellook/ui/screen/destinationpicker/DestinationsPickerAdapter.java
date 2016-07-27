package com.hotellook.ui.screen.destinationpicker;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.api.data.DestinationData;
import com.hotellook.api.data.DestinationType;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.core.api.pojo.autocomlete.AutocompleteCityData;
import com.hotellook.core.api.pojo.autocomlete.AutocompleteData;
import com.hotellook.core.api.pojo.autocomlete.AutocompleteHotelData;
import com.hotellook.core.api.pojo.geo.GeoLocationData;
import com.hotellook.events.MapPointClickEvent;
import com.hotellook.ui.screen.destinationpicker.DestinationPickerFragment.OnDestinationSelectListener;
import com.hotellook.ui.screen.destinationpicker.Item.DestinationItem;
import com.hotellook.ui.screen.destinationpicker.Item.DividerItem;
import com.hotellook.ui.screen.destinationpicker.Item.GroupTitleCitiesItem;
import com.hotellook.ui.screen.destinationpicker.Item.GroupTitleHistoryItem;
import com.hotellook.ui.screen.destinationpicker.Item.GroupTitleHotelsItem;
import com.hotellook.ui.screen.destinationpicker.Item.GroupTitleItem;
import com.hotellook.ui.screen.destinationpicker.Item.GroupTitleNearestItem;
import com.hotellook.ui.screen.destinationpicker.Item.MapPointItem;
import com.hotellook.ui.screen.destinationpicker.Item.MyLocationItem;
import com.hotellook.utils.EventBus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.zhanghai.android.materialprogressbar.BuildConfig;

public class DestinationsPickerAdapter extends Adapter<ViewHolder> {
    private static final int TYPE_CITY = 5;
    private static final int TYPE_DIVIDER = 2;
    private static final int TYPE_GROUP_TITLE = 1;
    private static final int TYPE_HOTEL = 6;
    private static final int TYPE_MAP_POINT = 4;
    private static final int TYPE_MY_LOCATION = 3;
    private List<Item> destinationItems;
    private final EventBus eventBus;
    private OnDestinationSelectListener onDestinationSelectListener;

    /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationsPickerAdapter.1 */
    class C12331 extends ViewHolder {
        C12331(View x0) {
            super(x0);
        }
    }

    /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationsPickerAdapter.2 */
    class C12342 extends ViewHolder {
        C12342(View x0) {
            super(x0);
        }
    }

    /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationsPickerAdapter.3 */
    class C12353 extends ViewHolder {
        C12353(View x0) {
            super(x0);
        }
    }

    /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationsPickerAdapter.4 */
    class C12364 extends MonkeySafeClickListener {
        C12364() {
        }

        public void onSafeClick(View v) {
            DestinationsPickerAdapter.this.eventBus.post(new MapPointClickEvent());
        }
    }

    /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationsPickerAdapter.5 */
    class C12375 extends MonkeySafeClickListener {
        final /* synthetic */ DestinationData val$hotelData;

        C12375(DestinationData destinationData) {
            this.val$hotelData = destinationData;
        }

        public void onSafeClick(View v) {
            DestinationsPickerAdapter.this.informListener(this.val$hotelData);
        }
    }

    /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationsPickerAdapter.6 */
    class C12386 extends MonkeySafeClickListener {
        final /* synthetic */ DestinationData val$cityData;

        C12386(DestinationData destinationData) {
            this.val$cityData = destinationData;
        }

        public void onSafeClick(View v) {
            DestinationsPickerAdapter.this.informListener(this.val$cityData);
        }
    }

    static class DestinationHolder extends ViewHolder {
        TextView destination;
        TextView destinationLocation;
        View icCity;
        View icHotel;
        View item;

        public DestinationHolder(View itemView) {
            super(itemView);
            this.item = itemView;
            this.destination = (TextView) itemView.findViewById(C1178R.id.destination_name);
            this.destinationLocation = (TextView) itemView.findViewById(C1178R.id.destination_location);
            this.icHotel = itemView.findViewById(C1178R.id.iv_hotel_icon);
            this.icCity = itemView.findViewById(C1178R.id.iv_city_icon);
        }
    }

    static class GroupTitleHolder extends ViewHolder {
        TextView tvTitle;

        public GroupTitleHolder(View tvTitle) {
            super(tvTitle);
            this.tvTitle = (TextView) tvTitle;
        }
    }

    public DestinationsPickerAdapter(EventBus eventBus) {
        this.destinationItems = new ArrayList();
        this.eventBus = eventBus;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_GROUP_TITLE /*1*/:
                return new GroupTitleHolder(inflateGroupTitleView(parent));
            case TYPE_DIVIDER /*2*/:
                return new C12331(inflateDivider(parent));
            case TYPE_MY_LOCATION /*3*/:
                return new C12342(inflateMyLocation(parent));
            case TYPE_MAP_POINT /*4*/:
                return new C12353(inflateMapPointItem(parent));
            case TYPE_CITY /*5*/:
            case TYPE_HOTEL /*6*/:
                return new DestinationHolder(inflateDestinationView(parent));
            default:
                return null;
        }
    }

    private View inflateMapPointItem(ViewGroup parent) {
        View mapPointItem = LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.destination_map_point_item, parent, false);
        mapPointItem.setOnClickListener(new C12364());
        return mapPointItem;
    }

    private View inflateMyLocation(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.my_location_dp_list_item, parent, false);
    }

    private View inflateDivider(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.view_dp_list_divider, parent, false);
    }

    private View inflateGroupTitleView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.view_dp_list_group_title, parent, false);
    }

    private View inflateDestinationView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.view_dp_list_item, parent, false);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_GROUP_TITLE /*1*/:
                setUpGroupTitle((GroupTitleHolder) holder, Integer.valueOf(((GroupTitleItem) this.destinationItems.get(position)).getTitleResId()));
            case TYPE_CITY /*5*/:
                setUpCity((DestinationHolder) holder, ((DestinationItem) this.destinationItems.get(position)).destinationData());
            case TYPE_HOTEL /*6*/:
                setUpHotel((DestinationHolder) holder, ((DestinationItem) this.destinationItems.get(position)).destinationData());
            default:
        }
    }

    private void setUpGroupTitle(GroupTitleHolder holder, Integer resId) {
        holder.tvTitle.setText(holder.tvTitle.getContext().getString(resId.intValue()));
    }

    private void setUpHotel(DestinationHolder destinationHolder, DestinationData hotelData) {
        destinationHolder.destination.setText(hotelData.getHotelName());
        destinationHolder.destinationLocation.setText((hotelData.getState() != null ? hotelData.getState() + ", " : BuildConfig.FLAVOR) + hotelData.getCityName() + ", " + hotelData.getCountry());
        destinationHolder.item.setOnClickListener(new C12375(hotelData));
        destinationHolder.icHotel.setVisibility(0);
        destinationHolder.icCity.setVisibility(TYPE_MAP_POINT);
    }

    private void informListener(DestinationData data) {
        if (this.onDestinationSelectListener != null) {
            this.onDestinationSelectListener.onDestinationSelect(data);
        }
    }

    private void setUpCity(DestinationHolder cityHolder, DestinationData cityData) {
        cityHolder.destination.setText(cityData.getCityName());
        cityHolder.destinationLocation.setText((cityData.getState() != null ? cityData.getState() + ", " : BuildConfig.FLAVOR) + cityData.getCountry());
        cityHolder.item.setOnClickListener(new C12386(cityData));
        cityHolder.icHotel.setVisibility(TYPE_MAP_POINT);
        cityHolder.icCity.setVisibility(0);
    }

    public int getItemCount() {
        return this.destinationItems.size();
    }

    public void setData(AutocompleteData data) {
        List<Item> items = new ArrayList();
        if (data.getCities().size() > 0) {
            items.add(new GroupTitleCitiesItem());
            addAllCities(items, data.getCities());
        }
        if (data.getHotels().size() > 0) {
            items.add(new GroupTitleHotelsItem());
            addAllHotels(items, data.getHotels());
        }
        items.add(new DividerItem());
        this.destinationItems = items;
        notifyDataSetChanged();
    }

    public void setData(List<DestinationData> historyData, List<GeoLocationData> nearestLocations) {
        int i;
        List<Item> items = new ArrayList();
        DividerItem dividerItem = new DividerItem();
        items.add(new MapPointItem());
        items.add(dividerItem);
        items.add(new MyLocationItem());
        if (nearestLocations != null && nearestLocations.size() > 0) {
            items.add(new GroupTitleNearestItem());
            Collections.sort(nearestLocations, DestinationsPickerAdapter$$Lambda$1.lambdaFactory$());
            for (i = 0; i < nearestLocations.size(); i += TYPE_GROUP_TITLE) {
                items.add(new DestinationItem(new DestinationData((GeoLocationData) nearestLocations.get(i))));
                if (i < nearestLocations.size() - 1) {
                    items.add(dividerItem);
                }
            }
        }
        if (historyData != null && historyData.size() > 0) {
            items.add(new GroupTitleHistoryItem());
            for (i = 0; i < historyData.size(); i += TYPE_GROUP_TITLE) {
                items.add(new DestinationItem((DestinationData) historyData.get(i)));
                if (i < historyData.size() - 1) {
                    items.add(dividerItem);
                }
            }
        }
        items.add(dividerItem);
        this.destinationItems = items;
        notifyDataSetChanged();
    }

    private void addAllCities(List<Item> items, List<AutocompleteCityData> cities) {
        for (int i = 0; i < cities.size(); i += TYPE_GROUP_TITLE) {
            items.add(new DestinationItem(new DestinationData((AutocompleteCityData) cities.get(i))));
            if (i < cities.size() - 1) {
                items.add(new DividerItem());
            }
        }
    }

    private void addAllHotels(List<Item> items, List<AutocompleteHotelData> hotels) {
        DividerItem dividerItem = new DividerItem();
        for (int i = 0; i < hotels.size(); i += TYPE_GROUP_TITLE) {
            items.add(new DestinationItem(new DestinationData((AutocompleteHotelData) hotels.get(i))));
            if (i < hotels.size() - 1) {
                items.add(dividerItem);
            }
        }
    }

    public int getItemViewType(int position) {
        if (this.destinationItems.get(position) instanceof DividerItem) {
            return TYPE_DIVIDER;
        }
        if (this.destinationItems.get(position) instanceof MyLocationItem) {
            return TYPE_MY_LOCATION;
        }
        if (this.destinationItems.get(position) instanceof MapPointItem) {
            return TYPE_MAP_POINT;
        }
        if (this.destinationItems.get(position) instanceof GroupTitleItem) {
            return TYPE_GROUP_TITLE;
        }
        if (((DestinationItem) this.destinationItems.get(position)).destinationData().getType() == DestinationType.CITY) {
            return TYPE_CITY;
        }
        return TYPE_HOTEL;
    }

    public void setOnDestinationSelectListener(OnDestinationSelectListener onDestinationSelectListener) {
        this.onDestinationSelectListener = onDestinationSelectListener;
    }
}
