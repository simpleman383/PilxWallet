package com.payture.pilxwallet.chart;

import android.os.Handler;
import android.os.Looper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by simpleman383 on 29.11.17.
 */

public class ChartPresenter implements IChartPresenter {

    private IChartView chartView;
    private Chart chartModel;

    private Timer updateTimer;
    private static final long UPDATE_PERIOD = 15 * 60 * 1000; // every 15 min

    public ChartPresenter(IChartView view) {
        chartView = view;
        chartModel = new Chart();
    }

    @Override
    public void onViewCreated() {
        startViewUpdate();
    }

    @Override
    public void onFragmentStopped() {
        stopViewUpdate();
    }

    @Override
    public void onViewDestroyed() {
        return;
    }

    private void startViewUpdate() {
        final Handler uiHandler = new Handler(Looper.getMainLooper());
        updateTimer = new Timer(true);
        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                chartModel.updateData();
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (chartView != null)
                            chartView.renderChart(chartModel.getDataSet());
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
