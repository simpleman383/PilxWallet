package com.payture.pilxwallet.rate;

/**
 * Created by simpleman383 on 21.11.17.
 */

public interface IRatePresenter {
    double getRate(String currency);
    void onFragmentStop();
    void onViewCreated();
    void onViewDestroyed();
}
