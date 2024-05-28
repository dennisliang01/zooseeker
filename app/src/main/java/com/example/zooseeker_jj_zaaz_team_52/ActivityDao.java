package com.example.zooseeker_jj_zaaz_team_52;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
    * ActivityDao Interface: Single Responsibility - Data Access Object (DAO) defines common operations interact with entities
 */
@Dao
public interface ActivityDao {

    @Insert
    long insert(Activity activity);

    @Query("SELECT * FROM `activity` WHERE `activity_name`=:activity_name")
    Activity get(String activity_name);

    @Update
    int update(Activity activity);

    @Delete
    int delete(Activity activity);

    @Insert
    List<Long> insertAll(List<Activity> activity);

    @Query("DELETE FROM activity")
    void deleteAll();



}

