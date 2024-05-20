package com.example.zooseeker_jj_zaaz_team_52;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.zooseeker_jj_zaaz_team_52.ui.Map.MapFragment;
import com.example.zooseeker_jj_zaaz_team_52.ui.Map.Zoomarker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.zooseeker_jj_zaaz_team_52.databinding.ActivityMainBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    Dialog dialog;
    Button btnDialogAdd;
    Button btnDialogCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_direction, R.id.navigation_plan)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Check if we need to reopen the dialog
        if (getIntent().getBooleanExtra("OPEN_DIALOG", true)) {
            String currExhibitName = getIntent().getStringExtra("ExhibitName");
            String currExhibitID = getIntent().getStringExtra("ExhibitID");

            showDialog(currExhibitName, currExhibitID);
        }

    }
    public void goToPlan(View view) {
        Intent intent = new Intent(this, PlanViewActivity.class);
        intent.putExtra(PlanViewActivity.EXTRA_USE_LOCATION_SERVICE, true);
        startActivity(intent);
    }

}