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

public class ZooGraph implements Serializable {

    Graph<String, IdentifiedWeightedEdge> g = new DefaultUndirectedWeightedGraph<>(IdentifiedWeightedEdge.class);
    Map<String, ZooData.EdgeInfo> streetInfo;
    Map<String, ZooData.VertexInfo> exhibitInfo;

    public ZooGraph(String nodeFile, String graphFile, String edgeFile, Context context) {
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

        // IMPORT THE EDGES
        try {
            inputStream = context.getAssets().open(edgeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        reader = new InputStreamReader(inputStream);
        Type edgeType = new TypeToken<List<ZooData.EdgeInfo>>() {}.getType();
        List<ZooData.EdgeInfo> edgeData = gson_edge.fromJson(reader, edgeType);
        streetInfo = edgeData.stream()
                .collect(Collectors.toMap(v -> v.id, datum -> datum));

        // IMPORT THE NODES
        try {
            inputStream = context.getAssets().open(nodeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson_node = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
                .create();

        reader = new InputStreamReader(inputStream);
        Type nodeType = new TypeToken<List<ZooData.VertexInfo>>() {}.getType();
        List<ZooData.VertexInfo> nodeData = gson_node.fromJson(reader, nodeType);
        exhibitInfo = nodeData.stream()
                .collect(Collectors.toMap(v -> v.id, datum -> datum));
    }

    public String getExhibitNameById(String id) {
        return Objects.requireNonNull(exhibitInfo.get(id)).name;
    }
}
