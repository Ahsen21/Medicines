package com.example.android.medicines;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AlarmViewModel extends AndroidViewModel {

    private AlarmRepository alarmRepository;
    private LiveData<List<Alarm>> allAlarms;

    public AlarmViewModel(Application application) {
        super(application);
        alarmRepository = new AlarmRepository(application);
        allAlarms = alarmRepository.getAllAlarms();
    }

    LiveData<List<Alarm>> getAllAlarms() {
        return allAlarms;
    }

    public void insert(Alarm alarm) {
        alarmRepository.insert(alarm);
    }

    public void deleteAll() {
        alarmRepository.deleteAll();
    }

    public void deleteAlarm(Alarm alarm) {
        alarmRepository.deleteAlarm(alarm);
    }
}
