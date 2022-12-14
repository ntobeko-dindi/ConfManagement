package com.ntobeko.confmanagement.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDate {

    public String getLocalDateTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(myFormatObj);
    }
}
