package com.example.bitcoin.exchange.exception;

public class HistoryNotAvailableException extends Exception {

    public HistoryNotAvailableException(String errorMessage) {
        super(errorMessage);
    }

    public HistoryNotAvailableException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
