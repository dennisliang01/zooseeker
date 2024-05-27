package com.example.zooseeker_jj_zaaz_team_52;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActivityDB implements Serializable {

    public ActivityDB(String ActivityFile, Context context) {

        InputStream inputStream = null;

        Map<String, Activity> visitor_info;

        try {
            inputStream = context.getAssets().open(ActivityFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Reader reader = new InputStreamReader(inputStream);

        Gson gson = new Gson();
        Type type = new TypeToken<List<Activity>>() {
        }.getType();
        List<Activity> timeData = gson.fromJson(reader, type);

        visitor_info = timeData
                .stream()
                .collect(Collectors.toMap(k -> k.activity_name, datum -> datum));

    }
}
