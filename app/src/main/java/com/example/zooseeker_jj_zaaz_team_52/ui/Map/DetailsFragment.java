package com.example.zooseeker_jj_zaaz_team_52.ui.Map;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zooseeker_jj_zaaz_team_52.ZooData;
import com.example.zooseeker_jj_zaaz_team_52.ZooNavigator;
import com.example.zooseeker_jj_zaaz_team_52.ZooShortestNavigator;
import com.example.zooseeker_jj_zaaz_team_52.databinding.FragmentHomeBinding;

import com.example.zooseeker_jj_zaaz_team_52.DetailsActivity;
import com.example.zooseeker_jj_zaaz_team_52.MainActivity;
import com.example.zooseeker_jj_zaaz_team_52.PlanDatabase;
import com.example.zooseeker_jj_zaaz_team_52.PlanListItem;
import com.example.zooseeker_jj_zaaz_team_52.PlanListItemDao;
import com.example.zooseeker_jj_zaaz_team_52.R;
import com.example.zooseeker_jj_zaaz_team_52.databinding.FragmentHomeBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DetailsFragment extends Fragment {

    private FragmentHomeBinding binding;
    PlanListItem currentPlanListItem;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_exhibit_details, container, false);
        NavController controller = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);

        currentPlanListItem = (PlanListItem) getArguments().getSerializable("exhibitDetails");

        String currExhibitName = currentPlanListItem.exhibit_name;
        String currExhibitID = currentPlanListItem.exhibit_id;

        TextView titleText = view.findViewById(R.id.exhibit_id);
        titleText.setText(currExhibitName);

        Button backButton = view.findViewById(R.id.btn_back);
        Button planButton = view.findViewById(R.id.btn_detail_plan_add);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.popBackStack();
            }
        });

        planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(currentPlanListItem);
            }
        });

        return view;

    }

    public void showDialog(PlanListItem zoomarkerData) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(zoomarkerData.exhibit_name)
                .setMessage("Are you sure you would like to add " + zoomarkerData.exhibit_name + " to your plan?")
                .setPositiveButton("Confirm", (dialog, which) -> {

                    PlanListItemDao planListItemDao = getPlanItems();
                    PlanListItem newPlanExhibit = new PlanListItem(zoomarkerData.exhibit_name, zoomarkerData.exhibit_id);
                    planListItemDao.insert(newPlanExhibit);

                    Toast mapPlanSuccessToast = Toast.makeText(getContext(), "Added " + zoomarkerData.exhibit_name + " to plan!", Toast.LENGTH_LONG);
                    mapPlanSuccessToast.show();

                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Handle negative button press
                })
                .show();
    }

    private PlanListItemDao getPlanItems() {
        return PlanDatabase.getSingleton(getContext()).planListItemDao();
    }

}