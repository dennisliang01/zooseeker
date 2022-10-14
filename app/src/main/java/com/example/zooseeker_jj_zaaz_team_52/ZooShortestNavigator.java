package com.example.zooseeker_jj_zaaz_team_52;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.example.zooseeker_jj_zaaz_team_52.location.Coord;
import com.example.zooseeker_jj_zaaz_team_52.location.ZooLocation;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * ZooShortestNavigator Class: Single Responsibility - Maintains ZooNavigator Object that tracks current
 * state of directions according to optimized shortest distance plan which is used when updating
 * view in DirectionsActivity
 */
public class ZooShortestNavigator implements ZooNavigator, Serializable {
    ZooGraph map;
    final PlanListItem ENTRANCE = new PlanListItem("Entrance and Exit Gate", "entrance_exit_gate");
    PlanListItem startingExhibit;

    // Stores path to take from front gate to the next shortest exhibit
    List<GraphNavigationStep> plan;


    int currentIndex;

    public int getCurrentIndex(){
        return this.currentIndex;
    }

    public void setCurrentIndex(int updateIndex){
        this.currentIndex = updateIndex;
    }

    public ZooShortestNavigator(List<PlanListItem> destinations, Context context) {
        //Initialize the entrance as the current exhibit
        map = new ZooGraph(context.getString(R.string.node_file), context.getString(R.string.graph_file), context.getString(R.string.edge_file), context);
        plan = new ArrayList<>();
        currentIndex = 1;
        startingExhibit = ENTRANCE;
        createPlan(destinations);
    }

    public ZooShortestNavigator(Context context) {
        //Initialize the entrance as the current exhibit
        map = new ZooGraph(context.getString(R.string.node_file), context.getString(R.string.graph_file), context.getString(R.string.edge_file), context);
        plan = new ArrayList<>();
        currentIndex = 1;
        startingExhibit = ENTRANCE;

    }


    public ZooShortestNavigator(List<PlanListItem> destinations, Context context, ZooGraph map) {
        this(context);
        this.map = map;
        startingExhibit = new PlanListItem("Entrance and Exit Gate", "entrance_exit_gate");
        createPlan(destinations);

    }

    public ZooShortestNavigator(PlanListItem start, List<PlanListItem> destinations, Context context) {
        this(context);
        startingExhibit = start;
        createPlan(destinations);
    }


    public ZooShortestNavigator(PlanListItem start, List<PlanListItem> destinations, Context context, ZooGraph map) {
        this(context);
        this.map = map;
        startingExhibit = start;
        createPlan(destinations);
    }


    /**
     * @require destinations does not include ENTRANCE
     * @param destinations: Create a list of destinations
     */
    private void createPlan(List<PlanListItem> destinations) {
        if (destinations.isEmpty() ) return;

        PlanListItem currentExhibit = PlanListItem.deepCopy(startingExhibit);
        List<Pair<PlanListItem, Boolean>> destinationCopy = new ArrayList<>();

        for (PlanListItem exhibit : destinations) {
            if(!exhibit.exhibit_id.equals(ENTRANCE.exhibit_id) ) {
                destinationCopy.add(new Pair<>(PlanListItem.deepCopy(exhibit), false));
            }
        }

        do {
            GraphNavigationStep nextExhibit = getShortestDistance(currentExhibit, destinationCopy);
            PlanListItem nextShortest = nextExhibit.planItem;
            plan.add(nextExhibit);
            destinationCopy.remove(new Pair<>(currentExhibit, true));
            currentExhibit = nextShortest;

        } while (destinationCopy.size() > 1);

        PlanListItem currentLoc = currentExhibit;
        ArrayList<Pair<PlanListItem, Boolean>> exit = new ArrayList<>() {
            {
                add(new Pair<>(ENTRANCE, false));
            }
        };

        ArrayList<Pair<PlanListItem, Boolean>> entrance = new ArrayList<>() {
            {
                add(new Pair<>(ENTRANCE, false));
            }
        };

        //Add exit/entrance gate to front and back of the plan
        plan.add(getShortestDistance(currentLoc, exit));
        GraphNavigationStep pathFromFirstExhibitToEntrance = getShortestDistance(plan.get(0).planItem, entrance);
        plan.add(0, pathFromFirstExhibitToEntrance);

    }


