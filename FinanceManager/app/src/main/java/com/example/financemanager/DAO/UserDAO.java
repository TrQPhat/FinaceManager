package com.example.financemanager.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.financemanager.Activities.LoginActivity;
import com.example.financemanager.DAO.DBHelper;
import com.example.financemanager.Model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class UserDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Phương thức kiểm tra login
    public boolean checkLogin(String email, String password) {
        password = hashWithMD5(password);
        String query = "SELECT * FROM Users WHERE email = ? AND password_hash = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

    // Phương thức thêm tài khoản mới
    public boolean addUser(User user) {
        if (isEmailExists(user.getEmail())) {
            return false;
        }
        String username = user.getUsername();
        String email = user.getEmail();
        String passwordHash=user.getPassword();
        passwordHash = hashWithMD5(passwordHash);
        String createAt = user.getCreate_at();
        String lastLogin = user.getLast_login();

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("password_hash", passwordHash);
        values.put("created_at", createAt);
        values.put("last_login", lastLogin);

        long result = db.insert("Users", null, values);
        return result != -1; // Trả về true nếu thêm thành công
    }

    // Phương thức cập nhật lần đăng nhập cuối
    public boolean updateLastLogin(String email) {
        ContentValues values = new ContentValues();
        values.put("last_login", System.currentTimeMillis());

        int rowsAffected = db.update("Users", values, "email = ?", new String[]{email});
        return rowsAffected > 0; // Trả về true nếu cập nhật thành công
    }

    public static String hashWithMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes());

            // Chuyển byte array thành chuỗi hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByEmail(String email) {
        User user = null; // Biến để lưu kết quả
        String query = "SELECT * FROM Users WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow("user_id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("username")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password_hash")),
                    cursor.getString(cursor.getColumnIndexOrThrow("created_at")),
                    cursor.getString(cursor.getColumnIndexOrThrow("last_login"))
            );
        }
        cursor.close(); // Đóng con trỏ sau khi sử dụng
        return user;
    }

    public boolean isEmailExists(String email) {
        boolean exists = false; // Biến để lưu trạng thái email tồn tại

        // Câu lệnh truy vấn
        String query = "SELECT COUNT(*) FROM Users WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        // Kiểm tra kết quả
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0); // Lấy giá trị đầu tiên từ kết quả truy vấn
            exists = count > 0; // Nếu count > 0, nghĩa là email đã tồn tại
        }
        cursor.close();
        return exists;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM Users", null);
            if (cursor.moveToFirst()) {
                do {
                    User user = new User(
                            cursor.getInt(cursor.getColumnIndexOrThrow("user_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("username")),
                            cursor.getString(cursor.getColumnIndexOrThrow("email")),
                            cursor.getString(cursor.getColumnIndexOrThrow("password_hash")),
                            cursor.getString(cursor.getColumnIndexOrThrow("created_at")),
                            cursor.getString(cursor.getColumnIndexOrThrow("last_login"))
                    );
                    userList.add(user);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return userList;
    }


    // Đóng kết nối
    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
