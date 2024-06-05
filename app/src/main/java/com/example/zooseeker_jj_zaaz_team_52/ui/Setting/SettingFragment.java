package com.example.zooseeker_jj_zaaz_team_52.ui.Setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.zooseeker_jj_zaaz_team_52.R;
import com.example.zooseeker_jj_zaaz_team_52.databinding.FragmentSettingBinding;
import com.example.zooseeker_jj_zaaz_team_52.ui.MapSettingsViewModel;


public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;

    private MapSettingsViewModel mapSettingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapSettingsViewModel = new ViewModelProvider(requireActivity()).get(MapSettingsViewModel.class);

        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        CheckBox ExhibitCheckbox = view.findViewById(R.id.exhibits_checkbox);
        CheckBox RestroomCheckbox = view.findViewById(R.id.restrooms_checkbox);
        CheckBox RestaurantCheckbox = view.findViewById(R.id.restuarant_checkbox);

        ExhibitCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mapSettingsViewModel.setExhibitCheckbox(isChecked);
            }
        });

        RestroomCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mapSettingsViewModel.setRestroomCheckbox(isChecked);
            }
        });

        RestaurantCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mapSettingsViewModel.setRestaurantCheckbox(isChecked);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}