package com.example.financemanager.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {

    public static String format(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
        return formatter.format(date);
    }

    public static String formatFromString(String inputDate, String inputPattern) throws Exception {

        SimpleDateFormat inputFormatter = new SimpleDateFormat(inputPattern);

        Date date = inputFormatter.parse(inputDate);

        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd:MM:yyyy");
        return outputFormatter.format(date);
    }
    public static Date parseDate(String inputDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
        try {
            return formatter.parse(inputDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
