package com.example.bitcoin.exchange.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.bitcoin.exchange.exception.CurrencyNotAvailableException;
import com.example.bitcoin.exchange.exception.HistoryNotAvailableException;
import com.example.bitcoin.exchange.viewdata.BitcoinPriceRange;

public interface CoinDeskService {

    BigDecimal getCurrentBitcoinRateInCurrency(String currency) throws CurrencyNotAvailableException;

    BitcoinPriceRange getBitcoinPriceRangeForCurrency(String currency, LocalDate beginDate)
            throws CurrencyNotAvailableException, HistoryNotAvailableException;
}
