package com.example.android.medicines;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    MedicineViewModel medicineViewModel;
    AlarmViewModel alarmViewModel;
    List<Medicine> medicineArray;
    List<Alarm> alarmArray;
    TextView morningMeds, afternoonMeds, eveningMeds, nightMeds;
    TextView morningAlarm, afternoonAlarm, eveningAlarm, nightAlarm;
    ImageView morningArrow, afternoonArrow, eveningArrow, nightArrow;
    Button button1, button2, button3, button4, button5, button6, button7;
    TextView dayHeading;
    String dayName;
//    StringBuilder morningText = new StringBuilder();
//    StringBuilder afternoonText = new StringBuilder();
//    StringBuilder eveningText = new StringBuilder();
//    StringBuilder nightText = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNavBar);
        bottomNav.setSelectedItemId(R.id.home_page);
        bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_page:
                                return true;
                            case R.id.medicine_page:
                                startActivity(new Intent(getApplicationContext(),
                                        MedicineList.class));
                                overridePendingTransition(0, 0);
                                return true;
                            case R.id.alarm_page:
                                startActivity(new Intent(getApplicationContext(), SetAlarms.class));
                                overridePendingTransition(0, 0);
                                return true;
                        }
                        return false;
                    }
                });

        button1 = findViewById(R.id.monday_button);
        button2 = findViewById(R.id.tuesday_button);
        button3 = findViewById(R.id.wednesday_button);
        button4 = findViewById(R.id.thursday_button);
        button5 = findViewById(R.id.friday_button);
        button6 = findViewById(R.id.saturday_button);
        button7 = findViewById(R.id.sunday_button);

        dayHeading = findViewById(R.id.day_heading);

        morningMeds = findViewById(R.id.morning_meds);
        afternoonMeds = findViewById(R.id.afternoon_meds);
        eveningMeds = findViewById(R.id.evening_meds);
        nightMeds = findViewById(R.id.night_meds);

        morningAlarm = findViewById(R.id.morning_card_time);
        afternoonAlarm = findViewById(R.id.afternoon_card_time);
        eveningAlarm = findViewById(R.id.evening_card_time);
        nightAlarm = findViewById(R.id.night_card_time);

        morningArrow = findViewById(R.id.morning_arrow);
        afternoonArrow = findViewById(R.id.afternoon_arrow);
        eveningArrow = findViewById(R.id.evening_arrow);
        nightArrow = findViewById(R.id.night_arrow);

        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);

        medicineViewModel.getAllMeds().observe(this, new Observer<List<Medicine>>() {
            @Override
            public void onChanged(@Nullable final List<Medicine> medicines) {
                medicineArray = medicines;
                displayMedsByDay();
            }
        });

        alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel.class);

        alarmViewModel.getAllAlarms().observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(@Nullable final List<Alarm> alarms) {
                alarmArray = alarms;
                displayAlarms();
            }
        });
    }

    private void displayAlarms() {
        String morningTime = "",
                afternoonTime = "",
                eveningTime = "",
                nightTime = "";

        if (alarmArray == null)
            return;
        for (Alarm alarm : alarmArray) {
            if (alarm.alarmName.equals("Morning"))
                morningTime = alarm.alarmTime;
            if (alarm.alarmName.equals("Afternoon"))
                afternoonTime = alarm.alarmTime;
            if (alarm.alarmName.equals("Evening"))
                eveningTime = alarm.alarmTime;
            if (alarm.alarmName.equals("Night"))
                nightTime = alarm.alarmTime;
        }
        morningAlarm.setText(morningTime);
        afternoonAlarm.setText(afternoonTime);
        eveningAlarm.setText(eveningTime);
        nightAlarm.setText(nightTime);
    }

