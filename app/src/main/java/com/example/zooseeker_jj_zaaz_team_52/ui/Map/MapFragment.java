package com.example.zooseeker_jj_zaaz_team_52.ui.Map;

import android.content.Context;
import android.os.Bundle;
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

import com.example.zooseeker_jj_zaaz_team_52.MainActivity;
import com.example.zooseeker_jj_zaaz_team_52.databinding.FragmentHomeBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MapFragment extends Fragment implements Zoomarker.OnZoomarkerClickListener {
    private FragmentHomeBinding binding;
    private ImageView mapView;

    private Zoomarker zoomarker;
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MapViewModel homeViewModel =
                new ViewModelProvider(this).get(MapViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mapView = binding.map;

        // Create a new Zoomarker view
        zoomarker = new Zoomarker(getContext(), null);
        zoomarker.setOnZoomarkerClickListener(this);

        // Create layout parameters
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, 
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        // Set the margins to position the Zoomarker view
        params.leftMargin = convertDpToPixel(500, getContext()); // replace 400 with your desired x position
        params.topMargin = convertDpToPixel(400, getContext()); // replace 500 with your desired y position
        zoomarker.setLayoutParams(params);
        // Add the Zoomarker view to the RelativeLayout
        binding.relativeLayout.addView(zoomarker);

        return root;
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
    public void onZoomarkerClick() {
        // Handle the click event here
        showDialog();
    }

    public void showDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Title")
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