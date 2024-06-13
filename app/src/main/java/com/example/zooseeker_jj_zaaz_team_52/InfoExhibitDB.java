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
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// This class is used to store the information of the exhibit
public class InfoExhibitDB implements Serializable {

    // Read JSON file and store the information of the exhibit
    public InfoExhibitDB(String InfoExhibitFile, Context context) {

        InputStream inputStream = null;

        Map<List<String>, InfoExhibit> exhibit_info;

        try {
            inputStream = context.getAssets().open(InfoExhibitFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Reader reader = new InputStreamReader(inputStream);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
                .create();
        Type type = new TypeToken<List<InfoExhibit>>() {
        }.getType();
        List<InfoExhibit> timeData = gson.fromJson(reader, type);

        exhibit_info = timeData
                .stream()
                .collect(Collectors.toMap(k -> {
                    List<String> key = List.of(k.getScientific_name(),k.getExhibit_name());
                    return Collections.unmodifiableList(key);
                }, datum -> datum));

    }
}
