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
    public boolean addCategory(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        values.put("type", category.getType());
        values.put("user_id", category.getUserId());
        values.put("icon_id", category.getIconId());

        return db.insert("Categories", null, values) != -1;
    }

    public boolean deleteCategory(int categoryId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("Categories", "category_id = ?", new String[]{String.valueOf(categoryId)}) > 0;
    }


    public List<Category> getAllCategoriesByType(int userId, String type) {
        List<Category> categoryList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Truy vấn các danh mục thuộc userId và type chỉ định
            String query = "SELECT * FROM Categories JOIN Icon ON Categories.icon_id = Icon.id WHERE Categories.user_id = ? AND Categories.type = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId), type});

            if (cursor.moveToFirst()) {
                do {
                    // Tạo đối tượng Category từ dữ liệu truy vấn
                    Category category = new Category(
                            cursor.getInt(cursor.getColumnIndexOrThrow("Categories.category_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Categories.name")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Categories.type")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("Categories.user_id")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("Icon.icon_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Icon.path"))// Đường dẫn icon
                    );
                    categoryList.add(category);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }

        return categoryList;
    }

    public List<Category> getAllCategories(int userId) {
        List<Category> categoryList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Truy vấn các danh mục thuộc userId và type chỉ định
            String query = "SELECT * FROM Categories JOIN Icon ON Categories.icon_id = Icon.id WHERE user_id = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

            if (cursor.moveToFirst()) {
                do {
                    // Tạo đối tượng Category từ dữ liệu truy vấn
                    Category category = new Category(
                            cursor.getInt(cursor.getColumnIndexOrThrow("Categories.category_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Categories.name")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Categories.type")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("Categories.user_id")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("Icon.icon_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("Icon.path")) // Đường dẫn icon
                    );
                    categoryList.add(category);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }

        return categoryList;
    }


}
