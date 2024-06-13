package com.example.zooseeker_jj_zaaz_team_52;

import java.io.Serializable;
import java.time.LocalTime;

// This class is used to store the information of the exhibit
public class InfoExhibit implements Serializable {

    public InfoExhibit(String scientific_name, String exhibit_name, LocalTime start_time, LocalTime end_time) {
        this.scientific_name = scientific_name;
        this.exhibit_name = exhibit_name;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public String scientific_name;

    public String exhibit_name;

    public LocalTime start_time;

    public LocalTime end_time;

    public String getScientific_name() {
        return scientific_name;
    }

    public String getExhibit_name() {
        return exhibit_name;
    }

    @Override
    public String toString() {
        return "InfoExhibit{" +
                "scientific_name='" + scientific_name + '\'' +
                ", exhibit_name=" + exhibit_name + '\'' +
                ", start_time=" + start_time + '\'' +
                ", end_time=" + end_time + '\'' +
                '}';
    }
}
