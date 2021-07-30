package com.example.bitcoin.exchange.controller;

import java.time.LocalDate;

import com.example.bitcoin.exchange.service.CoinDeskService;
import com.example.bitcoin.exchange.viewdata.BitcoinPriceRange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BitcoinPriceRangeController {

    @Autowired
    private CoinDeskService coinDeskService;

    @GetMapping("/bitcoin/history")
    public BitcoinPriceRange findBitcoinPriceHistory(@RequestParam(name = "currency") String currency,
            @RequestParam(name = "beginDate") LocalDate beginDate) throws Exception {
        return coinDeskService.getBitcoinPriceRangeForCurrency(currency, beginDate);
    }
}
