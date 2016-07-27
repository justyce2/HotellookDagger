package com.hotellook.utils;

import android.support.annotation.NonNull;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.utils.map.SphericalUtil;
import java.util.List;

public class MapUtils {
    private static final double LN2 = 0.6931471805599453d;
    private static final int WORLD_PX_HEIGHT = 256;
    private static final int WORLD_PX_WIDTH = 256;
    private static final int ZOOM_MAX = 21;

    @NonNull
    public static LatLngBounds computeBounds(@NonNull List<HotelData> hotels) {
        double baseLat = ((HotelData) hotels.get(0)).getLocation().getLat();
        double baseLon = ((HotelData) hotels.get(0)).getLocation().getLon();
        double left = baseLon;
        double right = baseLon;
        double top = baseLat;
        double bottom = baseLat;
        for (HotelData hotel : hotels) {
            double hotelLat = hotel.getLocation().getLat();
            double hotelLon = hotel.getLocation().getLon();
            left = Math.min(left, hotelLon);
            right = Math.max(right, hotelLon);
            top = Math.max(top, hotelLat);
            bottom = Math.min(bottom, hotelLat);
        }
        Builder builder = new Builder();
        builder.include(new LatLng(top, left));
        builder.include(new LatLng(bottom, right));
        return builder.build();
    }

    @NonNull
    public static LatLngBounds calculateBounds(@NonNull LatLng center, @NonNull LatLng point) {
        return convertCenterAndRadiusToBounds(center, (double) LocationUtils.distanceBetween(center, point));
    }

    @NonNull
    public static LatLngBounds convertCenterAndRadiusToBounds(@NonNull LatLng center, double radius) {
        return new LatLngBounds(SphericalUtil.computeOffset(center, Math.sqrt(2.0d) * radius, 225.0d), SphericalUtil.computeOffset(center, Math.sqrt(2.0d) * radius, 45.0d));
    }

    public static int computeBoundsZoomLevel(@NonNull LatLngBounds bounds, int mapWidthPx, int mapHeightPx) {
        LatLng ne = bounds.northeast;
        LatLng sw = bounds.southwest;
        double latFraction = (latRad(ne.latitude) - latRad(sw.latitude)) / 3.141592653589793d;
        double lngDiff = ne.longitude - sw.longitude;
        if (lngDiff < 0.0d) {
            lngDiff += 360.0d;
        }
        double lngFraction = lngDiff / 360.0d;
        return Math.min(Math.min((int) zoom(mapHeightPx, WORLD_PX_WIDTH, latFraction), (int) zoom(mapWidthPx, WORLD_PX_WIDTH, lngFraction)), ZOOM_MAX);
    }

    private static double latRad(double lat) {
        double sin = Math.sin((lat * 3.141592653589793d) / 180.0d);
        return Math.max(Math.min(Math.log((1.0d + sin) / (1.0d - sin)) / 2.0d, 3.141592653589793d), -3.141592653589793d) / 2.0d;
    }

    private static double zoom(int mapPx, int worldPx, double fraction) {
        return Math.floor(Math.log(((double) (mapPx / worldPx)) / fraction) / LN2);
    }
}
