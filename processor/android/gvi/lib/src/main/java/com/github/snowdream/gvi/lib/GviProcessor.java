package com.github.snowdream.gvi.lib;

import android.annotation.TargetApi;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.github.snowdream.gvi.lib.entity.Bound;
import com.github.snowdream.gvi.lib.entity.GeoPoint;
import com.github.snowdream.gvi.lib.entity.Gvi;
import com.github.snowdream.gvi.lib.entity.NaviType;
import com.github.snowdream.gvi.lib.entity.Segment;
import com.github.snowdream.gvi.lib.entity.Track;
import com.github.snowdream.gvi.lib.util.TimeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanghui.yangh on 2016/4/20.
 */
public final class GviProcessor {

    public static Gvi fromJson(@NonNull String text) throws ProcessorException {
        JSONObject object = null;
        try {
            object = new JSONObject(text);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new ProcessorException("JSONException", e);
        }

        Gvi gvi = new Gvi();

        readHead(gvi, object);
        readBody(gvi, object);

        return gvi;
    }

    public static String toJson(@NonNull Gvi gvi) throws ProcessorException, JSONException, ParseException {

        JSONObject object = new JSONObject();

        writeHead(gvi, object);
        writeBody(gvi, object);

        int spacesToIndentEachLevel = 2;
        return object.toString(spacesToIndentEachLevel);
    }


    private static void readHead(Gvi gvi, @NonNull JSONObject object) throws ProcessorException {
        if (object.has(GviKey.KEY_CREATOR)) {
            gvi.setCreator(object.optString(GviKey.KEY_CREATOR));
        } else {
            throw new ProcessorException("Invalid GVI file.The creator should not be empty.");
        }

        if (object.has(GviKey.KEY_PROVIDER)) {
            gvi.setProvider(object.optString(GviKey.KEY_PROVIDER));
        } else {
            throw new ProcessorException("Invalid GVI file.The provider should not be empty.");
        }

        if (object.has(GviKey.KEY_NAME)) {
            gvi.setName(object.optString(GviKey.KEY_NAME));
        }

        if (object.has(GviKey.KEY_DESC)) {
            gvi.setDesc(object.optString(GviKey.KEY_DESC));
        }

        if (object.has(GviKey.KEY_AUTHOR)) {
            gvi.setAuthor(object.optString(GviKey.KEY_AUTHOR));
        }

        if (object.has(GviKey.KEY_COPYRIGHT)) {
            gvi.setCopyright(object.optString(GviKey.KEY_COPYRIGHT));
        }

        if (object.has(GviKey.KEY_LINK)) {
            gvi.setLink(object.optString(GviKey.KEY_LINK));
        }

        if (object.has(GviKey.KEY_TIME)) {
            gvi.setTime(object.optString(GviKey.KEY_TIME));
        }

        if (object.has(GviKey.KEY_KEYWORDS)) {
            String keywords = object.optString(GviKey.KEY_KEYWORDS);

            if (!TextUtils.isEmpty(keywords) && keywords.contains(",")) {
                gvi.setKeywords(keywords.split(","));
            }
        }

        if (object.has(GviKey.KEY_BOUNDS)) {
            JSONObject obj = object.optJSONObject(GviKey.KEY_BOUNDS);
            if (obj != null) {
                if (obj.has(GviKey.KEY_MINLATITUDE) &&
                        obj.has(GviKey.KEY_MINLONGITUDE) &&
                        obj.has(GviKey.KEY_MAXLATITUDE) &&
                        obj.has(GviKey.KEY_MAXLONGITUDE)) {
                    double minlongitude = obj.optDouble(GviKey.KEY_MINLONGITUDE);
                    double minlatitude = obj.optDouble(GviKey.KEY_MINLATITUDE);
                    double maxlongitude = obj.optDouble(GviKey.KEY_MAXLONGITUDE);
                    double maxlatitude = obj.optDouble(GviKey.KEY_MAXLATITUDE);

                    Bound bound = new Bound(minlongitude, minlatitude, maxlongitude, maxlatitude);
                    gvi.setBound(bound);
                }
            } else {
                throw new ProcessorException("Invalid GVI file.The bound should not be empty.");
            }
        } else {
            throw new ProcessorException("Invalid GVI file.The bound should not be empty.");
        }
    }

