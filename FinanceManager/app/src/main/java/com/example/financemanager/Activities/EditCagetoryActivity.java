package com.example.financemanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.financemanager.Adapter.IconAdapter;
import com.example.financemanager.DAO.DBHelper;
import com.example.financemanager.DAO.IconDAO;
import com.example.financemanager.Model.Icon;
import com.example.financemanager.R;
import com.example.financemanager.Utils.Validate;

import java.util.List;

public class EditCagetoryActivity extends AppCompatActivity {


    ImageView btnComeBack;
    EditText etName;
    Button btnSave;

    List<Icon> list;
    GridView gridView;
    IconAdapter adapter;

    int user_id;
    Icon selectedIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_cagetory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addControls() {

        btnComeBack = findViewById(R.id.btnComeBack);
        etName = findViewById(R.id.etName);
        btnSave = findViewById(R.id.btnSave);

        gridView = findViewById(R.id.gridView);
        IconDAO iconDAO = new IconDAO(new DBHelper(this));
        list = iconDAO.getAllIcons();
        adapter = new IconAdapter(this, R.layout.item_icon_grid, list);
        gridView.setAdapter(adapter);

        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 0);

        selectedIcon = null;

    }

    private void addEvents() {

        btnComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            selectedIcon = list.get(position);});


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                if (!Validate.validateName(name)) {
                    Toast.makeText(EditCagetoryActivity.this, "Tên danh mục trống hoặc không hợp lệ !!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedIcon == null) {
                    Toast.makeText(EditCagetoryActivity.this, "Vui lòng chọn biểu tượng !!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

}