package com.example.bitcoin.exchange.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DateUtilsTest {

    @Test
    public void assertThatDateIsConvertedToStringInTheRigthFormat() {
        final LocalDate date = LocalDate.of(2021, 07, 29);
        assertEquals("2021-07-29", DateUtils.formatDateForCoinDeskAPI(date));
    }

    @Test
    public void assertThatExceptionIsThrownWhenNullObjectIsProvided() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> DateUtils.formatDateForCoinDeskAPI(null));
        assertEquals("The providade date cannot be null!", e.getMessage());
    }
}
