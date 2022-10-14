package com.example.zooseeker_jj_zaaz_team_52;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.content.Intent;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import com.example.zooseeker_jj_zaaz_team_52.location.Coord;
import com.example.zooseeker_jj_zaaz_team_52.location.TestLocationAwareActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test that app grants permissions to use the user's current location
 */
@RunWith(AndroidJUnit4.class)
public class TestLocationAccess {

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");
    @Rule
    public InstantTaskExecutorRule execRule = new InstantTaskExecutorRule();

    /**
     * {
     * "id": "scripps_aviary",
     * "kind": "exhibit_group",
     * "name": "Scripps Aviary",
     * "tags": [],
     * "lat": 32.748538318135594,
     * "lng": -117.17255093386991
     * }
     */
    @Test
    public void testNearestExhibit() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), TestLocationAwareActivity.class);
        try (ActivityScenario<TestLocationAwareActivity> scenario = ActivityScenario.launch(intent)) {
            scenario.moveToState(Lifecycle.State.CREATED);
            scenario.moveToState(Lifecycle.State.STARTED);
            scenario.moveToState(Lifecycle.State.RESUMED);
            scenario.onActivity(activity -> activity.getModel().mockLocation(new Coord(32., -117.)));

            scenario.onActivity(activity -> {
                assertEquals("koi", activity.getPos().getNearestLandmark().id);

                activity.getModel().mockLocation(new Coord(32.748538318135594, -117.17255093386991));
            });

            scenario.onActivity(activity -> assertEquals("scripps_aviary", activity.getPos().getNearestLandmark().id));
        }

    }

}
