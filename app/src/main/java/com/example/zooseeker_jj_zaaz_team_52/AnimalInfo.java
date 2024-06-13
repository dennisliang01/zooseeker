package com.example.zooseeker_jj_zaaz_team_52;

import java.io.Serializable;

/**
 * This class is used to store the information of the animal.
 * The information includes the scientific name, regular name, animal description, and image location.
 */
public class AnimalInfo implements Serializable {

    /* Primary key is the scientific name of the animal. */
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
