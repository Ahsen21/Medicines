package com.example.android.medicines;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    int editAlarmPosition;
    public static final int NEW_ALARM_REQUEST = 1;
    public static final int EDIT_ALARM_REQUEST = 2;

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

        deleteOnSwipe();

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

        switch (requestCode) {
            case NEW_ALARM_REQUEST:
                if (resultCode == RESULT_OK) {
                    Alarm newAlarm = (Alarm) data.getSerializableExtra("newAlarm");

                    alarmList.add(newAlarm);
                    alarmViewModel.insert(newAlarm);

                } else if (resultCode == RESULT_CANCELED) {
                    alarmAdapter.notifyItemChanged(editAlarmPosition);
                }

                break;
            case EDIT_ALARM_REQUEST:
                if (resultCode == RESULT_OK) {
                    Alarm editedAlarm = (Alarm) data.getSerializableExtra("editIntent");

                    Alarm alarm = alarmAdapter.getAlarmAtPosition(editAlarmPosition);
                    alarmViewModel.deleteAlarm(alarm);
                    alarmAdapter.notifyItemRemoved(editAlarmPosition);

                    alarmList.add(editedAlarm);
                    alarmViewModel.insert(editedAlarm);

                } else if (resultCode == RESULT_CANCELED) {
                    alarmAdapter.notifyItemChanged(editAlarmPosition);
                }
                break;
        }

//        if (requestCode == TEXT_REQUEST && resultCode == RESULT_OK) {
//
//            Alarm newAlarm = (Alarm) data.getSerializableExtra("newAlarm");
//
//            alarmList.add(newAlarm);
//            alarmViewModel.insert(newAlarm);
//
//            alarmAdapter = new AlarmAdapter(this, (ArrayList<Alarm>) alarmList);
//            recyclerView.setAdapter(alarmAdapter);
//
//            alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel.class);
//
//            alarmViewModel.getAllAlarms().observe(this, new Observer<List<Alarm>>() {
//                @Override
//                public void onChanged(@Nullable final List<Alarm> alarms) {
//                    alarmAdapter.setAlarmArray(alarms);
//                }
//            });
//        }
        }

    public void addNewAlarm (View view){
        Intent intent = new Intent(this, AddAlarm.class);
        startActivityForResult(intent, NEW_ALARM_REQUEST);
    }

        public void deleteOnSwipe () {
            ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                    .SimpleCallback(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView,
                                      @NonNull RecyclerView.ViewHolder viewHolder,
                                      @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    final Alarm alarm = alarmAdapter.getAlarmAtPosition(position);

                    if (direction == ItemTouchHelper.RIGHT) {
                        alarmAdapter.notifyItemChanged(position);
                        AlertDialog.Builder deleteMedDialogBuilder =
                                new AlertDialog.Builder(SetAlarms.this);
                        deleteMedDialogBuilder.setCancelable(true)
                                .setMessage("Are you sure you want to delete this medicine?")
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(SetAlarms.this, "Deleting " +
                                                        alarm.getAlarmName(), Toast.LENGTH_LONG).show();

                                                alarmViewModel.deleteAlarm(alarm);
                                                alarmAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                                                Intent i = new Intent(AlarmClock.ACTION_DISMISS_ALARM);
                                                i.putExtra(AlarmClock.ALARM_SEARCH_MODE_LABEL, alarm.alarmName);
                                                startActivity(i);
                                            }
                                        });
                        deleteMedDialogBuilder.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        deleteMedDialogBuilder.create().show();
                    } else if (direction == ItemTouchHelper.LEFT) {
                        alarmAdapter.notifyItemChanged(editAlarmPosition);
                        editAlarmPosition = viewHolder.getAdapterPosition();
                        AlertDialog.Builder editMedDialogBuilder =
                                new AlertDialog.Builder(SetAlarms.this);
                        editMedDialogBuilder.setCancelable(true)
                                .setMessage("Edit medicine?")
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent editIntent = new Intent(SetAlarms.this, EditMedicine.class);
                                                editIntent.putExtra("alarmToEdit", alarm);
                                                startActivityForResult(editIntent, EDIT_ALARM_REQUEST);
                                            }
                                        });
                        editMedDialogBuilder.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        editMedDialogBuilder.create().show();
                    }
                }
            });
            helper.attachToRecyclerView(recyclerView);
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