//    public void mondayMeds(Medicine med) {
//
//        StringBuilder morningText = new StringBuilder();
//        StringBuilder afternoonText = new StringBuilder();
//        StringBuilder eveningText = new StringBuilder();
//        StringBuilder nightText = new StringBuilder();
//
//
//        if (med.isMondayOn()) {
//            if (medicineArray == null)
//                return;
//            if (!med.morningDosage.equals(""))
//                morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
//                        .append(med.morningUnit).append("\n");
//            if (!med.afternoonDosage.equals(""))
//                afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
//                        .append(" ").append(med.afternoonUnit).append("\n");
//            if (!med.eveningDosage.equals(""))
//                eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
//                        .append(" ").append(med.eveningUnit).append("\n");
//            if (!med.nightDosage.equals(""))
//                nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
//                        .append(med.nightUnit).append("\n");
//
//            morningMeds.setText(morningText.toString());
//            afternoonMeds.setText(afternoonText.toString());
//            eveningMeds.setText(eveningText.toString());
//            nightMeds.setText(nightText.toString());
//        }
//
//    }
//
//    public void tuesdayMeds(Medicine med) {
//
//        StringBuilder morningText = new StringBuilder();
//        StringBuilder afternoonText = new StringBuilder();
//        StringBuilder eveningText = new StringBuilder();
//        StringBuilder nightText = new StringBuilder();
//
//
//        if (med.isTuesdayOn()) {
//            if (medicineArray == null)
//                return;
//            if (!med.morningDosage.equals(""))
//                morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
//                        .append(med.morningUnit).append("\n");
//            if (!med.afternoonDosage.equals(""))
//                afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
//                        .append(" ").append(med.afternoonUnit).append("\n");
//            if (!med.eveningDosage.equals(""))
//                eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
//                        .append(" ").append(med.eveningUnit).append("\n");
//            if (!med.nightDosage.equals(""))
//                nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
//                        .append(med.nightUnit).append("\n");
//
//            morningMeds.setText(morningText.toString());
//            afternoonMeds.setText(afternoonText.toString());
//            eveningMeds.setText(eveningText.toString());
//            nightMeds.setText(nightText.toString());
//        }
//
//    }
//
//    public void wednesdayMeds(Medicine med) {
//
//        StringBuilder morningText = new StringBuilder();
//        StringBuilder afternoonText = new StringBuilder();
//        StringBuilder eveningText = new StringBuilder();
//        StringBuilder nightText = new StringBuilder();
//
//
//        if (med.isWednesdayOn()) {
//            if (medicineArray == null)
//                return;
//            if (!med.morningDosage.equals(""))
//                morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
//                        .append(med.morningUnit).append("\n");
//            if (!med.afternoonDosage.equals(""))
//                afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
//                        .append(" ").append(med.afternoonUnit).append("\n");
//            if (!med.eveningDosage.equals(""))
//                eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
//                        .append(" ").append(med.eveningUnit).append("\n");
//            if (!med.nightDosage.equals(""))
//                nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
//                        .append(med.nightUnit).append("\n");
//
//            morningMeds.setText(morningText.toString());
//            afternoonMeds.setText(afternoonText.toString());
//            eveningMeds.setText(eveningText.toString());
//            nightMeds.setText(nightText.toString());
//        }
//
//    }
//
//    public void thursdayMeds(Medicine med) {
//
//        StringBuilder morningText = new StringBuilder();
//        StringBuilder afternoonText = new StringBuilder();
//        StringBuilder eveningText = new StringBuilder();
//        StringBuilder nightText = new StringBuilder();
//
//
//        if (med.isThursdayOn()) {
//            if (medicineArray == null)
//                return;
//            if (!med.morningDosage.equals(""))
//                morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
//                        .append(med.morningUnit).append("\n");
//            if (!med.afternoonDosage.equals(""))
//                afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
//                        .append(" ").append(med.afternoonUnit).append("\n");
//            if (!med.eveningDosage.equals(""))
//                eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
//                        .append(" ").append(med.eveningUnit).append("\n");
//            if (!med.nightDosage.equals(""))
//                nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
//                        .append(med.nightUnit).append("\n");
//
//            morningMeds.setText(morningText.toString());
//            afternoonMeds.setText(afternoonText.toString());
//            eveningMeds.setText(eveningText.toString());
//            nightMeds.setText(nightText.toString());
//        }
//
//    }
//
//    public void fridayMeds(Medicine med) {
//
//        StringBuilder morningText = new StringBuilder();
//        StringBuilder afternoonText = new StringBuilder();
//        StringBuilder eveningText = new StringBuilder();
//        StringBuilder nightText = new StringBuilder();
//
//
//        if (med.isFridayOn()) {
//            if (medicineArray == null)
//                return;
//            if (!med.morningDosage.equals(""))
//                morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
//                        .append(med.morningUnit).append("\n");
//            if (!med.afternoonDosage.equals(""))
//                afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
//                        .append(" ").append(med.afternoonUnit).append("\n");
//            if (!med.eveningDosage.equals(""))
//                eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
//                        .append(" ").append(med.eveningUnit).append("\n");
//            if (!med.nightDosage.equals(""))
//                nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
//                        .append(med.nightUnit).append("\n");
//
//            morningMeds.setText(morningText.toString());
//            afternoonMeds.setText(afternoonText.toString());
//            eveningMeds.setText(eveningText.toString());
//            nightMeds.setText(nightText.toString());
//        }
//
//    }
//
//    public void saturdayMeds(Medicine med) {
//
//        StringBuilder morningText = new StringBuilder();
//        StringBuilder afternoonText = new StringBuilder();
//        StringBuilder eveningText = new StringBuilder();
//        StringBuilder nightText = new StringBuilder();
//
//
//        if (med.isSaturdayOn()) {
//            if (medicineArray == null)
//                return;
//            if (!med.morningDosage.equals(""))
//                morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
//                        .append(med.morningUnit).append("\n");
//            if (!med.afternoonDosage.equals(""))
//                afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
//                        .append(" ").append(med.afternoonUnit).append("\n");
//            if (!med.eveningDosage.equals(""))
//                eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
//                        .append(" ").append(med.eveningUnit).append("\n");
//            if (!med.nightDosage.equals(""))
//                nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
//                        .append(med.nightUnit).append("\n");
//
//            morningMeds.setText(morningText.toString());
//            afternoonMeds.setText(afternoonText.toString());
//            eveningMeds.setText(eveningText.toString());
//            nightMeds.setText(nightText.toString());
//        }
//
//    }
//
//    public void sundayMeds(Medicine med) {
//
//        StringBuilder morningText = new StringBuilder();
//        StringBuilder afternoonText = new StringBuilder();
//        StringBuilder eveningText = new StringBuilder();
//        StringBuilder nightText = new StringBuilder();
//
//
//        if (med.isSundayOn()) {
//            if (medicineArray == null)
//                return;
//            if (!med.morningDosage.equals(""))
//                morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
//                        .append(med.morningUnit).append("\n");
//            if (!med.afternoonDosage.equals(""))
//                afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
//                        .append(" ").append(med.afternoonUnit).append("\n");
//            if (!med.eveningDosage.equals(""))
//                eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
//                        .append(" ").append(med.eveningUnit).append("\n");
//            if (!med.nightDosage.equals(""))
//                nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
//                        .append(med.nightUnit).append("\n");
//
//            morningMeds.setText(morningText.toString());
//            afternoonMeds.setText(afternoonText.toString());
//            eveningMeds.setText(eveningText.toString());
//            nightMeds.setText(nightText.toString());
//        }
//
//    }

    @SuppressWarnings("deprecation")
    public void displayMedsByDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        StringBuilder morningText = new StringBuilder();
        StringBuilder afternoonText = new StringBuilder();
        StringBuilder eveningText = new StringBuilder();
        StringBuilder nightText = new StringBuilder();

        switch (day) {
            case Calendar.MONDAY:
                dayName = "Monday";
                dayHeading.setText(dayName);
                button1.setBackground(getResources().getDrawable(R.drawable.button_clicked));
                button1.setTextColor(Color.parseColor("#283593"));
                for (Medicine med : medicineArray) {
//                    mondayMeds(med);
                    if (med.isMondayOn()) {
                        if (medicineArray == null)
                            return;
                        if (!med.morningDosage.equals(""))
                            morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
                                    .append(med.morningUnit).append("\n");
                        if (!med.afternoonDosage.equals(""))
                            afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
                                    .append(" ").append(med.afternoonUnit).append("\n");
                        if (!med.eveningDosage.equals(""))
                            eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
                                    .append(" ").append(med.eveningUnit).append("\n");
                        if (!med.nightDosage.equals(""))
                            nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
                                    .append(med.nightUnit).append("\n");

                        morningMeds.setText(morningText.toString());
                        afternoonMeds.setText(afternoonText.toString());
                        eveningMeds.setText(eveningText.toString());
                        nightMeds.setText(nightText.toString());
                    }
                }
                break;
            case Calendar.TUESDAY:
                dayName = "Tuesday";
                dayHeading.setText(dayName);
                button2.setBackground(getResources().getDrawable(R.drawable.button_clicked));
                button2.setTextColor(Color.parseColor("#283593"));
                for (Medicine med : medicineArray) {
//                        tuesdayMeds(med);
                    if (med.isTuesdayOn()) {
                        if (medicineArray == null)
                            return;
                        if (!med.morningDosage.equals(""))
                            morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
                                    .append(med.morningUnit).append("\n");
                        if (!med.afternoonDosage.equals(""))
                            afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
                                    .append(" ").append(med.afternoonUnit).append("\n");
                        if (!med.eveningDosage.equals(""))
                            eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
                                    .append(" ").append(med.eveningUnit).append("\n");
                        if (!med.nightDosage.equals(""))
                            nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
                                    .append(med.nightUnit).append("\n");

                        morningMeds.setText(morningText.toString());
                        afternoonMeds.setText(afternoonText.toString());
                        eveningMeds.setText(eveningText.toString());
                        nightMeds.setText(nightText.toString());
                    }
                }
                break;
            case Calendar.WEDNESDAY:
                dayName = "Wednesday";
                dayHeading.setText(dayName);
                button3.setBackground(getResources().getDrawable(R.drawable.button_clicked));
                button3.setTextColor(Color.parseColor("#283593"));
                for (Medicine med : medicineArray) {
//                        wednesdayMeds(med);
                    if (med.isWednesdayOn()) {
                        if (medicineArray == null)
                            return;
                        if (!med.morningDosage.equals(""))
                            morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
                                    .append(med.morningUnit).append("\n");
                        if (!med.afternoonDosage.equals(""))
                            afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
                                    .append(" ").append(med.afternoonUnit).append("\n");
                        if (!med.eveningDosage.equals(""))
                            eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
                                    .append(" ").append(med.eveningUnit).append("\n");
                        if (!med.nightDosage.equals(""))
                            nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
                                    .append(med.nightUnit).append("\n");

                        morningMeds.setText(morningText.toString());
                        afternoonMeds.setText(afternoonText.toString());
                        eveningMeds.setText(eveningText.toString());
                        nightMeds.setText(nightText.toString());
                    }
                }
                break;
            case Calendar.THURSDAY:
                dayName = "Thursday";
                dayHeading.setText(dayName);
                button4.setBackground(getResources().getDrawable(R.drawable.button_clicked));
                button4.setTextColor(Color.parseColor("#283593"));
                for (Medicine med : medicineArray) {
//                        thursdayMeds(med);
                    if (med.isThursdayOn()) {
                        if (medicineArray == null)
                            return;
                        if (!med.morningDosage.equals(""))
                            morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
                                    .append(med.morningUnit).append("\n");
                        if (!med.afternoonDosage.equals(""))
                            afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
                                    .append(" ").append(med.afternoonUnit).append("\n");
                        if (!med.eveningDosage.equals(""))
                            eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
                                    .append(" ").append(med.eveningUnit).append("\n");
                        if (!med.nightDosage.equals(""))
                            nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
                                    .append(med.nightUnit).append("\n");

                        morningMeds.setText(morningText.toString());
                        afternoonMeds.setText(afternoonText.toString());
                        eveningMeds.setText(eveningText.toString());
                        nightMeds.setText(nightText.toString());
                    }
                }
                break;
            case Calendar.FRIDAY:
                dayName = "Friday";
                dayHeading.setText(dayName);
                button5.setBackground(getResources().getDrawable(R.drawable.button_clicked));
                button5.setTextColor(Color.parseColor("#283593"));
                for (Medicine med : medicineArray) {
//                    fridayMeds(med);
                    if (med.isFridayOn()) {
                        if (medicineArray == null)
                            return;
                        if (!med.morningDosage.equals(""))
                            morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
                                    .append(med.morningUnit).append("\n");
                        if (!med.afternoonDosage.equals(""))
                            afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
                                    .append(" ").append(med.afternoonUnit).append("\n");
                        if (!med.eveningDosage.equals(""))
                            eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
                                    .append(" ").append(med.eveningUnit).append("\n");
                        if (!med.nightDosage.equals(""))
                            nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
                                    .append(med.nightUnit).append("\n");

                        morningMeds.setText(morningText.toString());
                        afternoonMeds.setText(afternoonText.toString());
                        eveningMeds.setText(eveningText.toString());
                        nightMeds.setText(nightText.toString());
                    }
                }
                break;
            case Calendar.SATURDAY:
                dayName = "Saturday";
                dayHeading.setText(dayName);
                button6.setBackground(getResources().getDrawable(R.drawable.button_clicked));
                button6.setTextColor(Color.parseColor("#283593"));
                for (Medicine med : medicineArray) {
//                    saturdayMeds(med);
                    if (med.isSaturdayOn()) {
                        if (medicineArray == null)
                            return;
                        if (!med.morningDosage.equals(""))
                            morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
                                    .append(med.morningUnit).append("\n");
                        if (!med.afternoonDosage.equals(""))
                            afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
                                    .append(" ").append(med.afternoonUnit).append("\n");
                        if (!med.eveningDosage.equals(""))
                            eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
                                    .append(" ").append(med.eveningUnit).append("\n");
                        if (!med.nightDosage.equals(""))
                            nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
                                    .append(med.nightUnit).append("\n");

                        morningMeds.setText(morningText.toString());
                        afternoonMeds.setText(afternoonText.toString());
                        eveningMeds.setText(eveningText.toString());
                        nightMeds.setText(nightText.toString());
                    }
                }
                break;
            case Calendar.SUNDAY:
                dayName = "Sunday";
                dayHeading.setText(dayName);
                button7.setBackground(getResources().getDrawable(R.drawable.button_clicked));
                button7.setTextColor(Color.parseColor("#283593"));
                for (Medicine med : medicineArray) {
//                    sundayMeds(med);
                    if (med.isSundayOn()) {
                        if (medicineArray == null)
                            return;
                        if (!med.morningDosage.equals(""))
                            morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
                                    .append(med.morningUnit).append("\n");
                        if (!med.afternoonDosage.equals(""))
                            afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
                                    .append(" ").append(med.afternoonUnit).append("\n");
                        if (!med.eveningDosage.equals(""))
                            eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
                                    .append(" ").append(med.eveningUnit).append("\n");
                        if (!med.nightDosage.equals(""))
                            nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
                                    .append(med.nightUnit).append("\n");

                        morningMeds.setText(morningText.toString());
                        afternoonMeds.setText(afternoonText.toString());
                        eveningMeds.setText(eveningText.toString());
                        nightMeds.setText(nightText.toString());
                    }
                }
                break;
        }

    }

    @SuppressWarnings("deprecation")
    public void selectDay(View view) {
        button1.setBackground(getResources().getDrawable(R.drawable.button_style));
        button1.setTextColor(Color.parseColor("#FFFFFF"));

        button2.setBackground(getResources().getDrawable(R.drawable.button_style));
        button2.setTextColor(Color.parseColor("#FFFFFF"));

        button3.setBackground(getResources().getDrawable(R.drawable.button_style));
        button3.setTextColor(Color.parseColor("#FFFFFF"));

        button4.setBackground(getResources().getDrawable(R.drawable.button_style));
        button4.setTextColor(Color.parseColor("#FFFFFF"));

        button5.setBackground(getResources().getDrawable(R.drawable.button_style));
        button5.setTextColor(Color.parseColor("#FFFFFF"));

        button6.setBackground(getResources().getDrawable(R.drawable.button_style));
        button6.setTextColor(Color.parseColor("#FFFFFF"));

        button7.setBackground(getResources().getDrawable(R.drawable.button_style));
        button7.setTextColor(Color.parseColor("#FFFFFF"));

        StringBuilder morningText = new StringBuilder();
        StringBuilder afternoonText = new StringBuilder();
        StringBuilder eveningText = new StringBuilder();
        StringBuilder nightText = new StringBuilder();

        switch (view.getId()) {
            case R.id.monday_button:
                dayName = "Monday";
                dayHeading.setText(dayName);
                button1.setBackground(getResources().getDrawable(R.drawable.button_clicked));
                button1.setTextColor(Color.parseColor("#283593"));
                for (Medicine med : medicineArray) {
                    if (med.isMondayOn()) {
                        if (medicineArray == null)
                            return;
                        if (!med.morningDosage.equals(""))
                            morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
                                    .append(med.morningUnit).append("\n");
                        if (!med.afternoonDosage.equals(""))
                            afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
                                    .append(" ").append(med.afternoonUnit).append("\n");
                        if (!med.eveningDosage.equals(""))
                            eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
                                    .append(" ").append(med.eveningUnit).append("\n");
                        if (!med.nightDosage.equals(""))
                            nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
                                    .append(med.nightUnit).append("\n");

                        morningMeds.setText(morningText.toString());
                        afternoonMeds.setText(afternoonText.toString());
                        eveningMeds.setText(eveningText.toString());
                        nightMeds.setText(nightText.toString());
                    }
                }
                break;
            case R.id.tuesday_button:
                dayName = "Tuesday";
                dayHeading.setText(dayName);
                button2.setBackground(getResources().getDrawable(R.drawable.button_clicked));
                button2.setTextColor(Color.parseColor("#283593"));
                for (Medicine med : medicineArray) {
                    if (med.isTuesdayOn()) {
                        if (medicineArray == null)
                            return;
                        if (!med.morningDosage.equals(""))
                            morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
                                    .append(med.morningUnit).append("\n");
                        if (!med.afternoonDosage.equals(""))
                            afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
                                    .append(" ").append(med.afternoonUnit).append("\n");
                        if (!med.eveningDosage.equals(""))
                            eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
                                    .append(" ").append(med.eveningUnit).append("\n");
                        if (!med.nightDosage.equals(""))
                            nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
                                    .append(med.nightUnit).append("\n");

                        morningMeds.setText(morningText.toString());
                        afternoonMeds.setText(afternoonText.toString());
                        eveningMeds.setText(eveningText.toString());
                        nightMeds.setText(nightText.toString());
                    }
                }
                break;
            case R.id.wednesday_button:
                dayName = "Wednesday";
                dayHeading.setText(dayName);
                button3.setBackground(getResources().getDrawable(R.drawable.button_clicked));
                button3.setTextColor(Color.parseColor("#283593"));
                for (Medicine med : medicineArray) {
//                        wednesdayMeds(med);
                    if (med.isWednesdayOn()) {
                        if (medicineArray == null)
                            return;
                        if (!med.morningDosage.equals(""))
                            morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
                                    .append(med.morningUnit).append("\n");
                        if (!med.afternoonDosage.equals(""))
                            afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
                                    .append(" ").append(med.afternoonUnit).append("\n");
                        if (!med.eveningDosage.equals(""))
                            eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
                                    .append(" ").append(med.eveningUnit).append("\n");
                        if (!med.nightDosage.equals(""))
                            nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
                                    .append(med.nightUnit).append("\n");

                        morningMeds.setText(morningText.toString());
                        afternoonMeds.setText(afternoonText.toString());
                        eveningMeds.setText(eveningText.toString());
                        nightMeds.setText(nightText.toString());
                    }
                }
                break;
            case R.id.thursday_button:
                dayName = "Thursday";
                dayHeading.setText(dayName);
                button4.setBackground(getResources().getDrawable(R.drawable.button_clicked));
                button4.setTextColor(Color.parseColor("#283593"));
                for (Medicine med : medicineArray) {
//                        thursdayMeds(med);
                    if (med.isThursdayOn()) {
                        if (medicineArray == null)
                            return;
                        if (!med.morningDosage.equals(""))
                            morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
                                    .append(med.morningUnit).append("\n");
                        if (!med.afternoonDosage.equals(""))
                            afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
                                    .append(" ").append(med.afternoonUnit).append("\n");
                        if (!med.eveningDosage.equals(""))
                            eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
                                    .append(" ").append(med.eveningUnit).append("\n");
                        if (!med.nightDosage.equals(""))
                            nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
                                    .append(med.nightUnit).append("\n");

                        morningMeds.setText(morningText.toString());
                        afternoonMeds.setText(afternoonText.toString());
                        eveningMeds.setText(eveningText.toString());
                        nightMeds.setText(nightText.toString());
                    }
                }
                break;
            case R.id.friday_button:
                dayName = "Friday";
                dayHeading.setText(dayName);
                button5.setBackground(getResources().getDrawable(R.drawable.button_clicked));
                button5.setTextColor(Color.parseColor("#283593"));
                for (Medicine med : medicineArray) {
//                        fridayMeds(med);
                    if (med.isFridayOn()) {
                        if (medicineArray == null)
                            return;
                        if (!med.morningDosage.equals(""))
                            morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
                                    .append(med.morningUnit).append("\n");
                        if (!med.afternoonDosage.equals(""))
                            afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
                                    .append(" ").append(med.afternoonUnit).append("\n");
                        if (!med.eveningDosage.equals(""))
                            eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
                                    .append(" ").append(med.eveningUnit).append("\n");
                        if (!med.nightDosage.equals(""))
                            nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
                                    .append(med.nightUnit).append("\n");

                        morningMeds.setText(morningText.toString());
                        afternoonMeds.setText(afternoonText.toString());
                        eveningMeds.setText(eveningText.toString());
                        nightMeds.setText(nightText.toString());
                    }
                }
                break;
            case R.id.saturday_button:
                dayName = "Saturday";
                dayHeading.setText(dayName);
                button6.setBackground(getResources().getDrawable(R.drawable.button_clicked));
                button6.setTextColor(Color.parseColor("#283593"));
                for (Medicine med : medicineArray) {
//                    saturdayMeds(med);
                    if (med.isSaturdayOn()) {
                        if (medicineArray == null)
                            return;
                        if (!med.morningDosage.equals(""))
                            morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
                                    .append(med.morningUnit).append("\n");
                        if (!med.afternoonDosage.equals(""))
                            afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
                                    .append(" ").append(med.afternoonUnit).append("\n");
                        if (!med.eveningDosage.equals(""))
                            eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
                                    .append(" ").append(med.eveningUnit).append("\n");
                        if (!med.nightDosage.equals(""))
                            nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
                                    .append(med.nightUnit).append("\n");

                        morningMeds.setText(morningText.toString());
                        afternoonMeds.setText(afternoonText.toString());
                        eveningMeds.setText(eveningText.toString());
                        nightMeds.setText(nightText.toString());
                    }
                }
                break;
            case R.id.sunday_button:
                dayName = "Sunday";
                dayHeading.setText(dayName);
                button7.setBackground(getResources().getDrawable(R.drawable.button_clicked));
                button7.setTextColor(Color.parseColor("#283593"));
                for (Medicine med : medicineArray) {
//                        sundayMeds(med);
                    if (med.isSundayOn()) {
                        if (medicineArray == null)
                            return;
                        if (!med.morningDosage.equals(""))
                            morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
                                    .append(med.morningUnit).append("\n");
                        if (!med.afternoonDosage.equals(""))
                            afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
                                    .append(" ").append(med.afternoonUnit).append("\n");
                        if (!med.eveningDosage.equals(""))
                            eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
                                    .append(" ").append(med.eveningUnit).append("\n");
                        if (!med.nightDosage.equals(""))
                            nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
                                    .append(med.nightUnit).append("\n");

                        morningMeds.setText(morningText.toString());
                        afternoonMeds.setText(afternoonText.toString());
                        eveningMeds.setText(eveningText.toString());
                        nightMeds.setText(nightText.toString());
                    }
                }
                break;
        }
    }

    public void collapseExpand(View view) {

        switch (view.getId()) {
            case R.id.morning_card:
                if (morningMeds.getVisibility() == View.GONE) {
                    // it's collapsed - expand it
                    morningMeds.setVisibility(View.VISIBLE);
                    morningArrow.setImageResource(R.drawable.ic_up_arrow);
                } else {
                    // it's expanded - collapse it
                    morningMeds.setVisibility(View.GONE);
                    morningArrow.setImageResource(R.drawable.ic_down_arrow);
                }

                ObjectAnimator morningAnimation = ObjectAnimator.ofInt(morningMeds,
                        "maxLines", morningMeds.getMaxLines());
                morningAnimation.setDuration(200).start();
                break;
            case R.id.afternoon_card:
                if (afternoonMeds.getVisibility() == View.GONE) {
                    // it's collapsed - expand it
                    afternoonMeds.setVisibility(View.VISIBLE);
                    afternoonArrow.setImageResource(R.drawable.ic_up_arrow);
                } else {
                    // it's expanded - collapse it
                    afternoonMeds.setVisibility(View.GONE);
                    afternoonArrow.setImageResource(R.drawable.ic_down_arrow);
                }

                ObjectAnimator afternoonAnimation = ObjectAnimator.ofInt(afternoonMeds,
                        "maxLines", afternoonMeds.getMaxLines());
                afternoonAnimation.setDuration(200).start();
                break;
            case R.id.evening_card:
                if (eveningMeds.getVisibility() == View.GONE) {
                    // it's collapsed - expand it
                    eveningMeds.setVisibility(View.VISIBLE);
                    eveningArrow.setImageResource(R.drawable.ic_up_arrow);
                } else {
                    // it's expanded - collapse it
                    eveningMeds.setVisibility(View.GONE);
                    eveningArrow.setImageResource(R.drawable.ic_down_arrow);
                }

                ObjectAnimator eveningAnimation = ObjectAnimator.ofInt(eveningMeds,
                        "maxLines", eveningMeds.getMaxLines());
                eveningAnimation.setDuration(200).start();
                break;
            case R.id.night_card:
                if (nightMeds.getVisibility() == View.GONE) {
                    // it's collapsed - expand it
                    nightMeds.setVisibility(View.VISIBLE);
                    nightArrow.setImageResource(R.drawable.ic_up_arrow);
                } else {
                    // it's expanded - collapse it
                    nightMeds.setVisibility(View.GONE);
                    nightArrow.setImageResource(R.drawable.ic_down_arrow);
                }

                ObjectAnimator nightAnimation = ObjectAnimator.ofInt(nightMeds,
                        "maxLines", nightMeds.getMaxLines());
                nightAnimation.setDuration(200).start();
                break;
        }

    }
}

