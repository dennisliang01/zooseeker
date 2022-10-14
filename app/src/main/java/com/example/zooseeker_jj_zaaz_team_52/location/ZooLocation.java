package com.example.zooseeker_jj_zaaz_team_52.location;

import com.example.zooseeker_jj_zaaz_team_52.ZooData;

public class ZooLocation {
    private Coord latLng;
    private ZooData.VertexInfo nearestLandmark;


    public static final ZooLocation STARTING_POINT = new ZooLocation(Coords.ZOO, ZooData.VertexInfo.STARTING_POINT);

    public ZooLocation(Coord coord, ZooData.VertexInfo nearestLandmark) {
        this.latLng = coord;
        this.nearestLandmark = nearestLandmark;
    }

    public Coord getLatLng() {
        return latLng;
    }

    public void setLatLng(Coord latLng) {
        this.latLng = latLng;
    }

    public ZooData.VertexInfo getNearestLandmark() {
        return nearestLandmark;
    }

    public void setNearestLandmark(ZooData.VertexInfo nearestLandmark) {
        this.nearestLandmark = nearestLandmark;
    }
}
