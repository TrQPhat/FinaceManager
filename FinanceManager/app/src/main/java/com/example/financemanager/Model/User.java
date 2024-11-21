package com.example.financemanager.Model;

import com.example.financemanager.Utils.FormatDate;

import java.util.Date;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private Date create_at;
    private Date last_login;

    public User(String username, String email, String password) {
        this.id = 0;
        this.username = username;
        this.email = email;
        this.password = password;
        this.create_at = new Date();
        this.last_login = new Date();
    }

    public User(int id, String username, String email, String password, String create_at, String last_login) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.create_at = FormatDate.parseDate(create_at);
        this.last_login = FormatDate.parseDate(last_login);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreate_at() {
        return FormatDate.format(create_at);
    }

    public void setCreate_at() {
        this.create_at = new Date();
    }

    public String getLast_login() {
        return FormatDate.format(last_login);
    }

    public void updateLast_login() {
        this.last_login = new Date();
    }
}
