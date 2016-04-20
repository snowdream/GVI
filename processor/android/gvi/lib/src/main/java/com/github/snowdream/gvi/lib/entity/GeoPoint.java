package com.github.snowdream.gvi.lib.entity;

/**
 * GeoPoint
 *
 * Created by yanghui.yangh on 2016/4/20.
 */
public class GeoPoint {
    private double longitude = 0;
    private double latitude = 0;

    public GeoPoint(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
