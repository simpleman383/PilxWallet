package com.payture.pilxwallet.main;

import com.payture.pilxwallet.data.DTO.ExchangeDTO;

import java.io.File;

/**
 * Created by simpl on 12/4/2017.
 */

public class ApplicationPresenter implements IPresenter, IWalletEventListener {

    private ApplicationModel appModel;
    private IView view;

    public ApplicationPresenter(IView v){
        view = v;
        appModel = ApplicationModel.getInstance();
        appModel.addWalletEventListener(this);
    }

    @Override
    public void onStartExchange(ExchangeDTO exchangeParams) {
        appModel.setExchangeParams(exchangeParams);
        //view.openChartfragment();
    }

    @Override
    public void onViewDestroyed() {

    }

    @Override
    public void openDefaultFragments() {
        view.openBalanceFragment();
        view.openRateFragment();
        view.openChartFragment();
    }


    @Override
    public void setWalletDirectory(File directory) {
        appModel.setWalletDirectory(directory);
    }

    @Override
    public void initBitcoin() {
        appModel.initBitcoinWallet();
    }

    @Override
    public void onSyncProgress(double progress) {
        view.updateBitcoinSyncProgress((int)progress);
    }

    public void onWalletSetup(){
        String balance = appModel.getBitcoinWallet().getBalance().toFriendlyString();
        String address = appModel.getBitcoinWallet().currentReceiveAddress().toString();
        view.updateBitcoinWalletInfo(address, balance);
    }
}
