package com.example.zooseeker_jj_zaaz_team_52;

import static org.junit.Assert.assertEquals;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Test PlanViewActivity UI updates accurately when executing process of creating a plan on
 * Android device
 */
@RunWith(AndroidJUnit4.class)
public class CreatePlanTest {

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");
    @Rule
    public InstantTaskExecutorRule execRule = new InstantTaskExecutorRule();


    ArrayList<PlanListItem> items;
    ZooNavigator navigator;

    @Before
    public void setup() {
        PlanDatabase.getSingleton(ApplicationProvider.getApplicationContext()).planListItemDao().deleteAll();
        items = new ArrayList<PlanListItem>() {
            {
                add(new PlanListItem("Gorillas", "gorilla"));
                add(new PlanListItem("Crocodiles", "crocodile"));
                add(new PlanListItem("Orangutans", "orangutan"));
            }
        };

        navigator = new ZooShortestNavigator(items, ApplicationProvider.getApplicationContext());
    }

    /**
     * Test number of exhibits selected executes correctly on Android device
     */
    @Test
    public void testNumExhibit() {
        try (ActivityScenario<PlanViewActivity> scenario = ActivityScenario.launch(PlanViewActivity.class)) {
            scenario.moveToState(Lifecycle.State.CREATED);
            scenario.moveToState(Lifecycle.State.STARTED);
            scenario.moveToState(Lifecycle.State.RESUMED);

            scenario.onActivity(activity -> {
                //activity.adapter = new PlanViewAdapter(activity.findViewById(R.id.plan_title));
                activity.adapter.setPlanItems(items);
                TextView title = (TextView) activity.findViewById(R.id.plan_title);
                assertEquals("(3) Plan", title.getText().toString());
            });
        }
    }

    /**
     * Test plan created is optimal based on shortest distance and executes on Android device
     */
    @Test
    public void testPlanIsOptimal() {
        try (ActivityScenario<PlanViewActivity> scenario = ActivityScenario.launch(PlanViewActivity.class)) {
            scenario.moveToState(Lifecycle.State.CREATED);
            scenario.moveToState(Lifecycle.State.STARTED);
            scenario.moveToState(Lifecycle.State.RESUMED);

            scenario.onActivity(activity -> {

                activity.adapter = new PlanViewAdapter(activity.findViewById(R.id.plan_title));
                activity.adapter.setPlanItems(navigator);
                activity.recyclerView.setAdapter(activity.adapter);

                activity.recyclerView.postDelayed(() -> {
                    if (activity.recyclerView.findViewHolderForAdapterPosition(0) != null) {

                        TextView firstExhibit = (Objects.requireNonNull(activity.recyclerView.findViewHolderForAdapterPosition(0)).itemView.findViewById(R.id.plan_exhibit_name));
                        TextView secondExhibit = (Objects.requireNonNull(activity.recyclerView.findViewHolderForAdapterPosition(1)).itemView.findViewById(R.id.plan_exhibit_name));
                        TextView thirdExhibit = (Objects.requireNonNull(activity.recyclerView.findViewHolderForAdapterPosition(2)).itemView.findViewById(R.id.plan_exhibit_name));


                        TextView firstExhibitDist = (Objects.requireNonNull(activity.recyclerView.findViewHolderForAdapterPosition(0)).itemView.findViewById(R.id.plan_distance));
                        TextView secondExhibitDist = (Objects.requireNonNull(activity.recyclerView.findViewHolderForAdapterPosition(1)).itemView.findViewById(R.id.plan_distance));
                        TextView thirdExhibitDist = (Objects.requireNonNull(activity.recyclerView.findViewHolderForAdapterPosition(2)).itemView.findViewById(R.id.plan_distance));

                        assertEquals("Orangutans", firstExhibit.getText().toString());
                        assertEquals("Gorillas", thirdExhibit.getText().toString());
                        assertEquals("Crocodiles", secondExhibit.getText().toString());

                        //110ft from entrance to alligators
                        assertEquals("135ft", firstExhibitDist.getText().toString());

                        //200 feet from alligators to lions
                        assertEquals("285ft", secondExhibitDist.getText().toString());

                        //200 feet from lions to gorillas
                        assertEquals("445ft", thirdExhibitDist.getText().toString());
                    }
                }, 50);
            });
        }
    }
}