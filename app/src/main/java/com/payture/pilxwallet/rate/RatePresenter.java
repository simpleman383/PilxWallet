package com.payture.pilxwallet.rate;

import android.os.Handler;
import android.os.Looper;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by simpleman383 on 21.11.17.
 */

public class RatePresenter implements IRatePresenter {

    private IRateView rateView;
    private RateModel rateModel;

    private Timer updateTimer;
    private static final long UPDATE_PERIOD = 10 * 1000;


    public RatePresenter(IRateView view) {
        rateView = view;
        rateModel = RateModel.getInstance();
    }

    @Override
    public double getRate(String currency) {
        return rateModel.getRate(currency);
    }

    @Override
    public void onViewCreated() {
        startViewUpdate();
    }

    @Override
    public void onViewDestroyed() {
        return;
    }

    @Override
    public void onFragmentStop() {
        stopViewUpdate();
    }

    private void startViewUpdate() {
        final Handler uiHandler = new Handler(Looper.getMainLooper());
        updateTimer = new Timer(true);
        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                rateModel.updateModel();
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        rateView.updateRates();
                    }
                });
            }
        }, 0, UPDATE_PERIOD);
    }

    private void stopViewUpdate() {
        if ( updateTimer != null )
        {
            updateTimer.cancel();
            updateTimer.purge();
            updateTimer = null;
        }
    }
}
