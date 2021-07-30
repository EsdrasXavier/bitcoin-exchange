package com.example.bitcoin.exchange.service.impl;

import static java.util.Objects.nonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map.Entry;
import java.util.Optional;

import com.example.bitcoin.exchange.entity.currentprice.CurrentPrice;
import com.example.bitcoin.exchange.entity.historicalprice.HistoricalPrice;
import com.example.bitcoin.exchange.exception.CurrencyNotAvailableException;
import com.example.bitcoin.exchange.exception.HistoryNotAvailableException;
import com.example.bitcoin.exchange.service.CoinDeskService;
import com.example.bitcoin.exchange.utils.DateUtils;
import com.example.bitcoin.exchange.viewdata.BitcoinPriceRange;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CoinDeskServiceImpl implements CoinDeskService {

    private static final String BITCOIN_SHORT = "BTC";

    private static final String DOLLAR_SHORT = "USD";

    private static final String COIN_DESK_BASE_URL = "https://api.coindesk.com/v1/bpi";

    @Override
    public BigDecimal getCurrentBitcoinRateInCurrency(String currency) throws CurrencyNotAvailableException {
        if (StringUtils.isBlank(currency))
            throw new IllegalArgumentException("The currency should not be blank!");
        currency = currency.toUpperCase();
        return findAndConvertCurrentBitcoinPriceToCurrency(currency);
    }

    @Override
    public BitcoinPriceRange getBitcoinPriceRangeForCurrency(String currency, LocalDate beginDate)
            throws CurrencyNotAvailableException, HistoryNotAvailableException {
        HistoricalPrice historicalPrice = findHistoricalPrice(currency, beginDate);
        return populateBitcoinPriceRangeUsingAPIResponse(historicalPrice, currency);
    }

    private BitcoinPriceRange populateBitcoinPriceRangeUsingAPIResponse(HistoricalPrice historicalPrice,
            String currency) throws CurrencyNotAvailableException {
        BitcoinPriceRange result = new BitcoinPriceRange();
        result.setCurrency(currency);
        result.setCurrentPrice(getCurrentBitcoinRateInCurrency(currency));

        Double high = Double.NEGATIVE_INFINITY;
        Double low = Double.POSITIVE_INFINITY;
        for (Entry<LocalDate, Double> entry : historicalPrice.getBpi().entrySet()) {
            if (entry.getValue() < low)
                low = entry.getValue();
            if (entry.getValue() > high)
                high = entry.getValue();
        }

        result.setHighestPriceInTheLastThirtyDays(BigDecimal.valueOf(high));
        result.setLowestPriceInTheLastThirtyDays(BigDecimal.valueOf(low));

        return result;
    }

    private BigDecimal findAndConvertCurrentBitcoinPriceToCurrency(String currency)
            throws CurrencyNotAvailableException {
        CurrentPrice bitcoinPrice = findCurrentCurrencyRate(BITCOIN_SHORT);
        BigDecimal bitcoinUsdPrice = bitcoinPrice.getCurrencyPrice(DOLLAR_SHORT);
        BigDecimal currencyPriceInDollar = getCurrencyPriceInDollar(currency);
        return bitcoinUsdPrice.divide(currencyPriceInDollar, RoundingMode.HALF_UP);
    }

    private BigDecimal getCurrencyPriceInDollar(String currency) throws CurrencyNotAvailableException {
        CurrentPrice currencyPrice = findCurrentCurrencyRate(currency);
        BigDecimal currencyUsdPrice = currencyPrice.getCurrencyPrice(DOLLAR_SHORT);
        BigDecimal currencyCurrentPrice = currencyPrice.getCurrencyPrice(currency);
        return currencyUsdPrice.divide(currencyCurrentPrice, RoundingMode.HALF_UP);
    }

    private HistoricalPrice findHistoricalPrice(String currency, LocalDate beginDate)
            throws CurrencyNotAvailableException, HistoryNotAvailableException {
        try {
            Optional<HistoricalPrice> historicalPrice = getHistoricalPriceRate(currency, beginDate);
            if (historicalPrice.isPresent())
                return historicalPrice.get();
            throw new HistoryNotAvailableException("Could not find any history for the given currency.");
        } catch (HttpClientErrorException clientErrorException) {
            throw new CurrencyNotAvailableException(clientErrorException.getResponseBodyAsString(),
                    clientErrorException);
        }
    }

    private Optional<HistoricalPrice> getHistoricalPriceRate(String currency, LocalDate beginDate) {
        RestTemplate restTemplate = createRestTemplateWithInterceptors();

        ResponseEntity<HistoricalPrice> response = restTemplate
                .getForEntity(generateUrlForHistoricalPrice(currency, beginDate), HistoricalPrice.class);
        if (HttpStatus.OK.equals(response.getStatusCode()) && nonNull(response.getBody())) {
            return Optional.ofNullable(response.getBody());

        }
        return Optional.empty();
    }

    private String generateUrlForHistoricalPrice(String currency, LocalDate beginDate) {
        final LocalDate endDate = beginDate.minusMonths(1);
        StringBuilder builder = new StringBuilder(COIN_DESK_BASE_URL);
        builder.append("/historical/close.json");
        builder.append("?start=").append(DateUtils.formatDateForCoinDeskAPI(endDate));
        builder.append("&end=").append(DateUtils.formatDateForCoinDeskAPI(beginDate));
        builder.append("&currency=").append(currency.toLowerCase());
        return builder.toString();
    }

    private CurrentPrice findCurrentCurrencyRate(String currency) throws CurrencyNotAvailableException {
        try {
            Optional<CurrentPrice> optionalCurrencyPrice = getCurrencyRate(currency);
            if (optionalCurrencyPrice.isPresent())
                return optionalCurrencyPrice.get();
            throw new CurrencyNotAvailableException("Could not find any rate for the given currency.");
        } catch (HttpClientErrorException clientErrorException) {
            throw new CurrencyNotAvailableException(clientErrorException.getResponseBodyAsString(),
                    clientErrorException);
        }
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
