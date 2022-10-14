package com.example.zooseeker_jj_zaaz_team_52;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.zooseeker_jj_zaaz_team_52.location.Coord;
import com.example.zooseeker_jj_zaaz_team_52.location.LocationModel;
import com.example.zooseeker_jj_zaaz_team_52.location.ZooLocation;

import org.jgrapht.alg.util.Pair;

/**
 * DirectionsActivity Class: Single Responsibility - To maintain and update the view of
 * the DirectionsActivity UI as directions are traversed
 */
public class DirectionsActivity extends AppCompatActivity {
    final PlanListItem ENTRANCE = new PlanListItem("Entrance and Exit Gate", "entrance_exit_gate");
    public ZooLocation currentPosition = ZooLocation.STARTING_POINT;
    ZooNavigator currentNavigator;
    TextView directionsView;
    TextView exhibitName;
    TextView previousExhibit;
    TextView nextExhibit;
    Button nextButton;
    Button previousButton;
    Button skipButton;
    MenuItem briefToggle;
    Menu menu;
    boolean offeredReplan = false;
    LocationModel model;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY = "index";
    boolean showBrief = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        Intent intent = getIntent();
        currentNavigator = (ZooShortestNavigator) intent.getSerializableExtra("CurrentNavigator");
        saveData();
        // Initialize components of DirectionsActivity UI
        directionsView = this.findViewById(R.id.directions_view);
        exhibitName = this.findViewById(R.id.exhibit_name);
        previousExhibit = this.findViewById(R.id.previous_text);
        nextExhibit = this.findViewById(R.id.next_text);
        nextButton = this.findViewById(R.id.next_btn);
        previousButton = this.findViewById(R.id.previous_btn);
        skipButton = this.findViewById(R.id.skip_btn);
        model = new ViewModelProvider(this).get(LocationModel.class);
        model.getLastKnownCoords().observe(this, (zooLocation) -> {
            Log.i(this.getClass().toString(), String.format("Observing location model update to %s", zooLocation));
            updateUserLocation(zooLocation);
        });
        // Set DirectionsActivity UI for first exhibit upon creation of activity
        updateActivityView();
    }



    /**
     * Save the index of direction user has viewed in Direction.
     */
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY, currentNavigator.getCurrentIndex());
        editor.apply();
    }

    public void onPreviousButtonClicked(View view) {
        offeredReplan = false;
        currentNavigator.previous();
        updateActivityView();
        saveData();
    }

    public void onNextButtonClicked(View view) {
        offeredReplan = false;
        currentNavigator.next();
        saveData();
        updateActivityView();
    }

    public void updateActivityView() {
        exhibitName.setText(currentNavigator.getExhibit().exhibit_name);
        directionsView.setText(currentNavigator.calcLocationBasedDirections(currentPosition,showBrief));
        Pair<Integer, PlanListItem> previousExhibitInfo = currentNavigator.peekPrevious();
        Pair<Integer, PlanListItem> nextExhibitInfo = currentNavigator.peekNext();

        if (previousExhibitInfo != null) {
            previousButton.setEnabled(true);
            String display = previousExhibitInfo.getSecond().exhibit_name + " * " + previousExhibitInfo.getFirst();
            previousExhibit.setText(display);
        } else {
            previousButton.setEnabled(false);
            previousExhibit.setText("");
        }

        if (nextExhibitInfo != null) {
            nextButton.setEnabled(true);
            String display = nextExhibitInfo.getSecond().exhibit_name + " * " + nextExhibitInfo.getFirst();
            nextExhibit.setText(display);
        } else {
            nextButton.setEnabled(false);
            nextExhibit.setText("");
        }

        skipButton.setEnabled(currentNavigator.getExhibit().id != ENTRANCE.id);
    }

    /**
     * LocationObserver Interface method
     * The LocationObserver will call this method to update newPosition everytime it acquires a
     * new position value
     **/
    public void updateUserLocation(ZooLocation newPosition) {
        currentPosition = newPosition; //saving currentPosition for later use
        // this prompts a check on replan route ...
        checkToReplanRoute();
    }

    public void onSkipButtonClicked(View view) {
        offeredReplan = false;
        // Sync the database to delete the current exhibit the user skips.
        PlanListItemDao planListItemDao = PlanDatabase.getSingleton(this).planListItemDao();
        planListItemDao.delete(currentNavigator.getExhibit().exhibit_name);
        // Skip button will skip the current exhibit the user is on.
        currentNavigator.skipCurrentExhibit(new PlanListItem(currentPosition.getNearestLandmark().name,
                currentPosition.getNearestLandmark().id));
        // Prompt a replan
        updateActivityView();
    }


    /**
     * checks degree of severity that the user is off the route
     * if user is closer to later later exhibit in plan Then regenerate optimized plan
     * <p>
     * else: user is still closest to current exhibit seeing directions to
     * update the directions to the exhibit from current location
     */
    public void checkToReplanRoute() {
        if (!currentNavigator.getLandMarksOnPath().contains(currentPosition.getNearestLandmark().id)
                && currentNavigator.minDistanceFromUnvisited(currentPosition)
                < Coord.dist(Coord.fromZooLocation(currentPosition), currentNavigator.getCurrentLocation())) {
            // based on location, replan is best
            if (!offeredReplan) {
                offeredReplan = true;
                Utilities.showAlert(this, "Would you like to replan your route?");
            }
        } else {
            // no need to replan, just update directions.
            updateDirections();
        }
    }

    public void updateDirections(){
        updateActivityView();
    }

    public void updateRoute() {
        offeredReplan = false;
        currentNavigator.regenerateOptimizedPlan(new PlanListItem(currentPosition.
                getNearestLandmark().name, currentPosition.getNearestLandmark().id));
        updateActivityView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.replan_option) {
            updateRoute();
        } else if (id == R.id.DirectionsMode) {
            this.briefToggle = item;
            showBrief = !showBrief;
            if(showBrief == false){
                item.setTitle("Directions Mode: Brief");}else{
                item.setTitle("Directions Mode: Detail");
            }
            updateActivityView();
        } else if (id == R.id.ClearNavigation) {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(KEY, -1);
            editor.apply();
            PlanDatabase.getSingleton(this).planListItemDao().deleteAll();
            Intent intent = new Intent(this, SearchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.SetLocation) {
            Log.i("testingDirectionsSetLocation", "This was run");
            Utilities.mockLocationAlert(this, "Provide a latitude and longitude");
        }

        return super.onOptionsItemSelected(item);
    }

    public LocationModel getModel() {
        return model;
    }
}