package com.example.financemanager.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
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
import com.example.financemanager.Utils.Validate;

import java.util.Random;

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
                    if (userDAO.isEmailExists(email)) {
                        Toast.makeText(SignUpActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String otp = randomOTP();
                    String message = "Mã xác thực của bạn là: " + otp;
                    sendEmail(email,message);
                    showVerifyOTPDialog(otp, user);


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

    private void showVerifyOTPDialog(String otpGui, User user)
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
                    UserDAO userDAO = new UserDAO(SignUpActivity.this);
                    if (userDAO.addUser(user)) {
                        dialog.dismiss();
                        Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(SignUpActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(SignUpActivity.this,"OTP không hợp lệ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}