//    void displayMedsByTime(Medicine med) {
//
//        StringBuilder morningText = new StringBuilder();
//        StringBuilder afternoonText = new StringBuilder();
//        StringBuilder eveningText = new StringBuilder();
//        StringBuilder nightText = new StringBuilder();
//
//        if (medicineArray == null)
//            return;
////        for (Medicine med : medicineArray) {
//        if (!med.morningDosage.equals(""))
//            morningText.append(med.medName).append(" - ").append(med.morningDosage).append(" ")
//                    .append(med.morningUnit).append("\n");
//        if (!med.afternoonDosage.equals(""))
//            afternoonText.append(med.medName).append(" - ").append(med.afternoonDosage)
//                    .append(" ").append(med.afternoonUnit).append("\n");
//        if (!med.eveningDosage.equals(""))
//            eveningText.append(med.medName).append(" - ").append(med.eveningDosage)
//                    .append(" ").append(med.eveningUnit).append("\n");
//        if (!med.nightDosage.equals(""))
//            nightText.append(med.medName).append(" - ").append(med.nightDosage).append(" ")
//                    .append(med.nightUnit).append("\n");
////        }
//        morningMeds.setText(morningText.toString());
//        afternoonMeds.setText(afternoonText.toString());
//        eveningMeds.setText(eveningText.toString());
//        nightMeds.setText(nightText.toString());
//    }


