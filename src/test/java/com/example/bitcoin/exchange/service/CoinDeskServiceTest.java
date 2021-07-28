package com.example.bitcoin.exchange.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.github.tomakehurst.wiremock.WireMockServer;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CoinDeskServiceTest {

    private static WireMockServer wireMockServer = new WireMockServer();

    @Autowired
    private CoinDeskService coinDeskService;

    @BeforeAll
    public static void configureMockServer() {
        wireMockServer.start();
        stubFor(get(urlEqualTo("https://api.coindesk.com/v1/bpi/currentprice/btc.json"))
                .willReturn(aResponse().withBody(
                        "{\"time\":{\"updated\":\"Jul 28, 2021 10:46:00 UTC\",\"updatedISO\":\"2021-07-28T10:46:00+00:00\",\"updateduk\":\"Jul 28, 2021 at 11:46 BST\"},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\"bpi\":{\"USD\":{\"code\":\"USD\",\"rate\":\"40,185.5117\",\"description\":\"United States Dollar\",\"rate_float\":40185.5117},\"BTC\":{\"code\":\"BTC\",\"rate\":\"1.0000\",\"description\":\"Bitcoin\",\"rate_float\":1}}}")));

        stubFor(get(urlEqualTo("https://api.coindesk.com/v1/bpi/currentprice/eur.json"))
                .willReturn(aResponse().withBody(
                        "{\"time\":{\"updated\":\"Jul 28, 2021 10:45:00 UTC\",\"updatedISO\":\"2021-07-28T10:45:00+00:00\",\"updateduk\":\"Jul 28, 2021 at 11:45 BST\"},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\"bpi\":{\"USD\":{\"code\":\"USD\",\"rate\":\"40,152.6383\",\"description\":\"United States Dollar\",\"rate_float\":40152.6383},\"EUR\":{\"code\":\"EUR\",\"rate\":\"33,983.3862\",\"description\":\"Euro\",\"rate_float\":33983.3862}}}")));
    }

    public void assertThatCurrentPriceIsReturned() {
        configureMockServer();
        BigDecimal expected = new BigDecimal(34001.4812).setScale(4, RoundingMode.HALF_UP);
        BigDecimal result = coinDeskService.getCurrentBitcoinRateInCurrency("EUR");
        assertEquals(expected, result);
    }
}
