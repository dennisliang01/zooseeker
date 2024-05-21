package com.example.zooseeker_jj_zaaz_team_52;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_exhibit_details);

        String currExhibitName = getIntent().getStringExtra("ExhibitName");
        String currExhibitID = getIntent().getStringExtra("ExhibitID");

        TextView titleText = findViewById(R.id.exhibit_id);
        titleText.setText(currExhibitName);

        Button backButton = findViewById(R.id.btn_back);

        Context context = this;

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("ExhibitName", currExhibitName);
                intent.putExtra("ExhibitID", currExhibitID);
                intent.putExtra("OPEN_DIALOG", true);
                startActivity(intent);
                finish();
            }
        });
    }
}