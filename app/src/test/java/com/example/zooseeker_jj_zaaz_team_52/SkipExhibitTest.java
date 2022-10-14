package com.example.zooseeker_jj_zaaz_team_52;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

/**
 * Test that skip button functionality works and that skip triggers a replan of the
 * remaining unvisited exhibits
 */
@RunWith(AndroidJUnit4.class)
public class SkipExhibitTest {

    ZooNavigator nav;
    ZooNavigator customNav;
    ZooNavigator newNav;

    ZooGraph zooMap;
    ZooGraph newZooMap;

    @Before
    public void setup() {
        ArrayList<PlanListItem> items = new ArrayList<PlanListItem>() {
            {
                add(new PlanListItem("Gorillas", "gorillas"));
                add(new PlanListItem("Alligators", "gators"));
                add(new PlanListItem("Lions", "lions"));
            }
        };


        ArrayList<PlanListItem> items2 = new ArrayList<PlanListItem>() {
            {
                add(new PlanListItem("Flamingo", "flamingo"));
                add(new PlanListItem("Capuchin", "capuchin"));
                add(new PlanListItem("Gorilla", "gorilla"));
            }
        };

        PlanListItem startExhibit = new PlanListItem("Fox", "arctic_foxes");
        PlanListItem startGate = new PlanListItem("Entrance and Exit Gate", "entrance_exit_gate");

        zooMap = new ZooGraph(ApplicationProvider.getApplicationContext().getString(R.string.sample_node_file), ApplicationProvider.getApplicationContext().getString(R.string.sample_graph_file), ApplicationProvider.getApplicationContext().getString(R.string.sample_edge_file), ApplicationProvider.getApplicationContext());

        newZooMap = new ZooGraph(ApplicationProvider.getApplicationContext().getString(R.string.node_file), ApplicationProvider.getApplicationContext().getString(R.string.graph_file), ApplicationProvider.getApplicationContext().getString(R.string.edge_file), ApplicationProvider.getApplicationContext());

        //Test navigator with starting gate
        nav = new ZooShortestNavigator(startGate, items, ApplicationProvider.getApplicationContext(), zooMap);

        newNav = new ZooShortestNavigator(startGate, items2, ApplicationProvider.getApplicationContext(), newZooMap);


        //Test navigator with a custom starting exhibit
        customNav = new ZooShortestNavigator(startExhibit, items, ApplicationProvider.getApplicationContext(), zooMap);
    }


    /**
     * Skips an exhibit while near the exhibit
     */
    @Test
    public void skipExhibitNearBy() {

        assertEquals(nav.getExhibit().exhibit_name, "Alligators");
        nav.next();
        assertEquals(nav.getExhibit().exhibit_name, "Lions");

        //If lion is skipped, gorillas should be next
        nav.skipCurrentExhibit(new PlanListItem("Lions", "lions"));
        assertEquals(nav.getExhibit().exhibit_name, "Gorillas");

        //the previous one should be alligators
        assertEquals(nav.peekPrevious().getSecond().exhibit_name, "Alligators");
        assertEquals(nav.peekNext().getSecond().exhibit_name, "Entrance and Exit Gate");

    }


    /**
     * Skips an exhibit while near some other exhibit
     */
    @Test
    public void skipsExhibitFarAway() {
        assertEquals(nav.getExhibit().exhibit_name, "Alligators");
        //Skipping alligators when near gorilla, should replan to gorilla
        nav.skipCurrentExhibit(new PlanListItem("Gorillas", "gorillas"));
        assertEquals(nav.getExhibit().exhibit_name, "Gorillas");
    }

}
