package com.example.android.medicines;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Alarm alarm);

    @Query("DELETE FROM alarm_table")
    void deleteAll();

    @Query("SELECT * from alarm_table")
    LiveData<List<Alarm>> getAllAlarms();

    @Query("SELECT * from alarm_table LIMIT 1")
    Alarm[] getAnyAlarm();

    @Delete
    void deleteAlarm(Alarm alarm);
}
