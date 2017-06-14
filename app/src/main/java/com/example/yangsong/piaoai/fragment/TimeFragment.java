package com.example.yangsong.piaoai.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseFragment;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeFragment extends BaseFragment implements OnChartValueSelectedListener {
    private final static String TAG = TimeFragment.class.getSimpleName();
    @BindView(R.id.time_chart)
    CombinedChart mChart;
    @BindView(R.id.time_msg)
    TextView dyaMsg;


    public TimeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        initChart();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_time;
    }

    private void initChart() {

        /**
         * ====================1.初始化-自由配置===========================
         */
        // 是否在折线图上添加边框
        mChart.setDrawGridBackground(false);
        mChart.setVisibleXRangeMaximum(7);
        mChart.setDrawBorders(false);
        // 设置右下角描述
        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE,
        });
        //设置透明度
        // mChart.setAlpha(0.8f);
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mChart.setTouchEnabled(true);
        //设置是否可以拖拽
        mChart.setDragEnabled(true);
        //设置是否可以缩放
        mChart.setScaleYEnabled(false);
        mChart.setDoubleTapToZoomEnabled(false);
        //设置是否能扩大扩小
        mChart.setPinchZoom(true);
        //设置四个边的间距
        // mChart.setViewPortOffsets(10, 0, 0, 10);
        //隐藏Y轴
        mChart.getAxisRight().setEnabled(false);

        //不画网格
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisLeft().setAxisMaximum(1000);
        mChart.getAxisLeft().setAxisMinimum(0);
        mChart.getAxisLeft().setTextColor(R.color.silver_sand);
        mChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

               // dyaMsg.setVisibility(View.GONE);
            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {
                Log.e(TAG,"onChartSingleTapped");
            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });
        XAxis xAxis = mChart.getXAxis();

        xAxis.setAxisMinimum(-0.5f);
        xAxis.setGranularity(0.3f);

        xAxis.setTextColor(R.color.spindle);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴在底部
        //不画网格
        xAxis.setDrawGridLines(false);
        mChart.getLegend().setEnabled(false);

        CombinedData data = new CombinedData();

        data.setData(gettimeData());
        mChart.setOnChartValueSelectedListener(this);
        mChart.setData(data);
        mChart.setVisibleXRangeMaximum(7);
        mChart.invalidate();
    }

    private LineData gettimeData() {
        mChart.getXAxis().setAxisMaximum(11);
        mChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            String[] tiem = new String[]{"0", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "60"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return tiem[(int) value % tiem.length];
            }


        });

        ArrayList<Entry> values1 = new ArrayList<>();
        values1.add(new Entry(0, 300));
        values1.add(new Entry(1, 400));
        values1.add(new Entry(2, 512));
        values1.add(new Entry(3, 700));
        values1.add(new Entry(4, 124));
        values1.add(new Entry(5, 567));
        values1.add(new Entry(6, 234));
        values1.add(new Entry(7, 323));
        values1.add(new Entry(8, 232));
        values1.add(new Entry(9, 283));
        values1.add(new Entry(10, 456));
        values1.add(new Entry(11, 234));


        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values1, "");
        }
        set1.setLineWidth(2f);//设置线宽
        set1.setCircleRadius(3f);//设置焦点圆心的大小
        //set1.enableDashedHighlightLine(1f, 0f, 1f);//点击后的高亮线的显示样式
        set1.setHighlightLineWidth(0.5f);//设置点击交点后显示高亮线宽
        set1.setHighlightEnabled(true);//是否禁用点击高亮线
        set1.setDrawHorizontalHighlightIndicator(false);//设置不显示水平高亮线
        set1.setDrawCircles(false);  //设置有圆点
        set1.setDrawValues(false);  //不显示数据
        set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER); //设置为曲线
        set1.setHighLightColor(Color.rgb(51, 51, 51));//设置点击交点后显示交高亮线的颜色
        set1.setColor(Color.rgb(51, 153, 255));    //设置曲线的颜色
        return new LineData(set1);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        dyaMsg.setVisibility(View.VISIBLE);
        dyaMsg.setText((int) e.getY() + "");
        dyaMsg.setX((h.getXPx() - dyaMsg.getWidth() / 2));

    }

    @Override
    public void onNothingSelected() {

        dyaMsg.setVisibility(View.GONE);
    }
}
