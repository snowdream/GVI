package com.github.snowdream.gvi.lib.entity;

/**
 * Created by yanghui.yangh on 2016/4/20.
 */
public class Bound {
    private double minlongitude = 0;
    private double minlatitude = 0;
    private double maxlongitude = 0;
    private double maxlatitude = 0;

    public Bound(double minlongitude, double minlatitude, double maxlongitude, double maxlatitude) {
        this.minlatitude = minlatitude;
        this.minlongitude = minlongitude;
        this.maxlatitude = maxlatitude;
        this.maxlongitude = maxlongitude;
    }

    public double getMinlongitude() {
        return minlongitude;
    }

    public void setMinlongitude(double minlongitude) {
        this.minlongitude = minlongitude;
    }

    public double getMinlatitude() {
        return minlatitude;
    }

    public void setMinlatitude(double minlatitude) {
        this.minlatitude = minlatitude;
    }

    public double getMaxlongitude() {
        return maxlongitude;
    }

    public void setMaxlongitude(double maxlongitude) {
        this.maxlongitude = maxlongitude;
    }

    public double getMaxlatitude() {
        return maxlatitude;
    }

    public void setMaxlatitude(double maxlatitude) {
        this.maxlatitude = maxlatitude;
    }
}
