package com.example.android.medicines;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class EditAlarm extends AppCompatActivity {

    EditText alarmTimeText;
    String timeValue, spinnerLabel;
    Spinner spinner;
    int currentHour, currentMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        alarmTimeText = findViewById(R.id.alarm_time);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(listener);
        spinner.setAdapter(adapter);

        Intent i = getIntent();
        Alarm alarmToEdit = (Alarm) i.getSerializableExtra("alarmToEdit");
        if (alarmToEdit != null) setDefaultText(alarmToEdit);

        assert alarmToEdit != null;
        String alarmName = alarmToEdit.getAlarmName();
        int alarmPosition = adapter.getPosition(alarmName);
        spinner.setSelection(alarmPosition);

    }

    public void setDefaultText(Alarm alarmToEdit) {
        alarmTimeText.setText(alarmToEdit.getAlarmTime());
    }

    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            spinnerLabel = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void setTime(View view) {
        final Calendar calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog alarmDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String am_pm = "";
                        String minuteOfDay;
                        String alarmLabel = "";

                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);

                        if (c.get(Calendar.AM_PM) == Calendar.AM)
                            am_pm = "AM";
                        else if (c.get(Calendar.AM_PM) == Calendar.PM)
                            am_pm = "PM";

                        String strHrsToShow = (c.get(Calendar.HOUR) == 0)
                                ? "12" : c.get(Calendar.HOUR) + "";

                        if (c.get(Calendar.MINUTE) == 0)
                            minuteOfDay = "00";
                        else minuteOfDay = String.valueOf(c.get(Calendar.MINUTE));

                        switch (spinnerLabel) {
                            case "Morning":
                                alarmLabel = "Morning medicine";
                                break;
                            case "Afternoon":
                                alarmLabel = "Afternoon medicine";
                                break;
                            case "Evening":
                                alarmLabel = "Evening medicine";
                                break;
                            case "Night":
                                alarmLabel = "Night medicine";
                                break;
                        }

                        String timeText = strHrsToShow + ":" + minuteOfDay + " " + am_pm;
                        alarmTimeText.setText(timeText);

                        Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
                        alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, hourOfDay);
                        alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, minute);
                        alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                        alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, alarmLabel);
                        startActivity(alarmIntent);
                    }
                }, currentHour, currentMinute, false);
        alarmDialog.show();
    }

    public void saveAlarm(View view) {
        timeValue = alarmTimeText.getText().toString();

        Alarm newAlarm = new Alarm(spinnerLabel, timeValue);

        Intent i = new Intent(EditAlarm.this, SetAlarms.class);
        i.putExtra("editAlarm", newAlarm);
        setResult(RESULT_OK, i);
        finish();
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, SetAlarms.class);
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}