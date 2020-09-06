package com.example.android.medicines;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MedicineList extends AppCompatActivity {

    List<Medicine> medicineArray;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    MedicineAdapter adapter;
    MedicineViewModel medicineViewModel;
    BottomNavigationView bottomNav;
    int editMedPosition;
    public static final int TEXT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        fab = findViewById(R.id.fab);
        medicineArray = new ArrayList<>();

        bottomNav = findViewById(R.id.bottomNavBar);

        bottomNav.setSelectedItemId(R.id.medicine_page);

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
                                return true;
                            case R.id.alarm_page:
                                startActivity(new Intent(getApplicationContext(), SetAlarms.class));
                                overridePendingTransition(0, 0);
                                return true;
                        }
                        return false;
                    }
                });

        recyclerView = findViewById(R.id.meds_list);
        adapter = new MedicineAdapter(this, medicineArray);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        enableItemRemoval();

        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);

        medicineViewModel.getAllMeds().observe(this, new Observer<List<Medicine>>() {
            @Override
            public void onChanged(@Nullable final List<Medicine> medicines) {
                adapter.setMedicineArray(medicines);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST && resultCode == RESULT_OK) {

            Medicine newMed = (Medicine) data.getSerializableExtra("key");

            Medicine medicine = adapter.getMedAtPosition(editMedPosition);
            medicineViewModel.deleteWord(medicine);
            adapter.notifyItemRemoved(editMedPosition);

            medicineArray.add(newMed);
            medicineViewModel.insert(newMed);

            adapter = new MedicineAdapter(this, medicineArray);
            recyclerView.setAdapter(adapter);

            medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);

            medicineViewModel.getAllMeds().observe(this, new Observer<List<Medicine>>() {
                @Override
                public void onChanged(@Nullable final List<Medicine> medicines) {
                    adapter.setMedicineArray(medicines);
                }
            });
        } else if (requestCode == TEXT_REQUEST && resultCode == RESULT_CANCELED) {
            adapter.notifyItemChanged(editMedPosition);
        }
    }

    public void addNewMedicine(View view) {
        Intent intent = new Intent(this, NewMedicine.class);
        startActivityForResult(intent, TEXT_REQUEST);
    }

//    public void editMedicine(View view) {
//        int position
//        Medicine medToEdit = adapter.getMedAtPosition(position);
//        Intent editIntent = new Intent(this, EditMedicine.class);
//        editIntent.putExtra("medToEdit", medToEdit);
//        setResult(RESULT_OK, editIntent);
//        finish();
//    }

    public void enableItemRemoval() {
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
                final Medicine medicine = adapter.getMedAtPosition(position);

                if (direction == ItemTouchHelper.RIGHT) {
                    adapter.notifyItemChanged(editMedPosition);
                    AlertDialog.Builder deleteMedDialogBuilder =
                            new AlertDialog.Builder(MedicineList.this);
                    deleteMedDialogBuilder.setCancelable(true)
                            .setMessage("Are you sure you want to delete this medicine?")
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(MedicineList.this, "Deleting " +
                                                    medicine.getMedName(), Toast.LENGTH_LONG).show();

                                            // Delete the word
                                            medicineViewModel.deleteWord(medicine);
                                            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
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
                    adapter.notifyItemChanged(editMedPosition);
                    editMedPosition = viewHolder.getAdapterPosition();
                    AlertDialog.Builder editMedDialogBuilder =
                            new AlertDialog.Builder(MedicineList.this);
                    editMedDialogBuilder.setCancelable(true)
                            .setMessage("Edit medicine?")
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent editIntent = new Intent(MedicineList.this, EditMedicine.class);
                                            editIntent.putExtra("medToEdit", medicine);
                                            startActivityForResult(editIntent, TEXT_REQUEST);
                                        }
                                    });
                    editMedDialogBuilder.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    editMedDialogBuilder.create().show();
//                    Intent editIntent = new Intent(MedicineList.this, EditMedicine.class);
//                    editIntent.putExtra("medToEdit", medicine);
//                    startActivity(editIntent);
                }
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.top_app_bar, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.clear_data) {
//            // Add a toast just for confirmation
//            Toast.makeText(this, "Clearing the data...",
//                    Toast.LENGTH_SHORT).show();
//
//            // Delete the existing data
//            medicineViewModel.deleteAll();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    Fragment selectedFragment = null;
//                    int id = item.getItemId();
//
//                    if (id == R.id.home_page) {
//                        selectedFragment = new MedicineListFragment();
//                    }
//                    getSupportFragmentManager().beginTransaction().replace(R.id.)
//                }
//            };

}