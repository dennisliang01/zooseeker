package com.example.zooseeker_jj_zaaz_team_52;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Test that ZooNavigator accurately maintains current state of directions when traversing
 * directions of created plan
 */
@RunWith(AndroidJUnit4.class)
public class ZooNavigatorTest {

    ZooNavigator nav;
    ZooNavigator customNav;
    ZooNavigator newNav;

    ZooGraph zooMap;
    ZooGraph newZooMap;
    ZooNavigator customNav2;

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

        ArrayList<PlanListItem> items3 = new ArrayList<PlanListItem>() {
            {
                add(new PlanListItem("Crocodiles", "crocodile"));
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

        customNav2 = new ZooShortestNavigator(startGate, items3, ApplicationProvider.getApplicationContext(), newZooMap);

    }


    /**
     * Tests for the correct exhibits in a simple path through the zoo
     */
    @Test
    public void testSimpleOptimizedPath() {

        assertEquals("Entrance and Exit Gate", nav.peekPrevious().getSecond().exhibit_name);
        assertEquals(nav.getExhibit().exhibit_name, "Alligators");
        nav.next();
        assertEquals(nav.getExhibit().exhibit_name, "Lions");
        assertEquals(nav.peekNext().getSecond().exhibit_name, "Gorillas");
        nav.next();
        assertEquals(nav.getExhibit().exhibit_name, "Gorillas");
        nav.next();
        assertEquals("Entrance and Exit Gate", nav.getExhibit().exhibit_name);
        assertNull(nav.peekNext());
        assertFalse(nav.next());

    }


    /**
     * Tests for the correct directions in a simple path through the zoo
     */
    @Test
    public void testDirection() {
        assertEquals("Proceed on Entrance Way 10 feet towards Entrance Plaza\n" +
                "Proceed on Reptile Road 100 feet to Alligators", nav.getDirection());
        nav.next();
        assertEquals("Proceed on Sharp Teeth Shortcut 200 feet to Lions", nav.getDirection());
        nav.next();
        assertEquals("Proceed on Africa Rocks Street 200 feet to Gorillas", nav.getDirection());
    }

    /**
     * Test direction when getting next direction for each exhibit along the path.
     */
    @Test
    public void testPathWithGetNextDirection() {
        assertEquals("Proceed on Arctic Avenue 300 feet towards Entrance Plaza\n" +
                        "Proceed on Reptile Road 100 feet to Alligators",
                customNav.getDirection()
        );
        assertEquals(400, customNav.getDistance());
        customNav.next();
        assertEquals("Proceed on Sharp Teeth Shortcut 200 feet to Lions",
                customNav.getDirection()
        );
        assertEquals(200, customNav.getDistance());
        customNav.next();
        assertEquals("Proceed on Africa Rocks Street 200 feet to Gorillas",
                customNav.getDirection()
        );
        assertEquals(200, customNav.getDistance());
    }

    /**
     * Test direction when getting previous direction for each exhibit along the path.
     */
    @Test
    public void testPathWithGetPreviousDirection() {
        assertEquals("Proceed on Sharp Teeth Shortcut 200 feet to Alligators\n",
                customNav.getPreviousDirection()
        );

        assertEquals(400, customNav.getDistance());
        customNav.previous();
        assertEquals("Proceed on Reptile Road 100 feet to Entrance Plaza\nProceed on Arctic Avenue 300 feet towards Arctic Foxes\n",
                customNav.getPreviousDirection()
        );

        customNav.next();
        assertEquals("Proceed on Sharp Teeth Shortcut 200 feet to Alligators\n", customNav.getPreviousDirection());

        customNav.next();

        assertEquals(400, customNav.getPreviousDistance());
        assertEquals(200, customNav.getDistance());
        assertEquals("Proceed on Africa Rocks Street 200 feet to Lions\n",
                customNav.getPreviousDirection()
        );
    }

    @Test
    public void testBriefNavigators() {
        final PlanListItem ENTRANCE = new PlanListItem("Entrance and Exit Gate", "entrance_exit_gate");
        String currentLoc = ENTRANCE.exhibit_id;
        String desId = "crocodile";
        assertEquals("Proceed on Gate Path 10 feet towards Front Street / Treetops Way\n" +
                "Proceed on Treetops Way 30 feet towards Treetops Way / Fern Canyon Trail\n" +
                "Proceed on Treetops Way 30 feet towards Treetops Way / Orangutan Trail\n" +
                "Proceed on Treetops Way 100 feet towards Treetops Way / Hippo Trail\n" +
                "Proceed on Hippo Trail 30 feet towards Hippos\n" +
                "Proceed on Hippo Trail 10 feet to Crocodiles", customNav2.getDirection());
        GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(newZooMap.g, currentLoc, desId);
        assertEquals("Proceed on Gate Path 10 feet towards Front Street / Treetops Way\n" +
                "Proceed on Treetops Way 160 feet towards Treetops Way / Hippo Trail\n" +
                "Proceed on Hippo Trail 40 feet to Crocodiles", customNav2.getBriefDirection(path));
    }
}
