package com.example.zooseeker_jj_zaaz_team_52.ui.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.zooseeker_jj_zaaz_team_52.databinding.FragmentHomeBinding;
public class MapFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ImageView mapView;
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MapViewModel homeViewModel =
                new ViewModelProvider(this).get(MapViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mapView = binding.map;

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
