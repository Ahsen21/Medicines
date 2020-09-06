package com.example.android.medicines;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@SuppressWarnings("serial")

@Entity(tableName = "medicine_table")
public class Medicine implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "medName")
    String medName;

    String morningDosage, morningUnit;

    String afternoonDosage, afternoonUnit;

    String eveningDosage, eveningUnit;

    String nightDosage, nightUnit;

    boolean mondayOn, tuesdayOn, wednesdayOn, thursdayOn, fridayOn, saturdayOn, sundayOn;

    public Medicine(@NonNull String medName,
                    String morningDosage, String morningUnit,
                    String afternoonDosage, String afternoonUnit,
                    String eveningDosage, String eveningUnit,
                    String nightDosage, String nightUnit,
                    boolean mondayOn, boolean tuesdayOn, boolean wednesdayOn, boolean thursdayOn,
                    boolean fridayOn, boolean saturdayOn, boolean sundayOn) {
        this.medName = medName;
        this.morningDosage = morningDosage;
        this.morningUnit = morningUnit;
        this.afternoonDosage = afternoonDosage;
        this.afternoonUnit = afternoonUnit;
        this.eveningDosage = eveningDosage;
        this.eveningUnit = eveningUnit;
        this.nightDosage = nightDosage;
        this.nightUnit = nightUnit;
        this.mondayOn = mondayOn;
        this.tuesdayOn = tuesdayOn;
        this.wednesdayOn = wednesdayOn;
        this.thursdayOn = thursdayOn;
        this.fridayOn = fridayOn;
        this.saturdayOn = saturdayOn;
        this.sundayOn = sundayOn;

    }

    @NonNull
    String getMedName() {
        return medName;
    }

    String getMorningDosage() {
        return morningDosage;
    }

    String getMorningUnit() {
        return morningUnit;
    }

    String getAfternoonDosage() {
        return afternoonDosage;
    }

    String getAfternoonUnit() {
        return afternoonUnit;
    }

    String getEveningDosage() {
        return eveningDosage;
    }

    String getEveningUnit() {
        return eveningUnit;
    }

    String getNightDosage() {
        return nightDosage;
    }

    String getNightUnit() {
        return nightUnit;
    }

    boolean isMondayOn() {
        return mondayOn;
    }

    boolean isTuesdayOn() {
        return tuesdayOn;
    }

    boolean isWednesdayOn() {
        return wednesdayOn;
    }

    boolean isThursdayOn() {
        return thursdayOn;
    }

    boolean isFridayOn() {
        return fridayOn;
    }

    boolean isSaturdayOn() {
        return saturdayOn;
    }

    boolean isSundayOn() {
        return sundayOn;
    }
}
