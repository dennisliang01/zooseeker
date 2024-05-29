package com.example.zooseeker_jj_zaaz_team_52;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.time.LocalTime;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * ZooData Class: Single Responsibility - Converts strings in JSON asset files into information
 * about exhibits / intersections / gates / paths in Zoo
 */
public class ZooData implements Serializable {

    public static class VertexInfo implements Serializable {
        public static final VertexInfo STARTING_POINT = new VertexInfo(
                "entrance_exit_gate", "owens_aviary", "Entrance and Exit Gate", "Place to enter or exit the zoo",
                "Accessible by wheelchairs", VertexInfo.Kind.GATE, 32.73561,
                -117.14936, LocalTime.of(10,30), LocalTime.of(18,20), null);
        public VertexInfo(String id, String parent_id, String name, String description, String accessibility_options,
                          Kind kind, double lat, double lng, LocalTime start_time, LocalTime end_time,
                          URL img_link) {
            this.id = id;
            this.parent_id = parent_id;
            this.name = name;
            this.description = description;
            this.accessibility_options = accessibility_options;
            this.kind = kind;
            this.lat = lat;
            this.lng = lng;
            this.start_time = start_time;
            this.end_time = end_time;
            this.img_link = img_link;
        }


        public enum Kind {
            // The SerializedName annotation tells GSO/;pN how to convert
            // from the strings in our JSON to this Enum.
            @SerializedName("gate") GATE,
            @SerializedName("exhibit") EXHIBIT,
            @SerializedName("intersection") INTERSECTION,
            @SerializedName("exhibit_group") EXHIBIT_GROUP,
            @SerializedName("restroom") RESTROOM,
            @SerializedName("restaurants") RESTAURANT,
            @SerializedName("resting_area") RESTING_AREA,
            @SerializedName("event") EVENT

            }

        public String id;
        public String parent_id;
        public String description;
        public String accessibility_options;
        public Kind kind;

        public String name;
        public List<String> tags;
        public double lat;
        public double lng;
        public LocalTime start_time;
        public LocalTime end_time;
        public URL img_link;
        public boolean isSelected;

        @NonNull
        public String getID(){ return this.id;}

        @NonNull
        public VertexInfo getStarting_Point(){ return this.STARTING_POINT;}

        @NonNull
        @Override
        public String toString() {
            return "VertexInfo{" +
                    "id='" + id + '\'' +
                    ", description=" + description + '\'' +
                    ", accessibility_options =" + accessibility_options + '\'' +
                    ", kind=" + kind +
                    ", name='" + name + '\'' +
                    ", tags=" + tags + '\'' +
                    ", lat=" + lat + '\'' +
                    ", lng=" + lng + '\'' +
                    ", start_time=" + start_time + '\'' +
                    ", end_time=" + end_time + '\'' +
                    ", img_link=" + img_link + '\'' +
                    '}';
        }
    }

    public static class EdgeInfo implements Serializable {
        public String id;
        public String street;
    }
}