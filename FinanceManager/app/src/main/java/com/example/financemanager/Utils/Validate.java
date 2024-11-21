package com.example.financemanager.Utils;

public class Validate {

    public static boolean validateName(String name) {
        return name != null && name.matches("^[A-Za-z\\p{L} ]{2,}$");
    }

    public static boolean validatePassword(String password) {
        return password != null && password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,20}$");
    }

    public static boolean validateEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$");
    }

}
