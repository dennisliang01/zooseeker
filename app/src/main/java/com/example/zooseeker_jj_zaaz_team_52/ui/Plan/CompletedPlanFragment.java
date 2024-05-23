package com.example.zooseeker_jj_zaaz_team_52.ui.Plan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.zooseeker_jj_zaaz_team_52.*;
import com.example.zooseeker_jj_zaaz_team_52.PlanDatabase;
import com.example.zooseeker_jj_zaaz_team_52.PlanListItem;
import com.example.zooseeker_jj_zaaz_team_52.PlanListItemDao;
import com.example.zooseeker_jj_zaaz_team_52.PlanViewAdapter;
import com.example.zooseeker_jj_zaaz_team_52.ZooNavigator;
import com.example.zooseeker_jj_zaaz_team_52.ZooShortestNavigator;
import com.example.zooseeker_jj_zaaz_team_52.location.PermissionChecker;

import java.util.List;

/**
 * PlanViewFragment Class: Single Responsibility - Display overview of optimized route with
 * distances and allow transition into DirectionsActivity
 */
public class CompletedPlanFragment extends Fragment {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY = "index";
    private RecyclerView recyclerView;
    private ZooNavigator zooNavigator;
    private PlanViewAdapter adapter;
    private boolean hasPermissions;
    private PermissionChecker permissionChecker;

    public boolean hasPermissions() {
        return hasPermissions;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_plan_view, container, false);

        Button directionButton = view.findViewById(R.id.direction_btn);

        // Set a click listener on the button
        directionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(view);
                Bundle bundle = new Bundle();
                bundle.putSerializable("navigator", zooNavigator);
                controller.navigate(R.id.navigation_directions, bundle);

            }
        });

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY, -2);
        editor.apply();

        PlanListItemDao planListItemDao = PlanDatabase.getSingleton(requireActivity()).planListItemDao();
        List<PlanListItem> planListItems = planListItemDao.getAll();
        zooNavigator = new ZooShortestNavigator(planListItems, requireActivity());
        adapter = new PlanViewAdapter(view.findViewById(R.id.plan_title));
        adapter.setHasStableIds(true);
        adapter.setPlanItems(zooNavigator);
        recyclerView = view.findViewById(R.id.plan_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        if (getArguments() != null && getArguments().getBoolean("use_location_updated", false)) {
            permissionChecker = new PermissionChecker(requireActivity());
            permissionChecker.checkAndRequestPermissions();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY, -2);
        editor.apply();

        PlanListItemDao planListItemDao = PlanDatabase.getSingleton(requireActivity()).planListItemDao();
        List<PlanListItem> planListItems = planListItemDao.getAll();
        zooNavigator = new ZooShortestNavigator(planListItems, requireActivity());
        adapter.setPlanItems(zooNavigator);
        recyclerView.setAdapter(adapter);
        if (planListItemDao.getAll().size() < 1) {
            View directionBtn = getView().findViewById(R.id.direction_btn);
            directionBtn.setEnabled(false);
        }
    }

}
