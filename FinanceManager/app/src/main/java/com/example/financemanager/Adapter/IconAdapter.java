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
import androidx.recyclerview.widget.RecyclerView;

import com.example.financemanager.Model.Icon;
import com.example.financemanager.R;

import java.util.List;

public class IconAdapter extends ArrayAdapter<Icon> {

    Context context;
    List<Icon> list;
    int layout;


    public IconAdapter(@NonNull Context context, int resource, @NonNull List<Icon> objects) {
        super(context, resource, objects);
        this.context=context;
        this.layout = resource;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
        }

        Icon icon = list.get(position);

        ImageView imageView = convertView.findViewById(R.id.imageView);
        imageView.setImageResource(context.getResources().getIdentifier(icon.getPath(), "drawable", context.getPackageName()));


        return convertView;
    }
}

