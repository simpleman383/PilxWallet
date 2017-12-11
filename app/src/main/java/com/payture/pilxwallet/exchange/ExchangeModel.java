package com.payture.pilxwallet.exchange;

import com.payture.pilxwallet.rate.RateModel;

/**
 * Created by simpleman383 on 02.12.17.
 */

public class ExchangeModel {

    private String type;
    private double amount;
    private double cost;
    private String currency;

    public void resetModel(){
        type = null;
        amount = 0.0;
        cost = 0.0;
        currency = RateModel.getInstance().getCurrencies()[0];
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public boolean changeAmount(double amount) {
        this.amount = amount;
        this.cost = calculateCost(amount);
        return true;
    }

    public double getCost() {
        return cost;
    }

    public boolean changeCost(double cost) {
        this.cost = cost;
        this.amount = calculateAmount(cost);
        return true;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
        amount = calculateAmount(cost);
    }

    public String[] getCurrencies(){
        return RateModel.getInstance().getCurrencies();
    }

    private double calculateAmount(double cost){
        double amount = RateModel.getInstance().getRate(currency) == 0 ? 0 : cost / RateModel.getInstance().getRate(currency);
        return amount;
    }

    private double calculateCost(double amount){
        double cost = amount * RateModel.getInstance().getRate(currency);
        return cost;
    }

}
