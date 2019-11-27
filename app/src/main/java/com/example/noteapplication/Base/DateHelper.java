package com.example.noteapplication.Base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    // get current date
    public static String getCurrentDate(){

        DateFormat dateFormat= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date= new Date();

        return dateFormat.format(date);

    }
}
