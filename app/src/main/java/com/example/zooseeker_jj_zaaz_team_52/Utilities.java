package com.example.zooseeker_jj_zaaz_team_52;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.zooseeker_jj_zaaz_team_52.location.Coord;
import com.example.zooseeker_jj_zaaz_team_52.location.Coords;
import com.example.zooseeker_jj_zaaz_team_52.location.LocationModel;
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
 * Utilities Class: Single Responsibility - Manages alerts
 */
public class Utilities {

    public static void showAlert(Activity activity, String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder
                .setTitle("Alert!")
                .setMessage(message)
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    ((DirectionsActivity) activity).updateRoute();
                    dialogInterface.dismiss();
                })
                .setNegativeButton("No", (dialogInterface, i) -> {
                    ((DirectionsActivity) activity).updateDirections();
                    dialogInterface.dismiss();
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    @SuppressLint("VisibleForTests")
    public static void mockLocationAlert(Activity activity, String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        // Set up the input
        final EditText lng = new EditText(activity);
        final EditText lat = new EditText(activity);

        // Specify the type of input expected
        lng.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        lng.setHint("longitude");
        lng.setGravity(Gravity.CENTER);

        lat.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        lat.setHint("latitude");
        lat.setGravity(Gravity.CENTER);

        //Set up a linear layout
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20, 20, 20, 20);

        LinearLayout lp = new LinearLayout(activity.getApplicationContext());
        lp.setOrientation(LinearLayout.VERTICAL);

        lp.addView(lat, layoutParams);
        lp.addView(lng, layoutParams);

        alertBuilder
                .setTitle("Alert!")
                .setView(lp)
                .setMessage(message)
                .setPositiveButton("Enter", (dialogInterface, i) -> {
                    double input_lng = 0;
                    double input_lat = 0;
                    try{
                        input_lng = Double.parseDouble(lng.getText().toString().trim());
                        input_lat = Double.parseDouble(lat.getText().toString().trim());
                    }catch (NumberFormatException e){
                        input_lat = Coords.ZOO.lat;
                        input_lng = Coords.ZOO.lng;
                    }
                    Log.i("latitude", String.valueOf(input_lat));
                    Log.i("longitude", String.valueOf(input_lng));

                    Coord coord = new Coord(input_lat, input_lng);
                    LocationModel model = new ViewModelProvider((AppCompatActivity) activity).get(LocationModel.class);
                    model.setZooData(loadJson(activity));
                    model.mockLocation(coord);
                    dialogInterface.dismiss();
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();

    }

    private static Map<String, ZooData.VertexInfo> loadJson(Context context) {
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

        return zooData.stream().collect(Collectors.toMap(v -> v.id, datum -> datum));
    }
}
