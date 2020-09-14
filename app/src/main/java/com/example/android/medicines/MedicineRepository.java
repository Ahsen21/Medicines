package com.example.android.medicines;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MedicineRepository {
    private MedicineDao medicineDao;
    private LiveData<List<Medicine>> allMeds;

    MedicineRepository(Application application) {
        MedRoomDatabase db = MedRoomDatabase.getDatabase(application);
        medicineDao = db.medicineDao();
        allMeds = medicineDao.getAllMedicines();
    }

    // Gets all Medicines
    LiveData<List<Medicine>> getAllMeds() {
        return allMeds;
    }

    // Method to insert a new Medicine
    private static class insertAsyncTask extends AsyncTask<Medicine, Void, Void> {
        private MedicineDao asyncTaskMedDao;

        insertAsyncTask(MedicineDao dao) {
            asyncTaskMedDao = dao;
        }

        @Override
        protected Void doInBackground(final Medicine... params) {
            asyncTaskMedDao.insert(params[0]);
            return null;
        }
    }
    // Calls the insert method
    public void insert(Medicine medicine) {
        new insertAsyncTask(medicineDao).execute(medicine);
    }

    // Method to delete all Medicines
    private static class deleteAllMedsAsyncTask extends AsyncTask<Void, Void, Void> {
        private MedicineDao asyncTaskMedDao;

        deleteAllMedsAsyncTask(MedicineDao dao) {
            asyncTaskMedDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskMedDao.deleteAll();
            return null;
        }
    }
    // Calls the deleteAll method
    public void deleteAll() {
        new deleteAllMedsAsyncTask(medicineDao).execute();
    }

    // Method to delete a single Medicine
    private static class deleteMedAsyncTask extends AsyncTask<Medicine, Void, Void> {
        private MedicineDao asyncTaskMedDao;

        deleteMedAsyncTask(MedicineDao dao) {
            asyncTaskMedDao = dao;
        }

        @Override
        protected Void doInBackground(final Medicine... params) {
            asyncTaskMedDao.deleteMed(params[0]);
            return null;
        }
    }
    // Calls the deleteMed method
    public void deleteMed(Medicine medicine) {
        new deleteMedAsyncTask(medicineDao).execute(medicine);
    }
}
