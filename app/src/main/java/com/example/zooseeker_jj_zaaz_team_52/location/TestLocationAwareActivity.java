package com.example.zooseeker_jj_zaaz_team_52.location;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.zooseeker_jj_zaaz_team_52.R;
import com.example.zooseeker_jj_zaaz_team_52.ZooData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TestLocationAwareActivity Class: Single Responsibility - Solely used for testing to
 * ensure that app is aware of changes in user's current location
 */
public class TestLocationAwareActivity extends AppCompatActivity {
    private ZooLocation pos = null;
    private LocationModel model;
    private Map<String, ZooData.VertexInfo> zooData;

    public ZooLocation getPos() {
        return pos;
    }

    public LocationModel getModel() {
        return model;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadJson(this);
        model = new ViewModelProvider(this).get(LocationModel.class);
        model.setZooData(zooData);
        model.getLastKnownCoords().observe(this, (zooLocation) -> {
            Log.i(this.getClass().toString(), String.format("Observing location model update to %s", zooLocation));
            pos = zooLocation;
        });
    }

    public void loadJson(Context context) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(context.getString(R.string.node_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Reader reader = new InputStreamReader(inputStream);

        Gson gson = new Gson();
        Type type = new TypeToken<List<ZooData.VertexInfo>>() {
        }.getType();
        List<ZooData.VertexInfo> zooData = gson.fromJson(reader, type);

        this.zooData = zooData.stream().collect(Collectors.toMap(v -> v.id, datum -> datum));
    }

}
