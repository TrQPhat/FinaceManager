package com.example.financemanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.financemanager.Model.Transaction;
import com.example.financemanager.R;

import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.List;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    private Context context;
    private int layout;
    private List<Transaction> list;

    public TransactionAdapter(@NonNull Context context, int layout, @NonNull List<Transaction> list) {
        super(context, layout, list);
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
        }

        Transaction transaction = list.get(position);

        TextView tvName = convertView.findViewById(R.id.tvName);
        tvName.setText(transaction.getName());
        TextView tvAmount = convertView.findViewById(R.id.tvAmount);
        String amount = String.valueOf(transaction.getAmount());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        amount = decimalFormat.format(transaction.getAmount()) + " ₫";
        tvAmount.setText(amount);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        tvDate.setText(transaction.getDate());
        ImageView imageView = convertView.findViewById(R.id.imageView);
        imageView.setImageResource(context.getResources().getIdentifier(transaction.iconPath(), "drawable", context.getPackageName()));

        return convertView;

    }
}
