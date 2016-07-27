package com.hotellook.ui.screen.hotel.general;

import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.common.distance.DistanceFormatter;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.core.api.pojo.common.Coordinates;
import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;

public class PoiView extends RelativeLayout {
    private TextView mDistance;
    private View mDivider;
    private TextView mName;

    /* renamed from: com.hotellook.ui.screen.hotel.general.PoiView.1 */
    class C13231 extends MonkeySafeClickListener {
        C13231() {
        }

        public void onSafeClick(View v) {
        }
    }

    public PoiView(Context context) {
        super(context);
    }

    public PoiView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mName = (TextView) findViewById(C1178R.id.name);
        this.mDistance = (TextView) findViewById(C1178R.id.distance);
        this.mDivider = findViewById(C1178R.id.divider);
    }

    public void setData(Poi poi, HotelDetailData hotelDetailData, Location cityCenterLocation) {
        setOnClickListener(new C13231());
        this.mName.setText(poi.getName());
        this.mDistance.setText(getDistanceTxt(hotelDetailData.getLocation(), poi.getLocation()));
    }

    private String getDistanceTxt(Coordinates loc1, Coordinates loc2) {
        return new DistanceFormatter(getResources(), C1178R.string.hotel_general_poi_distance).getDistanceTxt(loc1, loc2);
    }

    public void hideDivider() {
        this.mDivider.setVisibility(8);
    }
}
