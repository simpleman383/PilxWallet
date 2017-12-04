package com.payture.pilxwallet.exchange;

import com.payture.pilxwallet.data.DTO.ExchangeDTO;

/**
 * Created by simpleman383 on 02.12.17.
 */

public interface IExchangeView {
    void updateAmount(double amount);
    void updateCost(double cost);
    void clearPreviousTab();
    void updateSpinnerData(String[] data);
    void startExchange(ExchangeDTO params);
}
