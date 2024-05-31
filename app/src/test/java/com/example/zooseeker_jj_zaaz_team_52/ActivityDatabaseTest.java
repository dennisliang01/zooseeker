package com.example.zooseeker_jj_zaaz_team_52;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ActivityDatabaseTest {

    private ActivityDao dao;
    private ActivityDB db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, ActivityDB.class)
                .allowMainThreadQueries()
                .build();
        dao = db.activityDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    // Test inserting activities into the database
    @Test
    public void testInsert() {
        Activity activity1 = new Activity("Elephant", "Feeding");
        Activity activity2 = new Activity("Baboons", "Sleeping");

        long id1 = dao.insert(activity1);
        long id2 = dao.insert(activity2);
        assertNotEquals(id1, id2);
        assertNotEquals(id1, id2);
    }

    // Test retrieving activities from the database
    @Test
    public void testGet() {
        Activity activity1 = new Activity("Elephant", "Feeding");
        Activity activity2 = new Activity("Baboons", "Sleeping");

        long id1 = dao.insert(activity1);
        long id2 = dao.insert(activity2);

        Activity activity1FromDb = dao.get("Elephant");
        Activity activity2FromDb = dao.get("Baboons");

        assertNotNull(activity1FromDb);
        assertNotNull(activity2FromDb);
        assertEquals(activity1.getActivityName(), activity1FromDb.getActivityName());
        assertEquals(activity1.getActivityType(), activity1FromDb.getActivityType());
        assertEquals(activity2.getActivityName(), activity2FromDb.getActivityName());
        assertEquals(activity2.getActivityType(), activity2FromDb.getActivityType());
    }

    // Test updating activities in the database
    @Test
    public void testUpdate() {
        Activity activity = new Activity("Elephant", "Feeding");
        long id = dao.insert(activity);

        activity = dao.get("Elephant");
        assertNotNull(activity);

        int activityUpdated = dao.update(activity);
        assertEquals(1, activityUpdated);
    }

    // Test deleting activities from the database
    @Test
    public void testDelete() {
        Activity activity = new Activity("Elephant", "Feeding");
        long id = dao.insert(activity);

        activity = dao.get("Elephant");
        assertNotNull(activity);

        int activityDeleted = dao.delete(activity);
        assertEquals(1, activityDeleted);

        activity = dao.get("Elephant");
        assertNull(activity);
    }


}
