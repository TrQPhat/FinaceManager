package com.example.financemanager.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormatDate {

    public static String DateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }

    public static String DateToStringByPattern(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    public static String StringToStringByPattern(String inputDate, String inputPattern) throws Exception {

        SimpleDateFormat inputFormatter = new SimpleDateFormat(inputPattern);

        Date date = inputFormatter.parse(inputDate);

        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
        return outputFormatter.format(date);
    }
    public static Date StringToDate(String inputDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formatter.parse(inputDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date StringToDateByPattern(String inputDate, String inputPattern) throws Exception {
        SimpleDateFormat inputFormatter = new SimpleDateFormat(inputPattern);
        return inputFormatter.parse(inputDate);
    }

    public static String addDays(String inputDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(FormatDate.StringToDate(inputDate));
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return FormatDate.DateToString(calendar.getTime());
    }

    public static String subtractDays(String inputDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(FormatDate.StringToDate(inputDate));
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        return FormatDate.DateToString(calendar.getTime());
    }
}
