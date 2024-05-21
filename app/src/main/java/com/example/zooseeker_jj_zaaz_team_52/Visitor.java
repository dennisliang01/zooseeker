package com.example.zooseeker_jj_zaaz_team_52;

import java.io.Serializable;
import java.time.LocalTime;

public class Visitor implements Serializable {
        // Ex of visitor_type: Adult, Children, etc.
        public Visitor(String visitor_id, String visitor_type, int break_freq, String accessibility_needs) {

            this.visitor_id = visitor_id;
            this.visitor_type = visitor_type;
            this.break_freq = break_freq;
            this.accessibility_needs = accessibility_needs;
        }

        public String visitor_id;
        public String visitor_type;
        public int break_freq;
        public String accessibility_needs;

        public String getVisitor_id(){ return visitor_id;}
        @Override
        public String toString() {
            return "VisitorInfo{" +
                    "visitor_id='" + visitor_id + '\'' +
                    ", visitor_type=" + visitor_type + '\'' +
                    ", break_freq =" + break_freq + '\'' +
                    ", accessibility_needs=" + accessibility_needs + '\'' +
                    '}';
        }
}
