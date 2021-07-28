package com.example.bitcoin.exchange.entity.currentprice;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Currency {

    private String code;
    private String rate;
    private String description;

    @JsonProperty("rate_float")
    private BigDecimal rateFloat;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRateFloat() {
        return rateFloat;
    }

    public void setRateFloat(BigDecimal rateFloat) {
        this.rateFloat = rateFloat;
    }

    @Override
    public String toString() {
        return "Currency [code=" + code + ", description=" + description + ", rate=" + rate + ", rateFloat=" + rateFloat
                + "]";
    }

}
