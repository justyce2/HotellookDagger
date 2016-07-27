package com.hotellook.ui.screen.filters.pois.listitems;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.ui.screen.filters.pois.adapter.ViewHolderCreator;
import pl.charmas.android.reactivelocation.C1822R;

public class PoiItemsHolderCreator implements ViewHolderCreator {
    public static final int TYPE_CENTER = 2;
    public static final int TYPE_MAP_POINT = 5;
    public static final int TYPE_MULTI_LOCATIONS = 3;
    public static final int TYPE_MY_LOCATION = 4;
    public static final int TYPE_POI = 1;
    public static final int TYPE_SEARCH_POINT = 6;
    public static final int TYPE_TITLE = 0;

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case C1822R.styleable.SignInButton_buttonSize /*0*/:
                return new TitleHolder(inflateTitle(parent));
            case TYPE_POI /*1*/:
                return new SimpleItemHolder(inflateSimpleView(parent));
            case TYPE_CENTER /*2*/:
                return new SimpleItemHolder(inflateSimpleView(parent));
            case TYPE_MULTI_LOCATIONS /*3*/:
                return new SimpleItemHolder(inflateSimpleView(parent));
            case TYPE_MY_LOCATION /*4*/:
                return new MyLocationHolder(inflateMyLocationView(parent));
            case TYPE_MAP_POINT /*5*/:
                return new MapPointHolder(inflateMapPointView(parent), C1178R.string.pin_on_map);
            case TYPE_SEARCH_POINT /*6*/:
                return new MapPointHolder(inflateMapPointView(parent), C1178R.string.search_point_distance_target);
            default:
                return null;
        }
    }

    private View inflateMyLocationView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.pin_my_location, parent, false);
    }

    private View inflateMapPointView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.map_point_item, parent, false);
    }

    private View inflateSimpleView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.pin_item, parent, false);
    }

    private View inflateTitle(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.pin_title, parent, false);
    }
}
