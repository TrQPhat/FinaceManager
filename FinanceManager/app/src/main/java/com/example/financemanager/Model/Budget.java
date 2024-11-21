package com.example.financemanager.Model;

public class Budget {
    private int budgetId;
    private int categoryId;
    private double amount;
    private String startDate;
    private String endDate;
    private int userId;

    public Budget(int budgetId, int categoryId, double amount, String startDate, String endDate, int userId) {
        this.budgetId = budgetId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
