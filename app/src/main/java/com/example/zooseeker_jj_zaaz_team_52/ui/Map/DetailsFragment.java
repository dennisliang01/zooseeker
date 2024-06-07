package com.example.zooseeker_jj_zaaz_team_52.ui.Map;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;


public class DetailsFragment extends Fragment {

    private FragmentHomeBinding binding;
    PlanListItem currentPlanListItem;
    private Map<String, ZooData.ExhibitInfo> exhibitDataMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_exhibit_details, container, false);
        NavController controller = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);

        exhibitDataMap = loadExhibitData(getContext());
        if (exhibitDataMap == null) {
            return view;  // Handle the error as appropriate
        }

        currentPlanListItem = (PlanListItem) getArguments().getSerializable("exhibitDetails");

        String currExhibitName = currentPlanListItem.exhibit_name;
        String currExhibitID = currentPlanListItem.exhibit_id;

        TextView titleText = view.findViewById(R.id.exhibit_id);
        titleText.setText(currExhibitName);
        TextView locationText = view.findViewById(R.id.exhibit_location);
        TextView statusText = view.findViewById(R.id.exhibit_status);
        TextView descriptionText = view.findViewById(R.id.exhibit_description);
        TextView avalibilityText = view.findViewById(R.id.exhibit_avaliability);
        ImageView exhibitImageView = view.findViewById(R.id.exhibit_image);
        ImageView statusImage  = view.findViewById(R.id.exhibit_status_image);
        ImageView avaliabilityImage = view.findViewById(R.id.exhibit_avaliability_image);

        ZooData.ExhibitInfo exhibitInfo = exhibitDataMap.get(currExhibitName);
        if (exhibitInfo != null) {
            locationText.setText("Location: " + exhibitInfo.location);
            statusText.setText("Conservation Status: " + exhibitInfo.status);
            avalibilityText.setText("Available: " + exhibitInfo.availability.start_time + " - " + exhibitInfo.availability.end_time);
            descriptionText.setText(exhibitInfo.description);

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.US);
                Date startTime = sdf.parse(exhibitInfo.availability.start_time);
                Date endTime = sdf.parse(exhibitInfo.availability.end_time);
                Date currentTime = new Date();

                if (currentTime.after(startTime) && currentTime.before(endTime)) {
                    avaliabilityImage.setImageResource(R.drawable.exhibit_avaliable);
                } else {
                    avaliabilityImage.setImageResource(R.drawable.not_avaliable);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if ("stable".equals(exhibitInfo.status)) {
                statusImage.setImageResource(R.drawable.ic_status_green);
            } else if ("threatened".equals(exhibitInfo.status)){
                statusImage.setImageResource(R.drawable.ic_status_yellow);
            } else {
                statusImage.setImageResource(R.drawable.ic_status_red);
            }

            int imageResId = getResources().getIdentifier(exhibitInfo.image_location, "drawable", getContext().getPackageName());
            //Instant Glide = null;
            //Glide.with(this).load(imageResId).into(exhibitImageView);
        }

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

    public static Map<String, ZooData.ExhibitInfo> loadExhibitData(Context context) {
        Map<String, ZooData.ExhibitInfo> exhibitDataMap = new HashMap<>();
        try {
            InputStream is = context.getAssets().open("exhibit_info.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            JSONArray jsonArray = new JSONArray(jsonBuilder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ZooData.ExhibitInfo.Availability availability = new ZooData.ExhibitInfo.Availability(
                        jsonObject.getJSONObject("availability").getString("start_time"),
                        jsonObject.getJSONObject("availability").getString("end_time")
                );
                ZooData.ExhibitInfo exhibitInfo = new ZooData.ExhibitInfo(
                        jsonObject.getString("animal_name"),
                        jsonObject.getString("location"),
                        jsonObject.getString("status"),
                        jsonObject.getString("description"),
                        availability,
                        jsonObject.getString("image_location")
                );
                exhibitDataMap.put(exhibitInfo.animal_name, exhibitInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exhibitDataMap;
    }
}

