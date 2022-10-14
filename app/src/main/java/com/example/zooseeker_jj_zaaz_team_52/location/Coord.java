package com.example.zooseeker_jj_zaaz_team_52.location;

import android.location.Location;

import androidx.annotation.NonNull;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.maps.model.LatLng;

public class Coord {
    public Coord(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public final double lat;
    public final double lng;

    public static Coord of(double lat, double lng) {
        return new Coord(lat, lng);
    }

    public static Coord fromLatLng(LatLng latLng) {
        return Coord.of(latLng.latitude, latLng.longitude);
    }

    public LatLng toLatLng() {
        return new LatLng(lat, lng);
    }

    public static Coord fromLocation(Location location) {
        return Coord.of(location.getLatitude(), location.getLongitude());
    }

    public static double dist(Coord c1, Coord c2){
        float[] results = new float[1];
        Location.distanceBetween(c1.lat, c1.lng, c2.lat, c2.lng, results);
        return results[0];
    }

    public static Coord fromZooLocation(ZooLocation loc) {
        return Coord.of(loc.getLatLng().lat, loc.getLatLng().lng);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return Double.compare(coord.lat, lat) == 0 && Double.compare(coord.lng, lng) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(lat, lng);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Coord{lat=%s, lng=%s}", lat, lng);
    }
}