    private static void readBody(Gvi gvi, @NonNull JSONObject object) throws ProcessorException {
        if (object.has(GviKey.KEY_TRACK)) {
            JSONArray array = object.optJSONArray(GviKey.KEY_TRACK);
            if (array != null) {
                if (array.length() == 0) return;

                Track track = readTrack(array);
                gvi.setTrack(track);
            } else {
                throw new ProcessorException("Invalid GVI file.The track should not be empty.");
            }
        } else {
            throw new ProcessorException("Invalid GVI file.The track should not be empty.");
        }

    }

    private static Track readTrack(@NonNull JSONArray array) throws ProcessorException {
        Track track = new Track();
        List<Segment> list = new ArrayList<Segment>();

        int length = array.length();
        for (int i = 0; i < length; i++) {
            JSONObject object = array.optJSONObject(i);
            if (object == null) continue;

            Segment segment = readSegment(object);
            if (segment != null) {
                list.add(segment);
            }
        }

        if (list.size() > 0) {
            track.setSegments(list);
        }

        return track;

    }

    private static Segment readSegment(@NonNull JSONObject object) throws ProcessorException {
        Segment segment = new Segment();

        if (object.has(GviKey.KEY_TYPE)) {
            segment.setType(object.optInt(GviKey.KEY_TYPE));
        } else {
            throw new ProcessorException("Invalid GVI file.The type of the segment doe not exist.");
        }

        if (object.has(GviKey.KEY_START)) {
            JSONObject obj = object.optJSONObject(GviKey.KEY_START);
            GeoPoint start = readGeoPoint(obj);
            if (start != null) {
                segment.setStart(start);
            }
        } /*else {
            throw new ProcessorException("Invalid GVI file.The start point of the segment doe not exist.");
        }*/

        if (object.has(GviKey.KEY_END)) {
            JSONObject obj = object.optJSONObject(GviKey.KEY_END);
            GeoPoint end = readGeoPoint(obj);
            if (end != null) {
                segment.setEnd(end);
            }
        } /*else {
            throw new ProcessorException("Invalid GVI file.The start point of the segment doe not exist.");
        }*/

        if (object.has(GviKey.KEY_MIDDLE)) {
            JSONArray array = object.optJSONArray(GviKey.KEY_MIDDLE);
            if (array.length() > 0) {
                List<GeoPoint> list = new ArrayList<GeoPoint>();

                int length = array.length();
                for (int i = 0; i < length; i++) {
                    JSONObject obj = array.optJSONObject(i);
                    if (obj == null) continue;

                    GeoPoint mid = readGeoPoint(obj);
                    if (mid != null) {
                        list.add(mid);
                    }
                }

                if (!list.isEmpty()) {
                    segment.setMiddle(list);
                }
            }
        }

        if (object.has(GviKey.KEY_LOC)) {
            JSONArray array = object.optJSONArray(GviKey.KEY_LOC);
            List<Location> list = readLoc(array);

            if (list != null && !list.isEmpty()) {
                segment.setLoc(list);
            }
        } else {
            throw new ProcessorException("Invalid GVI file.The loc of the segment doe not exist.");
        }

        return segment;
    }

    private static GeoPoint readGeoPoint(@NonNull JSONObject object) throws ProcessorException {
        if (!object.has(GviKey.KEY_LONGITUDE) || !object.has(GviKey.KEY_LATITUDE)) {
            throw new ProcessorException("Invalid GVI file.The longitude or latitude doe not exist.");
        }

        double longitude = object.optDouble(GviKey.KEY_LONGITUDE);
        double latitude = object.optDouble(GviKey.KEY_LATITUDE);

        return new GeoPoint(longitude, latitude);
    }

