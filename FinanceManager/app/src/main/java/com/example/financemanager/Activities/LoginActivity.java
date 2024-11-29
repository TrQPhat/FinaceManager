package com.example.financemanager.Activities;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.financemanager.DAO.UserDAO;
import com.example.financemanager.Model.User;
import com.example.financemanager.R;
import com.example.financemanager.Utils.JavaMailAPI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Random;


public class LoginActivity extends AppCompatActivity {

    EditText etPassword, etEmail;
    TextView tvForgetPass, tvSignUp, btnLogin;
    ImageButton btnTogglePassword;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addControls() {
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        tvForgetPass = findViewById(R.id.tvForgetPassword);
        tvSignUp = findViewById(R.id.tvSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        btnTogglePassword = findViewById(R.id.btn_toggle_password);

    }

    private void addEvents() {
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        btnTogglePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

    }

    private void login() {
        UserDAO userDAO = new UserDAO(LoginActivity.this);
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (userDAO.checkLogin(email, password)) {

            String otp = randomOTP();
            String message = "Mã xác thực của bạn là: " + otp;
            sendEmail(email,message);
            showVerifyOTPDialog(otp, email);
            //login success


        }
        else Toast.makeText(LoginActivity.this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
    }

    private String randomOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Tạo số OTP 6 chữ số
        return String.valueOf(otp);
    }
    private void sendEmail(String email, String message) {
        JavaMailAPI javaMailAPI = new JavaMailAPI(email, message);
        javaMailAPI.execute();
        Toast.makeText(this, "OTP xác nhận đã gửi đến " + email, Toast.LENGTH_SHORT).show();
    }

    private void showVerifyOTPDialog(String otpGui, String email)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_input_otp,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText txtOTP = view.findViewById(R.id.txtOTP);
        AppCompatButton btnXacthuc = view.findViewById(R.id.btnXacthucOTP);

        btnXacthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otpNhan = txtOTP.getText().toString();
                if (otpNhan.equals(otpGui))
                {
                    UserDAO userDAO = new UserDAO(LoginActivity.this);
                    userDAO.updateLastLogin(email);
                    finish();
                    Intent intent = new Intent(LoginActivity.this, NavigationViewActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"OTP không đúng",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}