//        bottomNav.setOnNavigationItemSelectedListener(navListener);
//        Toolbar toolbar = findViewById(R.id.topAppBar);
//        setSupportActionBar(toolbar);
//
//        recyclerView = findViewById(R.id.meds_list);
//        adapter = new MedicineAdapter(this, (ArrayList<Medicine>) medicineArray);
//        recyclerView.setAdapter(adapter);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        enableItemRemoval();
//
//        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);
//
//        medicineViewModel.getAllMeds().observe(this, new Observer<List<Medicine>>() {
//            @Override
//            public void onChanged(@Nullable final List<Medicine> medicines) {
//                adapter.setMedicineArray(medicines);
//            }
//        });


//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == TEXT_REQUEST && resultCode == RESULT_OK) {
//
//            Medicine newMed = (Medicine) data.getSerializableExtra("key");
//
//            medicineArray.add(newMed);
//            medicineViewModel.insert(newMed);
//
//            adapter = new MedicineAdapter(this, (ArrayList<Medicine>) medicineArray);
//            recyclerView.setAdapter(adapter);
//
//            medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);
//
//            medicineViewModel.getAllMeds().observe(this, new Observer<List<Medicine>>() {
//                @Override
//                public void onChanged(@Nullable final List<Medicine> medicines) {
//                    adapter.setMedicineArray(medicines);
//                }
//            });
//        }
//    }
//
//    public void addNewMedicine(View view) {
//        Intent intent = new Intent(this, NewMedicine.class);
//        startActivityForResult(intent, TEXT_REQUEST);
//    }
//
//    public void enableItemRemoval() {
//        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
//                .SimpleCallback(0,
//                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView,
//                                  @NonNull RecyclerView.ViewHolder viewHolder,
//                                  @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder,
//            int direction) {
//                int position = viewHolder.getAdapterPosition();
//                final Medicine medicine = adapter.getMedAtPosition(position);
//
//                AlertDialog.Builder deleteMedDialogBuilder =
//                        new AlertDialog.Builder(MainActivity.this);
//                deleteMedDialogBuilder.setCancelable(true)
//                             .setMessage("Are you sure you want to delete this medicine?")
//                deleteMedDialogBuilder.setPositiveButton("OK",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(MainActivity.this, "Deleting " +
//                                        medicine.getName(), Toast.LENGTH_LONG).show();
//
//                                // Delete the word
//                                medicineViewModel.deleteWord(medicine);
//                                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
//                            }
//                        });
//                deleteMedDialogBuilder.setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        });
//                deleteMedDialogBuilder.create().show();
////                medicineArray.remove(viewHolder.getAdapterPosition());
////                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
//            }
//        });
//        helper.attachToRecyclerView(recyclerView);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.top_app_bar, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.clear_data) {
//            // Add a toast just for confirmation
//            Toast.makeText(this, "Clearing the data...",
//                    Toast.LENGTH_SHORT).show();
//
//            // Delete the existing data
//            medicineViewModel.deleteAll();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

