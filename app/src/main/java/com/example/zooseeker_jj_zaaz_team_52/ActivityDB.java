package com.example.zooseeker_jj_zaaz_team_52;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// This class is used to create the database for the activities
@Database(entities = {Activity.class}, version = 1, exportSchema = false)
public abstract class ActivityDB extends RoomDatabase {

    private static ActivityDB singleton = null;

    public abstract ActivityDao activityDao();

    public synchronized static ActivityDB getSingleton(Context context) {
        if (singleton == null) {
            singleton = ActivityDB.makeDatabase(context);
        }
        return singleton;
    }

    // This method creates the database
    private static ActivityDB makeDatabase(Context context) {
        return Room.databaseBuilder(context, ActivityDB.class, "activity.db")
                .allowMainThreadQueries()
                .build();
    }
}
