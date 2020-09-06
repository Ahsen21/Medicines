package com.example.android.medicines;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MedicineViewModel extends AndroidViewModel {
    private MedicineRepository repository;
    private LiveData<List<Medicine>> allMeds;

    public MedicineViewModel(Application application) {
        super(application);
        repository = new MedicineRepository(application);
        allMeds = repository.getAllMeds();
    }

    LiveData<List<Medicine>> getAllMeds() {
        return allMeds;
    }

    public void insert(Medicine medicine) {
        repository.insert(medicine);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void deleteWord(Medicine medicine) {
        repository.deleteMed(medicine);
    }
}
