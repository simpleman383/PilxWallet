package com.payture.pilxwallet.main;

import com.payture.pilxwallet.data.DTO.ExchangeDTO;

/**
 * Created by simpleman383 on 21.11.17.
 */

public interface IView {
    void startExchange(ExchangeDTO params);
    void updateBitcoinSyncProgress(int percentage);
    void updateBitcoinWalletInfo(String receiveAddress, String balance);


    void openBalanceFragment();
    void openRateFragment();
    void openCardFragment();
    void openChartFragment();
    void openExchangeFragment();
}
