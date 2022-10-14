package com.example.zooseeker_jj_zaaz_team_52;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import com.example.zooseeker_jj_zaaz_team_52.ZooData.VertexInfo;
import com.example.zooseeker_jj_zaaz_team_52.location.LatLngs;
import com.example.zooseeker_jj_zaaz_team_52.location.LocationModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.fakes.RoboMenuItem;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Test DirectionsActivity UI updates correctly for different stages of directions and traversal
 */
@RunWith(AndroidJUnit4.class)
public class DirectionsActivityTest {
    ZooNavigator navigator;
    Intent intent;
    Map<String, VertexInfo> zooData;
    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");
    @Rule
    public InstantTaskExecutorRule execRule = new InstantTaskExecutorRule();

    @Before
    public void init() {
        intent = new Intent(ApplicationProvider.getApplicationContext(), DirectionsActivity.class);


        ArrayList<PlanListItem> items = new ArrayList<>() {
            {
                add(new PlanListItem("Flamingos", "flamingo"));
                add(new PlanListItem("Koi Fish", "koi"));
                add(new PlanListItem("Gorillas", "gorilla"));
                add(new PlanListItem("Hippos", "hippo"));
            }
        };
        var context = ApplicationProvider.getApplicationContext();
        ZooGraph zooMap = new ZooGraph(context.getString(R.string.node_file), context.getString(R.string.graph_file), context.getString(R.string.edge_file), context);
        navigator = new ZooShortestNavigator(items, ApplicationProvider.getApplicationContext(), zooMap);
        intent.putExtra("CurrentNavigator", navigator);
        loadJson(ApplicationProvider.getApplicationContext());

    }