    private static List<Location> readLoc(@NonNull JSONArray array) throws ProcessorException {
        List<Location> list = new ArrayList<Location>();

        int length = array.length();
        for (int i = 0; i < length; i++) {
            JSONObject object = array.optJSONObject(i);
            if (object == null) continue;

            Location location = readLocation(object);
            if (location != null) {
                list.add(location);
            }
        }

        return list;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static Location readLocation(@NonNull JSONObject object) throws ProcessorException {
        if (!object.has(GviKey.KEY_TIME) || !object.has(GviKey.KEY_ELAPSEDREALTIMENANOS)
                || !object.has(GviKey.KEY_LONGITUDE) || !object.has(GviKey.KEY_LATITUDE)
                || !object.has(GviKey.KEY_ALTITUDE) || !object.has(GviKey.KEY_SPEED)
                || !object.has(GviKey.KEY_BEARING) || !object.has(GviKey.KEY_ACCURACY)) {
            throw new ProcessorException("Invalid GVI file.The location is not valid.");
        }

        String time = object.optString(GviKey.KEY_TIME);
        long elapsedrealtimenanos = object.optLong(GviKey.KEY_ELAPSEDREALTIMENANOS);
        double longitude = object.optDouble(GviKey.KEY_LONGITUDE);
        double latitude = object.optDouble(GviKey.KEY_LATITUDE);
        double altitude = object.optDouble(GviKey.KEY_ALTITUDE);
        float speed = (float) object.optDouble(GviKey.KEY_SPEED);
        float bearing = (float) object.optDouble(GviKey.KEY_BEARING);
        float accuracy = (float) object.optDouble(GviKey.KEY_ACCURACY);
        long timeLong = 0L;

        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAltitude(altitude);
        location.setAccuracy(accuracy);
        location.setBearing(bearing);
        location.setSpeed(speed);
        try {
            timeLong = TimeUtil.StringToLong(time);
        } catch (ParseException e) {
            timeLong = System.currentTimeMillis();
            e.printStackTrace();
        }
        location.setTime(timeLong);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            location.setElapsedRealtimeNanos(elapsedrealtimenanos);
        }
        return location;
    }

    private static void writeHead(Gvi gvi, @NonNull JSONObject object) throws ProcessorException, JSONException {
        object.put(GviKey.KEY_VERSION, "0.1");

        if (!TextUtils.isEmpty(gvi.getCreator())) {
            object.put(GviKey.KEY_CREATOR, gvi.getCreator());
        } else {
            throw new ProcessorException("The creator should not be empty.");
        }

        if (!TextUtils.isEmpty(gvi.getProvider())) {
            object.put(GviKey.KEY_PROVIDER, gvi.getProvider());
        } else {
            throw new ProcessorException("The provider should not be empty.");
        }

        if (!TextUtils.isEmpty(gvi.getName())) {
            object.put(GviKey.KEY_NAME, gvi.getName());
        }

        if (!TextUtils.isEmpty(gvi.getDesc())) {
            object.put(GviKey.KEY_DESC, gvi.getDesc());
        }

        if (!TextUtils.isEmpty(gvi.getAuthor())) {
            object.put(GviKey.KEY_AUTHOR, gvi.getAuthor());
        }

        if (!TextUtils.isEmpty(gvi.getCopyright())) {
            object.put(GviKey.KEY_COPYRIGHT, gvi.getCopyright());
        }

        if (!TextUtils.isEmpty(gvi.getLink())) {
            object.put(GviKey.KEY_LINK, gvi.getLink());
        }

        if (!TextUtils.isEmpty(gvi.getTime())) {
            object.put(GviKey.KEY_TIME, gvi.getTime());
        }

        String[] keywords = gvi.getKeywords();
        if (keywords != null && keywords.length > 0) {
            object.put(GviKey.KEY_KEYWORDS, TextUtils.join(",", keywords));
        }

        Bound bound = gvi.getBound();

        if (bound != null) {
            JSONObject obj = new JSONObject();
            obj.put(GviKey.KEY_MINLONGITUDE, bound.getMinlongitude());
            obj.put(GviKey.KEY_MINLATITUDE, bound.getMinlatitude());
            obj.put(GviKey.KEY_MAXLONGITUDE, bound.getMaxlongitude());
            obj.put(GviKey.KEY_MAXLATITUDE, bound.getMaxlatitude());

            object.put(GviKey.KEY_BOUNDS, obj);
        } else {
            throw new ProcessorException("The bound should not be empty.");
        }
    }

    private static void writeBody(Gvi gvi, @NonNull JSONObject object) throws ProcessorException, JSONException, ParseException {
        Track track = gvi.getTrack();

        if (track != null) {
            JSONArray array = writeTrack(track);
            if (array != null) {
                object.put(GviKey.KEY_TRACK, array);
            } else {
                throw new ProcessorException("The track should not be empty.");
            }
        } else {
            throw new ProcessorException("The track should not be empty.");
        }
    }

