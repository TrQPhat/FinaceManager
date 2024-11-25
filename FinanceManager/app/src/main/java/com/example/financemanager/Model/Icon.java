package com.example.financemanager.Model;

public class Icon {
    private int id;        // ID của icon
    private String name;   // Tên của icon
    private String path;   // Đường dẫn đến file icon

    // Constructor
    public Icon(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    // Getter và Setter
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}

