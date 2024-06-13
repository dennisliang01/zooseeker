package com.example.zooseeker_jj_zaaz_team_52;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * This class is used to store the information of the activity and exhibit. This was intended as the linking table between the activity and exhibit.
 * It has the following attributes:
 * - activity_name: the name of the activity
 * - exhibit_name: the name of the exhibit
 * - start_time: the start time of the activity
 * - end_time: the end time of the activity
 */
public class ActivityExhibit implements Serializable {

    public ActivityExhibit(String activity_name, String exhibit_name, LocalTime start_time, LocalTime end_time) {
        this.activity_name = activity_name;
        this.exhibit_name = exhibit_name;
        this.start_time = start_time;
        this.end_time = end_time;

    }

    public String activity_name;

    public String exhibit_name;

    public LocalTime start_time;

    public LocalTime end_time;

    public String getActivity_name() {
        return activity_name;
    }

    public String getExhibit_name() {
        return exhibit_name;
    }

    public String toString() {
        return "ActivityExhibit{" +
                "activity_name='" + activity_name + '\'' +
                ", exhibit_name=" + exhibit_name + '\'' +
                ", start_time=" + start_time + '\'' +
                ", end_time=" + end_time + '\'' +
                '}';
    }
}
