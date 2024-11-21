package com.example.financemanager.Model;

public class Category {
    private int categoryId;
    private String name;
    private String type;
    private int userId;

    public Category(int categoryId, String name, String type, int userId) {
        this.categoryId = categoryId;
        this.name = name;
        this.type = type;
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
