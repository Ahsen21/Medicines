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
    public static final int NEW_MED_REQUEST = 1;
    public static final int EDIT_MED_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        fab = findViewById(R.id.fab);
        medicineArray = new ArrayList<>();

        bottomNav = findViewById(R.id.bottomNavBar);

        bottomNav.setSelectedItemId(R.id.medicine_page);

        // Bottom nav functionality
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

        //Setting the adapter for the RecyclerView
        recyclerView = findViewById(R.id.meds_list);
        adapter = new MedicineAdapter(this, medicineArray);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Calls swipe/edit function
        enableItemRemoval();

        // Get and display Medicine List from the ViewModel
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

        switch (requestCode) {
            // Checks if the intent was from the NewMed activity
            case NEW_MED_REQUEST:
                if (resultCode == RESULT_OK) {
                    // Gets the new med from the intent and adds the new object  list
                    Medicine newMed = (Medicine) data.getSerializableExtra("addIntent");

                    medicineArray.add(newMed);
                    medicineViewModel.insert(newMed);

                } else if (resultCode == RESULT_CANCELED) {
                    adapter.notifyItemChanged(editMedPosition);
                }

                break;
            // Checks if the intent was from the EditMed activity
            case EDIT_MED_REQUEST:
                if (resultCode == RESULT_OK) {
                    // Deletes the Med at the position that was swiped
                    Medicine medicine = adapter.getMedAtPosition(editMedPosition);
                    medicineViewModel.deleteWord(medicine);
                    adapter.notifyItemRemoved(editMedPosition);

                    // Gets the edited med from the intent and adds it to the list
                    Medicine editedMed = (Medicine) data.getSerializableExtra("editIntent");

                    medicineArray.add(editedMed);
                    medicineViewModel.insert(editedMed);

                } else if (resultCode == RESULT_CANCELED) {
                    adapter.notifyItemChanged(editMedPosition);
                }
                break;
        }
//        if (requestCode == NEW_MED_REQUEST && resultCode == RESULT_OK) {
//
//            Medicine newMed = (Medicine) data.getSerializableExtra("addIntent");
//
//            medicineArray.add(newMed);
//            medicineViewModel.insert(newMed);
//
//            Medicine editedMed = (Medicine) data.getSerializableExtra("editIntent");
//
//            Medicine medicine = adapter.getMedAtPosition(editMedPosition);
//            medicineViewModel.deleteWord(medicine);
//            adapter.notifyItemRemoved(editMedPosition);
//
//
//
//            adapter = new MedicineAdapter(this, medicineArray);
//            recyclerView.setAdapter(adapter);
//
//            medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);
//
//            medicineViewModel.getAllMeds().observe(this, new Observer<List<Medicine>>() {
//                @Override
//                public void onChanged(@Nullable final List<Medicine> medicines) {
//                    adapter.setMedicineArray(medicines);
//                }
//            });
//        } else if (requestCode == NEW_MED_REQUEST && resultCode == RESULT_CANCELED) {
//            adapter.notifyItemChanged(editMedPosition);
//        }
    }

    // Sends an intent to go to the NewMed activity
    public void addNewMedicine(View view) {
        Intent intent = new Intent(this, NewMedicine.class);
        startActivityForResult(intent, NEW_MED_REQUEST);
    }

    // Enables item swipe/move
    public void enableItemRemoval() {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            // Does something when list items are moved (drag and drop)
            // Currently does nothing
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            // Does something when list items are swiped
            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                final Medicine medicine = adapter.getMedAtPosition(position);

                if (direction == ItemTouchHelper.RIGHT) {
                    adapter.notifyItemChanged(editMedPosition);

                    // Creates a dialog box asking for confirmation to delete
                    AlertDialog.Builder deleteMedDialogBuilder =
                            new AlertDialog.Builder(MedicineList.this);
                    deleteMedDialogBuilder.setCancelable(true)
                            .setMessage("Are you sure you want to delete this medicine?")
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            // If "OK" is pressed, it deletes the item
                                            Toast.makeText(MedicineList.this, "Deleting " +
                                                    medicine.getMedName(), Toast.LENGTH_LONG).show();

                                            medicineViewModel.deleteWord(medicine);
                                            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                                        }
                                    });
                    deleteMedDialogBuilder.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Does nothing
                                }
                            });
                    deleteMedDialogBuilder.create().show();

                } else if (direction == ItemTouchHelper.LEFT) {

                    // Gets the position of the swiped item
                    adapter.notifyItemChanged(editMedPosition);
                    editMedPosition = viewHolder.getAdapterPosition();

                    // Creates a dialog box asking for confirmation to edit
                    AlertDialog.Builder editMedDialogBuilder =
                            new AlertDialog.Builder(MedicineList.this);
                    editMedDialogBuilder.setCancelable(true)
                            .setMessage("Edit medicine?")
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Sends an intent to the EditMed activity, with the current Med as an extra
                                            Intent editIntent = new Intent(MedicineList.this, EditMedicine.class);
                                            editIntent.putExtra("medToEdit", medicine);
                                            startActivityForResult(editIntent, EDIT_MED_REQUEST);
                                        }
                                    });
                    editMedDialogBuilder.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Does nothing
                                }
                            });
                    editMedDialogBuilder.create().show();

                }
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }

}