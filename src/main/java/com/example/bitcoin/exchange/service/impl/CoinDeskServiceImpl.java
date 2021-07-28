package com.example.bitcoin.exchange.service.impl;

import static java.util.Objects.nonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import com.example.bitcoin.exchange.entity.currentprice.CurrentPrice;
import com.example.bitcoin.exchange.service.CoinDeskService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoinDeskServiceImpl implements CoinDeskService {

    private static final String BITCOIN_SHORT = "BTC";

    private static final String DOLLAR_SHORT = "USD";

    private static final String COIN_DESK_BASE_URL = "https://api.coindesk.com/v1/bpi";

    @Override
    public BigDecimal getCurrentBitcoinRateInCurrency(String currency) {
        if (StringUtils.isBlank(currency))
            throw new IllegalArgumentException("The currency should not be blank!");
        currency = currency.toUpperCase();
        return findAndConvertCurrentBitcoinPriceToCurrency(currency);
    }

    private BigDecimal findAndConvertCurrentBitcoinPriceToCurrency(String currency) {
        Optional<CurrentPrice> optionalBitcoinPrice = getCurrentBitcoinRate();

        if (optionalBitcoinPrice.isPresent()) {
            BigDecimal bitcoinUsdPrice = optionalBitcoinPrice.get().getCurrencyPrice(DOLLAR_SHORT);
            BigDecimal currencyPriceInDollar = getCurrencyPriceInDollar(currency);
            return bitcoinUsdPrice.divide(currencyPriceInDollar, RoundingMode.HALF_UP);
        }

        return BigDecimal.ZERO;
    }

    private BigDecimal getCurrencyPriceInDollar(String currency) {
        Optional<CurrentPrice> optionalCurrencyPrice = getCurrencyRate(currency);
        if (optionalCurrencyPrice.isEmpty())
            return BigDecimal.ONE;

        BigDecimal currencyUsdPrice = optionalCurrencyPrice.get().getCurrencyPrice(DOLLAR_SHORT);
        BigDecimal currencyPrice = optionalCurrencyPrice.get().getCurrencyPrice(currency);
        return currencyUsdPrice.divide(currencyPrice, RoundingMode.HALF_UP);
    }

    private Optional<CurrentPrice> getCurrencyRate(String currency) {
        RestTemplate restTemplate = createRestTemplateWithInterceptors();

        ResponseEntity<CurrentPrice> response = restTemplate.getForEntity(generateUrlForCurrentPrice(currency),
                CurrentPrice.class);
        if (HttpStatus.OK.equals(response.getStatusCode()) && nonNull(response.getBody())) {
            return Optional.ofNullable(response.getBody());

        }
        return Optional.empty();
    }

    private Optional<CurrentPrice> getCurrentBitcoinRate() {
        return getCurrencyRate(BITCOIN_SHORT);
    }

    private String generateUrlForCurrentPrice(String currency) {
        StringBuilder builder = new StringBuilder(COIN_DESK_BASE_URL);
        builder.append("/currentprice/");
        builder.append(currency.toLowerCase());
        builder.append(".json");
        return builder.toString();
    }

    private RestTemplate createRestTemplateWithInterceptors() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request, body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        });
        return restTemplate;
    }

}
