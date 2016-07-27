package com.hotellook.utils.map.clustering.projection;

import com.google.android.gms.maps.model.LatLng;
import com.hotellook.utils.map.geometry.Point;

public class SphericalMercatorProjection {
    final double mWorldWidth;

    public SphericalMercatorProjection(double worldWidth) {
        this.mWorldWidth = worldWidth;
    }

    public Point toPoint(LatLng latLng) {
        double x = (latLng.longitude / 360.0d) + 0.5d;
        double siny = Math.sin(Math.toRadians(latLng.latitude));
        return new Point(this.mWorldWidth * x, this.mWorldWidth * (((Math.log((1.0d + siny) / (1.0d - siny)) * 0.5d) / -6.283185307179586d) + 0.5d));
    }

    public LatLng toLatLng(Point point) {
        return new LatLng(90.0d - Math.toDegrees(Math.atan(Math.exp(((-(0.5d - (point.f744y / this.mWorldWidth))) * 2.0d) * 3.141592653589793d)) * 2.0d), ((point.f743x / this.mWorldWidth) - 0.5d) * 360.0d);
    }
}
