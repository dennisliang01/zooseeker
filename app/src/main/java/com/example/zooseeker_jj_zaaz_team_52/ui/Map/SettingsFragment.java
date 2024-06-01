package com.example.zooseeker_jj_zaaz_team_52.ui.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.zooseeker_jj_zaaz_team_52.PlanListItem;
import com.example.zooseeker_jj_zaaz_team_52.R;

import android.widget.CheckBox;

public class SettingsFragment extends Fragment {

    private boolean exhibitIncluded;
    private boolean restroomIncluded;
    private boolean restaurantIncluded;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        NavController controller = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);

        //Check to see if the checkboxes are checked in Settings Fragment
        CheckBox ExhibitCheckbox = view.findViewById(R.id.exhibits_checkbox);
        CheckBox RestroomCheckbox = view.findViewById(R.id.restrooms_checkbox);
        CheckBox RestuarantCheckbox = view.findViewById(R.id.restuarant_checkbox);

        ExhibitCheckbox.setChecked((boolean)(getArguments().getSerializable("currExhibitToggledStatus")));
        RestroomCheckbox.setChecked((boolean)(getArguments().getSerializable("currRestroomToggledStatus")));
        RestuarantCheckbox.setChecked((boolean)(getArguments().getSerializable("currRestaurantToggledStatus")));

        Button comfirmBtn = view.findViewById(R.id.settings_confirm_btn);

        //Transfer checkbox information back to navigation home
        comfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ifExhibitIsChecked = ExhibitCheckbox.isChecked();
                boolean ifRestroomsIsChecked = RestroomCheckbox.isChecked();
                boolean ifRestaurantIsChecked = RestuarantCheckbox.isChecked();
                Bundle bundle = new Bundle();
                bundle.putSerializable("ExhibitCheckBox", ifExhibitIsChecked);
                bundle.putSerializable("RestroomCheckBox", ifRestroomsIsChecked);
                bundle.putSerializable("RestaurantCheckBox", ifRestaurantIsChecked);
                controller.navigate(R.id.navigation_home, bundle);
            }
        });

        return view;
    }



}
