package com.payture.pilxwallet.chart;

import android.arch.lifecycle.Lifecycle;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.payture.pilxwallet.R;
import com.payture.pilxwallet.rate.RatePresenter;
import com.payture.pilxwallet.utils.ChartDateFormatter;

/**
 * Created by simpleman383 on 21.11.17.
 */

public class ChartFragment extends Fragment implements IChartView {

    private IChartPresenter presenter;

    private LineChart chart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ChartPresenter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chart = (LineChart) getView().findViewById(R.id.chart);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onViewCreated();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onFragmentStopped();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chart_layout, container, false);
    }

    @Override
    public void renderChart(LineDataSet data) {

        if ( chart != null && data != null) {
            stylizeChart(data);
            LineData lineData = new LineData(data);
            chart.setData(lineData);
            chart.invalidate();
        }
    }

    private void stylizeChart(LineDataSet dataSet){

        if (chart != null) {

            // x-axis
            XAxis xAxis = chart.getXAxis();
            xAxis.setLabelCount(4, true);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(10f);
            xAxis.setTextColor(Color.BLACK);
            xAxis.setDrawAxisLine(true);
            xAxis.setDrawGridLines(false);
            xAxis.setValueFormatter(new ChartDateFormatter());

            // y-axis
            YAxis leftAxis = chart.getAxisLeft();
            YAxis rightAxis = chart.getAxisRight();
            leftAxis.setDrawAxisLine(false);
            rightAxis.setDrawAxisLine(false);
            leftAxis.setTextSize(10f);
            leftAxis.setTextColor(Color.BLACK);
            leftAxis.setDrawGridLines(true);
            rightAxis.setDrawGridLines(true);

            // common
            dataSet.setColor(R.color.colorPrimary);
            dataSet.setHighlightEnabled(false);
            dataSet.setDrawCircles(false);
            dataSet.setDrawFilled(true);
            //dataSet.setFillDrawable(getContext().getDrawable(R.drawable.chart_gradient));
            chart.animateX(1500, Easing.EasingOption.EaseInOutCubic);
            chart.getLegend().setEnabled(false);
            chart.getDescription().setEnabled(false);
            chart.setScaleEnabled(true);
        }
    }


}
