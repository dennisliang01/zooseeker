package com.example.zooseeker_jj_zaaz_team_52;

import java.io.Serializable;

// This class is used to store the information of the activity
public class InfoActivity implements Serializable {

        public InfoActivity(String activity_name, String scientific_name) {
            this.activity_name = activity_name;
            this.scientific_name = scientific_name;
        }
        public String activity_name;

        public String scientific_name;

        public String getActivity_name() {
            return activity_name;
        }

        public String getScientific_name() {
            return scientific_name;
        }

        @Override
        public String toString() {
            return "Activity{" +
                    "activity_name='" + activity_name + '\'' +
                    ", scientific_name=" + scientific_name + '\'' +
                    '}';
        }
}
