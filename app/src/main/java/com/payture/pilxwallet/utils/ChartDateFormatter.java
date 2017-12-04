package com.payture.pilxwallet.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by simpleman383 on 02.12.17.
 */

public class ChartDateFormatter implements IAxisValueFormatter {

    private DateFormat dateFormat;

    public ChartDateFormatter(){
        dateFormat = new SimpleDateFormat("dd/MM/yy");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Date date = new Date((long)value * 1000);
        return dateFormat.format(date);
    }
}
