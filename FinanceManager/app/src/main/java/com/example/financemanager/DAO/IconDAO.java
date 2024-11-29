package com.example.financemanager.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financemanager.Model.Icon;

import java.util.ArrayList;
import java.util.List;

public class IconDAO {
    private DBHelper dbHelper;

    // Constructor nhận đối tượng DBHelper
    public IconDAO(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Lấy toàn bộ icon
    public List<Icon> getAllIcons() {
        List<Icon> iconList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM Icon", null);
            if (cursor.moveToFirst()) {
                do {
                    Icon icon = new Icon(
                            cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("name")),
                            cursor.getString(cursor.getColumnIndexOrThrow("path"))
                    );
                    iconList.add(icon);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }
        return iconList;
    }

}
