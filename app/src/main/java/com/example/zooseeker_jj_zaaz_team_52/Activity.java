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

@Entity(tableName = "activity")
public class Activity implements Serializable {

    public Activity(@NonNull String activity_name, String activity_type) {
        this.activity_name = activity_name;
        this.activity_type = activity_type;
    }

    @PrimaryKey
    @NonNull
    public String activity_name;

    public String activity_type;

    public String getActivityName() {
        return activity_name;
    }

    public String getActivityType() {
        return activity_type;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activity_name='" + activity_name + '\'' +
                ", activity_type=" + activity_type + '\'' +
                '}';
    }

    public static List<Activity> loadJSON(Context context, String path) {
        try {
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);
            Gson gson = new Gson();
            Type type = new TypeToken<List<Activity>>() {
            }.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}

