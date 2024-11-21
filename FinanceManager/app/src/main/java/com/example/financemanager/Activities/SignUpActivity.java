package com.example.financemanager.Activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.financemanager.DAO.UserDAO;
import com.example.financemanager.Model.User;
import com.example.financemanager.R;
import com.example.financemanager.Utils.Validate;

public class SignUpActivity extends AppCompatActivity {
    EditText etTen, etEmail, etPassword, etRePass;
    TextView btnSignUp;
    CheckBox checkBox;
    TextView tvQuayLai;
    ImageButton btnTogglePassword, btnToggleRePass;
    private boolean isPasswordVisible = false, isRepassVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }
    private void addControls() {
        etTen = findViewById(R.id.etTen);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etRePass = findViewById(R.id.etRePass);
        checkBox = findViewById(R.id.checkBox);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvQuayLai = findViewById(R.id.tvQuayLai);
        tvQuayLai.setText(Html.fromHtml("<u>Quay lại</u>"));
        btnTogglePassword = findViewById(R.id.btn_toggle_password);
        btnToggleRePass = findViewById(R.id.btn_toggle_repass);
    }
    private void addEvents() {
        tvQuayLai.setOnClickListener(v -> {
            finish();
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()){
                    String password = etPassword.getText().toString();
                    String rePass = etRePass.getText().toString();
                    String ten = etTen.getText().toString();
                    String email = etEmail.getText().toString();

                    //kiểm tra định dạng
                    if (!Validate.validateName(ten)) { //tên
                        Toast.makeText(SignUpActivity.this, "Tên chỉ chứa các ký tự [A-Z] và [a-z]", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!Validate.validateEmail(email)){
                        Toast.makeText(SignUpActivity.this, "Email không hợp lệ hoặc không phải email cá nhân", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!(Validate.validatePassword(password)&&Validate.validatePassword(rePass))){
                        new AlertDialog.Builder(SignUpActivity.this)
                                .setTitle("Thông báo")
                                .setMessage("Mật khẩu bắt buộc:" +
                                        "\n - Độ dài từ 6-20 ký tự" +
                                        "\n - Ít nhất một ký tự in hoa" +
                                        "\n - Ít nhất một ký tự in thường" +
                                        "\n - Ít nhất một ký tự là chữ số" +
                                        "\n - Ít nhất một ký tự đặc biệt")
                                .setPositiveButton("Thoát", (dialogInterface, i1) -> {
                                })
                                .show();
                        return;
                    }


                    User user = new User(ten, email, password);
                    UserDAO userDAO = new UserDAO(SignUpActivity.this);
                    userDAO.addUser(user);
                    Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else Toast.makeText(SignUpActivity.this,"Vui lòng đồng ý với điều khoản sử dụng", Toast.LENGTH_SHORT).show();
            }
        });
        btnTogglePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPasswordVisible = setVisiblePassword(isPasswordVisible,btnTogglePassword , etPassword);
            }
        });

        btnToggleRePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRepassVisible = setVisiblePassword(isRepassVisible, btnToggleRePass, etRePass);
            }
        });
    }

    private boolean setVisiblePassword(boolean isPasswordVisible, ImageButton btnTogglePassword, EditText etPassword) {
        if (isPasswordVisible) {
            // ẩn mật khẩu
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            btnTogglePassword.setImageResource(R.drawable.closeeye); // Đổi lại icon
            isPasswordVisible = false;
        } else {
            // hiển thị mật khẩu
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            btnTogglePassword.setImageResource(R.drawable.openeye); // Đổi sang icon mở mắt
            isPasswordVisible = true;
        }
        // đưa về cuối
        etPassword.setSelection(etPassword.getText().length());
        return isPasswordVisible;
    }

}