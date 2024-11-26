package com.example.financemanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.financemanager.Adapter.IconAdapter;
import com.example.financemanager.DAO.CategoryDAO;
import com.example.financemanager.DAO.DBHelper;
import com.example.financemanager.DAO.IconDAO;
import com.example.financemanager.Model.Category;
import com.example.financemanager.Model.Icon;
import com.example.financemanager.R;
import com.example.financemanager.Utils.Validate;

import java.util.List;

public class EditCagetoryActivity extends AppCompatActivity {


    ImageView btnComeBack;
    EditText etName;
    Button btnSave;
    RadioGroup radioGroup;

    List<Icon> list;
    GridView gridView;
    IconAdapter adapter;

    boolean typeRequest;
    int user_id;
    Intent intent;
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
        radioGroup = findViewById(R.id.radioGroup);

        gridView = findViewById(R.id.gridView);
        IconDAO iconDAO = new IconDAO(new DBHelper(this));
        list = iconDAO.getAllIcons();
        adapter = new IconAdapter(this, R.layout.item_icon_grid, list);
        gridView.setAdapter(adapter);

        intent = getIntent();
        user_id = intent.getIntExtra("user_id", 0);
        if (intent.hasExtra("category"))
        {
            typeRequest = true;//kiểu request là edit
            //lấy danh mục
            Category category = (Category) intent.getSerializableExtra("category");
            etName.setText(category.getName());//set tên

            //set kiểu
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);

                // Kiểm tra nếu tag của RadioButton khớp với giá trị type
                if (radioButton.getTag() != null && radioButton.getText().equals(category.getType())) {
                    radioButton.setChecked(true);
                    break; // Thoát vòng lặp
                }
            }

            //set icon
            adapter.setSelectedIconId(category.getIconId());


        }
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
            selectedIcon = list.get(position);
            etName.setText(selectedIcon.getName());

            for (int i = 0; i < gridView.getChildCount(); i++) {
                gridView.getChildAt(i).setSelected(false);
            }

            view.setSelected(true);

        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                if (!Validate.validateName(name)) {
                    Toast.makeText(EditCagetoryActivity.this, "Tên danh mục trống hoặc không hợp lệ !!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String type = "";
                int selectedType = radioGroup.getCheckedRadioButtonId();
                if (selectedType == -1) {
                    Toast.makeText(EditCagetoryActivity.this, "Vui lòng chọn loại !!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    RadioButton radioButton = findViewById(selectedType);
                    type = radioButton.getText().toString();
                }

                if (selectedIcon == null) {
                    Toast.makeText(EditCagetoryActivity.this, "Vui lòng chọn biểu tượng !!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Category category = new Category(0, name, type, user_id, selectedIcon.getId(), selectedIcon.getPath());
                CategoryDAO categoryDAO = new CategoryDAO(EditCagetoryActivity.this);
                if (categoryDAO.addCategory(category)){
                    Toast.makeText(EditCagetoryActivity.this, "Thêm danh mục "+ name +" thành công !!", Toast.LENGTH_SHORT).show();
                    intent.putExtra("change", category);
                    setResult(RESULT_OK, intent);
                    return;

                }
                else Toast.makeText(EditCagetoryActivity.this, "Thêm danh mục "+ name +" thất bại !!", Toast.LENGTH_SHORT).show();

            }
        });

    }

}