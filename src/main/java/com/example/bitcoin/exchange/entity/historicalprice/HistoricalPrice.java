package com.example.bitcoin.exchange.entity.historicalprice;

import java.time.LocalDate;
import java.util.Map;

import com.example.bitcoin.exchange.entity.currentprice.Time;

public class HistoricalPrice {

    private Map<LocalDate, Double> bpi;
    private String disclaimer;
    private Time time;

    public Map<LocalDate, Double> getBpi() {
        return bpi;
    }

    public void setBpi(Map<LocalDate, Double> bpi) {
        this.bpi = bpi;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

}
