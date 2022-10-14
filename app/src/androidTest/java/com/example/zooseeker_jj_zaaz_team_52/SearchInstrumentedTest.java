package com.example.zooseeker_jj_zaaz_team_52;

import android.content.SharedPreferences;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test scenarios of Search execute correctly on Android device
 */
@RunWith(AndroidJUnit4.class)
public class SearchInstrumentedTest {

    @Before
    public void clearDb() {
        PlanDatabase.getSingleton(ApplicationProvider.getApplicationContext()).planListItemDao().deleteAll();
        SharedPreferences preferences = ApplicationProvider.getApplicationContext().getSharedPreferences("sharedPrefs", 0);
        preferences.edit().remove("index").commit();
    }

    /**
     * Test that the default view of searchActivity displays correctly on Android device
     */
    @Test
    public void testDefaultView() {
        ActivityScenario<SearchActivity> scenario = ActivityScenario.launch(SearchActivity.class);

        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
            RecyclerView recyclerView = activity.getCourseRV();
            assertTrue(recyclerView.getAdapter() instanceof SearchShowAdapter);
        });
    }

    /**
     * Test that scenario where there are no search results and selected exhibits executes correctly
     * on Android device
     */
    @Test
    public void testEmptySearchAndSelect() {
        ActivityScenario<SearchActivity> scenario = ActivityScenario.launch(SearchActivity.class);

        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {

            RecyclerView recyclerView = activity.getCourseRV();
            ((ActionMenuItemView) activity.findViewById(R.id.actionSearch)).callOnClick();
            recyclerView.postDelayed(() -> {
                RecyclerView.ViewHolder firstVH = recyclerView.findViewHolderForAdapterPosition(0);
                assertNotNull(firstVH);
                ZooDataAdapter.ViewHolder viewHolder = ((ZooDataAdapter.ViewHolder) firstVH);
                boolean selected = viewHolder.getAnimalView().isSelected();
                viewHolder.getAnimalView().callOnClick();
                assertFalse(selected);
            }, 50);
        });
    }


}
