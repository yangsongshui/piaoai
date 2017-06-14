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
import com.example.yangsong.piaoai.util.DateUtil;
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
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthFragment extends BaseFragment implements OnChartValueSelectedListener {
    @BindView(R.id.month_chart)
    CombinedChart mChart;
    @BindView(R.id.month_msg)
    TextView dyaMsg;

    List<String> date = new ArrayList<>();
    public MonthFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        initX();
        initChart();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_month;
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
        mChart.setScaleEnabled(false);
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

        XAxis xAxis = mChart.getXAxis();

        xAxis.setAxisMinimum(-0.5f);
        xAxis.setGranularity(0.3f);
        xAxis.setAxisMaximum(29);
        xAxis.setTextColor(R.color.spindle);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴在底部
        //不画网格
        xAxis.setDrawGridLines(false);
        mChart.getLegend().setEnabled(false);

        CombinedData data = new CombinedData();

        data.setData(getLineData());
        mChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {


            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return date.get((int) value % date.size());
            }


        });
        //data.setValueTypeface(mTfLight);
        mChart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e("OnTouchListener", motionEvent.getX() + " " + motionEvent.getY() + " ");
                return false;
            }
        });
        mChart.setOnChartValueSelectedListener(this);
        mChart.setData(data);
        mChart.setVisibleXRangeMaximum(7);
        mChart.invalidate();
    }

    private LineData getLineData() {


        ArrayList<Entry> values1 = new ArrayList<>();
        for (int x = 1; x <= date.size(); x++) {
            values1.add(new Entry(x-1, x + 300));

        }

        LineDataSet set1;
        set1 = new LineDataSet(values1, "");

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


    private void initX() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        int YEAY = cal.get(Calendar.YEAR);
        int MONTH = cal.get(Calendar.MONTH) + 1;
        int DAY = DateUtil.getDaysOfMonth(YEAY, MONTH);
        for (int x = 1; x <= DAY; x++) {
            if (x < 10) {
                date.add("0" + x + "号");
            } else {
                date.add(x + "号");
            }
        }
    }
}
