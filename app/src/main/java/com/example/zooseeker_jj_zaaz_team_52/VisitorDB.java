package com.example.zooseeker_jj_zaaz_team_52;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.nio.json.JSONImporter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
public class VisitorDB {
    Map<String, Schedule.VertexInfo> exhibitInfo;

    public VisitorDB(String visitorFile, Context context) {

        InputStream inputStream = null;

        Map<String, Visitor> visitor_info;

        //IMPORT THE EDGE
        try {
            inputStream = context.getAssets().open(visitorFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Reader reader = new InputStreamReader(inputStream);

        Gson gson = new Gson();
        Type type = new TypeToken<List<Visitor>>() {
        }.getType();
        List<Visitor> timeData = gson.fromJson(reader, type);

        visitor_info = timeData
                .stream()
                .collect(Collectors.toMap(v -> v.visitor_id, datum -> datum));

    }
}
