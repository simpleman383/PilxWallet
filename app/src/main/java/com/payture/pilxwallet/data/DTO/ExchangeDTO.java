package com.payture.pilxwallet.data.DTO;

/**
 * Created by simpl on 12/4/2017.
 */

public class ExchangeDTO {
    private String type;
    private double amount;

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getCost() {
        return cost;
    }

    public String getCurrency() {
        return currency;
    }

    private double cost;
    private String currency;

    public ExchangeDTO(String type, double amount, double cost, String currency){
        this.type = type;
        this.amount = amount;
        this.cost = cost;
        this.currency = currency;
    }
}
