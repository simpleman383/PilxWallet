package com.payture.pilxwallet.data.DTO;

/**
 * Created by simpleman383 on 21.11.17.
 */

public class RateDTO {

    private String from;
    private String to;
    private double rate;
    private long timestamp;

    public RateDTO(String cur, double rate){
        this.from = "BTC";
        this.to = cur;
        this.rate = rate;
        this.timestamp = System.currentTimeMillis();
    }

    public String getCurrency() {
        return to;
    }

    public double getRate() {
        return rate;
    }

}
