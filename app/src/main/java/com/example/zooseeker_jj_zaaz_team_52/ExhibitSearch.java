package com.example.zooseeker_jj_zaaz_team_52;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import info.debatty.java.stringsimilarity.JaroWinkler;
import info.debatty.java.stringsimilarity.interfaces.StringDistance;

/**
 * ExhibitSearch Class: Single Responsibility - To determine which exhibits should and should
 * not be displayed in search menu depending on String user inputs
 */
public class ExhibitSearch {
    public Map<String, ZooData.VertexInfo> zooData;
    StringDistance stringMatcher;
    public static final double DEFAULT_THRESHOLD = .3;
    public static final StringDistance DEFAULT_STRING_MATCHER = new JaroWinkler();

    private void setupSearch(String jsonFile, Context context, StringDistance stringMatcher) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(jsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson_node = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
                .create();

        Reader reader = new InputStreamReader(inputStream);
        Type nodeType = new TypeToken<List<ZooData.VertexInfo>>() {}.getType();
        List<ZooData.VertexInfo> zooData = gson_node.fromJson(reader, nodeType);

        this.zooData = zooData.stream().collect(Collectors.toMap(v -> v.id, datum -> datum));
        this.stringMatcher = stringMatcher;
    }

    /**
     * Default constructor, Uses DEFAULT_STRING_MATCHER and sample_node_file.
     * see {@link #ExhibitSearch(String, Context, StringDistance)} for full information
     */
    public ExhibitSearch(Context context) {
        System.out.println("WHAT THE FUCK");
        setupSearch(context.getString(R.string.node_file), context, DEFAULT_STRING_MATCHER);
    }

    /**
     * Load a .json file of data to load and the related VertexInfo objects to be searched.
     *
     * @param jsonFile      file containing VertexInfo for loading into search.
     * @param stringMatcher an Algorithm to Compute similarity between strings
     */
    public ExhibitSearch(String jsonFile, Context context, StringDistance stringMatcher) {
        setupSearch(jsonFile, context, stringMatcher);
    }

    /**
     * see {@link #searchKeyword(String, double)} for more info
     *
     * @param keyword the search term to match.
     * @return a list of VertexInfo matching the keyword within DEFAULT_THRESHOLD
     */
    public List<ZooData.VertexInfo> searchKeyword(String keyword) {
        return searchKeyword(keyword, DEFAULT_THRESHOLD);
    }


    /**
     * Uses assigned search algorithm to compare against exhibit names and the related tags for
     * each exhibit.
     *
     * @param keyword   the search term to match.
     * @param threshold maximum distance from keyword to be included in results.
     * @return a list of VertexInfo Containing only the vertexes of exhibits that have at least one
     * tag or with a name less than threshold.
     */
    List<ZooData.VertexInfo> searchKeyword(String keyword, double threshold) {

        if(keyword.equals("")){
            return new ArrayList<>(zooData.values());
        }
        keyword = keyword.toLowerCase(Locale.ROOT);
        List<Pair<Double, ZooData.VertexInfo>> data = new ArrayList<>();

        // Android log class, used to log informational messages
        Log.i(this.getClass().getSimpleName(),
                String.format("Search Weights for search term: [%s]", keyword));
        for (Map.Entry<String, ZooData.VertexInfo> exhibit : zooData.entrySet()) {
            String[] exhibitWords = exhibit.getValue().name.split(" ");
            double exhibitWeight = stringMatcher.distance(keyword, exhibit.getValue().name.toLowerCase(Locale.ROOT));

            for (String word : exhibitWords) {
                word = word.toLowerCase(Locale.ROOT);
                double weight = stringMatcher.distance(keyword, word);
                if (weight < exhibitWeight) {
                    exhibitWeight = weight;
                }
            }

            double minAcceptableThreshold = threshold + 1;
            Log.i(this.getClass().getSimpleName(), String.format("Exhibit: [%s] weight [%f]",
                    exhibit.getValue().name, exhibitWeight));

            for (String tag : exhibit.getValue().tags) {
                tag = tag.toLowerCase(Locale.ROOT);
                double dist = stringMatcher.distance(keyword, tag);

                Log.i(this.getClass().getSimpleName(), String.format("\tTag: [%s] weight [%f]",
                        tag, dist));
                if (dist <= threshold && dist < minAcceptableThreshold) {
                    minAcceptableThreshold = dist;
                }
            }
            if (minAcceptableThreshold <= threshold) {
                data.add(new Pair<>(minAcceptableThreshold, exhibit.getValue()));
            } else if (exhibitWeight < threshold) {
                data.add(new Pair<>(exhibitWeight, exhibit.getValue()));
            }
        }
        //Convert to int and compare to put best matches at top
        data.sort((p1, p2) -> (int) Math.ceil(1e4 * (p1.first - p2.first)));
        List<ZooData.VertexInfo> result = new ArrayList<>();
        for (Pair<Double, ZooData.VertexInfo> info : data) {
            result.add(info.second);
        }
        result.removeIf(vertexInfo -> vertexInfo.kind != ZooData.VertexInfo.Kind.EXHIBIT);

        return result;
    }
}
