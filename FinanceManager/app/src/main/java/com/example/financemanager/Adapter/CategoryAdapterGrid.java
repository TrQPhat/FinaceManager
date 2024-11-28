package com.example.financemanager.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

public class CategoryAdapterGrid extends ArrayAdapter<Category> {
    Context context;
    int layout;
    List<Category> list;
    int selectedCategoryId = -1;

    public CategoryAdapterGrid(@NonNull Context context, int layout, @NonNull List<Category> list) {
        super(context, layout, list);
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    public CategoryAdapterGrid(@NonNull Context context, int layout, @NonNull List<Category> list, int categoryId) {
        super(context, layout, list);
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.selectedCategoryId = categoryId;
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
        String name = category.getName();
        if (name.length() > 14)
            name = name.substring(0, 11) + "...";
        tvName.setText(name);

        if (selectedCategoryId == category.getCategoryId()){
            convertView.setBackgroundResource(R.drawable.custom_icon_grid);
        }

        return convertView;
    }
}
