package com.example.zooseeker_jj_zaaz_team_52.ui.Map;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.zooseeker_jj_zaaz_team_52.ExhibitSearch;
import com.example.zooseeker_jj_zaaz_team_52.MainActivity;
import com.example.zooseeker_jj_zaaz_team_52.ZooData;
import com.example.zooseeker_jj_zaaz_team_52.databinding.FragmentHomeBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapFragment extends Fragment implements Zoomarker.OnZoomarkerClickListener {
    private FragmentHomeBinding binding;
    private RelativeLayout mapView;
    private ExhibitSearch search;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MapViewModel homeViewModel =
                new ViewModelProvider(this).get(MapViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mapView = binding.relativeLayout;
        binding.searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String newText = s.toString();
                addZoomerkers(search.searchKeyword(newText)); // This will trigger the search and notify the listener
            }
        });

        this.search = new ExhibitSearch(getContext());
        addZoomerkers(new ArrayList<>(this.search.searchKeyword("")));
        return root;
    }


    private void addZoomerkers(List<ZooData.VertexInfo> zooData) {

        //Clear all zoomarkers mapview
        for (int i = mapView.getChildCount() - 1; i >= 0; i--) {
            View child = mapView.getChildAt(i);
            if ((child instanceof Zoomarker)) {
                mapView.removeViewAt(i);
            }
        }
        int x = 10;
        int y = 10;

        //Create Zoomarkers for each exhibits and add them to the map
        for (ZooData.VertexInfo value : zooData) {
            //Only add exhibits, not streets and etc.
            if (value.kind == ZooData.VertexInfo.Kind.EXHIBIT) {
                Zoomarker zoomarker = new Zoomarker(value, getContext(), null);
                zoomarker.setOnZoomarkerClickListener(this);
                // Create layout parameters
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                // Set the margins to position the Zoomarker view
                params.leftMargin = convertDpToPixel(x, getContext()); // replace 400 with your desired x position
                params.topMargin = convertDpToPixel(y, getContext()); // replace 500 with your desired y position
                zoomarker.setLayoutParams(params);
                // Add the Zoomarker view to the RelativeLayout
                mapView.addView(zoomarker);
                x += 20;
                y += 20;
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
                .setMessage("Message")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Handle positive button press
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Handle negative button press
                })
                .show();
    }

}