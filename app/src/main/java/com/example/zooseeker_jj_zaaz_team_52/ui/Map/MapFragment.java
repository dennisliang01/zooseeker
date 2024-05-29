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
import java.util.Arrays;
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
            if((item.kind.toString()).equals("EXHIBIT")) {
                System.out.println(item.name);
            }
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

        List<double[]> coordinates = new ArrayList<>();

        coordinates.add(new double[]{604.44104, 324.71805});//Crocodiles
        coordinates.add(new double[]{380.44638, 644.33734}); //koi
        coordinates.add(new double[]{547.3508, 713.41656});
        coordinates.add(new double[]{568.0732, 673.77734});
        coordinates.add(new double[]{605.53906, 619.2369});
        coordinates.add(new double[]{510.99115, 509.788}); //gorilla
        coordinates.add(new double[]{718.6182, 555.6023});
        coordinates.add(new double[]{792.07526, 581.0689});
        coordinates.add(new double[]{552.8093, 381.7997}); // hippo
        coordinates.add(new double[]{972.8068, 773.0561}); //Orangutans
        coordinates.add(new double[]{395.54013, 697.6978}); //flamingo
        coordinates.add(new double[]{817.8963, 672.6971});
        coordinates.add(new double[]{885.89526, 671.2436});
        coordinates.add(new double[]{884.07635, 750.16406}); //siamangs
        coordinates.add(new double[]{411.35617, 513.4215}); // Lion
        coordinates.add(new double[]{381.89667, 584.32385}); //meerkat
        coordinates.add(new double[]{447.3473, 584.32385}); //Warthog
        coordinates.add(new double[]{478.6282, 654.8814}); //Parker Aviary
        coordinates.add(new double[]{622.261, 529.0543}); //scripps aviary
        coordinates.add(new double[]{735.5291, 432.71237}); // elephant
        coordinates.add(new double[]{608.08453, 715.61755}); //orange 1
        coordinates.add(new double[]{648.80756, 788.3246}); //orange 2
        coordinates.add(new double[]{710.6236, 834.1406}); //orange 3
        coordinates.add(new double[]{889.1701, 523.70416});
        coordinates.add(new double[]{981.4393, 518.16693});
        coordinates.add(new double[]{991.9801, 582.5341});
        coordinates.add(new double[]{982.1644, 645.7994}); //owens aviary
        coordinates.add(new double[]{783.99115, 773.0689});
        coordinates.add(new double[]{793.08167, 904.3363});
        coordinates.add(new double[]{920.35156, 869.7965});
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
                if(i < coordinates.size()) {
                    params.leftMargin = convertDpToPixel((float) coordinates.get(i)[0], getContext()); // replace 400 with your desired x position
                    params.topMargin = convertDpToPixel((float) coordinates.get(i)[1], getContext()); // replace 500 with your desired y position
                }
                zoomarker.setLayoutParams(params);
                // Add the Zoomarker view to the RelativeLayout
                mapView.addView(zoomarker);
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