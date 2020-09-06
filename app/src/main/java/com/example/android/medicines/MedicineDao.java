package com.example.android.medicines;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MedicineDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Medicine medicine);

    @Query("DELETE FROM medicine_table")
    void deleteAll();

    @Query("SELECT * from medicine_table ORDER BY medName ASC")
    LiveData<List<Medicine>> getAllMedicines();

    @Query("SELECT * from medicine_table LIMIT 1")
    Medicine[] getAnyMedicine();

    @Delete
    void deleteMed(Medicine medicine);
}
