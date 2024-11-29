package com.example.financemanager.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financemanager.Model.Budget;

import java.util.ArrayList;
import java.util.List;

public class BudgetDAO {
    private DBHelper dbHelper;

    public BudgetDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Thêm ngân sách mới
    public long addBudget(int categoryId, double amount, String startDate, String endDate, int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("category_id", categoryId);
        values.put("amount", amount);
        values.put("start_date", startDate);
        values.put("end_date", endDate);
        values.put("user_id", userId);

        long id = db.insert("Budgets", null, values);
        db.close();
        return id;
    }

    // Lấy ngân sách của người dùng
    public Cursor getBudgetsByUser(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query("Budgets", null, "user_id = ?", new String[]{String.valueOf(userId)}, null, null, null);
    }

    public List<Budget> getAllBudgets() {
        List<Budget> budgetList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM Budgets", null);
            if (cursor.moveToFirst()) {
                do {
                    Budget budget = new Budget(
                            cursor.getInt(cursor.getColumnIndexOrThrow("budget_id")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("category_id")),
                            cursor.getDouble(cursor.getColumnIndexOrThrow("amount")),
                            cursor.getString(cursor.getColumnIndexOrThrow("start_date")),
                            cursor.getString(cursor.getColumnIndexOrThrow("end_date")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("user_id"))
                    );
                    budgetList.add(budget);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return budgetList;
    }

}
