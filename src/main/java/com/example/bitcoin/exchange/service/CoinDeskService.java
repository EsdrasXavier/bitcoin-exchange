package com.example.bitcoin.exchange.service;

import java.math.BigDecimal;

public interface CoinDeskService {

    BigDecimal getCurrentBitcoinRateInCurrency(String currency);
}
