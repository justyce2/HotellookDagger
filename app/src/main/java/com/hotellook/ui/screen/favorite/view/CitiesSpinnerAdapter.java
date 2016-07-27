package com.hotellook.ui.screen.favorite.view;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.interactor.favorites.FavoriteCityData;
import java.util.List;

public class CitiesSpinnerAdapter extends ArrayAdapter<FavoriteCityData> {
    private final LayoutInflater inflater;
    private int selectedPosition;

    public CitiesSpinnerAdapter(Context context, List<FavoriteCityData> data) {
        super(context, C1178R.layout.spinner_item, C1178R.id.city_name, data);
        setDropDownViewResource(C1178R.layout.spinner_item);
        this.inflater = LayoutInflater.from(context);
    }

    public long getItemId(int position) {
        return (long) ((FavoriteCityData) getItem(position)).id;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = this.inflater.inflate(C1178R.layout.spinner_item, parent, false);
        } else {
            view = convertView;
        }
        ((TextView) view.findViewById(C1178R.id.item_title)).setText(((FavoriteCityData) getItem(position)).name);
        return view;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;
        boolean z;
        if (convertView == null) {
            view = this.inflater.inflate(C1178R.layout.spinner_favorite_city_dropdown_item, parent, false);
        } else {
            view = convertView;
        }
        TextView textName = (TextView) view.findViewById(C1178R.id.item_title);
        TextView textCount = (TextView) view.findViewById(C1178R.id.hotels_count);
        RadioButton radioBtn = (RadioButton) view.findViewById(C1178R.id.radio_btn);
        FavoriteCityData item = (FavoriteCityData) getItem(position);
        if (position == this.selectedPosition) {
            z = true;
        } else {
            z = false;
        }
        radioBtn.setChecked(z);
        textName.setText(item.name);
        if (item.count > 0) {
            Resources resources = textCount.getResources();
            textCount.setText(String.format("%,d", new Object[]{Integer.valueOf(item.count)}) + " " + resources.getQuantityString(C1178R.plurals.sf_hotel_num, item.count));
            textCount.setVisibility(0);
        } else {
            textCount.setVisibility(8);
        }
        return view;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
}
