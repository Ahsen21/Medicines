package com.example.android.medicines;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SetAlarms extends AppCompatActivity {

//    int currentHour, currentMinute;
//    EditText morningTime, afternoonTime, eveningTime, nightTime;
//    String morningMed, afternoonMed, eveningMed, nightMed;
//    Button morningAlarm, afternoonAlarm, eveningAlarm, nightAlarm;
    List<Alarm> alarmList;
    AlarmViewModel alarmViewModel;
    RecyclerView recyclerView;
    AlarmAdapter alarmAdapter;
    BottomNavigationView bottomNav;
    public static final int TEXT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);

        bottomNav = findViewById(R.id.bottomNavBar);
        bottomNav.setSelectedItemId(R.id.alarm_page);
        bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home_page:
                                startActivity(new Intent(getApplicationContext(),
                                        MainActivity.class));
                                overridePendingTransition(0, 0);
                                return true;
                            case R.id.medicine_page:
                                startActivity(new Intent(getApplicationContext(),
                                        MedicineList.class));
                                overridePendingTransition(0, 0);
                                return true;
                            case R.id.alarm_page:
                                return true;
                        }
                        return false;
                    }
                });

        alarmList = new ArrayList<>();
        recyclerView = findViewById(R.id.alarm_list);
        alarmAdapter = new AlarmAdapter(this, (ArrayList<Alarm>) alarmList);
        recyclerView.setAdapter(alarmAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel.class);

        alarmViewModel.getAllAlarms().observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(@Nullable final List<Alarm> alarms) {
                alarmAdapter.setAlarmArray(alarms);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST && resultCode == RESULT_OK) {

            Alarm newAlarm = (Alarm) data.getSerializableExtra("newAlarm");

            alarmList.add(newAlarm);
            alarmViewModel.insert(newAlarm);

            alarmAdapter = new AlarmAdapter(this, (ArrayList<Alarm>) alarmList);
            recyclerView.setAdapter(alarmAdapter);

            alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel.class);

            alarmViewModel.getAllAlarms().observe(this, new Observer<List<Alarm>>() {
                @Override
                public void onChanged(@Nullable final List<Alarm> alarms) {
                    alarmAdapter.setAlarmArray(alarms);
                }
            });
        }
    }

//    public void displayAlarms() {
//        if (alarmList == null)
//            return;
//        for (Alarm alarm : alarmList) {
//            if (alarm.alarmName.equals("Morning"))
//                morningTime.setText(alarm.alarmTime);
//            if (alarm.alarmName.equals("Afternoon"))
//                afternoonTime.setText(alarm.alarmTime);
//            if (alarm.alarmName.equals("Evening"))
//                eveningTime.setText(alarm.alarmTime);
//            if (alarm.alarmName.equals("Night"))
//                nightTime.setText(alarm.alarmTime);
//        }
//    }

    public void addNewAlarm(View view) {
        Intent intent = new Intent(this, AddAlarm.class);
        startActivityForResult(intent, TEXT_REQUEST);
    }

}


//        TimePickerDialog alarmDialog = new TimePickerDialog(this,
//                new TimePickerDialog.OnTimeSetListener() {
//
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//                        switch (view.getId()) {
//                            case R.id.morning_button:
//                                morningTime.setText(hourOfDay + ":" + minute);
//                                break;
//                            case R.id.afternoon_button:
//                                afternoonTime.setText(hourOfDay + ":" + minute);
//                                break;
//                            case R.id.evening_button:
//                                eveningTime.setText(hourOfDay + ":" + minute);
//                                break;
//                            case R.id.night_button:
//                                nightTime.setText(hourOfDay + ":" + minute);
//                                break;
//                        }
//                    }
//                }, hour, minute, false);
//        alarmDialog.show();