    private GraphNavigationStep getShortestDistance(PlanListItem src, List<Pair<PlanListItem, Boolean>> destinations) {

        GraphNavigationStep shortest = new GraphNavigationStep(src, Integer.MAX_VALUE, null);
        for (Pair<PlanListItem, Boolean> visitingExhibit : destinations) {
            if (visitingExhibit.getSecond()) continue;
            String srcId = src.exhibit_id;
            String destId = visitingExhibit.getFirst().exhibit_id;

            if (src.parent_exhibit_id != null) {
                srcId = src.parent_exhibit_id;
            }

            if (visitingExhibit.getFirst().parent_exhibit_id != null) {
                destId = visitingExhibit.getFirst().parent_exhibit_id;
            }

            GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(map.g, srcId, destId);
            int totalDist = 0;
            for (IdentifiedWeightedEdge e : path.getEdgeList()) {
                totalDist += map.g.getEdgeWeight(e);
            }

            if (totalDist < shortest.totalDist) {
                shortest = new GraphNavigationStep(visitingExhibit.getFirst(), totalDist, path);
            }

        }

        Log.i("Path Finding", "Finding shortest path" + shortest);
        for (var item : destinations) {
            if (Objects.equals(item.getFirst().exhibit_id, shortest.planItem.exhibit_id)) {
                item.setSecond(true);
            }
        }
        return shortest;
    }


    @Override
    public PlanListItem getExhibit() {
        return plan.get(currentIndex).planItem;
    }

    @Override
    public String getDirection() {
        //set the starting location to be the previous
        //TODO: change currentLoc to be user location
        String currentLoc = ENTRANCE.exhibit_id;

        if (peekPrevious() != null) {
            currentLoc = peekPrevious().getSecond().getExhibitId();
        }


        StringBuilder directions = new StringBuilder();

        PlanListItem exhibitToVisit = plan.get(currentIndex).planItem;

        GraphPath<String, IdentifiedWeightedEdge> currentPath = plan.get(currentIndex).graphPath;
        List<IdentifiedWeightedEdge> paths = currentPath.getEdgeList();
        for (int i = 0; i < paths.size(); i++) {
            IdentifiedWeightedEdge e = paths.get(i);

            directions.append("Proceed on ").append(Objects.requireNonNull(map.streetInfo.get(e.getId())).street).append(" ");
            directions.append((int) map.g.getEdgeWeight(e)).append(" feet");
            if (i == paths.size() - 1) {
                directions.append(" to ");
            } else {
                directions.append(" towards ");
            }
            if (currentLoc.equals(map.g.getEdgeSource(e))) {
                directions.append(map.getExhibitNameById(map.g.getEdgeTarget(e)));
                currentLoc = map.g.getEdgeTarget(e);
            } else {
                directions.append(map.getExhibitNameById(map.g.getEdgeSource(e)));
                currentLoc = map.g.getEdgeSource(e);
            }

            if (i != paths.size() - 1) {
                directions.append("\n");
            }
        }

        if (exhibitToVisit.parent_exhibit_id != null) {
            directions.append("\n");
            directions.append(String.format("Find %s inside %s", exhibitToVisit.exhibit_name,
                    map.getExhibitNameById(exhibitToVisit.parent_exhibit_id)));
        }

        if(paths.size() == 0){
            directions.append(String.format("Find %s nearby", exhibitToVisit.exhibit_name));

        }
        return directions.toString();
    }

