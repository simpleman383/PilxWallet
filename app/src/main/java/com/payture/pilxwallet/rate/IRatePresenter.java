package com.payture.pilxwallet.rate;

import com.payture.pilxwallet.IPresenter;

/**
 * Created by simpleman383 on 21.11.17.
 */

public interface IRatePresenter extends IPresenter {
    double getRate(String currency);
    void onFragmentStop();
}
