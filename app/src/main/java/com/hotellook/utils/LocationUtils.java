package com.hotellook.utils;

import android.location.Location;
import android.support.annotation.NonNull;
import com.google.android.gms.maps.model.LatLng;
import com.hotellook.core.api.pojo.common.Coordinates;
import me.zhanghai.android.materialprogressbar.BuildConfig;

public class LocationUtils {
    static double PI_RAD;

    static {
        PI_RAD = 0.017453292519943295d;
    }

    @NonNull
    public static Location from(@NonNull Coordinates coordinates) {
        return from(coordinates.getLat(), coordinates.getLon());
    }

    @NonNull
    public static Location from(float lat, float lng) {
        return from((double) lat, (double) lng);
    }

    @NonNull
    public static Location from(double lat, double lng) {
        Location location = new Location(BuildConfig.FLAVOR);
        location.setLatitude(lat);
        location.setLongitude(lng);
        return location;
    }

    @NonNull
    public static LatLng toLatLng(@NonNull Coordinates coordinates) {
        return new LatLng(coordinates.getLat(), coordinates.getLon());
    }

    @NonNull
    public static LatLng toLatLng(@NonNull Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    @NonNull
    public static Coordinates toCoordinates(@NonNull Location location) {
        Coordinates coordinates = new Coordinates();
        coordinates.setLat(location.getLatitude());
        coordinates.setLon(location.getLongitude());
        return coordinates;
    }

    @NonNull
    public static Coordinates toCoordinates(@NonNull LatLng latLng) {
        Coordinates coordinates = new Coordinates();
        coordinates.setLat(latLng.latitude);
        coordinates.setLon(latLng.longitude);
        return coordinates;
    }

    public static Coordinates toCoordinates(double lat, double lng) {
        Coordinates coordinates = new Coordinates();
        coordinates.setLat(lat);
        coordinates.setLon(lng);
        return coordinates;
    }

    public static double simpleDistance(Location loc1, Location loc2) {
        return simpleDistance(loc1.getLatitude(), loc1.getLongitude(), loc2.getLatitude(), loc2.getLongitude());
    }

    public static double simpleDistance(double lat1, double long1, double lat2, double long2) {
        double phi1 = lat1 * PI_RAD;
        double phi2 = lat2 * PI_RAD;
        return 6371010.0d * Math.acos((Math.sin(phi1) * Math.sin(phi2)) + ((Math.cos(phi1) * Math.cos(phi2)) * Math.cos((long2 * PI_RAD) - (long1 * PI_RAD))));
    }

    private static double deg2rad(double deg) {
        return (3.141592653589793d * deg) / 180.0d;
    }

    private static double rad2deg(double rad) {
        return (180.0d * rad) / 3.141592653589793d;
    }

    public static float distanceBetween(@NonNull Location l1, @NonNull Location l2) {
        return l1.distanceTo(l2);
    }

    public static float distanceBetween(@NonNull LatLng l1, @NonNull LatLng l2) {
        return distanceBetween(l1.latitude, l1.longitude, l2.latitude, l2.longitude);
    }

    public static float distanceBetween(@NonNull Coordinates c1, @NonNull Coordinates c2) {
        return distanceBetween(c1.getLat(), c1.getLon(), c2.getLat(), c2.getLon());
    }

    public static float distanceBetween(double lat1, double lng1, double lat2, double lng2) {
        float[] result = new float[1];
        Location.distanceBetween(lat1, lng1, lat2, lng2, result);
        return result[0];
    }

    public static double metersToLat(float m) {
        return (double) ((m / Distances.KILOMETER) / 110.54f);
    }

    public static double metersToLng(float m, double lat) {
        return ((double) (m / Distances.KILOMETER)) / (111.32d * Math.cos(Math.toRadians(lat)));
    }
}
