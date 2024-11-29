package com.example.financemanager.Model;

import java.io.Serializable;

public class Category implements Serializable {
    private int categoryId;
    private String name;
    private String type;
    private int userId;
    private int iconId;
    private String iconPath;

    public Category(int categoryId, String name, String type, int userId, int iconId, String iconPath) {
        this.categoryId = categoryId;
        this.name = name;
        this.type = type;
        this.userId = userId;
        this.iconId = iconId;
        this.iconPath = iconPath;
    }

    // Getter và Setter cho tất cả các trường
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

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
}

