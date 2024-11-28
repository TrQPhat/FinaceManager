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
    public boolean insertTransaction(Transaction transaction) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("amount", transaction.getAmount());
        values.put("date", transaction.getDate());
        values.put("description", transaction.getDescription());
        values.put("category_id", transaction.getCategoryId());
        values.put("user_id", transaction.getUserId());

        return db.insert("Transactions", null, values) != -1;
    }

<<<<<<< HEAD
    public boolean deleteTransaction(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("Transactions", "transaction_id = ?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean updateTransaction(Transaction transaction){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("amount", transaction.getAmount());
        values.put("date", transaction.getDate());
        values.put("description", transaction.getDescription());
        values.put("category_id", transaction.getCategoryId());

        return db.update("Transactions", values, "transaction_id = ?", new String[]{String.valueOf(transaction.getId())}) > 0;
    }

    public List<Transaction> getAllTransactions(int user_Id) {
        List<Transaction> transactionList = new ArrayList<>();
=======
    public boolean getAllTransactionsById(int id) {
>>>>>>> parent of 625ff51 (update Transaction)
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM Transactions WHERE transaction_id = ?", new String[]{String.valueOf(id)});
            return cursor.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return false;
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

