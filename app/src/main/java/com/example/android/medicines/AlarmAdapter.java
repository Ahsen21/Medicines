package com.example.android.medicines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {

    private List<Alarm> alarmArray;
    private Context context;

    AlarmAdapter(Context context, List<Alarm> alarms) {
        this.alarmArray = alarms;
        this.context = context;
    }

    @NonNull
    @Override
    public AlarmAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).
                inflate(R.layout.row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AlarmAdapter.ViewHolder holder, int position) {
        // Get current medicine.
        Alarm currentAlarm = alarmArray.get(position);

        // Populate the TextViews with data.
        holder.bindTo(currentAlarm);
    }

    @Override
    public int getItemCount() {
        if (alarmArray != null) {
            return alarmArray.size();
        } else return 0;
    }

    void setAlarmArray(List<Alarm> alarms) {
        alarmArray = alarms;
        notifyDataSetChanged();
    }

    public Alarm getAlarmAtPosition (int position) {
        return alarmArray.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        // Member Variables for the TextViews
        TextView alarmName;
        TextView alarmTime;

        ViewHolder(View itemView) {
            super(itemView);
            alarmName = itemView.findViewById(R.id.alarm_label);
            alarmTime = itemView.findViewById(R.id.time_value);
        }

        void bindTo(Alarm currentAlarm) {
            alarmName.setText(currentAlarm.getAlarmName() + " alarm:");
            alarmTime.setText(currentAlarm.getAlarmTime());
        }
    }
}