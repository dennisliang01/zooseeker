package com.example.zooseeker_jj_zaaz_team_52;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * PlanListItem Class: Single Responsibility - Create PlanListItem objects which are objects in
 * the database PlanDatabase
 */
@Entity(tableName = "plan_list_item")
public class PlanListItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    public String exhibit_name;
    public String exhibit_id;
    public String parent_exhibit_id;
    public String loc;
    public int dist;
    public boolean visited;

    @NonNull
    @Override
    public String toString() {
        return "PlanListItem{"
                + "exhibit_name'" + exhibit_name + '\'' +
                ", loc=" + loc + ", dist = " + dist + ", parent id = " + parent_exhibit_id +
                '}';
    }

    PlanListItem(@NonNull String exhibit_name, String exhibit_id) {
        this.exhibit_name = exhibit_name;
        this.exhibit_id = exhibit_id;
    }


    PlanListItem(@NonNull String exhibit_name, String exhibit_id, String parent_id) {
        this.exhibit_name = exhibit_name;
        this.exhibit_id = exhibit_id;
        this.parent_exhibit_id = parent_id;
    }

    public static PlanListItem deepCopy(PlanListItem item) {
        PlanListItem cloned = new PlanListItem(item.exhibit_name, item.exhibit_id);
        cloned.loc = item.loc;
        cloned.dist = item.dist;
        cloned.parent_exhibit_id = item.parent_exhibit_id;
        cloned.id = item.id;
        cloned.visited = item.visited;

        return cloned;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public String getExhibitId() {
        if(this.parent_exhibit_id !=null) return parent_exhibit_id;
        return exhibit_id;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    // Factory method for loading our JSON
    public static List<PlanListItem> loadJSON(Context context, String path) {
        try {
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);
            Gson gson = new Gson();
            Type type = new TypeToken<List<PlanListItem>>() {
            }.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
