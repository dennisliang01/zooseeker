package com.example.zooseeker_jj_zaaz_team_52;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Class to read the animal information from the JSON file
public class AnimalInfoDB {
    public AnimalInfoDB(String AnimalInfoFile, Context context) {

        InputStream inputStream = null;

        Map<String, AnimalInfo> visitor_info;

        try {
            inputStream = context.getAssets().open(AnimalInfoFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Reader reader = new InputStreamReader(inputStream);

        Gson gson = new Gson();
        Type type = new TypeToken<List<AnimalInfo>>() {
        }.getType();
        List<AnimalInfo> timeData = gson.fromJson(reader, type);

        visitor_info = timeData
                .stream()
                .collect(Collectors.toMap(k -> k.scientific_name, datum -> datum));

    }
}
