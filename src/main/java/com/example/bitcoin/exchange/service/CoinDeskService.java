package com.example.bitcoin.exchange.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.bitcoin.exchange.viewdata.BitcoinPriceRange;

public interface CoinDeskService {

    BigDecimal getCurrentBitcoinRateInCurrency(String currency);

    BitcoinPriceRange getBitcoinPriceRangeForCurrency(String currency, LocalDate beginDate) throws Exception;
}
