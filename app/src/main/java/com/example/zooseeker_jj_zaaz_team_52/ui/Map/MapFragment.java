package com.example.zooseeker_jj_zaaz_team_52.ui.Map;

import static androidx.core.util.TypedValueCompat.pxToDp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.zooseeker_jj_zaaz_team_52.DetailsActivity;
import com.example.zooseeker_jj_zaaz_team_52.ExhibitSearch;
import com.example.zooseeker_jj_zaaz_team_52.PlanDatabase;
import com.example.zooseeker_jj_zaaz_team_52.PlanListItem;
import com.example.zooseeker_jj_zaaz_team_52.PlanListItemDao;
import com.example.zooseeker_jj_zaaz_team_52.R;
import com.example.zooseeker_jj_zaaz_team_52.ZooData;
import com.example.zooseeker_jj_zaaz_team_52.databinding.FragmentHomeBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.zooseeker_jj_zaaz_team_52.ui.MapSettingsViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//SETTINGS
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class MapFragment extends Fragment implements Zoomarker.OnZoomarkerClickListener {
    private FragmentHomeBinding binding;
    private RelativeLayout mapView;
    private ImageView map;

    private ExhibitSearch search;

    private boolean exhibitIncluded = true;
    private boolean restroomIncluded = true;
    private boolean restaurantIncluded = true;

    public float convertPixelToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private MapSettingsViewModel mapSettingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        MapViewModel homeViewModel =
                new ViewModelProvider(this).get(MapViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mapView = binding.relativeLayout;
        search = new ExhibitSearch(getContext());
        map = binding.map;
        Drawable drawable = map.getDrawable();

        if (drawable != null) {
            // Get the intrinsic dimensions of the drawable
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();

            // Get the scale factors
            float scaleX = map.getScaleX();
            float scaleY = map.getScaleY();

            // Calculate the new dimensions
            int scaledWidth = (int) (intrinsicWidth * scaleX);
            int scaledHeight = (int) (intrinsicHeight * scaleY);

            // Set the new dimensions to the ImageView
            ViewGroup.LayoutParams layoutParams = map.getLayoutParams();
            layoutParams.width = scaledWidth;
            layoutParams.height = scaledHeight;
            map.setLayoutParams(layoutParams);

        }

        binding.searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String newText = s.toString();
                addZoomarkers(search.searchKeyword(newText)); // This will trigger the search and notify the listener
            }
        });

        List<ZooData.VertexInfo> allExhibits = new ArrayList<>(search.zooData.values());

        List<String> items = new ArrayList<String>();

        for(ZooData.VertexInfo item : allExhibits) {
            if((item.kind != null) && (item.kind.toString()).equals("EXHIBIT"))
                items.add(item.name);
        }

        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<String>(requireContext(), R.layout.serachdropdown, items);
        binding.searchField.setAdapter(autoCompleteAdapter);

        binding.searchField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String exhibitmName = (String) ((TextView)(view.findViewById(R.id.dropdownTextView))).getText();
                for (int i = 0; i < mapView.getChildCount(); i++) {
                    View child = mapView.getChildAt(i);
                    if (child instanceof Zoomarker) {
                        Zoomarker zoomarker = (Zoomarker) child;
                        if (Objects.equals(zoomarker.markerData.name, exhibitmName)) {
                            System.out.println("FOUND SCROLLING");
                            HorizontalScrollView horizontalScrollView = binding.horizontalScrollView;
                            ScrollView verticalScrollView = binding.verticalScrollView;
                            NestedScrollView nestedScrollView = binding.nestedScrollView;
                            // Calculate the coordinates of the target view
                            int[] targetLocation = new int[2];
                            zoomarker.getLocationInWindow(targetLocation);

                            int[] scrollViewLocation = new int[2];
                            nestedScrollView.getLocationInWindow(scrollViewLocation);

                            // Calculate the scroll position to center the target view
                            System.out.println("Target loc x: "+ targetLocation[0] + " y: " + targetLocation[1]);
                            // Scroll to the calculated position
                            horizontalScrollView.smoothScrollTo(targetLocation[0], 0);
                            verticalScrollView.smoothScrollTo(0, targetLocation[1]);
                            break;
                        }
                    }
                }
            }
        });

        this.search = new ExhibitSearch(getContext());
        addZoomarkers(new ArrayList<>(this.search.searchKeyword("")));

        //SETTINGS-filtering
        mapSettingsViewModel = new ViewModelProvider(requireActivity()).get(MapSettingsViewModel.class);

        mapSettingsViewModel.getExhibitCheckbox().observe(getViewLifecycleOwner(), isChecked -> {

            clearZooMarkers();

            if(isChecked)
                addZoomarkers(new ArrayList<>(this.search.searchKeyword("")));
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void addZoomarkers(List<ZooData.VertexInfo> zooData) {

        //if statement to check the type of zoo marker
        //for loop to filter out the ones we want to show and show them

        //Clear all zoomarkers mapview
        for (int i = mapView.getChildCount() - 1; i >= 0; i--) {
            View child = mapView.getChildAt(i);
            if ((child instanceof Zoomarker)) {
                mapView.removeViewAt(i);
            }
        }

        //Create Zoomarkers for each exhibits and add them to the map
        for (ZooData.VertexInfo value : zooData) {
            //Only add exhibits, not streets and etc.
            if (exhibitIncluded && value.kind == ZooData.VertexInfo.Kind.EXHIBIT && value.parent_id == null ||
                    value.kind == ZooData.VertexInfo.Kind.EXHIBIT_GROUP  ||
                    restroomIncluded && value.kind == ZooData.VertexInfo.Kind.RESTROOM ||
                    restaurantIncluded && value.kind == ZooData.VertexInfo.Kind.RESTAURANT) {


                boolean zooMarkerinPlan = false;
                //Change stroke color if already in plan
                PlanListItemDao planListItemDao = PlanDatabase.getSingleton(requireActivity()).planListItemDao();
                for (PlanListItem item : planListItemDao.getAll()){
                    if(Objects.equals(item.exhibit_id, value.id)) {
                        zooMarkerinPlan = true;
                    }
                }


                Zoomarker zoomarker = new Zoomarker( getContext(), value, (value.scale == 0) ? 2 : (int) value.scale, zooMarkerinPlan);
                zoomarker.setOnZoomarkerClickListener(this);
                // Create layout parameters
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                // Set the margins to position the Zoomarker view
                params.leftMargin = convertDpToPixel((float) zoomarker.markerData.lat, getContext());
                params.topMargin = convertDpToPixel((float) zoomarker.markerData.lng, getContext());

                zoomarker.setLayoutParams(params);
                // Add the Zoomarker view to the RelativeLayout
                mapView.addView(zoomarker);

            }
        }
    }

    private void clearZooMarkers() {
        for(int i = mapView.getChildCount() - 1; i >= 0; i--) {
            View child = mapView.getChildAt(i);

            if(child instanceof Zoomarker)
                mapView.removeViewAt(i);

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Method to convert dp to pixels
    public static int convertDpToPixel(float dp, Context context){
        return (int) (dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void onZoomarkerClick(ZooData.VertexInfo clickedMarkerData) {
        // Handle the click event here
        showDialog(clickedMarkerData);
    }

    public void showDialog(ZooData.VertexInfo zoomarkerData) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(zoomarkerData.name)
                .setMessage("Are you sure you would like to add " + zoomarkerData.name + " to your plan?")
                .setPositiveButton("Confirm", (dialog, which) -> {

                    PlanListItemDao planListItemDao = getPlanItems();
                    PlanListItem newPlanExhibit = new PlanListItem(zoomarkerData.name, zoomarkerData.id);
                    planListItemDao.insert(newPlanExhibit);

                    Toast mapPlanSuccessToast = Toast.makeText(getContext(), "Added " + zoomarkerData.name + " to plan!", Toast.LENGTH_LONG);
                    mapPlanSuccessToast.show();
                    for (int i = 0; i < mapView.getChildCount(); i++) {
                        View child = mapView.getChildAt(i);
                        if (child instanceof Zoomarker) {
                            Zoomarker zoomarker = (Zoomarker) child;
                            if (Objects.equals(zoomarker.markerData.id, zoomarkerData.id)) {
                                zoomarker.setPlanned();
                                break;
                            }
                        }
                    }

                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Handle negative button press
                })
                .setNeutralButton("Details", (dialog, which) -> {
                    PlanListItem newPlanExhibit = new PlanListItem(zoomarkerData.name, zoomarkerData.id);
//                    openExhibitDetails(newPlanExhibit);

                    NavController controller = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("exhibitDetails", newPlanExhibit);
                    controller.navigate(R.id.fragment_details, bundle);

                })
                .show();
    }


    public void navToSettings() {
        NavController controller = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);

        Bundle bundle = new Bundle();
        bundle.putSerializable("currExhibitToggledStatus", exhibitIncluded);
        bundle.putSerializable("currRestroomToggledStatus", restroomIncluded);
        bundle.putSerializable("currRestaurantToggledStatus", restaurantIncluded);
        controller.navigate(R.id.fragment_settings, bundle);
    }

    public void openExhibitDetails(PlanListItem details) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra("ExhibitID", details.exhibit_id);
        intent.putExtra("ExhibitName", details.exhibit_name);
        startActivity(intent);
    }

    private PlanListItemDao getPlanItems() {
        return PlanDatabase.getSingleton(getContext()).planListItemDao();
    }
}
