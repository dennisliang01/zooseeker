package com.example.zooseeker_jj_zaaz_team_52.ui.Plan;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.zooseeker_jj_zaaz_team_52.PlanDatabase;
import com.example.zooseeker_jj_zaaz_team_52.PlanListItem;
import com.example.zooseeker_jj_zaaz_team_52.PlanListItemDao;
import com.example.zooseeker_jj_zaaz_team_52.R;
import com.example.zooseeker_jj_zaaz_team_52.Utilities;
import com.example.zooseeker_jj_zaaz_team_52.ZooNavigator;
import com.example.zooseeker_jj_zaaz_team_52.ZooShortestNavigator;
import com.example.zooseeker_jj_zaaz_team_52.location.Coord;
import com.example.zooseeker_jj_zaaz_team_52.location.LocationModel;
import com.example.zooseeker_jj_zaaz_team_52.location.ZooLocation;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.jgrapht.alg.util.Pair;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DirectionFragment extends Fragment {
    final PlanListItem ENTRANCE = new PlanListItem("Entrance and Exit Gate", "entrance_exit_gate");
    public ZooLocation currentPosition = ZooLocation.STARTING_POINT;
    ZooNavigator currentNavigator;
    TextView directionsView;
    TextView exhibitName;
    TextView previousExhibit;
    TextView nextExhibit;
    ImageButton nextButton;
    ImageButton previousButton;
    ImageButton stopButton;
    ImageButton skipButton;
    MenuItem briefToggle;
    Menu menu;

    ProgressBar stepStatus;
    boolean offeredReplan = false;
    LocationModel model;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY = "index";
    boolean showBrief = true;

    int progress = 0;

    int planMax;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_directions, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentNavigator = (ZooShortestNavigator) getArguments().getSerializable("navigator");
//      currentNavigator = (ZooShortestNavigator) intent.getSerializableExtra("CurrentNavigator");
        saveData();
        // Initialize components of DirectionsActivity UI
        directionsView = view.findViewById(R.id.navigation_directions);
        exhibitName = view.findViewById(R.id.exhibit_name);
        previousExhibit = view.findViewById(R.id.previous_text);
        nextExhibit = view.findViewById(R.id.next_text);
        nextButton = view.findViewById(R.id.next_btn);
        previousButton = view.findViewById(R.id.previous_btn);
        stopButton = view.findViewById(R.id.stop_btn);
        skipButton = view.findViewById(R.id.skip_btn);
        model = new ViewModelProvider(this).get(LocationModel.class);
        model.getLastKnownCoords().observe(requireActivity(), (zooLocation) -> {
            Log.i(this.getClass().toString(), String.format("Observing location model update to %s", zooLocation));
            updateUserLocation(zooLocation);
        });

        planMax = currentNavigator.getPlan().size() - 2;
        stepStatus = view.findViewById(R.id.progressBar);
        stepStatus.setMax(planMax);


        // Set a click listener on the button
        nextButton.setOnClickListener(v -> {
            offeredReplan = false;
            currentNavigator.next();
            saveData();
            updateActivityView();
            progress++;
            stepStatus.setProgress(progress);
        });
        previousButton.setOnClickListener(v -> {
            offeredReplan = false;
            currentNavigator.previous();
            updateActivityView();
            saveData();
            progress--;
            stepStatus.setProgress(progress);
        });
        stopButton.setOnClickListener(v -> {
            // Pop up alert asking user to confirm if they want to end the plan
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("End Plan")
                    .setMessage("Are you sure you would like to end your plan?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Log.d("Yes pressed", "Yes pressed");
                        ArrayList<PlanListItem> remainingExhibits = new ArrayList<>();

                        if (currentNavigator.getCurrentIndex() != currentNavigator.getPlan().size() - 1) {
                            remainingExhibits = currentNavigator.findRemainingExhibits();
                        }

                        PlanListItemDao planListItemDao = PlanDatabase.getSingleton(requireActivity()).planListItemDao();

                        currentNavigator.skipToEnd();
                        updateActivityView();
                        saveData();
                        Log.d("Current Index", String.valueOf(currentNavigator.getCurrentIndex()));
                        Log.d("Plan Size", String.valueOf(currentNavigator.getPlan().size()));

                        // If the user is at the end of the plan, prompt a message.
                        if (currentNavigator.getCurrentIndex() == currentNavigator.getPlan().size() - 1) {
                            new MaterialAlertDialogBuilder(requireContext())
                                    .setTitle("Alert")
                                    .setMessage("You already reached the end.").show();
                        // If the user is at the beginning of the plan
                        } else if (currentNavigator.getCurrentIndex() == 0) {
                            planMax = currentNavigator.getPlan().size() - 1;
                            stepStatus = view.findViewById(R.id.progressBar);
                            stepStatus.setMax(planMax);
                            progress = planMax - 1;
                            stepStatus.setProgress(progress);

                            // Delete the remaining exhibits from the database
                            for (int i = 0; i < remainingExhibits.size(); i++) {
                                Log.d("Deleting", remainingExhibits.get(i).exhibit_name);
                                planListItemDao.delete(remainingExhibits.get(i).exhibit_name);
                                currentNavigator.next();
                            }
                            remainingExhibits.add(remainingExhibits.get(remainingExhibits.size() - 1));
                        } else {
                                planMax = currentNavigator.getPlan().size() - 2;
                                stepStatus = view.findViewById(R.id.progressBar);
                                stepStatus.setMax(planMax);
                                progress = planMax - 1;
                                stepStatus.setProgress(progress);

                                // Delete the remaining exhibits from the database
                                for (int i = 0; i < remainingExhibits.size(); i++) {
                                    Log.d("Deleting", remainingExhibits.get(i).exhibit_name);
                                    planListItemDao.delete(remainingExhibits.get(i).exhibit_name);
                                    currentNavigator.next();
                                }
                                remainingExhibits.add(remainingExhibits.get(remainingExhibits.size() - 1));
                        }

                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        Log.d("Yes pressed", "Yes pressed");
                    })
                    .show();


        });
        skipButton.setOnClickListener(v -> {
            offeredReplan = false;
            // Sync the database to delete the current exhibit the user skips.
            PlanListItemDao planListItemDao = PlanDatabase.getSingleton(requireActivity()).planListItemDao();
            planListItemDao.delete(currentNavigator.getExhibit().exhibit_name);
            // Skip button will skip the current exhibit the user is on.
            currentNavigator.skipCurrentExhibit(new PlanListItem(currentPosition.getNearestLandmark().name,
                    currentPosition.getNearestLandmark().id));
            // Prompt a replan
            updateActivityView();
            planMax -= 1;
            stepStatus.setMax(planMax);
        });
        // Set DirectionsActivity UI for first exhibit upon creation of activity
        updateActivityView();


    }
    /**
     * Save the index of direction user has viewed in Direction.
     */
    public void saveData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY, currentNavigator.getCurrentIndex());
        editor.apply();
    }

    public void updateActivityView() {
        exhibitName.setText(currentNavigator.getExhibit().exhibit_name);
        String fullText = currentNavigator.calcLocationBasedDirections(currentPosition,showBrief);

        String regex = "\\b\\d+ft\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fullText);
        SpannableString spannableString = new SpannableString(fullText);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new RelativeSizeSpan(1.5f), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        directionsView.setText(spannableString);

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


}
