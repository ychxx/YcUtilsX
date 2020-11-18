package com.yc.ycutilsx.mp;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.yclibx.comment.YcResources;
import com.yc.ycutilsx.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class MPActivity extends YcAppCompatActivity {
    LineChart mLineChart;

    @Override
    protected int getLayoutId() {
        return R.layout.mp_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mLineChart = findViewById(R.id.mp_lineChart);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setDrawGridBackground(false);

        mLineChart.setData(generateLineData());
        mLineChart.setRenderer(new YcLineRenderer(mLineChart));
    }

    List<Double> data = Arrays.asList(30.0, -3.0, 7.0, 66.0, 9.0, 36.0, 22.0, 13.0, 18.0, 4.0, 14.0,-30.0,-21.0);

    protected LineData generateLineData() {
        LineDataSet ds1 = getLineDataSet(mLineChart, data, 0, Color.parseColor("#7bb6eb"), "均值", LineDataSet.Mode.HORIZONTAL_BEZIER, false);
        return new LineData(ds1);
    }

    public static LineDataSet getLineDataSet(LineChart lineChart, List<Double> dataList, int chartDataIndex, int color, String name, LineDataSet.Mode mode, boolean isDottedLine) {
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < dataList.size(); i++) {
            yVals.add(new Entry(i, Float.parseFloat(dataList.get(i) + "")));
        }
        LineDataSet set;
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0 && lineChart.getData().getDataSetByIndex(chartDataIndex) != null) {
            set = (LineDataSet) lineChart.getData().getDataSetByIndex(chartDataIndex);
            set.setLabel(name);
            set.setValues(yVals);
        } else {
            set = new LineDataSet(yVals, name);
            if (isDottedLine)
                set.enableDashedLine(10f, 10f, 0f);
            // 设置平滑曲线
            set.setMode(mode);
//            set.setColors(YcResources.getColor(R.drawable.selector_blue));
            set.setAxisDependency(YAxis.AxisDependency.LEFT);
//            set.setColor(color);//设置线的颜色
            set.setLineWidth(2.5f);//设置线的宽度

            //顶点圆
            set.setCircleRadius(4f);//设置顶点的半径
            set.setCircleColor(color);//设置顶点的颜色

            //顶点上方显示的值
            set.setDrawValues(false);//关闭顶点上方显示y值
            set.setValueTextColor(Color.BLACK);//顶点上方显示的字颜色
//            set.setValueTextSize(10f);//顶点上方显示的字大小

            //指引线
//            set.setHighlightEnabled(false);//关闭指引线宽度
//            set.setHighlightLineWidth(1f); //指引线宽度
            set.setHighLightColor(Color.GRAY); //指引线的颜色。
            //开启填充（关闭后会极大提升性能）
            set.setDrawFilled(true);
            set.setFillDrawable(YcResources.getDrawable(R.drawable.selector_blue));
            set.setFillAlpha(100);
            set.setDrawHorizontalHighlightIndicator(false);
//            set.setFillFormatter(new IFillFormatter() {
//                @Override
//                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
//                    return lineChart.getAxisLeft().getAxisMinimum();
//                }
//            });
        }
        return set;
    }
}
