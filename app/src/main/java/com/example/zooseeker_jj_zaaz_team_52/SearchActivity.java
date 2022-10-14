package com.example.zooseeker_jj_zaaz_team_52;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * SearchActivity Class: Single Responsibility - Maintain and display the components of
 * view of the SearchActivity UI as user searches for and selects
 * exhibits and also transition into PlanViewActivity
 */
public class SearchActivity extends AppCompatActivity {

    private RecyclerView courseRV;

    private ZooDataAdapter zooAdapter;
    private SearchShowAdapter searchAdapter;
    private ExhibitSearch search;
    private SearchView searchView;
    private PlanListItemDao dao;
    private Button createPlanBtn;

    private boolean isVisible = false;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY = "index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Reserve the app state if user has killed the app.
        if(loadData() == -2){
            Intent intent = new Intent(this, PlanViewActivity.class);
            startActivity(intent);
        }
        else if(loadData() != -1){
            Intent intent = new Intent(this, DirectionsActivity.class);
            PlanListItemDao planListItemDao = PlanDatabase.getSingleton(this).planListItemDao();
            List<PlanListItem> planListItems = planListItemDao.getAll();
            ZooNavigator zooNavigator = new ZooShortestNavigator(planListItems, this);
            zooNavigator.setCurrentIndex(loadData());
            intent.putExtra("CurrentNavigator", (Serializable) zooNavigator);
            startActivity(intent);
        }
        if (search == null) {
            this.search = new ExhibitSearch(getApplicationContext());
        }
        createPlanBtn = findViewById(R.id.create_plan);
        dao = PlanDatabase.getSingleton(this).planListItemDao();
        buildRecyclerView();
        if (dao.getAll().size() < 1) {
            createPlanBtn.setEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY, -1);
        editor.apply();
        if (dao.getAll().size() < 1) {
            createPlanBtn.setEnabled(false);
        }
        //loop through each item in zooData, if we find a matching exhibit in the planItem, it means
        //that item is still in the plan, and therefore should be selected, otherwise, set to unselected.
        for (var item : search.zooData.keySet()) {
            boolean foundMatchinDao = false;
            for (var daoItem : dao.getAll()) {
                if (Objects.requireNonNull(search.zooData.get(item)).id.equals(daoItem.exhibit_id)) {
                    foundMatchinDao = true;
                }
            }
            Objects.requireNonNull(search.zooData.get(item)).isSelected = foundMatchinDao;
        }
    }

    // Load the index of direction user has viewed in Direction before the app is killed. Default value is -1.
    public int loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getInt(KEY, -1);
    }

    public RecyclerView getCourseRV() {
        return courseRV;
    }

    public SearchView getSearchView() {
        return searchView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                toggleListVisible();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                toggleListVisible();
                return true;
            }
        });
        // getting search view of our item.
        this.searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.

                filter(newText, newText.length() == 0);
                return false;
            }
        });
        return true;
    }

    public void toggleListVisible() {
        if (isVisible) {
            courseRV.setAdapter(searchAdapter);
            createPlanBtn.setVisibility(View.VISIBLE);
        } else {
            courseRV.setAdapter(zooAdapter);
            createPlanBtn.setVisibility(View.INVISIBLE);
        }
        isVisible = !isVisible;
    }

    private void filter(String text, boolean show_all) {
        List<ZooData.VertexInfo> results;
        if (show_all) {
            results = new ArrayList<>(search.zooData.values());
            results.removeIf(((vertexInfo -> vertexInfo.kind != ZooData.VertexInfo.Kind.EXHIBIT)));
        } else {
            results = search.searchKeyword(text);
        }

        if (results.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
            zooAdapter.setExhibits(Collections.emptyList());
        } else {
            zooAdapter.setExhibits(results);
        }
    }

    private void buildRecyclerView() {

        this.courseRV = findViewById(R.id.idRVCourses);
        // initializing our adapter class.
        zooAdapter = new ZooDataAdapter();
        zooAdapter.setDataBase(dao);
        searchAdapter = new SearchShowAdapter(findViewById(R.id.num_item_selected));

        SearchListViewModel viewModel = new ViewModelProvider(this).get(SearchListViewModel.class);
        searchAdapter.setOnDeletedClickedHandler(viewModel::setDeleted);

        searchAdapter.setHasStableIds(true);
        viewModel.getPlanListItems().observe(this, searchAdapter::setPlanItems);
        viewModel.setPlanBtn(createPlanBtn);

        zooAdapter.setSelectedConsumer((view, exhibit) -> {
            exhibit.isSelected = !exhibit.isSelected;

            if (exhibit.isSelected) {
                view.setBackgroundColor(Color.GRAY);
                PlanListItem exhibitPlan;
                if (exhibit.parent_id == null) {
                    exhibitPlan = new PlanListItem(exhibit.name, exhibit.id);
                } else {
                    exhibitPlan = new PlanListItem(exhibit.name, exhibit.id, exhibit.parent_id);
                }
                Log.i("SearchActivity", "added" + exhibitPlan);
                dao.insert(exhibitPlan);
                searchAdapter.setPlanItems(dao.getAll());
            } else {
                dao.delete(exhibit.name);
                searchAdapter.setPlanItems(dao.getAll());
                view.setBackgroundColor(Color.WHITE);
            }

            createPlanBtn.setEnabled(dao.getAll().size() >= 1);
        });

        List<ZooData.VertexInfo> allExhibits = new ArrayList<>(search.zooData.values());
        zooAdapter.setExhibits(allExhibits);
        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        courseRV.setHasFixedSize(true);
        courseRV.setVisibility(View.VISIBLE);
        // setting layout manager
        // to our recycler view.
        courseRV.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.

        //courseRV.setAdapter(adapter);
        courseRV.setAdapter(searchAdapter);
        searchAdapter.setPlanItems(dao.getAll());
    }

    public void goToPlan(View view) {
        Intent intent = new Intent(this, PlanViewActivity.class);
        intent.putExtra(PlanViewActivity.EXTRA_USE_LOCATION_SERVICE, true);
        startActivity(intent);
    }
}