package com.example.zooseeker_jj_zaaz_team_52;

import java.io.Serializable;

public class Activity implements Serializable {

    public Activity(String activity_name, String activity_type) {
        this.activity_name = activity_name;
        this.activity_type = activity_type;
    }
    public String activity_name;

    public String activity_type;

    public String getActivity_name() {
        return activity_name;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activity_name='" + activity_name + '\'' +
                ", activity_type=" + activity_type + '\'' +
                '}';
    }
}

