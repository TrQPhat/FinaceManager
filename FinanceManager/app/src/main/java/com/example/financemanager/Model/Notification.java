package com.example.financemanager.Model;

public class Notification {
    private int notificationId;
    private int userId;
    private String message;
    private String date;
    private String status;

    public Notification(int notificationId, int userId, String message, String date, String status) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.date = date;
        this.status = status;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

