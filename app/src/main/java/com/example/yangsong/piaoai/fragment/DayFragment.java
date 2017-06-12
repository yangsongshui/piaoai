package com.example.yangsong.piaoai.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.app.MyApplication;
import com.example.yangsong.piaoai.bean.BaseFragment;
import com.example.yangsong.piaoai.util.Toastor;
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
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by Administrator on 2016/5/28.
 */
public class DayFragment extends BaseFragment implements OnChartValueSelectedListener {
    private final static String TAG = DayFragment.class.getSimpleName();
    @BindView(R.id.day_chart)
    CombinedChart mChart;
    @BindView(R.id.day_msg)
    TextView dyaMsg;


    private int TYPE = 0;

    private Map<String, String> map = new HashMap<String, String>();
    private Toastor toastor;
    private RequestQueue mQueue;



    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        mQueue = MyApplication.newInstance().getmQueue();
        toastor = new Toastor(getActivity());
        initChart();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_day;
    }

    private void initChart() {

        /**
         * ====================1.初始化-自由配置===========================
         */
        // 是否在折线图上添加边框
        mChart.setDrawGridBackground(false);
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

        XAxis xAxis = mChart.getXAxis();

        xAxis.setAxisMinimum(-0.5f);
        xAxis.setGranularity(0.3f);

        xAxis.setTextColor(R.color.spindle);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴在底部
        //不画网格
        xAxis.setDrawGridLines(false);
        mChart.getLegend().setEnabled(false);

        CombinedData data = new CombinedData();

        data.setData(getdayData());

        //data.setValueTypeface(mTfLight);

        mChart.setOnChartValueSelectedListener(this);
        mChart.setData(data);

        mChart.invalidate();
    }





    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.e(TAG, " " + h.getXPx() + " " + h.getYPx() + " ");
        dyaMsg.setVisibility(View.VISIBLE);
        dyaMsg.setText((int) e.getY() + "");
        dyaMsg.setX((h.getXPx() - dyaMsg.getWidth() / 2));

    }

    @Override
    public void onNothingSelected() {
        Log.e(TAG, "onNothingSelected ");
        dyaMsg.setVisibility(View.GONE);
    }






    private LineData getdayData() {

        mChart.getXAxis().setAxisMaximum(23);
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            String[] day = new String[]{"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00"
                    , "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return day[(int) value % day.length];
            }


        };
        mChart.getXAxis().setValueFormatter(formatter);
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
        values1.add(new Entry(12, 334));
        values1.add(new Entry(13, 434));
        values1.add(new Entry(14, 534));
        values1.add(new Entry(15, 634));
        values1.add(new Entry(16, 734));
        values1.add(new Entry(17, 834));
        values1.add(new Entry(18, 134));
        values1.add(new Entry(19, 434));
        values1.add(new Entry(20, 634));
        values1.add(new Entry(21, 334));
        values1.add(new Entry(22, 834));
        values1.add(new Entry(23, 634));

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

}