    private static JSONArray writeTrack(@NonNull Track track) throws ProcessorException, JSONException, ParseException {
        JSONArray array = new JSONArray();

        List<Segment> list = track.getSegments();

        int length = list.size();
        for (int i = 0; i < length; i++) {
            Segment segment = list.get(i);
            if (segment == null) continue;

            JSONObject obj = writeSegment(segment);
            if (obj != null) {
                array.put(obj);
            }
        }
        return array;
    }


    private static JSONObject writeSegment(@NonNull Segment segment) throws ProcessorException, JSONException, ParseException {
        JSONObject object = new JSONObject();

        if (segment.getType() < NaviType.DRIVING || segment.getType() > NaviType.FLIGHTS) {
            throw new ProcessorException("The type of the segment doe not exist.");
        }
        object.put(GviKey.KEY_TYPE, segment.getType());

        GeoPoint start = segment.getStart();
        if (start != null) {
            JSONObject obj = writeGeoPoint(start);
            if (obj != null) {
                object.put(GviKey.KEY_START, obj);
            }
        }/*else {
            throw new ProcessorException("The start point of the segment doe not exist.");
        }*/

        GeoPoint end = segment.getEnd();
        if (end != null) {
            JSONObject obj = writeGeoPoint(end);
            if (obj != null) {
                object.put(GviKey.KEY_END, obj);
            }
        }/*else {
            throw new ProcessorException("The end point of the segment doe not exist.");
        }*/

        List<GeoPoint> middle = segment.getMiddle();
        if (middle != null && !middle.isEmpty()) {
            JSONArray array = new JSONArray();
            int length = middle.size();

            for (int i = 0; i < length; i++) {
                GeoPoint geoPoint = middle.get(i);
                if (geoPoint == null) continue;

                JSONObject obj = writeGeoPoint(geoPoint);
                if (obj != null) {
                    array.put(obj);
                }
            }

            if (array.length() > 0) {
                object.put(GviKey.KEY_MIDDLE, array);
            }
        }

        List<Location> list = segment.getLoc();
        if (list != null && list.size() > 0) {
            JSONArray array = writeLoc(list);

            if (array != null && array.length() > 0) {
                object.put(GviKey.KEY_LOC, array);
            }
        } else {
            throw new ProcessorException("The loc of the segment doe not exist.");
        }

        return object;
    }

    private static JSONObject writeGeoPoint(@NonNull GeoPoint geoPoint) throws ProcessorException, JSONException {
        JSONObject object = new JSONObject();

        double longitude = geoPoint.getLongitude();
        object.put(GviKey.KEY_LONGITUDE, longitude);

        double latitude = geoPoint.getLatitude();
        object.put(GviKey.KEY_LATITUDE, latitude);

        return object;
    }

    private static JSONArray writeLoc(@NonNull List<Location> list) throws ProcessorException, ParseException, JSONException {
        JSONArray array = new JSONArray();

        int length = list.size();
        for (int i = 0; i < length; i++) {
            Location location = list.get(i);
            if (location == null) continue;

            JSONObject obj = writeLocation(location);
            if (obj != null) {
                array.put(obj);
            }
        }

        return array;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static JSONObject writeLocation(@NonNull Location location) throws ProcessorException, ParseException, JSONException {
        JSONObject object = new JSONObject();

        String time = TimeUtil.LongToString(location.getTime());
        long elapsedrealtimenanos = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            elapsedrealtimenanos = location.getElapsedRealtimeNanos();
        }
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        double altitude = location.getAltitude();
        float speed = location.getSpeed();
        float bearing = location.getBearing();
        float accuracy = location.getAccuracy();

        object.put(GviKey.KEY_TIME,time);
        object.put(GviKey.KEY_ELAPSEDREALTIMENANOS,elapsedrealtimenanos);
        object.put(GviKey.KEY_LONGITUDE,longitude);
        object.put(GviKey.KEY_LATITUDE,latitude);
        object.put(GviKey.KEY_ALTITUDE,altitude);
        object.put(GviKey.KEY_SPEED,speed);
        object.put(GviKey.KEY_BEARING,bearing);
        object.put(GviKey.KEY_ACCURACY,accuracy);

        return object;
    }
}