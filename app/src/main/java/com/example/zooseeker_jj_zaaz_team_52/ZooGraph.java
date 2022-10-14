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

/**
 * ZooGraph Class: Single Responsibility - Imports information given in JSON asset files to create
 * graph of Zoo where vertices are exhibits/intersections/gates and edges are paths between vertices
 */

public class ZooGraph implements Serializable {

    Graph<String, IdentifiedWeightedEdge> g = new DefaultUndirectedWeightedGraph<>(IdentifiedWeightedEdge.class);
    Map<String, ZooData.EdgeInfo> streetInfo;
    Map<String, ZooData.VertexInfo> exhibitInfo;


    public ZooGraph(String nodeFile, String graphFile, String edgeFile, Context context) {
        //IMPORT THE GRAPH
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(graphFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Create an importer that can be used to populate our empty graph.
        JSONImporter<String, IdentifiedWeightedEdge> importer = new JSONImporter<>();

        // We don't need to convert the vertices in the graph, so we return them as is.
        importer.setVertexFactory(v -> v);

        // We need to make sure we set the IDs on our edges from the 'id' attribute.
        // While this is automatic for vertices, it isn't for edges. We keep the
        // definition of this in the IdentifiedWeightedEdge class for convenience.
        importer.addEdgeAttributeConsumer(IdentifiedWeightedEdge::attributeConsumer);

        // On Android, you would use context.getAssets().open(path) here like in Lab 5.
        Reader reader = new InputStreamReader(inputStream);

        // And now we just import it!
        importer.importGraph(g, reader);


        //IMPORT THE EDGE
        try {
            inputStream = context.getAssets().open(edgeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        reader = new InputStreamReader(inputStream);

        Gson gson = new Gson();
        Type type = new TypeToken<List<ZooData.EdgeInfo>>() {
        }.getType();
        List<ZooData.EdgeInfo> zooData = gson.fromJson(reader, type);

        streetInfo = zooData
                .stream()
                .collect(Collectors.toMap(v -> v.id, datum -> datum));


        //IMPORT THE NODES
        try {
            inputStream = context.getAssets().open(nodeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        reader = new InputStreamReader(inputStream);

        gson = new Gson();
        type = new TypeToken<List<ZooData.VertexInfo>>() {
        }.getType();
        List<ZooData.VertexInfo> exhibitData = gson.fromJson(reader, type);

        exhibitInfo = exhibitData.stream().collect(Collectors.toMap(v -> v.id, datum -> datum));
    } 

    public String getExhibitNameById(String id) {
        return Objects.requireNonNull(exhibitInfo.get(id)).name;
    }
}
