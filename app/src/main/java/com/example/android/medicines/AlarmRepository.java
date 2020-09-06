package com.example.android.medicines;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AlarmRepository {
    private AlarmDao alarmDao;
    private LiveData<List<Alarm>> allAlarms;

    AlarmRepository(Application application) {
        MedRoomDatabase db = MedRoomDatabase.getDatabase(application);
        alarmDao = db.alarmDao();
        allAlarms = alarmDao.getAllAlarms();
    }

    // Gets all Alarms
    LiveData<List<Alarm>> getAllAlarms() {
        return allAlarms;
    }

    // Method to insert an Alarm
    private static class insertAsyncTask extends AsyncTask<Alarm, Void, Void> {
        private AlarmDao asyncTaskAlarmDao;

        insertAsyncTask(AlarmDao dao) {
            asyncTaskAlarmDao = dao;
        }

        @Override
        protected Void doInBackground(final Alarm... params) {
            asyncTaskAlarmDao.insert(params[0]);
            return null;
        }
    }
    // Calls the insert method
    public void insert (Alarm alarm) {
        new insertAsyncTask(alarmDao).execute(alarm);
    }

    // Method to delete all Alarms
    private static class deleteAllAlarmsAsyncTask extends AsyncTask<Void, Void, Void> {
        private AlarmDao asyncTaskAlarmDao;

        deleteAllAlarmsAsyncTask(AlarmDao dao) {
            asyncTaskAlarmDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskAlarmDao.deleteAll();
            return null;
        }
    }
    // Calls the deleteAll method
    public void deleteAll() {
        new AlarmRepository.deleteAllAlarmsAsyncTask(alarmDao).execute();
    }

    // Method to delete a single Alarm
    private static class deleteAlarmAsyncTask extends AsyncTask<Alarm, Void, Void> {
        private AlarmDao asyncTaskAlarmDao;

        deleteAlarmAsyncTask(AlarmDao dao) {
            asyncTaskAlarmDao = dao;
        }

        @Override
        protected Void doInBackground(final Alarm... params) {
            asyncTaskAlarmDao.deleteAlarm(params[0]);
            return null;
        }
    }
    // Calls the deleteAlarm method
    public void deleteMed(Alarm alarm) {
        new AlarmRepository.deleteAlarmAsyncTask(alarmDao).execute(alarm);
    }
}
