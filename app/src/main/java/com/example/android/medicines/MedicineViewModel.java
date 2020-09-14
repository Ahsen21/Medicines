package com.example.android.medicines;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MedicineViewModel extends AndroidViewModel {

    private MedicineRepository medRepository;
    private LiveData<List<Medicine>> allMeds;

    public MedicineViewModel(Application application) {
        super(application);
        medRepository = new MedicineRepository(application);
        allMeds = medRepository.getAllMeds();
    }

    LiveData<List<Medicine>> getAllMeds() {
        return allMeds;
    }

    public void insert(Medicine medicine) {
        medRepository.insert(medicine);
    }

    public void deleteAll() {
        medRepository.deleteAll();
    }

    public void deleteWord(Medicine medicine) {
        medRepository.deleteMed(medicine);
    }
}
