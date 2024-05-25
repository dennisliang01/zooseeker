package com.example.zooseeker_jj_zaaz_team_52.ui.Map;

import static androidx.core.util.TypedValueCompat.pxToDp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import java.util.List;

public class MapFragment extends Fragment implements Zoomarker.OnZoomarkerClickListener {
    private FragmentHomeBinding binding;
    private RelativeLayout mapView;
    private ImageView map;

    private ExhibitSearch search;

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
            if((item.kind.toString()).equals("EXHIBIT"))
                items.add(item.name);
        }

        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, items);
        binding.searchField.setAdapter(autoCompleteAdapter);

        this.search = new ExhibitSearch(getContext());
        addZoomarkers(new ArrayList<>(this.search.searchKeyword("")));
        return root;
    }

    private void addZoomarkers(List<ZooData.VertexInfo> zooData) {

        //Clear all zoomarkers mapview
        for (int i = mapView.getChildCount() - 1; i >= 0; i--) {
            View child = mapView.getChildAt(i);
            if ((child instanceof Zoomarker)) {
                mapView.removeViewAt(i);
            }
        }
        int x = 10;
        int y = 10;

        List<double[]> coordinates = new ArrayList<>();

        coordinates.add(new double[]{609.44104, 332.71805});//Crocodiles
        coordinates.add(new double[]{385.44638, 652.33734}); //koi
        coordinates.add(new double[]{552.3508, 721.41656});
        coordinates.add(new double[]{573.0732, 681.77734});
        coordinates.add(new double[]{610.53906, 627.2369});
        coordinates.add(new double[]{515.99115, 517.788}); //gorilla
        coordinates.add(new double[]{723.6182, 563.6023});
        coordinates.add(new double[]{797.07526, 589.0689});
        coordinates.add(new double[]{557.8093, 389.7997}); // hippo
        coordinates.add(new double[]{977.8068, 781.0561}); //Orangutans
        coordinates.add(new double[]{400.54013, 705.6978}); //flamingo
        coordinates.add(new double[]{822.8963, 680.6971});
        coordinates.add(new double[]{890.89526, 679.2436});
        coordinates.add(new double[]{889.07635, 758.16406}); //siamangs
        coordinates.add(new double[]{416.35617, 521.4215}); // Lion
        coordinates.add(new double[]{386.89667, 592.32385}); //meerkat
        coordinates.add(new double[]{452.3473, 592.32385}); //Warthog


        coordinates.add(new double[]{483.6282, 662.8814}); //Parker Aviary


        coordinates.add(new double[]{627.261, 537.0543}); //scripps aviary

        coordinates.add(new double[]{740.5291, 440.71237}); // elephant
        coordinates.add(new double[]{613.08453, 723.61755}); //orange 1
        coordinates.add(new double[]{653.80756, 796.3246}); //orange 2
        coordinates.add(new double[]{715.6236, 842.1406}); //orange 3





        coordinates.add(new double[]{894.1701, 528.70416});
        coordinates.add(new double[]{989.4393, 526.16693});
        coordinates.add(new double[]{991.9801, 582.5341});
        coordinates.add(new double[]{982.1644, 645.7994}); //owens aviary
        coordinates.add(new double[]{783.99115, 773.0689});


        coordinates.add(new double[]{801.08167, 912.3363});
        coordinates.add(new double[]{928.35156, 877.7965});
        int i = 0;
        //Create Zoomarkers for each exhibits and add them to the map
        for (ZooData.VertexInfo value : zooData) {
            //Only add exhibits, not streets and etc.
            if (value.kind == ZooData.VertexInfo.Kind.EXHIBIT) {
                Zoomarker zoomarker = new Zoomarker( getContext(), value, 2);
                zoomarker.setOnZoomarkerClickListener(this);
                // Create layout parameters
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                // Set the margins to position the Zoomarker view
                params.leftMargin = convertDpToPixel((float) coordinates.get(i)[0], getContext()); // replace 400 with your desired x position
                params.topMargin = convertDpToPixel((float)coordinates.get(i)[1], getContext()); // replace 500 with your desired y position
                zoomarker.setLayoutParams(params);
                // Add the Zoomarker view to the RelativeLayout
                mapView.addView(zoomarker);
                x += 20;
                y += 20;
                i += 1;
            }
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