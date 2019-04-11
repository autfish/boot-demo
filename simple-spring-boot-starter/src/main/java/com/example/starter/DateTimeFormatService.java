package com.example.starter;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeFormatService {
    private  DateTimeFormatter dateTimeFormatter;

    public DateTimeFormatService(String format) {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(format);
    }

    public String format(Date date) {
        return dateTimeFormatter.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }
}
