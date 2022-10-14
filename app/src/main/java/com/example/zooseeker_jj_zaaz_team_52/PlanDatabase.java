package com.example.zooseeker_jj_zaaz_team_52;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * PlanDatabase Abstract Class: Single Responsibility - Creates database to store zoo
 * exhibit plan
 */
@Database(entities = {PlanListItem.class}, version = 1, exportSchema = false)
public abstract class PlanDatabase extends RoomDatabase {
    private static PlanDatabase singleton = null;

    public abstract PlanListItemDao planListItemDao();

    public synchronized static PlanDatabase getSingleton(Context context) {
        if (singleton == null) {
            singleton = PlanDatabase.makeDatabase(context);
        }
        return singleton;
    }

    private static PlanDatabase makeDatabase(Context context) {
        return Room.databaseBuilder(context, PlanDatabase.class, "zoo_app.db")
                .allowMainThreadQueries()
                .build();
    }
}
