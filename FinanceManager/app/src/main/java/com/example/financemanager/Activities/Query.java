package com.example.financemanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financemanager.DAO.BudgetDAO;
import com.example.financemanager.DAO.CategoryDAO;
import com.example.financemanager.DAO.DBHelper;
import com.example.financemanager.DAO.NotificationDAO;
import com.example.financemanager.DAO.TransactionDAO;
import com.example.financemanager.DAO.UserDAO;
import com.example.financemanager.Model.Budget;
import com.example.financemanager.Model.Category;
import com.example.financemanager.Model.Transaction;
import com.example.financemanager.Model.User;
import com.example.financemanager.Model.Notification;
import com.example.financemanager.R;

import java.util.ArrayList;
import java.util.List;

public class Query extends AppCompatActivity {

    private ListView listViewData;
    private UserDAO userDAO;
    private CategoryDAO categoryDAO;
    private TransactionDAO transactionDAO;
    private BudgetDAO budgetDAO;
    private NotificationDAO notificationDAO;
    EditText etUserId;
    Button btnSearch;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        // Initialize DAOs
        DBHelper dbHelper = new DBHelper(this);
        userDAO = new UserDAO(this);
        categoryDAO = new CategoryDAO(this);
        transactionDAO = new TransactionDAO(this);
        budgetDAO = new BudgetDAO(this);
        notificationDAO = new NotificationDAO(this);
        btnSearch = findViewById(R.id.searchbyemail);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Query.this, test.class);
                startActivity(intent);
            }
        });

        etUserId = findViewById(R.id.etUserId);


        // Initialize ListView
        listViewData = findViewById(R.id.listViewData);

        // Initialize Buttons
        Button btnShowUsers = findViewById(R.id.btnShowUsers);
        Button btnShowCategories = findViewById(R.id.btnShowCategories);
        Button btnShowTransactions = findViewById(R.id.btnShowTransactions);
        Button btnShowBudgets = findViewById(R.id.btnShowBudgets);
        Button btnShowNotifications = findViewById(R.id.btnShowNotifications);

        // Set button listeners
        btnShowUsers.setOnClickListener(v -> showUsers());
        btnShowCategories.setOnClickListener(v -> showCategories());
        btnShowTransactions.setOnClickListener(v -> showTransactions());
        btnShowBudgets.setOnClickListener(v -> showBudgets());
        btnShowNotifications.setOnClickListener(v -> showNotifications());
    }

    private void showUsers() {
        List<User> users = userDAO.getAllUsers();
        List<String> userStrings = new ArrayList<>();
        for (User user : users) {
            userStrings.add("ID: " + user.getId() + ", Username: " + user.getUsername() + ", Email: " + user.getEmail());
        }
        displayData(userStrings);
    }

    private void showCategories() {
        if (etUserId.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập userId", Toast.LENGTH_SHORT).show();
            return;
        }
        userId = Integer.parseInt(etUserId.getText().toString());
        List<Category> categories = categoryDAO.getAllCategories(userId);
        List<String> categoryStrings = new ArrayList<>();
        for (Category category : categories) {
            categoryStrings.add("ID: " + category.getCategoryId() + ", Name: " + category.getName());
        }
        displayData(categoryStrings);
    }

    private void showTransactions() {
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        List<String> transactionStrings = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionStrings.add("ID: " + transaction.getTransactionId() + ", Amount: " + transaction.getAmount());
        }
        displayData(transactionStrings);
    }

    private void showBudgets() {
        List<Budget> budgets = budgetDAO.getAllBudgets();
        List<String> budgetStrings = new ArrayList<>();
        for (Budget budget : budgets) {
            budgetStrings.add("ID: " + budget.getBudgetId() + ", Amount: " + budget.getAmount());
        }
        displayData(budgetStrings);
    }

    private void showNotifications() {
        List<Notification> notifications = notificationDAO.getAllNotifications();
        List<String> notificationStrings = new ArrayList<>();
        for (Notification notification : notifications) {
            notificationStrings.add("ID: " + notification.getNotificationId() + ", Message: " + notification.getMessage());
        }
        displayData(notificationStrings);
    }

    private void displayData(List<String> data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listViewData.setAdapter(adapter);
    }
}
