package com.example.zooseeker_jj_zaaz_team_52;

import android.app.Application;
import android.content.Context;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * SearchListViewModel Class: Single Responsibility - Initialize and set status of components of
 * SearchActivity UI
 */
public class SearchListViewModel extends AndroidViewModel {
    private LiveData<List<PlanListItem>> planListItems;
    private final PlanListItemDao planListItemDao;
    private Button createPlanBtn;

    public SearchListViewModel(@NonNull Application application) {
        super(application);
        Context context = getApplication().getApplicationContext();
        PlanDatabase db = PlanDatabase.getSingleton(context);
        planListItemDao = db.planListItemDao();
    }

    public LiveData<List<PlanListItem>> getPlanListItems() {
        if (planListItems == null) {
            loadUsers();
        }
        return planListItems;
    }

    private void loadUsers() {
        planListItems = planListItemDao.getAllLive();
    }

    public void setPlanBtn(Button btn) {
        createPlanBtn = btn;
    }

    public void setDeleted(PlanListItem planListItem) {
        ZooDataAdapter.unselectFromPlan(planListItem);
        planListItemDao.delete(planListItem);
        createPlanBtn.setEnabled(planListItemDao.getAll().size() >= 1);
    }
}
