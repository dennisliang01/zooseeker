package com.example.zooseeker_jj_zaaz_team_52;

import java.io.Serializable;

// This class is used to store the animal information
public class AnimalInfo implements Serializable {

    // Scientific name is the primary key
    public AnimalInfo(String scientific_name, String regular_name, String animal_description, String image_location) {
        this.scientific_name = scientific_name;
        this.regular_name = regular_name;
        this.animal_description = animal_description;
        this.image_location = image_location;
    }

    public String scientific_name;

    public String regular_name;

    public String animal_description;

    public String image_location;

    public String getScientific_name() {
        return scientific_name;
    }

    @Override
    public String toString() {
        return "AnimalInfo{" +
                "scientific_name='" + scientific_name + '\'' +
                ", regular_name=" + regular_name + '\'' +
                ", animal_description =" + animal_description + '\'' +
                ", image_location=" + image_location + '\'' +
                '}';
    }
}
