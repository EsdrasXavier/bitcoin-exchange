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
        StringBuilder builder = new StringBuilder();
        builder.append("The price range fot the currency ");
        builder.append(this.getCurrency());
        builder.append(" is:");
        builder.append("\n\tCurrentPrice: ").append(this.getCurrentPrice());
        builder.append("\n\tHighest Price In The Last Thirty Days: ");
        builder.append(this.getHighestPriceInTheLastThirtyDays());
        builder.append("\n\tLowest Price In The Last Thirty Days: ");
        builder.append(this.getLowestPriceInTheLastThirtyDays());
        return builder.toString();
    }

}