    public void updatePreviewDistance(ZooLocation currentPosition){
        String currentLoc = currentPosition.getNearestLandmark().id;
        Pair<Integer, PlanListItem> nextExhibit = peekNext();
        Pair<Integer, PlanListItem> previousExhibit = peekPrevious();


        if(nextExhibit!= null) {
            GraphPath<String, IdentifiedWeightedEdge> pathToNext =
                    DijkstraShortestPath.findPathBetween(map.g, currentLoc, nextExhibit.getSecond().getExhibitId());
            int dist = 0;
            for (int i = 0; i < pathToNext.getEdgeList().size(); i++) {
                IdentifiedWeightedEdge e = pathToNext.getEdgeList().get(i);
                dist += map.g.getEdgeWeight(e);
            }
            plan.get(currentIndex + 1).totalDist = dist;
        }

        if(previousExhibit!= null) {
            GraphPath<String, IdentifiedWeightedEdge> pathToPrevious =
                    DijkstraShortestPath.findPathBetween(map.g, currentLoc, previousExhibit.getSecond().getExhibitId());
            int dist = 0;
            for (int i = 0; i < pathToPrevious.getEdgeList().size(); i++) {
                IdentifiedWeightedEdge e = pathToPrevious.getEdgeList().get(i);
                dist += map.g.getEdgeWeight(e);
            }
            plan.get(currentIndex - 1).totalDist = dist;

        }

    }

    @Override
    public String calcLocationBasedDirections(ZooLocation currentPosition, boolean isBrief) {

        String currentLoc = currentPosition.getNearestLandmark().id;

        StringBuilder directions = new StringBuilder();

        PlanListItem exhibitToVisit = plan.get(currentIndex).planItem;
        GraphPath<String, IdentifiedWeightedEdge> currentPath;
        currentPath = DijkstraShortestPath.findPathBetween(map.g, currentLoc, exhibitToVisit.getExhibitId());

        if(isBrief) return getBriefDirection(currentPath);
        /* NO! use location not existing edge list */
        List<IdentifiedWeightedEdge> paths = currentPath.getEdgeList();
        for (int i = 0; i < paths.size(); i++) {
            IdentifiedWeightedEdge e = paths.get(i);

            directions.append("Proceed on ").append(Objects.requireNonNull(map.streetInfo.get(e.getId()).street)).append(" ");
            directions.append((int) map.g.getEdgeWeight(e)).append(" feet");
            if (i == paths.size() - 1) {
                directions.append(" to ");
            } else {
                directions.append(" towards ");
            }
            if (currentLoc.equals(map.g.getEdgeSource(e))) {
                directions.append(map.getExhibitNameById(map.g.getEdgeTarget(e)));
                currentLoc = map.g.getEdgeTarget(e);
            } else {
                directions.append(map.getExhibitNameById(map.g.getEdgeSource(e)));
                currentLoc = map.g.getEdgeSource(e);
            }

            if (i != paths.size() - 1) {
                directions.append("\n");
            }
        }

        if (exhibitToVisit.parent_exhibit_id != null) {
            directions.append("\n");
            directions.append(String.format("Find %s inside %s", exhibitToVisit.exhibit_name,
                    map.getExhibitNameById(exhibitToVisit.parent_exhibit_id)));
        }
        if(paths.size() == 0){
            directions.append(String.format("Find %s nearby", exhibitToVisit.exhibit_name));

        }
        updatePreviewDistance(currentPosition);
        return directions.toString();
    }

    /**
     * Get directions from the next exhibit to the current displayed exhibit, without taking
     * into account user's location
     */
    @Override
    public String getPreviousDirection() {
        String currentLoc = ENTRANCE.exhibit_id;
        if (peekPrevious() != null) {
            currentLoc = peekNext().getSecond().getExhibitId();
        }

        StringBuilder directions = new StringBuilder();
        PlanListItem exhibitToVisit = plan.get(currentIndex).planItem;

        GraphPath<String, IdentifiedWeightedEdge> currentPath = plan.get(currentIndex + 1).graphPath;
        List<IdentifiedWeightedEdge> paths = currentPath.getEdgeList();
        for (int i = paths.size() - 1; i >= 0; i--) {
            IdentifiedWeightedEdge e = paths.get(i);

            directions.append("Proceed on ").append(Objects.requireNonNull(map.streetInfo.get(e.getId())).street).append(" ");
            directions.append((int) map.g.getEdgeWeight(e)).append(" feet");
            if (i == paths.size() - 1) {
                directions.append(" to ");
            } else {
                directions.append(" towards ");
            }
            if (currentLoc.equals(map.g.getEdgeSource(e))) {
                directions.append(map.getExhibitNameById(map.g.getEdgeTarget(e)));
                currentLoc = map.g.getEdgeTarget(e);
            } else {
                directions.append(map.getExhibitNameById(map.g.getEdgeSource(e)));
                currentLoc = map.g.getEdgeSource(e);
            }
            directions.append("\n");

        }

        if (exhibitToVisit.parent_exhibit_id != null) {
            directions.append(String.format("Find %s inside %s", exhibitToVisit.exhibit_name,
                    map.getExhibitNameById(exhibitToVisit.parent_exhibit_id)));
        }
        return directions.toString();
    }

