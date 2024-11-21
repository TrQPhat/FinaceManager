package com.example.financemanager.DAO;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "QuanLyChiTieu.db";

    //Bảng Users
    private static final String TABLE_USERS = "Users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD_HASH = "password_hash";
    private static final String COLUMN_CREATED_AT = "created_at";
    private static final String COLUMN_LAST_LOGIN = "last_login";

    // Bảng Categories
    private static final String TABLE_CATEGORIES = "Categories";
    private static final String COLUMN_CATEGORY_ID = "category_id";
    private static final String COLUMN_CATEGORY_NAME = "name";
    private static final String COLUMN_CATEGORY_TYPE = "type";
    private static final String COLUMN_USER_ID_FK = "user_id";

    // Bảng Transactions
    private static final String TABLE_TRANSACTIONS = "Transactions";
    private static final String COLUMN_TRANSACTION_ID = "transaction_id";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_CATEGORY_ID_FK = "category_id";
    private static final String COLUMN_USER_ID_TRANSACTION_FK = "user_id";

    // Bảng Budgets
    private static final String TABLE_BUDGETS = "Budgets";
    private static final String COLUMN_BUDGET_ID = "budget_id";
    private static final String COLUMN_BUDGET_AMOUNT = "amount";
    private static final String COLUMN_START_DATE = "start_date";
    private static final String COLUMN_END_DATE = "end_date";

    // Bảng Notifications
    private static final String TABLE_NOTIFICATIONS = "Notifications";
    private static final String COLUMN_NOTIFICATION_ID = "notification_id";
    private static final String COLUMN_MESSAGE = "message";
    private static final String COLUMN_NOTIFICATION_DATE = "date";
    private static final String COLUMN_STATUS = "status";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý khi có sự thay đổi trong phiên bản cơ sở dữ liệu
    }

}
