package com.example.financemanager.DAO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financemanager.Model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
    private DBHelper dbHelper;

    public NotificationDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Thêm thông báo mới
    public long addNotification(int userId, String message, String date, String status) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("message", message);
        values.put("date", date);
        values.put("status", status);

        long id = db.insert("Notifications", null, values);
        db.close();
        return id;
    }

    // Lấy tất cả thông báo của người dùng
    public Cursor getNotificationsByUser(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query("Notifications", null, "user_id = ?", new String[]{String.valueOf(userId)}, null, null, null);
    }

    public List<Notification> getAllNotifications() {
        List<Notification> notificationList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM Notifications", null);
            if (cursor.moveToFirst()) {
                do {
                    Notification notification = new Notification(
                            cursor.getInt(cursor.getColumnIndexOrThrow("notification_id")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("user_id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("message")),
                            cursor.getString(cursor.getColumnIndexOrThrow("date")),
                            cursor.getString(cursor.getColumnIndexOrThrow("status"))
                    );
                    notificationList.add(notification);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return notificationList;
    }

}
