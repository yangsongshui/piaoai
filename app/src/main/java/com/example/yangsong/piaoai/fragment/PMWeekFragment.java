package com.example.yangsong.piaoai.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseFragment;
import com.example.yangsong.piaoai.bean.PMBean;
import com.example.yangsong.piaoai.presenter.PMdataPresenterImp;
import com.example.yangsong.piaoai.util.DateUtil;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.PMView;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.example.yangsong.piaoai.util.DateUtil.LONG_DATE_FORMAT;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class PMWeekFragment extends BaseFragment implements OnChartValueSelectedListener, PMView {
    private final static String TAG = PMWeekFragment.class.getSimpleName();
    @BindView(R.id.pm_chart)
    CombinedChart mChart;
    @BindView(R.id.pm_msg)
    TextView dyaMsg;
    private Toastor toastor;
    ProgressDialog progressDialog;
    private Map<String, String> map;
    PMdataPresenterImp pMdataPresenterImp;
    List<String> week;
    private Activity activity;
    private String indext;
    List<String> mList;

    public PMWeekFragment(Activity activity, String indext) {
        this.activity = activity;
        this.indext = indext;
    }

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        mList = new ArrayList<>();
        week = new ArrayList<>();
        initChart();
        initWeek();
        toastor = new Toastor(getActivity());
        pMdataPresenterImp = new PMdataPresenterImp(this, getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("数据查询中...");
        Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
        map = new HashMap<>();
        map.put("imei", indext);
        map.put("type", "2");
        //通过格式化输出日期
        String time = DateUtil.getCurrDate(LONG_DATE_FORMAT);
        String time2 = DateUtil.dateToString(DateUtil.nextDay(cal.getTime(), -6), LONG_DATE_FORMAT);
        map.put("endDate", time );
        map.put("beginDate", time2 );
        pMdataPresenterImp.binding(map);
    }

    @Override
    protected int getContentView() {
        return R.layout.ftagment_chart;
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
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.LINE,
        });
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mChart.setTouchEnabled(true);
        //设置是否可以拖拽
        mChart.setDragEnabled(false);
        //设置是否可以缩放
        mChart.setScaleEnabled(false);
        mChart.setDoubleTapToZoomEnabled(false);
        //设置是否能扩大扩小
        mChart.setPinchZoom(false);
        //隐藏Y轴
        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisLeft().setDrawLabels(false);
        mChart.setDoubleTapToZoomEnabled(false);
        //不画网格
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisLeft().setAxisMinimum(0);
        mChart.getAxisLeft().setAxisMaximum(500);
        XAxis xAxis = mChart.getXAxis();

        // xAxis.setAxisMinimum(-0.1f);
        xAxis.setGranularity(0.3f);
        xAxis.setAxisMaximum(6);
        xAxis.setTextColor(Color.rgb(255, 255, 255));
        xAxis.setAxisLineColor(Color.rgb(255, 255, 255));
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴在底部
        YAxis yAxis = mChart.getAxisRight();
        //不画网格

        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(500);
        yAxis.setAxisLineColor(Color.rgb(255, 255, 255));

        LimitLine yLimitLine = new LimitLine(35);
        yLimitLine.setLineWidth(0.3f);
        yLimitLine.setLineColor(Color.rgb(107, 237, 29));
        yAxis.addLimitLine(yLimitLine);

        LimitLine yLimitLine2 = new LimitLine(75);
        yLimitLine2.setLineWidth(0.3f);
        yLimitLine2.setLineColor(Color.rgb(255, 245, 69));
        yAxis.addLimitLine(yLimitLine2);

        LimitLine yLimitLine3 = new LimitLine(115);
        yLimitLine3.setLineWidth(0.3f);
        yLimitLine3.setLineColor(Color.rgb(245, 98, 35));
        yAxis.addLimitLine(yLimitLine3);

        LimitLine yLimitLine4 = new LimitLine(150);
        yLimitLine4.setLineWidth(0.3f);
        yLimitLine4.setLineColor(Color.rgb(245, 40, 9));
        yAxis.addLimitLine(yLimitLine4);

        LimitLine yLimitLine5 = new LimitLine(250);
        yLimitLine5.setLineWidth(0.3f);
        yLimitLine5.setLineColor(Color.rgb(173, 45, 21));
        yAxis.addLimitLine(yLimitLine5);
        LimitLine yLimitLine6 = new LimitLine(350);
        yLimitLine6.setLineWidth(0.3f);
        yLimitLine6.setLineColor(Color.rgb(134, 22, 0));
        yAxis.addLimitLine(yLimitLine6);
        LimitLine yLimitLine7= new LimitLine(500);
        yLimitLine7.setLineWidth(0.3f);
        yLimitLine7.setLineColor(Color.rgb(134, 22, 0));
        yAxis.addLimitLine(yLimitLine7);
        //不画网格
        yAxis.setDrawLabels(false);
        yAxis.setDrawGridLines(false);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return week.get((int) value % week.size());
            }


        });
        mChart.getLegend().setEnabled(false);
        mChart.setOnChartValueSelectedListener(this);

    }

    private LineData gettimeData() {
        ArrayList<Entry> values1 = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (i >= (mList.size())) {
                values1.add(new Entry(i, 0));
            } else
                values1.add(new Entry(i, Integer.parseInt(mList.get(i))));
        }

        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values1);

        } else {
            set1 = new LineDataSet(values1, "");
            set1.setLineWidth(1f);//设置线宽
            set1.setCircleRadius(3f);//设置焦点圆心的大小
            //set1.enableDashedHighlightLine(1f, 0f, 1f);//点击后的高亮线的显示样式
            set1.setHighlightLineWidth(0.5f);//设置点击交点后显示高亮线宽
            set1.setHighlightEnabled(true);//是否禁用点击高亮线
            set1.setDrawHorizontalHighlightIndicator(false);//设置不显示水平高亮线
            set1.setDrawCircles(false);  //设置有圆点
            set1.setDrawValues(false);  //不显示数据
            set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER); //设置为曲线
            set1.setHighLightColor(Color.rgb(255, 255, 255));//设置点击交点后显示交高亮线的颜色
            set1.setColor(Color.rgb(255, 255, 255));    //设置曲线的颜色高亮线的颜色

        }

        return new LineData(set1);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        dyaMsg.setVisibility(View.VISIBLE);
        dyaMsg.setText((int) e.getY() + "");
        dyaMsg.setX((h.getXPx() - 9));

    }

    @Override
    public void onNothingSelected() {

        dyaMsg.setVisibility(View.INVISIBLE);
    }


    @Override
    public void showProgress() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void disimissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void loadDataSuccess(PMBean tData) {
        toastor.showSingletonToast(tData.getResMessage());
        if (tData.getResCode().equals("0")) {
            if (tData.getResBody().getList().size() > 0) {
                mList = tData.getResBody().getList();

            }
            CombinedData data = new CombinedData();
            data.setData(gettimeData());
            mChart.setData(data);
            mChart.invalidate();
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        Log.e(TAG, throwable.getLocalizedMessage());
        toastor.showSingletonToast("服务器连接异常");
    }
    private void initWeek() {
        String string = "";
        Date data = new Date();
        SimpleDateFormat format2 = new SimpleDateFormat("EEEE");
        for (int i = 0; i < 7; i++) {
            string = format2.format(DateUtil.nextDay(data, -i));
            week.add(0, string);

        }
    }
}