    @Override
    public String getBriefDirection(GraphPath<String, IdentifiedWeightedEdge> path) {

        StringBuilder directions = new StringBuilder();
        PlanListItem exhibitToVisit = plan.get(currentIndex).planItem;
        GraphPath<String, IdentifiedWeightedEdge> currentPath = path;
        List<IdentifiedWeightedEdge> paths = currentPath.getEdgeList();
        String currentLoc = path.getStartVertex();

        int pathWeight = 0;
        String curStreet;
        String nextStreet;
        for (int i = 0; i < paths.size(); i++) {
            IdentifiedWeightedEdge e = paths.get(i);
            curStreet = Objects.requireNonNull(map.streetInfo.get(paths.get(i).getId())).street;
            if(i< paths.size()-1){
                nextStreet = Objects.requireNonNull(map.streetInfo.get(paths.get(i+1).getId())).street;
            }else{
                nextStreet = " ";
            }
            pathWeight+=(int) map.g.getEdgeWeight(e);

            if(curStreet.equals(nextStreet)){
                currentLoc = map.g.getEdgeTarget(e);
                continue;
            }else{
                directions.append("Proceed on ").append(Objects.requireNonNull(map.streetInfo.get(e.getId())).street).append(" ");
                directions.append((int) pathWeight).append(" feet");
                pathWeight = 0;
            }
            if (i == paths.size() - 1) {
                directions.append(" to ");
            } else {
                directions.append(" towards ");
            }
            if (currentLoc.equals(map.g.getEdgeSource(e))) {
                directions.append(map.getExhibitNameById(map.g.getEdgeTarget(e)));
                currentLoc = map.g.getEdgeTarget(e);
            } else {
                directions.append(map.getExhibitNameById(map.g.getEdgeSource(e)));
                currentLoc = map.g.getEdgeSource(e);
            }

            if (i != paths.size() - 1) {
                directions.append("\n");
            }
        }

        if (exhibitToVisit.parent_exhibit_id != null) {
            directions.append("\n");
            directions.append(String.format("Find %s inside %s", exhibitToVisit.exhibit_name,
                    map.getExhibitNameById(exhibitToVisit.parent_exhibit_id)));
        }

        if(paths.size() == 0){
            directions.append(String.format("Find %s nearby", exhibitToVisit.exhibit_name));

        }
        return directions.toString();
    }

    @Override
    public int getDistance() {
        return plan.get(currentIndex).totalDist;
    }

    @Override
    public int getPreviousDistance() { return plan.get(currentIndex-1).totalDist; }

    public boolean next() {
        if (currentIndex + 1 < plan.size()) {
            getExhibit().visited = true;
            currentIndex++;
            return true;
        }
        return false;
    }

    @Override
    public boolean previous() {
        if (currentIndex - 1 >= 0) {
            currentIndex--;
            getExhibit().visited = false;
            return true;
        }
        return false;
    }

    @Override
    public Pair<Integer, PlanListItem> peekPrevious() {
        if (currentIndex - 1 >= 0) {
            int dis = plan.get(currentIndex - 1).totalDist;
            PlanListItem exhibit = plan.get(currentIndex - 1).planItem;
            return new Pair<>(dis, exhibit);
        }
        return null;
    }

    @Override
    public Pair<Integer, PlanListItem> peekNext() {
        if (currentIndex + 1 < plan.size()) {
            int dis = plan.get(currentIndex + 1).totalDist;
            PlanListItem exhibit = plan.get(currentIndex + 1).planItem;
            return new Pair<>(dis, exhibit);
        }
        return null;
    }