    /**
     * Test previous button cannot be clicked on the first exhibit directions
     */
    @Test
    public void testPreviousButtonDisabledAtStart() {

        try (ActivityScenario<DirectionsActivity> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                scenario.moveToState(Lifecycle.State.CREATED);
                scenario.moveToState(Lifecycle.State.STARTED);
                scenario.moveToState(Lifecycle.State.RESUMED);

                Button previousButton = activity.findViewById(R.id.previous_btn);
                previousButton.callOnClick();
                assertFalse(previousButton.isEnabled());
            });
        }
    }


    @Test
    public void clearExhibit() {

        try (ActivityScenario<DirectionsActivity> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                scenario.moveToState(Lifecycle.State.CREATED);
                scenario.moveToState(Lifecycle.State.STARTED);
                scenario.moveToState(Lifecycle.State.RESUMED);

                Button previousButton = activity.findViewById(R.id.previous_btn);
                previousButton.callOnClick();
                assertFalse(previousButton.isEnabled());
                assertEquals(0,PlanDatabase.getSingleton(ApplicationProvider.getApplicationContext()).planListItemDao().getAll().size());


            });
        }
    }



    /**
     * Test next button can be clicked on the first exhibit directions
     */
    @Test
    public void testNextButtonEnabledAtStart() {
        try (ActivityScenario<DirectionsActivity> scenario = ActivityScenario.launch(intent)) {

            scenario.onActivity(activity -> {
                scenario.moveToState(Lifecycle.State.CREATED);
                scenario.moveToState(Lifecycle.State.STARTED);
                scenario.moveToState(Lifecycle.State.RESUMED);

                Button nextButton = activity.findViewById(R.id.next_btn);
                assertTrue(nextButton.isEnabled());
            });
        }
    }

    /**
     * Test next button cannot be clicked on directions to the exit gate
     */
    @Test
    public void testNextButtonDisabledAtEnd() {
        try (ActivityScenario<DirectionsActivity> scenario = ActivityScenario.launch(intent)) {

            scenario.onActivity(activity -> {

                scenario.moveToState(Lifecycle.State.CREATED);
                scenario.moveToState(Lifecycle.State.STARTED);
                scenario.moveToState(Lifecycle.State.RESUMED);
                // Exhibit 1 (start) -> Exhibit 2 from 1 -> Exhibit 3 from 2 -> Exit Gate
                Button nextButton = activity.findViewById(R.id.next_btn);
                nextButton.callOnClick(); // At Exhibit 2
                nextButton.callOnClick(); // At Exhibit 3
                nextButton.callOnClick(); // At Exhibit 4
                nextButton.callOnClick(); // Exit Gate
                assertFalse(nextButton.isEnabled());
            });
        }
    }

    /**
     * Test exhibit names are accurately displayed on UI in directions subheading for directions to
     * that exhibit
     */
    @Test
    public void testExhibitNames() {
        try (ActivityScenario<DirectionsActivity> scenario = ActivityScenario.launch(intent)) {
            scenario.moveToState(Lifecycle.State.CREATED);
            scenario.moveToState(Lifecycle.State.STARTED);
            scenario.moveToState(Lifecycle.State.RESUMED);

            scenario.onActivity(activity -> {
                TextView exhibitTextView = activity.findViewById(R.id.exhibit_name);
                Button nextButton = activity.findViewById(R.id.next_btn);
                Button prevButton = activity.findViewById(R.id.previous_btn);
                MenuItem directionsToggle = activity.findViewById(R.id.DirectionsMode);

                prevButton.callOnClick();
                assertFalse(prevButton.isEnabled());
                assertEquals("Entrance and Exit Gate", exhibitTextView.getText());
                nextButton.callOnClick();
                assertTrue(nextButton.isEnabled());
                assertEquals("Koi Fish", exhibitTextView.getText());

                nextButton.callOnClick();
                assertTrue(nextButton.isEnabled());
                assertTrue(prevButton.isEnabled());
                assertEquals("Flamingos", exhibitTextView.getText());

                nextButton.callOnClick();
                assertTrue(nextButton.isEnabled());
                assertTrue(prevButton.isEnabled());
                assertEquals("Hippos", exhibitTextView.getText());

                nextButton.callOnClick();
                assertTrue(nextButton.isEnabled());
                assertTrue(prevButton.isEnabled());
                assertEquals("Gorillas", exhibitTextView.getText());

                nextButton.callOnClick();
                assertFalse(nextButton.isEnabled());
                assertTrue(prevButton.isEnabled());
                assertEquals("Entrance and Exit Gate", exhibitTextView.getText());

            });
        }
    }


    private static final String DIRECTIONS_TO_KOI_FISH =
            "Proceed on Gate Path 10 feet towards Front Street / Treetops Way\n" +
                    "Proceed on Front Street 30 feet towards Front Street / Terrace Lagoon Loop (South)\n" +
                    "Proceed on Terrace Lagoon Loop 20 feet to Koi Fish";

    private static final LatLng FRONT_ST_TERRACE_LAGOON_LOOP_SOUTH = new LatLng(32.72726737662313, -117.15496383006723);
    private static final LatLng FRONT_ST_TREETOPS_WAY = new LatLng(32.735546539459556, -117.1521136981983);
    private static final LatLng FRONT_ST_TERRACE_LAGOON_LOOP_NORTH = new LatLng(32.72442520989079, -117.16066409380507);
    private static final LatLng GORILLAS_EXHIBIT = new LatLng(32.74812588554637, -117.17565073656901);
    /**
     * Test that the directions to the next exhibit are displayed correctly when user walks away from
     * path
     */
    @Test
    public void testMinimalLocationChange() {

        try (ActivityScenario<DirectionsActivity> scenario = ActivityScenario.launch(intent)) {
            scenario.moveToState(Lifecycle.State.CREATED);
            scenario.moveToState(Lifecycle.State.STARTED);
            scenario.moveToState(Lifecycle.State.RESUMED);

            scenario.onActivity(activity -> {
                LocationModel model = activity.getModel();
                model.setZooData(zooData);
                model.mockLocation(LatLngs.toCoord(FRONT_ST_TREETOPS_WAY));
                model.mockRoute(LatLngs.toCoordsList(LatLngs.pointsBetween(FRONT_ST_TREETOPS_WAY, FRONT_ST_TERRACE_LAGOON_LOOP_SOUTH, 30)), 100, TimeUnit.MILLISECONDS);
            });

            Thread.sleep(5000);

            scenario.onActivity(activity -> {

                TextView directionsTextView = activity.findViewById(R.id.directions_view);
                assertFalse(directionsTextView.getText().toString().toLowerCase(Locale.ROOT).contains("gate path"));
                assertEquals("intxn_front_lagoon_1", activity.currentPosition.getNearestLandmark().id);
                TextView exhibitTextView = activity.findViewById(R.id.exhibit_name);
                LocationModel model = activity.getModel();
                model.mockRoute(LatLngs.toCoordsList(LatLngs.pointsBetween(FRONT_ST_TERRACE_LAGOON_LOOP_SOUTH, FRONT_ST_TERRACE_LAGOON_LOOP_NORTH, 30)), 100, TimeUnit.MILLISECONDS);
            });

            Thread.sleep(5000);

            scenario.onActivity(activity -> {
                assertEquals("intxn_front_lagoon_2", activity.currentPosition.getNearestLandmark().id);
                TextView exhibitTextView = activity.findViewById(R.id.exhibit_name);
                TextView directionsTextView = activity.findViewById(R.id.directions_view);
                assertEquals("Koi Fish", exhibitTextView.getText());
                LocationModel model = activity.getModel();
                assertNotEquals(DIRECTIONS_TO_KOI_FISH, directionsTextView.getText());
                assertEquals("Proceed on Terrace Lagoon Loop 30 feet to Koi Fish", directionsTextView.getText());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMajorLocationChange() {
        try (ActivityScenario<DirectionsActivity> scenario = ActivityScenario.launch(intent)) {
            scenario.moveToState(Lifecycle.State.CREATED);
            scenario.moveToState(Lifecycle.State.STARTED);
            scenario.moveToState(Lifecycle.State.RESUMED);

            scenario.onActivity(activity -> {
                LocationModel model = activity.getModel();
                model.setZooData(zooData);
                assertFalse(activity.offeredReplan);
                model.mockLocation(LatLngs.toCoord(FRONT_ST_TREETOPS_WAY));
                model.mockRoute(LatLngs.toCoordsList(LatLngs.pointsBetween(FRONT_ST_TREETOPS_WAY, GORILLAS_EXHIBIT, 50)), 100, TimeUnit.MILLISECONDS);
            });

            Thread.sleep(5000);
            scenario.onActivity(activity -> assertTrue(activity.offeredReplan));
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void loadJson(Context context) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(context.getString(R.string.node_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Reader reader = new InputStreamReader(inputStream);

        Gson gson = new Gson();
        Type type = new TypeToken<List<VertexInfo>>() {
        }.getType();
        List<VertexInfo> zooData = gson.fromJson(reader, type);

        this.zooData = zooData.stream().collect(Collectors.toMap(v -> v.id, datum -> datum));
    }

}
