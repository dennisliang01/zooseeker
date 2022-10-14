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

import java.io.IOException;

/**
 * Test that UI integrates with PlanDatabase correctly
 */
@RunWith(AndroidJUnit4.class)
public class PlanDatabaseTest {
    private PlanListItemDao dao;
    private PlanDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, PlanDatabase.class)
                .allowMainThreadQueries()
                .build();
        dao = db.planListItemDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    /**
     * Test added plan items are inserted into database
     */
    @Test
    public void testInsert() {
        PlanListItem item1 = new PlanListItem("Elephant", "1 Way");
        PlanListItem item2 = new PlanListItem("Baboons", "Forcest Way");

        long id1 = dao.insert(item1);
        long id2 = dao.insert(item2);
        assertNotEquals(id1, id2);
    }

    /**
     * Test added plan items can also be retrieved from database
     */
    @Test
    public void testGet() {
        PlanListItem insertedItem = new PlanListItem("Elephant", "1 Way");
        long id = dao.insert(insertedItem);

        PlanListItem item = dao.get(id);
        assertEquals(id, item.id);
        assertEquals(insertedItem.exhibit_name, item.exhibit_name);
        assertEquals(insertedItem.loc, item.loc);
        assertEquals(insertedItem.dist, item.dist);
    }

    /**
     * Test database updates with accurate plan items when plan items inserted / edited
     */
    @Test
    public void testUpdate() {
        PlanListItem item = new PlanListItem("Elephant", "1 Way");
        long id = dao.insert(item);

        item = dao.get(id);
        item.exhibit_name = "Monkey";
        int itemsUpdated = dao.update(item);
        assertEquals(1, itemsUpdated);

        item = dao.get(id);
        assertNotNull(item);
        assertEquals("Monkey", item.exhibit_name);
    }

    /**
     * Test deleted plan items are reflected in database
     */
    @Test
    public void testDelete() {
        PlanListItem item = new PlanListItem("Elephant", "1 Way");
        long id = dao.insert(item);

        item = dao.get(id);
        int itemsDeleted = dao.delete(item);
        assertEquals(1, itemsDeleted);
        assertNull(dao.get(id));
    }
}
