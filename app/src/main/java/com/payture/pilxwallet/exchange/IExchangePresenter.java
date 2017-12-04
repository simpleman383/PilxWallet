package com.payture.pilxwallet.exchange;

/**
 * Created by simpleman383 on 02.12.17.
 */

public interface IExchangePresenter {
    void onSelectedTabChanged(ExchangeFragment.Tab currentTab);
    boolean onCostChanged(String enteredCost);
    boolean onAmountChanged(String enteredAmount);
    void onCurrencyChanged(String newCurrency);
    void getCurrenciesAvailable();
    void onFragmentStop();
    void onActionClick();
}
