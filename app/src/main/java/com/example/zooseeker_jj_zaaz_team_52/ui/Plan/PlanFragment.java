package com.example.zooseeker_jj_zaaz_team_52.ui.Plan;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zooseeker_jj_zaaz_team_52.*;
import com.example.zooseeker_jj_zaaz_team_52.databinding.FragmentNotificationsBinding;
import com.example.zooseeker_jj_zaaz_team_52.location.PermissionChecker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PlanFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public static final String EXTRA_USE_LOCATION_SERVICE = "use_location_updated";
    private boolean useLocationService;
    private RecyclerView recyclerView;
    private ZooNavigator zooNavigator;
    private PlanViewAdapter adapter;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY = "index";
    private boolean hasPermissions;
    private PermissionChecker permissionChecker;
    private RecyclerView courseRV;
    private TextView numItemSelected;

    private ZooDataAdapter zooAdapter;
    private SearchShowAdapter searchAdapter;
    private ExhibitSearch search;
    private SearchView searchView;
    private PlanListItemDao dao;
    private Button createPlanBtn;

    private boolean isVisible = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        if (search == null) {
            this.search = new ExhibitSearch(getContext());
        }
        createPlanBtn = view.findViewById(R.id.create_plan);
        courseRV = view.findViewById(R.id.idRVCourses);
        numItemSelected = view.findViewById(R.id.num_item_selected);
        dao = PlanDatabase.getSingleton(getContext()).planListItemDao();
        buildRecyclerView();
        if (dao.getAll().size() < 1) {
            createPlanBtn.setEnabled(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY, -1);
        editor.apply();
        if (dao.getAll().size() < 1) {
            createPlanBtn.setEnabled(false);
        }
        for (var item : search.zooData.keySet()) {
            boolean foundMatchingDao = false;
            for (var daoItem : dao.getAll()) {
                if (Objects.requireNonNull(search.zooData.get(item)).id.equals(daoItem.exhibit_id)) {
                    foundMatchingDao = true;
                }
            }
            Objects.requireNonNull(search.zooData.get(item)).isSelected = foundMatchingDao;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
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
        this.searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText, newText.length() == 0);
                return false;
            }
        });
    }

    private void toggleListVisible() {
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
            results.removeIf(vertexInfo -> vertexInfo.kind != ZooData.VertexInfo.Kind.EXHIBIT);
        } else {
            results = search.searchKeyword(text);
        }
        if (results.isEmpty()) {
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
            zooAdapter.setExhibits(Collections.emptyList());
        } else {
            zooAdapter.setExhibits(results);
        }
    }

    private void buildRecyclerView() {
        // initializing our adapter class.
        zooAdapter = new ZooDataAdapter();
        zooAdapter.setDataBase(dao);
        searchAdapter = new SearchShowAdapter(numItemSelected);

        SearchListViewModel viewModel = new ViewModelProvider(this).get(SearchListViewModel.class);
        searchAdapter.setOnDeletedClickedHandler(viewModel::setDeleted);

        searchAdapter.setHasStableIds(true);
        viewModel.getPlanListItems().observe(getViewLifecycleOwner(), searchAdapter::setPlanItems);
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
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
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


}