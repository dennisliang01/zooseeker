package com.example.zooseeker_jj_zaaz_team_52;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zooseeker_jj_zaaz_team_52.location.LocationModel;
import com.example.zooseeker_jj_zaaz_team_52.location.PermissionChecker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * PlanViewActivity Class: Single Responsibility - Display overview of optimized route with
 * distances and allow transition into DirectionsActivity
 */
public class PlanViewActivity extends AppCompatActivity {

    public static final String EXTRA_USE_LOCATION_SERVICE = "use_location_updated";
    public boolean useLocationService;
    public RecyclerView recyclerView;
    public ZooNavigator zooNavigator;
    public PlanViewAdapter adapter;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY = "index";

    private boolean hasPermissions;
    private PermissionChecker permissionChecker;

    public boolean hasPermissions() {
        return hasPermissions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_view);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY, -2);
        editor.apply();

        PlanListItemDao planListItemDao = PlanDatabase.getSingleton(this).planListItemDao();
        List<PlanListItem> planListItems = planListItemDao.getAll();
        zooNavigator = new ZooShortestNavigator(planListItems, this);
        adapter = new PlanViewAdapter(findViewById(R.id.plan_title));
        adapter.setHasStableIds(true);
        adapter.setPlanItems(zooNavigator);
        recyclerView = findViewById(R.id.plan_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        useLocationService = getIntent().getBooleanExtra(EXTRA_USE_LOCATION_SERVICE, false);
        if (useLocationService) {
            permissionChecker = new PermissionChecker(this);
            permissionChecker.checkAndRequestPermissions();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY, -2);
        editor.apply();
        PlanListItemDao planListItemDao = PlanDatabase.getSingleton(this).planListItemDao();
        List<PlanListItem> planListItems = planListItemDao.getAll();
        zooNavigator = new ZooShortestNavigator(planListItems, this);
        adapter.setPlanItems(zooNavigator);
        recyclerView.setAdapter(adapter);
        if (planListItemDao.getAll().size() < 1) {
            var directionBtn = findViewById(R.id.direction_btn);
            directionBtn.setEnabled(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!permissionChecker.hasLocationPermission()) {
            finish();
        } else {
            hasPermissions = true;
            var model = new ViewModelProvider(this).get(LocationModel.class);
            model.setZooData(loadJson(this));
            var locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            var provider = LocationManager.GPS_PROVIDER;
            model.addLocationProviderSource(locationManager, provider);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!useLocationService) {
            hasPermissions = true;
            var model = new ViewModelProvider(this).get(LocationModel.class);
            model.setZooData(loadJson(this));
            var locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            var provider = LocationManager.GPS_PROVIDER;
            model.addLocationProviderSource(locationManager, provider);
        }else{
            if(permissionChecker.hasLocationPermission()){
                hasPermissions = true;
            }
        }
    }

    private Map<String, ZooData.VertexInfo> loadJson(Context context) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(context.getString(R.string.node_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Reader reader = new InputStreamReader(inputStream);

        Gson gson = new Gson();
        Type type = new TypeToken<List<ZooData.VertexInfo>>() {
        }.getType();
        List<ZooData.VertexInfo> zooData = gson.fromJson(reader, type);

        return zooData.stream().collect(Collectors.toMap(v -> v.id, datum -> datum));
    }


    public void goToDirection(View view) {
        Intent intent = new Intent(this, DirectionsActivity.class);
        intent.putExtra("CurrentNavigator", zooNavigator);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}