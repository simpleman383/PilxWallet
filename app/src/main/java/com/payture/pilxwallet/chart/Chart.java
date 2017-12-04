package com.payture.pilxwallet.chart;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.payture.pilxwallet.data.ChartRepository;
import com.payture.pilxwallet.data.DTO.ChartDTO;
import com.payture.pilxwallet.rate.RateModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simpleman383 on 30.11.17.
 */

public class Chart {

    private List<Entry> entries;
    private LineDataSet dataSet;

    private ChartRepository repository;

    public Chart(){
        repository = new ChartRepository();
    }

    public void updateData() {
        ChartDTO[] chartData = repository.getData();
        entries = new ArrayList<>();

        if (chartData == null) {
            dataSet = null;
            return;
        }

        for (ChartDTO item : chartData) {
            entries.add(new Entry(item.getDate(), item.getPrice()));
        }

        dataSet = new LineDataSet(entries, null);
    }

    public LineDataSet getDataSet(){
        return dataSet;
    }
}

