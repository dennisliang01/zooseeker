package com.example.zooseeker_jj_zaaz_team_52;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * ZooData Class: Single Responsibility - Converts strings in JSON asset files into information
 * about exhibits / intersections / gates / paths in Zoo
 */
public class ZooData implements Serializable {

    public static class VertexInfo implements Serializable {
        public static final VertexInfo STARTING_POINT = new VertexInfo("entrance_exit_gate", "Entrance and Exit Gate", VertexInfo.Kind.GATE, 32.73561, -117.14936);
        public VertexInfo(String id, String name, Kind kind, double lat, double lng) {
            this.id = id;
            this.name = name;
            this.kind = kind;
            this.lat = lat;
            this.lng = lng;
        }


        public enum Kind {
            // The SerializedName annotation tells GSON how to convert
            // from the strings in our JSON to this Enum.
            @SerializedName("gate") GATE,
            @SerializedName("exhibit") EXHIBIT,
            @SerializedName("intersection") INTERSECTION,
            @SerializedName("exhibit_group") EXHIBIT_GROUP

        }

        public String id;
        public String parent_id;
        public Kind kind;
        public String name;
        public List<String> tags;
        public double lat;
        public double lng;
        public boolean isSelected;


        @NonNull
        @Override
        public String toString() {
            return "VertexInfo{" +
                    "id='" + id + '\'' +
                    ", kind=" + kind +
                    ", name='" + name + '\'' +
                    ", tags=" + tags + '\'' +
                    ", lat=" + lat +
                    ", lng=" + lng +
                    '}';
        }
    }

    public static class EdgeInfo implements Serializable {
        public String id;
        public String street;
    }
}