package com.example.financemanager.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.financemanager.R;
import com.example.financemanager.Utils.FormatDate;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class Calendar_Dialog extends Dialog {
    Context context;
    public Calendar_Dialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public void showCalendarDialog(TextView tvDate) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.calendar_dialog);

        TextView tvOK = dialog.findViewById(R.id.tvOK);
        TextView tvHuy = dialog.findViewById(R.id.tvHuy);
        CalendarView calendarView = dialog.findViewById(R.id.calendarView);

        AtomicReference<String> selectedDate = new AtomicReference<>(FormatDate.DateToString(new Date()));
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate.set(dayOfMonth + "/" + ((month + 1)>9?(month + 1):"0"+(month + 1)) + "/" + year);
        });
        tvHuy.setOnClickListener(view -> {
            dialog.dismiss();
        });
        tvOK.setOnClickListener(view -> {
            tvDate.setText(selectedDate.get());
            dialog.dismiss();
        });

        dialog.show();
    }
}
