package com.payture.pilxwallet.main;

/**
 * Created by simpl on 12/7/2017.
 */

public interface IWalletEventListener {
    //void onWalletSetupCompleted();
    void onSyncProgress(double progress);
    void onWalletSetup();
}
