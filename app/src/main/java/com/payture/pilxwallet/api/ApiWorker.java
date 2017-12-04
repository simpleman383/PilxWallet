package com.payture.pilxwallet.api;

import android.content.Context;

import com.payture.pilxwallet.chart.Chart;

/**
 * Created by simpleman383 on 30.11.17.
 */

public class ApiWorker {

    private static ApiWorker instance;
    private Context context;

    private ApiWorker(Context context){
        this.context = context;
    }

    public static ApiWorker getInstance(Context context) {
        if (instance == null)
            instance = new ApiWorker(context);
        return instance;
    }

    private static ChartApi chartApi;

    public void initialize() {
        chartApi = new ChartApi(context);
        // some other api
    }

    public static ChartApi getChartApi() {
        return chartApi;
    }

}
