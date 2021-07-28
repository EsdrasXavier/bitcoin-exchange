package com.example.bitcoin.exchange.utils;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

public class NumberUtils {

    public static BigDecimal parseStringToBigDecimal(String value) {
        if (StringUtils.isBlank(value))
            throw new IllegalArgumentException("The value to be parsed should not be blank!");

        return new BigDecimal(clearBigDecimalString(value));
    }

    private static String clearBigDecimalString(String value) {
        return value.replaceAll(",", "");
    }
}
