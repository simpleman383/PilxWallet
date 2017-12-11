package com.payture.pilxwallet.main;

import com.payture.pilxwallet.data.DTO.ExchangeDTO;

import java.io.File;

/**
 * Created by simpleman383 on 21.11.17.
 */

public interface IPresenter {
    void onStartExchange(ExchangeDTO exchangeParams);

    void openDefaultFragments();

    void setWalletDirectory(File dir);
    void initBitcoin();

    void onViewDestroyed();
}
