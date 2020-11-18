package com.yc.ycutilsx.mp;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.yclibx.comment.YcResources;
import com.yc.ycutilsx.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class MPBarActivity extends YcAppCompatActivity {
    BarChart mBarChart;

    @Override
    protected int getLayoutId() {
        return R.layout.mp_bar_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mBarChart = findViewById(R.id.mpBarBc);
        mBarChart.getDescription().setEnabled(false);
        mBarChart.setDrawGridBackground(false);

        mBarChart.setData(generateBarData());

        mBarChart.setRenderer(new YcBarRenderer(mBarChart,mBarChart.getAnimator(),mBarChart.getViewPortHandler()));
    }

    List<Double> data = Arrays.asList(30.0, -3.0, 7.0, 66.0, 9.0, 36.0, 22.0, 13.0, 18.0, 4.0, 14.0, -30.0, -21.0,1.0,3.0,4.0);
    List<Double> data2 = Arrays.asList(40.0, -13.0, 17.0, 26.0, 19.0, 26.0, 14.0, 4.0, 58.0, 4.0, 14.0, -30.0, -44.0,3.0,1.0,10.0);

    protected BarData generateBarData() {
        BarDataSet ds1 = getBarDataSet(mBarChart, data, 0, YcResources.getColor(R.color.colorPrimary));
        BarDataSet ds2 = getBarDataSet(mBarChart, data2, 1, YcResources.getColor(R.color.colorBlue500));
        return new BarData(ds1, ds2);
    }

    public static BarDataSet getBarDataSet(BarChart barChart, List<Double> dataList, int chartDataIndex, int color) {
        ArrayList<BarEntry> yVals = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            yVals.add(new BarEntry(i, Float.parseFloat(dataList.get(i) + "")));
        }
        BarDataSet set;
        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0 && barChart.getData().getDataSetByIndex(chartDataIndex) != null) {
            set = (BarDataSet) barChart.getData().getDataSetByIndex(chartDataIndex);
            set.setLabel("labelName-" + chartDataIndex);
            set.setValues(yVals);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(yVals, "labelName-" + chartDataIndex);
            set.setDrawIcons(false);
            set.setColors(color);
            set.setStackLabels(new String[]{"Births", "Divorces", "Marriages"});

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);

            BarData data = new BarData(dataSets);
            data.setValueFormatter(new StackedValueFormatter(false, "", 1));
            data.setValueTextColor(Color.WHITE);
            barChart.setData(data);
        }
        return set;
    }
}
