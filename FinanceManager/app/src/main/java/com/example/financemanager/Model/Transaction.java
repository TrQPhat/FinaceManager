package com.example.financemanager.Model;

public class Transaction {
    private int transactionId;
    private double amount;
    private String date;
    private String description;
    private int categoryId;
    private int userId;

    public Transaction(int transactionId, double amount, String date, String description, int categoryId, int userId) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
