package com.example.bitcoin.exchange.entity.currentprice;

import java.math.BigDecimal;

public class Currency {

    private String code;
    private String rate;
    private String description;
    private BigDecimal rate_float;

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

    public BigDecimal getRate_float() {
        return rate_float;
    }

    public void setRate_float(BigDecimal rate_float) {
        this.rate_float = rate_float;
    }

    @Override
    public String toString() {
        return "Currency [code=" + code + ", description=" + description + ", rate=" + rate + ", rate_float="
                + rate_float + "]";
    }

}
