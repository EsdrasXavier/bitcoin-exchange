package com.example.bitcoin.exchange.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.bitcoin.exchange.service.impl.CommandLineServiceImpl.CurrencyAsker;

import org.junit.jupiter.api.Test;

public class CommandLineServiceTest {
    @Test
    public void shouldProcessUserInput() {
        CurrencyAsker asker = mock(CurrencyAsker.class);
        when(asker.ask("Test")).thenReturn("This should be returned");
        assertEquals("This should be returned", asker.ask("Test"));
    }
}
