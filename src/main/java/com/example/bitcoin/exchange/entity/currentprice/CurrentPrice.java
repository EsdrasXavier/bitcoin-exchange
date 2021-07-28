package com.example.bitcoin.exchange.entity.currentprice;

import static java.util.Objects.isNull;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.example.bitcoin.exchange.utils.NumberUtils;

public class CurrentPrice {
    private String disclaimer;
    private Map<String, Currency> bpi;
    private Time time;

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public Map<String, Currency> getBpi() {
        if (this.bpi == null)
            this.bpi = new HashMap<>();
        return bpi;
    }

    public BigDecimal getCurrencyPrice(String key) {
        Currency currency = this.getBpi().get(key);
        if (isNull(currency))
            throw new IllegalArgumentException("Could not find currency rate for currency: " + key + ".");


        return NumberUtils.parseStringToBigDecimal(currency.getRate());
    }

    public void setBpi(Map<String, Currency> bpi) {
        this.bpi = bpi;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CurrentPrice [bpi=" + bpi + ", disclaimer=" + disclaimer + ", time=" + time + "]";
    }

}
