package com.example.android.medicines;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "alarm_table")
public class Alarm implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "alarmName")
    String alarmName;

    @ColumnInfo(name = "alarmTime")
    String alarmTime;

    public Alarm(@NonNull String alarmName, String alarmTime) {
        this.alarmName = alarmName;
        this.alarmTime = alarmTime;
    }

    @NonNull
    public String getAlarmName() {
        return this.alarmName;
    }

    public String getAlarmTime() {
        return this.alarmTime;
    }
}
