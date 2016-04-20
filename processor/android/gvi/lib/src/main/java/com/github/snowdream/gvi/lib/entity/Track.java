package com.github.snowdream.gvi.lib.entity;

import java.util.List;

/**
 * Contains many Navi Segments, which seperated by navi types. The navi types are
 * driving,transit,walking,cycling,flights etc.  required.
 *
 * Created by yanghui.yangh on 2016/4/20.
 */
public class Track {
    private List<Segment> segments = null;

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }
}
