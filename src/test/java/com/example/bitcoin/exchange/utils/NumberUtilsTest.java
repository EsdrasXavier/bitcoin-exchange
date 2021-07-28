package com.example.bitcoin.exchange.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NumberUtilsTest {

    @Test
    public void assertThatSmallStringIsParsedToBigDecimal() {
        BigDecimal expected = new BigDecimal(123.45).setScale(2, RoundingMode.HALF_UP);
        BigDecimal result = NumberUtils.parseStringToBigDecimal("123.45");
        assertEquals(expected, result);
    }

    @Test
    public void assertThatLargeStringIsParsedToBigDecimal() {
        BigDecimal expected = new BigDecimal(123456.78).setScale(2, RoundingMode.HALF_UP);
        BigDecimal result = NumberUtils.parseStringToBigDecimal("123,456.78");
        assertEquals(expected, result);
    }
}
