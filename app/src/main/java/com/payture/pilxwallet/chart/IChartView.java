package com.payture.pilxwallet.chart;

import com.github.mikephil.charting.data.LineDataSet;
import com.payture.pilxwallet.IView;

/**
 * Created by simpleman383 on 29.11.17.
 */

public interface IChartView extends IView {
    void renderChart(LineDataSet data);
}
