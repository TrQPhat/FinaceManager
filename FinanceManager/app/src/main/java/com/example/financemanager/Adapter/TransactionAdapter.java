package com.example.financemanager.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.financemanager.Model.Transaction;

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
        return super.getView(position, convertView, parent);

    }
}
