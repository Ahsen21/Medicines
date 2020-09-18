package com.example.android.medicines;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class NewMedicine extends AppCompatActivity {

    EditText nameText, morningText, afternoonText, eveningText, nightText;
    String nameValue, morningValue, afternoonValue, eveningValue, nightValue;
    Spinner morningSpinner, afternoonSpinner, eveningSpinner, nightSpinner;
    String morningUnit, afternoonUnit, eveningUnit, nightUnit;
    CheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    boolean mondayOn, tuesdayOn, wednesdayOn, thursdayOn, fridayOn, saturdayOn, sundayOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medicine);

        nameText = findViewById(R.id.name_value);
        morningText = findViewById(R.id.morning_value);
        afternoonText = findViewById(R.id.afternoon_value);
        eveningText = findViewById(R.id.evening_value);
        nightText = findViewById(R.id.night_value);

        monday = findViewById(R.id.checkBox1);
        tuesday = findViewById(R.id.checkBox2);
        wednesday = findViewById(R.id.checkBox3);
        thursday = findViewById(R.id.checkBox4);
        friday = findViewById(R.id.checkBox5);
        saturday = findViewById(R.id.checkBox6);
        sunday = findViewById(R.id.checkBox7);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.medicine_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        morningSpinner = findViewById(R.id.morning_spinner);
        afternoonSpinner = findViewById(R.id.afternoon_spinner);
        eveningSpinner = findViewById(R.id.evening_spinner);
        nightSpinner = findViewById(R.id.night_spinner);

        morningSpinner.setOnItemSelectedListener(listener);
        afternoonSpinner.setOnItemSelectedListener(listener);
        eveningSpinner.setOnItemSelectedListener(listener);
        nightSpinner.setOnItemSelectedListener(listener);

        morningSpinner.setAdapter(adapter);
        afternoonSpinner.setAdapter(adapter);
        eveningSpinner.setAdapter(adapter);
        nightSpinner.setAdapter(adapter);
    }

    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.morning_spinner:
                    morningUnit = parent.getItemAtPosition(position).toString();
                    break;
                case R.id.afternoon_spinner:
                    afternoonUnit = parent.getItemAtPosition(position).toString();
                    break;
                case R.id.evening_spinner:
                    eveningUnit = parent.getItemAtPosition(position).toString();
                    break;
                case R.id.night_spinner:
                    nightUnit = parent.getItemAtPosition(position).toString();
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    public void saveMedicine(View view) {
        nameValue = nameText.getText().toString();
        morningValue = morningText.getText().toString();
        afternoonValue = afternoonText.getText().toString();
        eveningValue = eveningText.getText().toString();
        nightValue = nightText.getText().toString();

        if (monday.isChecked()) mondayOn = true;
        if (tuesday.isChecked()) tuesdayOn = true;
        if (wednesday.isChecked()) wednesdayOn = true;
        if (thursday.isChecked()) thursdayOn = true;
        if (friday.isChecked()) fridayOn = true;
        if (saturday.isChecked()) saturdayOn = true;
        if (sunday.isChecked()) sundayOn = true;

        Medicine newMed = new Medicine(nameValue, morningValue, morningUnit, afternoonValue,
                afternoonUnit, eveningValue, eveningUnit, nightValue, nightUnit, mondayOn,
                tuesdayOn, wednesdayOn, thursdayOn, fridayOn, saturdayOn, sundayOn);

        Intent i = new Intent(this, MedicineList.class);
        i.putExtra("addIntent", newMed);
        setResult(RESULT_OK, i);
        finish();
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, MedicineList.class);
        startActivity(intent);
    }


}