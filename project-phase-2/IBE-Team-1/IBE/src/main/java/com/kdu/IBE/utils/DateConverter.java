package com.kdu.IBE.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateConverter {
    /**
     * @param date
     * @return
     */
    public LocalDate convertStringToDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate dateValue = LocalDate.parse(date, formatter);
        return dateValue;
    }

}


