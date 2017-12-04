package com.payture.pilxwallet.exchange;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.payture.pilxwallet.data.DTO.ExchangeDTO;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by simpleman383 on 02.12.17.
 */

public class ExchangePresenter implements IExchangePresenter {

    private IExchangeView exchangeView;
    private ExchangeModel exchangeModel;

    private Timer getCurrenciesTask;

    public ExchangePresenter(IExchangeView view){
        exchangeView = view;
        exchangeModel = new ExchangeModel();
    }

    public void onSelectedTabChanged(ExchangeFragment.Tab currentTab) {
        exchangeModel.resetModel();
        exchangeView.clearPreviousTab();
        exchangeModel.setType(currentTab.name());
    }

    public boolean onCostChanged(String currentCost) {
        double cost = 0.0;
        if ( !currentCost.equals("") ) {
            try {
                cost = Double.parseDouble(currentCost);
            } catch (Exception ex) {
                Log.e("ExchangePresenter", "onCostChanged : " + ex.toString());
                return false;
            }
        }

        boolean successUpdate = false;
        successUpdate = exchangeModel.changeCost(cost);
        exchangeView.updateAmount(exchangeModel.getAmount());
        return successUpdate;
    }

    public boolean onAmountChanged(String currentAmount) {
        double amount = 0.0;
        if ( !currentAmount.equals("") ) {
            try {
                amount = Double.parseDouble(currentAmount);
            } catch (Exception ex) {
                Log.e("ExchangePresenter", "onAmountChanged : " + ex.toString());
                return false;
            }
        }
        boolean successUpdate = false;
        successUpdate = exchangeModel.changeAmount(amount);
        exchangeView.updateCost(exchangeModel.getCost());
        return successUpdate;
    }

    public void onCurrencyChanged(String currentCurrency) {
        exchangeModel.setCurrency(currentCurrency);
        exchangeView.updateAmount(exchangeModel.getAmount());
    }

    public void getCurrenciesAvailable(){
        getCurrenciesTask = new Timer(true);
        final Handler uiHandler = new Handler(Looper.getMainLooper());

        getCurrenciesTask.schedule(new TimerTask() {
            @Override
            public void run() {
                final String[] currencies = exchangeModel.getCurrencies();
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        exchangeView.updateSpinnerData(currencies);
                    }
                });
            }
        }, 0);
    }

    public void onFragmentStop(){
        cancelAsyncTask();
    }

    private void cancelAsyncTask() {
        if ( getCurrenciesTask != null )
        {
            getCurrenciesTask.cancel();
            getCurrenciesTask.purge();
            getCurrenciesTask = null;
        }
    }

    public void onActionClick(){
        String type = exchangeModel.getType();
        String currency = exchangeModel.getCurrency();
        double amount = exchangeModel.getAmount();
        double cost = exchangeModel.getCost();
        exchangeView.startExchange(new ExchangeDTO(type, amount, cost, currency));
    }
}
