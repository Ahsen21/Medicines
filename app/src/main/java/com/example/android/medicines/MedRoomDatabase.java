package com.example.android.medicines;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Medicine.class, Alarm.class}, version = 4, exportSchema = false)
public abstract class MedRoomDatabase extends RoomDatabase {

    public abstract MedicineDao medicineDao();
    public abstract AlarmDao alarmDao();

    private static MedRoomDatabase INSTANCE;

    public static MedRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MedRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MedRoomDatabase.class, "medicine_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback(){
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final MedicineDao medDao;
        private final AlarmDao alarmDao;
        List<Medicine> medicines = new ArrayList<>();
        List<Alarm> alarms = new ArrayList<>();
        

        PopulateDbAsync(MedRoomDatabase db) {
//            Medicine med1 = new Medicine("med 1", "1", "2",
//                    "3", "4");
//            Medicine med2 = new Medicine("med 2", "5", "6",
//                    "7", "8");
//
//            medicines.add(med1);
//            medicines.add(med2);
//
//            Alarm alarm1 = new Alarm("Morning", "8:00 AM");
//            Alarm alarm2 = new Alarm("Afternoon", "12:00 PM");
//
//            alarms.add(alarm1);
//            alarms.add(alarm2);

            medDao = db.medicineDao();
            alarmDao = db.alarmDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
//            medDao.deleteAll();
//            alarmDao.deleteAll();

            if (medDao.getAnyMedicine().length < 1) {
                for (int i = 0; i < medicines.size(); i++) {
                    Medicine medicine = medicines.get(i);
                    medDao.insert(medicine);
                }
            }
            if (alarmDao.getAnyAlarm().length < 1) {
                for (int j = 0; j < alarms.size(); j++) {
                    Alarm alarm = alarms.get(j);
                    alarmDao.insert(alarm);
                }
            }
            return null;

        }
    }
}
