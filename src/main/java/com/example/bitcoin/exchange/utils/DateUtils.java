package com.example.bitcoin.exchange.utils;

import static java.util.Objects.isNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static String formatDateForCoinDeskAPI(LocalDate date) {
        if (isNull(date))
            throw new IllegalArgumentException("The providade date cannot be null!");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
