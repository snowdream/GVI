package com.github.snowdream.gvi.lib.entity;

import android.location.Location;

import java.util.List;

/**
 * Navi Segments, which seperated by navi types. The navi types are driving,transit,walking,cycling,flights
 * etc.  required.
 *
 * Created by yanghui.yangh on 2016/4/20.
 */
public class Segment {
    private int type = NaviType.DRIVING;
    private GeoPoint start = null;
    private GeoPoint end = null;
    private List<GeoPoint> middle = null;
    private List<Location> loc = null;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public GeoPoint getStart() {
        return start;
    }

    public void setStart(GeoPoint start) {
        this.start = start;
    }

    public GeoPoint getEnd() {
        return end;
    }

    public void setEnd(GeoPoint end) {
        this.end = end;
    }

    public List<GeoPoint> getMiddle() {
        return middle;
    }

    public void setMiddle(List<GeoPoint> middle) {
        this.middle = middle;
    }

    public List<Location> getLoc() {
        return loc;
    }

    public void setLoc(List<Location> loc) {
        this.loc = loc;
    }
}
