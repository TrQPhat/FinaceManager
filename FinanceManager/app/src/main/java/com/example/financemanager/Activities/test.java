package com.example.financemanager.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financemanager.DAO.UserDAO;
import com.example.financemanager.Model.User;
import com.example.financemanager.R;
import com.example.financemanager.Utils.DBConfigUtil;
import com.example.financemanager.Utils.FormatDate;

import java.util.HashMap;
import java.util.Map;

public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        DBConfigUtil.copyDatabaseFromAssets(this);
        // Liên kết với các view
        EditText emailInput = findViewById(R.id.emailInput);
        Button searchButton = findViewById(R.id.searchButton);
        TextView tvId = findViewById(R.id.tvId);
        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvEmail = findViewById(R.id.tvEmail);
        TextView tvName = findViewById(R.id.tvName);
        TextView tvPassword = findViewById(R.id.tvPassword);
        TextView tvCreateAt = findViewById(R.id.tvCreateAt);
        TextView tvLastLogin = findViewById(R.id.tvLastLogin);
        EditText etPassword = findViewById(R.id.etPassword);
        TextView tvKQcheckPassword = findViewById(R.id.tvKQcheckPassword);


        // Xử lý sự kiện tìm kiếm
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(test.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    UserDAO userDAO = new UserDAO(test.this);
                    User user = userDAO.getUserByEmail(email);
                    if (user != null) {
                        tvId.setText(String.valueOf(user.getId()));
                        tvUsername.setText(user.getUsername());
                        tvEmail.setText(user.getEmail());
                        tvPassword.setText(user.getPassword()); // Hiển thị mật khẩu dạng ẩn
                        tvCreateAt.setText(user.getCreate_at());
                        tvLastLogin.setText(user.getLast_login());
                        tvKQcheckPassword.setText(userDAO.hashWithMD5(password).equals(user.getPassword()) ? "Đúng" : "Sai");
                    } else {
                        Toast.makeText(test.this,"Không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }



}
