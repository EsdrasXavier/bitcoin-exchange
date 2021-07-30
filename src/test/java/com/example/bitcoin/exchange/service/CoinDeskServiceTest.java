package com.example.bitcoin.exchange.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import com.example.bitcoin.exchange.exception.CurrencyNotAvailableException;
import com.example.bitcoin.exchange.viewdata.BitcoinPriceRange;
import com.github.tomakehurst.wiremock.WireMockServer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

@SpringBootTest
@ActiveProfiles("test")
public class CoinDeskServiceTest {

    private static WireMockServer wireMockServer = new WireMockServer();

    @Autowired
    private CoinDeskService coinDeskService;

    @BeforeTestClass
    public static void configureMockServer() {
        stubFor(get(urlPathMatching("https://api.coindesk.com/v1/bpi/currentprice/btc.json"))
                .willReturn(aResponse().withBody(
                        "{\"time\":{\"updated\":\"Jul 28, 2021 10:46:00 UTC\",\"updatedISO\":\"2021-07-28T10:46:00+00:00\",\"updateduk\":\"Jul 28, 2021 at 11:46 BST\"},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\"bpi\":{\"USD\":{\"code\":\"USD\",\"rate\":\"40,185.5117\",\"description\":\"United States Dollar\",\"rate_float\":40185.5117},\"BTC\":{\"code\":\"BTC\",\"rate\":\"1.0000\",\"description\":\"Bitcoin\",\"rate_float\":1}}}")));

        stubFor(get(urlPathMatching("https://api.coindesk.com/v1/bpi/currentprice/eur.json"))
                .willReturn(aResponse().withBody(
                        "{\"time\":{\"updated\":\"Jul 28, 2021 10:45:00 UTC\",\"updatedISO\":\"2021-07-28T10:45:00+00:00\",\"updateduk\":\"Jul 28, 2021 at 11:45 BST\"},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\"bpi\":{\"USD\":{\"code\":\"USD\",\"rate\":\"40,152.6383\",\"description\":\"United States Dollar\",\"rate_float\":40152.6383},\"EUR\":{\"code\":\"EUR\",\"rate\":\"33,983.3862\",\"description\":\"Euro\",\"rate_float\":33983.3862}}}")));

        stubFor(get(urlPathMatching(
                "https://api.coindesk.com/v1/bpi/historical/close.json?start=2021-06-28&end=2021-07-28&currency=EUR"))
                        .willReturn(aResponse().withBody(
                                "{\"bpi\":{\"2021-06-29\":30187.1247,\"2021-06-30\":29560.3373,\"2021-07-01\":28291.8005,\"2021-07-02\":28490.8606,\"2021-07-03\":29232.6893,\"2021-07-04\":29732.4682,\"2021-07-05\":28402.522,\"2021-07-06\":28958.3425,\"2021-07-07\":28724.4858,\"2021-07-08\":27749.4105,\"2021-07-09\":28462.9115,\"2021-07-10\":28217.1493,\"2021-07-11\":28843.0326,\"2021-07-12\":27897.5012,\"2021-07-13\":27787.3857,\"2021-07-14\":27729.3279,\"2021-07-15\":26977.5477,\"2021-07-16\":26598.3552,\"2021-07-17\":26715.7633,\"2021-07-18\":26928.1826,\"2021-07-19\":26138.5656,\"2021-07-20\":25295.6699,\"2021-07-21\":27249.6477,\"2021-07-22\":27444.805,\"2021-07-23\":28582.9508,\"2021-07-24\":29129.4148,\"2021-07-25\":30083.4205,\"2021-07-26\":31576.5839,\"2021-07-27\":33414.3916,\"2021-07-28\":33789.7765,\"2021-07-29\":33681.2694},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index. BPI value data returned as EUR.\",\"time\":{\"updated\":\"Jul 30, 2021 00:03:00 UTC\",\"updatedISO\":\"2021-07-30T00:03:00+00:00\"}}")));

        wireMockServer.start();
    }

    @AfterTestClass
    public static void teardown() throws Exception {
        wireMockServer.stop();
    }


    // @Test
    public void assertThatCurrentPriceIsReturned() throws CurrencyNotAvailableException {
        BigDecimal expected = new BigDecimal(33713.5569).setScale(4, RoundingMode.HALF_UP);
        BigDecimal result = coinDeskService.getCurrentBitcoinRateInCurrency("EUR");
        assertEquals(expected, result);
    }

    @Test
    public void assertThatHistoricalPriceCanBeFound() throws Exception {
        BigDecimal highestPriceInTheLastThirtyDays = new BigDecimal(33789.7765).setScale(4, RoundingMode.HALF_UP);
        BigDecimal lowestPriceInTheLastThirtyDays = new BigDecimal(25295.6699).setScale(4, RoundingMode.HALF_UP);
        final LocalDate beginDate = LocalDate.of(2021, 07, 29);
        BitcoinPriceRange result = coinDeskService.getBitcoinPriceRangeForCurrency("EUR", beginDate);

        assertEquals("EUR", result.getCurrency());
        assertEquals(highestPriceInTheLastThirtyDays, result.getHighestPriceInTheLastThirtyDays());
        assertEquals(lowestPriceInTheLastThirtyDays, result.getLowestPriceInTheLastThirtyDays());
    }
}
