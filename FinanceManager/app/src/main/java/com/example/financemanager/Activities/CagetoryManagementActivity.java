package com.example.financemanager.Activities;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.financemanager.Adapter.CagetoryAdapter;
import com.example.financemanager.DAO.CategoryDAO;
import com.example.financemanager.Model.Category;
import com.example.financemanager.R;

import java.util.List;

public class CagetoryManagementActivity extends AppCompatActivity {

    TextView tvAdd;
    ListView listView;
    ImageView btnComeBack;
    List<Category> list;
    CagetoryAdapter adapter;
    private int user_id;
    private boolean isLongClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cagetory_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addControls();
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void addControls(){
        user_id = getIntent().getIntExtra("user_id", 0);

        tvAdd = findViewById(R.id.tvAdd);
        listView = findViewById(R.id.listView);
        btnComeBack = findViewById(R.id.btnComeBack);

        CategoryDAO categoryDAO = new CategoryDAO(this);

        list = categoryDAO.getAllCategories(user_id);
        adapter = new CagetoryAdapter(this, R.layout.item_cagetory, list);
        listView.setAdapter(adapter);

        isLongClick = false;

    }

    private void addEvents(){

        btnComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CagetoryManagementActivity.this, EditCagetoryActivity.class);
                intent.putExtra("user_id", user_id);
                startActivityForResult(intent,123);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                isLongClick = true;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CagetoryManagementActivity.this);
                alertDialog.setCancelable(false);
                alertDialog
                        .setTitle("Xóa danh mục")
                        .setMessage("Bạn có chắc chắn muốn xóa danh mục này?")
                        .setPositiveButton("Có", (dialog, which) -> {
                            int category_id = list.get(position).getCategoryId();
                            CategoryDAO categoryDAO = new CategoryDAO(CagetoryManagementActivity.this);
                            if (categoryDAO.deleteCategory(category_id)){
                                list.remove(list.get(position));
                                adapter.notifyDataSetChanged();
                                Toast.makeText(CagetoryManagementActivity.this, "Xóa danh mục thành công !!", Toast.LENGTH_SHORT).show();
                            }
                            isLongClick = false;
                        })
                        .setNegativeButton("Không", (dialog, which) -> {isLongClick = false;})
                        .show();

                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isLongClick){
                    Intent intent = new Intent(CagetoryManagementActivity.this, EditCagetoryActivity.class);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("category", list.get(position));
                    startActivityForResult(intent,321);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Category category = (Category) data.getSerializableExtra("change");
            if (requestCode == 123){
                list.add(category);
            }else {
                for (int i=0; i<list.size(); i++) {
                    if (list.get(i).getCategoryId() == category.getCategoryId()) {
                        list.set(i, category);
                        break;
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}