package com.example.zooseeker_jj_zaaz_team_52;

import androidx.annotation.NonNull;

import org.jgrapht.GraphPath;

import java.io.Serializable;

/**
 * GraphNavigatorStep holds data about a single step in the path, where the step is a series of
 * edges between two exhibits that the user wants to visit
 */
public class GraphNavigationStep implements Serializable {

    /**
     * the end of the path's Exhibit's name and ID
     */
    PlanListItem planItem;

    /**
     * the sum of all edge weights giving us the total weight of going to this exhibit
     */
    int totalDist;

    /**
     * the path of edges that lead to this exhibit
     */
    GraphPath<String, IdentifiedWeightedEdge> graphPath;


    public GraphNavigationStep(PlanListItem planItem, int totalDist, GraphPath<String, IdentifiedWeightedEdge> graphPath) {
        this.planItem = planItem;
        this.totalDist = totalDist;
        this.graphPath = graphPath;
    }

    @NonNull
    @Override
    public String toString() {
        return planItem.exhibit_name;
    }

}
