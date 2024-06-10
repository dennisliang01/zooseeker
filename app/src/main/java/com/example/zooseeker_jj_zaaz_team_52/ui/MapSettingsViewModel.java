package com.example.zooseeker_jj_zaaz_team_52.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapSettingsViewModel extends ViewModel {
    private final MutableLiveData<Boolean> exhibitCheckbox  = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> restroomCheckbox = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> restaurantCheckbox = new MutableLiveData<>(true);

    public LiveData<Boolean> getExhibitCheckbox() {
        return exhibitCheckbox;
    }

    public void setExhibitCheckbox(Boolean value) {
        exhibitCheckbox.setValue(value);
    }

    public LiveData<Boolean> getRestroomCheckbox() {
        return restroomCheckbox;
    }

    public void setRestroomCheckbox(Boolean value) {
        restroomCheckbox.setValue(value);
    }

    public LiveData<Boolean> getRestaurantCheckbox() {
        return restaurantCheckbox;
    }

    public void setRestaurantCheckbox(Boolean value) {
        restaurantCheckbox.setValue(value);
    }
}
