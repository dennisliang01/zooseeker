package com.example.zooseeker_jj_zaaz_team_52;


import com.example.zooseeker_jj_zaaz_team_52.location.Coord;
import com.example.zooseeker_jj_zaaz_team_52.location.ZooLocation;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.util.Pair;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * ZooNavigator Interface: Single Responsibility Principle - Interface method stubs that provide
 * information used when updating view in DirectionsActivity
 */
public interface ZooNavigator extends Serializable {

    int getCurrentIndex();

    void setCurrentIndex(int updateIndex);

    /**
     * gets information about the current exhibit
     */
    PlanListItem getExhibit();

    /**
     * gets detailed direction to the exhibit
     */
    String getDirection();

    /**
     * gets detailed direction from the next exhibit to the current
     */
    String getPreviousDirection();
    String getBriefDirection(GraphPath<String, IdentifiedWeightedEdge> path);

    /**
     * gets the calculated distance from previous exhibit to the current exhibit
     */
    int getDistance();

    /**
     * gets the calculated distance from current exhibit to the previous exhibit
     */
    int getPreviousDistance();

    /**
     * moves to the previous exhibit, will return false if no next exhibit exists
     */
    boolean next();

    /**
     * moves to the next exhibit, will return false if no previous exhibit exists
     */
    boolean previous();

    /**
     * checks if there is a previous exhibit, will return false if no previous exhibit exists
     */
    Pair<Integer, PlanListItem> peekPrevious();

    /**
     * checks if there is a next exhibit, will return false if no next exhibit exists
     */
    Pair<Integer, PlanListItem> peekNext();

    /**
     * returns a list of optimized path with distance along the path from the starting exhibit
     */
    List<PlanListItem> findOptimizedPath();

    /**
     * regenerates the optimized path for remaining exhibits that are unvisited
     * @param nearestLandMark
     */
    void regenerateOptimizedPlan(PlanListItem nearestLandMark);

    /**
     * returns landmarks on current path
     * @return
     */
    Set<String> getLandMarksOnPath();

    /**
     * functionality of skipping an exhibit, should trigger a replan
     * @param nearestLandMark
     */
    void skipCurrentExhibit(PlanListItem nearestLandMark);

    /**
     * returns nonvisited exhibits in plan
     * @return
     */
    Set<PlanListItem> findNonVisitedExhibits();

    /**
     * finds the unvisited exhibit in plan that is the minimum distance from user's
     * current location
     * @param loc
     * @return
     */
    double minDistanceFromUnvisited(ZooLocation loc);

    /**
     * returns lat lng coordinate of user's current location
     * @return
     */
    Coord getCurrentLocation();

    /**
     * finds directions to specified exhibit depending on user's current location
     * @param currentPosition
     * @param isBrief
     * @return
     */
    String calcLocationBasedDirections(ZooLocation currentPosition, boolean isBrief);

    /**
     * returns current path and edges traversed
     * @return
     */
    GraphPath<String, IdentifiedWeightedEdge> getCurrentPath();
}