    @Override
    public void regenerateOptimizedPlan(PlanListItem nearestLandMark) {

        List<PlanListItem> newDestinations = new ArrayList<>();
        for (int i = currentIndex; i < plan.size()-1; i++) {
            newDestinations.add(plan.get(i).planItem);

        }
        //Only replan when we have more exhibits to visit
        if(!newDestinations.isEmpty()){
            //Need to exclude front and exit gate from the plan, as they will always be added as part of plan by default
            List<GraphNavigationStep> newPlan = plan.subList(1, currentIndex);
            this.startingExhibit = nearestLandMark;
            this.plan = newPlan;
            createPlan(newDestinations);
        }


    }


    @Override
    public void skipCurrentExhibit(PlanListItem nearestLandMark) {
        this.plan.remove(currentIndex);
        regenerateOptimizedPlan(nearestLandMark);
    }

    @Override
    public List<PlanListItem> findOptimizedPath() {

        List<PlanListItem> optimizedPlan = new ArrayList<>();
        if (plan.size() <= 1) return optimizedPlan;
        int totalDist = 0;
        List<IdentifiedWeightedEdge> firstPath = plan.get(0).graphPath.getEdgeList();
        String prevStreet = Objects.requireNonNull(map.streetInfo.get(firstPath.get(firstPath.size() - 1).getId())).street;

        for (GraphNavigationStep route : plan) {
            if (route.planItem != ENTRANCE) {
                PlanListItem newPlanItem = PlanListItem.deepCopy(route.planItem);
                totalDist += route.totalDist;
                newPlanItem.setDist(totalDist);
                List<IdentifiedWeightedEdge> currentPath = route.graphPath.getEdgeList();
                String loc;
                if (currentPath.isEmpty()) {
                    loc = prevStreet;
                } else {
                    loc = Objects.requireNonNull(map.streetInfo.get(currentPath.get(currentPath.size() - 1).getId())).street;
                    prevStreet = loc;
                }

                newPlanItem.setLoc(loc);
                optimizedPlan.add(newPlanItem);
            }
        }

        return optimizedPlan;
    }

    @Override
    public Set<String> getLandMarksOnPath() {
        Set<String> nearestLandMark = new HashSet<>() {
        };
        GraphPath<String, IdentifiedWeightedEdge> currentPath = plan.get(currentIndex).graphPath;
        List<IdentifiedWeightedEdge> paths = currentPath.getEdgeList();
        for (IdentifiedWeightedEdge e : paths) {
            nearestLandMark.add(map.g.getEdgeTarget(e));
            nearestLandMark.add(map.g.getEdgeSource(e));

        }
        return nearestLandMark;
    }

    @Override
    public Set<PlanListItem> findNonVisitedExhibits() {
        Set<PlanListItem> nonVisitedIds = new HashSet<>();
        for (GraphNavigationStep route : plan) {
            if (!route.planItem.visited) {
                nonVisitedIds.add(route.planItem);
            }
        }
        return nonVisitedIds;
    }

    @Override
    public double minDistanceFromUnvisited(ZooLocation loc) {
        double minimum = Double.MAX_VALUE;
        for (PlanListItem item : findNonVisitedExhibits()) {
            if (item.exhibit_id.equals(getExhibit().exhibit_id)) continue;
            if (item.exhibit_id.equals(ENTRANCE.exhibit_id)) continue;

            ZooData.VertexInfo vertex = map.exhibitInfo.get(item.exhibit_id);

            float[] results = new float[1];
            assert vertex != null;
            Location.distanceBetween(loc.getLatLng().lat, loc.getLatLng().lng, vertex.lat, vertex.lng, results);
            if (results[0] < minimum) {
                minimum = results[0];
            }
        }
        return minimum;
    }

    @Override
    public Coord getCurrentLocation() {
        ZooData.VertexInfo vertex = map.exhibitInfo.get(getExhibit().exhibit_id);
        assert vertex != null;
        return new Coord(vertex.lat, vertex.lng);
    }

    public GraphPath<String, IdentifiedWeightedEdge> getCurrentPath(){
        return plan.get(currentIndex).graphPath;
    }
}