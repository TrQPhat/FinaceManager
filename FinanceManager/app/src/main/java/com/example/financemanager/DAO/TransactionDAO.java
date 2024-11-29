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
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        String query ="SELECT Transactions.transaction_id, Categories.name, Transactions.amount, Transactions.date,Transactions.description, "
                + "Transactions.category_id, Transactions.user_id, Icon.path, Categories.type "
                + "FROM Transactions JOIN Categories on Transactions.category_id = Categories.category_id"
                + " JOIN Icon on Categories.icon_id = Icon.id WHERE Transactions.user_id=?";
        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(user_Id)}, null);
            if (cursor.moveToFirst()) {
                do {
                    Transaction transaction = new Transaction(
                            cursor.getInt(0),    //transaction_id
                            cursor.getString(1), //name
                            cursor.getInt(2),    //amount
                            cursor.getString(3), //date
                            cursor.getString(4), //description
                            cursor.getInt(5),    //category_id
                            cursor.getInt(6),    //user_id
                            cursor.getString(7), //iconPath
                            cursor.getString(8)  //type
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

    public List<Transaction> getTransactionsByType(int user_Id, String type) {
        List<Transaction> transactionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        String query ="SELECT Transactions.transaction_id, Categories.name, Transactions.amount, Transactions.date, "
                + "Transactions.description, Transactions.category_id, Transactions.user_id, Icon.path, Categories.type "
                + "FROM Transactions JOIN Categories on Transactions.category_id = Categories.category_id"
                + " JOIN Icon on Categories.icon_id = Icon.id WHERE Transactions.user_id= ? and Categories.type = ?";
        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(user_Id), type}, null);
            if (cursor.moveToFirst()) {
                do {
                    Transaction transaction = new Transaction(
                            cursor.getInt(0),    //transaction_id
                            cursor.getString(1), //name
                            cursor.getInt(2),    //amount
                            cursor.getString(3), //date
                            cursor.getString(4), //description
                            cursor.getInt(5),    //category_id
                            cursor.getInt(6),    //user_id
                            cursor.getString(7), //iconPath
                            cursor.getString(8)  //type
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

