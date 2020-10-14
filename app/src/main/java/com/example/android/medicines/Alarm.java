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

    @ColumnInfo(name = "alarmHour")
    int alarmHour;

    @ColumnInfo(name = "alarmMinute")
    int alarmMinute;

    public Alarm(@NonNull String alarmName, String alarmTime,
                 int alarmHour, int alarmMinute) {
        this.alarmName = alarmName;
        this.alarmTime = alarmTime;
        this.alarmHour = alarmHour;
        this.alarmMinute = alarmMinute;
    }

    @NonNull
    public String getAlarmName() {
        return this.alarmName;
    }

    public String getAlarmTime() {
        return this.alarmTime;
    }

    public int getAlarmHour() {
        return alarmHour;
    }

    public int getAlarmMinute() {
        return alarmMinute;
    }
}
