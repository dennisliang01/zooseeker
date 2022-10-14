package com.example.zooseeker_jj_zaaz_team_52;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * PlanListItemDAO Interface: Single Responsibility - Data Access Object (DAO) defines common
 * operations interact with entities
 */
@Dao
public interface PlanListItemDao {
    @Insert
    long insert(PlanListItem planListItem);

    @Query("SELECT * FROM `plan_list_item` WHERE `id`=:id")
    PlanListItem get(long id);

    @Query("SELECT * FROM `plan_list_item` ORDER BY `dist`")
    List<PlanListItem> getAll();

    @Update
    int update(PlanListItem planListItem);

    @Delete
    int delete(PlanListItem planListItem);

    @Query("DELETE FROM `plan_list_item` WHERE `exhibit_name`=:name")
    void delete(String name);

    @Insert
    List<Long> insertAll(List<PlanListItem> planListItem);

    @Query("DELETE FROM plan_list_item")
    void deleteAll();

    @Query("SELECT * FROM `plan_list_item` ORDER BY `dist`")
    LiveData<List<PlanListItem>> getAllLive();

}
