package com.example.financemanager.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financemanager.Model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private DBHelper dbHelper;

    public TransactionDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Thêm giao dịch mới
    public long addTransaction(double amount, String date, String description, int categoryId, int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("amount", amount);
        values.put("date", date);
        values.put("description", description);
        values.put("category_id", categoryId);
        values.put("user_id", userId);

        long id = db.insert("Transactions", null, values);
        db.close();
        return id;
    }

    // Lấy tất cả giao dịch của một người dùng
    public Cursor getTransactionsByUser(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query("Transactions", null, "user_id = ?", new String[]{String.valueOf(userId)}, null, null, null);
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM Transactions", null);
            if (cursor.moveToFirst()) {
                do {
                    Transaction transaction = new Transaction(
                            cursor.getInt(cursor.getColumnIndexOrThrow("transaction_id")),
                            cursor.getDouble(cursor.getColumnIndexOrThrow("amount")),
                            cursor.getString(cursor.getColumnIndexOrThrow("date")),
                            cursor.getString(cursor.getColumnIndexOrThrow("description")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("category_id")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("user_id"))
                    );
                    transactionList.add(transaction);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return transactionList;
    }

}

