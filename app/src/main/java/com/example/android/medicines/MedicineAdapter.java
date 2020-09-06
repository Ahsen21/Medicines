package com.example.android.medicines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    private List<Medicine> medicineArray;
    private Context context;
//    private static MyListener itemListener;

    public MedicineAdapter(Context context, List<Medicine> medsData) {
        this.medicineArray = medsData;
        this.context = context;
//        MedicineAdapter.itemListener = itemListener;
    }

    @NonNull
    @Override
    public MedicineAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).
                inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MedicineAdapter.ViewHolder holder, int position) {
        // Get current medicine.
        Medicine currentMedicine = medicineArray.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentMedicine);
    }

    @Override
    public int getItemCount() {
        if (medicineArray != null) {
            return medicineArray.size();
        } else return 0;
    }

    void setMedicineArray(List<Medicine> medicines) {
        medicineArray = medicines;
        notifyDataSetChanged();
    }

    public Medicine getMedAtPosition(int position) {
        return medicineArray.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView medNameText, morningDosageText, afternoonDosageText, eveningDosageText,
                nightDosageText, medicineDays;

        public ViewHolder(View itemView) {
            super(itemView);

            medNameText = itemView.findViewById(R.id.medName);
            morningDosageText = itemView.findViewById(R.id.morningDosage);
            afternoonDosageText = itemView.findViewById(R.id.afternoonDosage);
            eveningDosageText = itemView.findViewById(R.id.eveningDosage);
            nightDosageText = itemView.findViewById(R.id.nightDosage);
            medicineDays = itemView.findViewById(R.id.days);

//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            itemListener.recyclerViewItemClicked(v, this.getLayoutPosition());
//        }

        void bindTo(Medicine currentMedicine) {
            String morningDosage = currentMedicine.getMorningDosage() + " " + currentMedicine.getMorningUnit();
            String afternoonDosage = currentMedicine.getAfternoonDosage() + " " + currentMedicine.getAfternoonUnit();
            String eveningDosage = currentMedicine.getEveningDosage() + " " + currentMedicine.getEveningUnit();
            String nightDosage = currentMedicine.getNightDosage() + " " + currentMedicine.getNightUnit();

            String days = "";
            if (currentMedicine.isMondayOn())
                days += "Mon";
            if (currentMedicine.isTuesdayOn())
                days += ", Tue";
            if (currentMedicine.isWednesdayOn())
                days += ", Wed";
            if (currentMedicine.isThursdayOn())
                days += ", Thu";
            if (currentMedicine.isFridayOn())
                days += ", Fri";
            if (currentMedicine.isSaturdayOn())
                days += ", Sat";
            if (currentMedicine.isSundayOn())
                days += ", Sun";

            if (days.equals("Mon, Tue, Wed, Thu, Fri, Sat, Sun"))
                days = "Daily";

            medNameText.setText(currentMedicine.getMedName());
            morningDosageText.setText(morningDosage);
            afternoonDosageText.setText(afternoonDosage);
            eveningDosageText.setText(eveningDosage);
            nightDosageText.setText(nightDosage);
            medicineDays.setText(days);
        }
    }
}

