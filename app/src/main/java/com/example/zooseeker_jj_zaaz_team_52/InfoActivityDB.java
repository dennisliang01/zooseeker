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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// This class is used to store the information of the activity.
public class InfoActivityDB implements Serializable {
    public InfoActivityDB(String InfoActivityFile, Context context) {

        InputStream inputStream = null;

        Map<List<String>, InfoActivity> activity_info;

        try {
            inputStream = context.getAssets().open(InfoActivityFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Reader reader = new InputStreamReader(inputStream);

        Gson gson = new Gson();
        Type type = new TypeToken<List<InfoActivity>>() {
        }.getType();
        List<InfoActivity> timeData = gson.fromJson(reader, type);

        activity_info = timeData
                .stream()
                .collect(Collectors.toMap(k -> {
                    List<String> key = List.of(k.getActivity_name(),k.getScientific_name());
                    return Collections.unmodifiableList(key);
                }, datum -> datum));

    }

}
