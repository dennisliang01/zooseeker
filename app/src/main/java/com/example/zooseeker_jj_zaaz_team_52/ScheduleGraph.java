package com.example.zooseeker_jj_zaaz_team_52;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
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
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ScheduleGraph Class: Single Responsibility - Imports information given in JSON asset files to create
 * graph of Zoo where vertices are exhibits/intersections/gates and edges are paths between vertices
 */
public class ScheduleGraph implements Serializable {

    Graph<String, IdentifiedWeightedEdge> g = new DefaultUndirectedWeightedGraph<>(IdentifiedWeightedEdge.class);
    Map<String, Schedule.EdgeInfo> timeInfo;
    Map<String, Schedule.VertexInfo> exhibitInfo;

    public ScheduleGraph(String nodeFile, String graphFile, String edgeFile, Context context) {
        // IMPORT THE GRAPH
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(graphFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONImporter<String, IdentifiedWeightedEdge> importer = new JSONImporter<>();
        importer.setVertexFactory(v -> v);
        importer.addEdgeAttributeConsumer(IdentifiedWeightedEdge::attributeConsumer);
        Reader reader = new InputStreamReader(inputStream);
        importer.importGraph(g, reader);

        Gson gson_edge = new Gson();
        // IMPORT THE EDGE
        try {
            inputStream = context.getAssets().open(edgeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        reader = new InputStreamReader(inputStream);
        Type type = new TypeToken<List<Schedule.EdgeInfo>>() {}.getType();
        List<Schedule.EdgeInfo> timeData = gson_edge.fromJson(reader, type);
        timeInfo = timeData.stream()
                .collect(Collectors.toMap(v -> v.id, datum -> datum));

        // IMPORT THE NODES
        try {
            inputStream = context.getAssets().open(nodeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        reader = new InputStreamReader(inputStream);
        Gson gson_node = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
                .create();
        type = new TypeToken<List<Schedule.VertexInfo>>() {}.getType();
        List<Schedule.VertexInfo> exhibitData = gson_node.fromJson(reader, type);
        exhibitInfo = exhibitData.stream()
                .collect(Collectors.toMap(v -> v.visitor_id, datum -> datum));
    }

    public String getExhibitNameById(String id) {
        return Objects.requireNonNull(exhibitInfo.get(id)).exhibit_name;
    }
}
