package com.example.bitcoin.exchange.viewdata;

import java.math.BigDecimal;

public class BitcoinPriceRange {

    private String currency;
    private BigDecimal currentPrice;
    private BigDecimal lowestPriceInTheLastThirtyDays;
    private BigDecimal highestPriceInTheLastThirtyDays;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getLowestPriceInTheLastThirtyDays() {
        return lowestPriceInTheLastThirtyDays;
    }

    public void setLowestPriceInTheLastThirtyDays(BigDecimal lowestPriceInTheLastThirtyDays) {
        this.lowestPriceInTheLastThirtyDays = lowestPriceInTheLastThirtyDays;
    }

    public BigDecimal getHighestPriceInTheLastThirtyDays() {
        return highestPriceInTheLastThirtyDays;
    }

    public void setHighestPriceInTheLastThirtyDays(BigDecimal highestPriceInTheLastThirtyDays) {
        this.highestPriceInTheLastThirtyDays = highestPriceInTheLastThirtyDays;
    }

    @Override
    public String toString() {
        return "BitcoinPriceRange [currency=" + currency + ", currentPrice=" + currentPrice
                + ", highestPriceInTheLastThirtyDays=" + highestPriceInTheLastThirtyDays
                + ", lowestPriceInTheLastThirtyDays=" + lowestPriceInTheLastThirtyDays + "]";
    }

}
