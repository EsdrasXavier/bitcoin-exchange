package com.example.bitcoin.exchange.exception;

public class CurrencyNotAvailableException extends Exception {

    public CurrencyNotAvailableException(String errorMessage) {
        super(errorMessage);
    }

    public CurrencyNotAvailableException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
