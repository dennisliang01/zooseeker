package com.example.zooseeker_jj_zaaz_team_52;

import com.android.tools.r8.internal.No;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Schedule Class: Single Responsibility - Converts strings in JSON asset files into information
 *  * about exhibits to be visited / intersections / gates / paths in Zoo
 */
public class Schedule {

    // VertexInfo: The Exhibit to be visited
    public class VertexInfo implements Serializable{

        // This is a starting vertex of the schedule graph
        VertexInfo starting_event = new VertexInfo("123", null, true);

        public VertexInfo(String visitor_id, String exhibit_name, boolean visited){

            this.visitor_id = visitor_id;
            this.exhibit_name = exhibit_name;
            this.visited = visited;
        }

        public String visitor_id;
        public String exhibit_name;
        public boolean visited;
        public String toString() {
            return "VertexInfo{" +
                    "visitor_id ='" + visitor_id + '\'' +
                    ", exhibit_name='" + exhibit_name + '\'' +
                    ", visited=" + visited + '\'' +
                    '}';
        }
    }

    public class EdgeInfo implements Serializable{

        public EdgeInfo(String id, LocalTime start_event_time, LocalTime end_event_time){

            this.id = id;
            this.start_event_time = start_event_time;
            this.end_event_time = end_event_time;
        }
        public String id;
        public LocalTime start_event_time;
        public LocalTime end_event_time;

        public String toString(){
            return "NodeInfo{" +
                    "start_event_time ='" + start_event_time + '\'' +
                    ", end_event_time='" + end_event_time + '\'' +
                    '}';
        }
    }
}
