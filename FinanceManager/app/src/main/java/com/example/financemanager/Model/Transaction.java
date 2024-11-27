package com.example.financemanager.Model;

import java.io.Serializable;

public class Transaction implements Serializable {
    private int id;
    private String name;
    private int amount;
    private String date;
    private String description;
    private int categoryId;
    private int userId;
    private String iconPath;
    private String type;

    public Transaction(int amount, String date, String description, int categoryId, int userId) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    public Transaction(int id,String name, int amount, String date, String description, int categoryId, int userId, String iconPath, String type) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
        this.userId = userId;
        this.iconPath = iconPath;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
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

    public String iconPath() { return iconPath; }

    public String getType() { return type; }
}
