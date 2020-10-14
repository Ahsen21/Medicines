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

                    assert newAlarm != null;
                    String alarmLabel = newAlarm.getAlarmName() + " Medicines";

                    Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
                    alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, newAlarm.getAlarmHour());
                    alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, newAlarm.getAlarmMinute());
                    alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, alarmLabel);
                    startActivity(alarmIntent);

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

                } else if (resultCode == RESULT_CANCELED) {
                    alarmAdapter.notifyItemChanged(editAlarmPosition);
                }

                break;
            case EDIT_ALARM_REQUEST:
                if (resultCode == RESULT_OK) {
                    // Gets alarm at swiped position
                    Alarm alarm = alarmAdapter.getAlarmAtPosition(editAlarmPosition);

                    // Deletes that alarm from the system clock and ViewModel
                    String alarmName = alarm.getAlarmName() + " Medicines";

                    Intent i = new Intent(AlarmClock.ACTION_DISMISS_ALARM);
                    i.putExtra(AlarmClock.EXTRA_ALARM_SEARCH_MODE, AlarmClock.ALARM_SEARCH_MODE_LABEL);
                    i.putExtra(AlarmClock.EXTRA_MESSAGE, alarmName);
                    startActivity(i);
                    alarmViewModel.deleteAlarm(alarm);
                    alarmAdapter.notifyItemRemoved(editAlarmPosition);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                    // Gets alarm from EditAlarm activity
                    Alarm editedAlarm = (Alarm) data.getSerializableExtra("editAlarm");

                    // Adds alarm to system clock and ViewModel
                    assert editedAlarm != null;
                    String alarmLabel = editedAlarm.getAlarmName() + " Medicines";

                    Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
                    alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, editedAlarm.getAlarmHour());
                    alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, editedAlarm.getAlarmMinute());
                    alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                    alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, alarmLabel);
                    startActivity(alarmIntent);

                    alarmList.add(editedAlarm);
                    alarmViewModel.insert(editedAlarm);

                    alarmAdapter = new AlarmAdapter(this, (ArrayList<Alarm>) alarmList);
                    recyclerView.setAdapter(alarmAdapter);
                    alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel.class);
                    alarmViewModel.getAllAlarms().observe(this, new Observer<List<Alarm>>() {
                        @Override
                        public void onChanged(@Nullable final List<Alarm> alarms) {
                            alarmAdapter.setAlarmArray(alarms);
                        }
                    });

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

    public void addNewAlarm(View view) {
        Intent intent = new Intent(this, AddAlarm.class);
        startActivityForResult(intent, NEW_ALARM_REQUEST);
    }

    public void deleteOnSwipe() {
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
                    AlertDialog.Builder deleteMedDialogBuilder = new AlertDialog.Builder(SetAlarms.this);
                    deleteMedDialogBuilder.setCancelable(true)
                            .setMessage("Are you sure you want to delete this alarm?")
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(SetAlarms.this, "Deleting " +
                                                    alarm.getAlarmName() + " Alarm", Toast.LENGTH_LONG).show();

                                            alarmViewModel.deleteAlarm(alarm);
                                            alarmAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                                            String alarmName = alarm.getAlarmName() + " Medicines";

                                            Intent i = new Intent(AlarmClock.ACTION_DISMISS_ALARM);
                                            i.putExtra(AlarmClock.EXTRA_ALARM_SEARCH_MODE, AlarmClock.ALARM_SEARCH_MODE_LABEL);
                                            i.putExtra(AlarmClock.EXTRA_MESSAGE, alarmName);
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
                    AlertDialog.Builder editMedDialogBuilder = new AlertDialog.Builder(SetAlarms.this);
                    editMedDialogBuilder.setCancelable(true)
                            .setMessage("Edit alarm?")
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent editIntent = new Intent(SetAlarms.this, EditAlarm.class);
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
