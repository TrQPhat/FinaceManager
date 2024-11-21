package com.example.financemanager.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financemanager.Model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private DBHelper dbHelper;

    public CategoryDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Thêm danh mục mới
    public long addCategory(String name, String type, int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("type", type);
        values.put("user_id", userId);

        long id = db.insert("Categories", null, values);
        db.close();
        return id;
    }

    // Lấy tất cả danh mục của một người dùng
    public Cursor getCategoriesByUser(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query("Categories", null, "user_id = ?", new String[]{String.valueOf(userId)}, null, null, null);
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM Categories", null);
            if (cursor.moveToFirst()) {
                do {
                    Category category = new Category(
                            cursor.getInt(cursor.getColumnIndexOrThrow("category_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("name")),
                            cursor.getString(cursor.getColumnIndexOrThrow("type")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("user_id"))
                    );
                    categoryList.add(category);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return categoryList;
    }

}
