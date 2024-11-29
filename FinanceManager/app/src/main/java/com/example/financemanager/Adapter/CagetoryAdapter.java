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

import com.example.financemanager.Model.Category;
import com.example.financemanager.R;

import java.util.List;

public class CagetoryAdapter extends ArrayAdapter<Category> {

    Context context;
    int layout;
    List<Category> list;

    public CagetoryAdapter(@NonNull Context context, int layout, @NonNull List<Category> list) {
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
        Category category = list.get(position);

        ImageView imageView = convertView.findViewById(R.id.imageView);
        imageView.setImageResource(context.getResources().getIdentifier(category.getIconPath(), "drawable", context.getPackageName()));
        TextView tvName = convertView.findViewById(R.id.tvName);
        tvName.setText(category.getName());
        TextView tvType = convertView.findViewById(R.id.tvType);
        tvType.setText(category.getType());
        return convertView;
    }
}
