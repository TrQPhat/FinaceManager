package com.example.financemanager.Activities;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.financemanager.Adapter.CagetoryAdapter;
import com.example.financemanager.DAO.CategoryDAO;
import com.example.financemanager.DAO.DBHelper;
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

    private void addControls(){
        user_id = getIntent().getIntExtra("user_id", 0);

        tvAdd = findViewById(R.id.tvAdd);
        listView = findViewById(R.id.listView);
        btnComeBack = findViewById(R.id.btnComeBack);

        CategoryDAO categoryDAO = new CategoryDAO(this);

        list = categoryDAO.getAllCategories(user_id);
        adapter = new CagetoryAdapter(this, R.layout.item_cagetory, list);
        listView.setAdapter(adapter);

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
    }
}