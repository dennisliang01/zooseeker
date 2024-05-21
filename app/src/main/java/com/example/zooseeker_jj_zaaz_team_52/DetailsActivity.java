package com.example.zooseeker_jj_zaaz_team_52;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        Button planButton = findViewById(R.id.btn_detail_plan_add);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                intent.putExtra("ExhibitName", currExhibitName);
                intent.putExtra("ExhibitID", currExhibitID);
                intent.putExtra("OPEN_DIALOG", true);
                startActivity(intent);
                finish();
            }
        });

        planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateDetailsDialog(currExhibitName, currExhibitID);
            }
        });

    }

    private void generateDetailsDialog(String currExhibitName, String currExhibitID) {

        Button planButton = findViewById(R.id.btn_detail_plan_add);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DetailsActivity.this);

        dialogBuilder.setTitle(currExhibitName);
        dialogBuilder.setMessage("Are you sure you want to add " + currExhibitName + " to your plan?");

        dialogBuilder.setPositiveButton("Confirm", (DialogInterface.OnClickListener) (dialog, which) -> {

            PlanListItemDao planListItemDao = PlanDatabase.getSingleton(DetailsActivity.this).planListItemDao();
            PlanListItem newPlanExhibit = new PlanListItem(currExhibitName, currExhibitID);
            planListItemDao.insert(newPlanExhibit);

            Toast mapPlanSuccessToast = Toast.makeText(DetailsActivity.this, "Added " + currExhibitName + " to plan!", Toast.LENGTH_LONG);
            mapPlanSuccessToast.show();

            planButton.setEnabled(false);

        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        dialogBuilder.setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = dialogBuilder.create();
        // Show the Alert Dialog box
        alertDialog.show();

    }
}