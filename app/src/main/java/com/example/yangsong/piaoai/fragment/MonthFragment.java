package com.example.yangsong.piaoai.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseFragment;
import com.example.yangsong.piaoai.bean.PMBean;
import com.example.yangsong.piaoai.inter.FragmentEvent;
import com.example.yangsong.piaoai.presenter.CodataPresenterImp;
import com.example.yangsong.piaoai.presenter.MethanalPresenterImp;
import com.example.yangsong.piaoai.presenter.PMdataPresenterImp;
import com.example.yangsong.piaoai.presenter.TVOCdataPresenterImp;
import com.example.yangsong.piaoai.util.AppUtil;
import com.example.yangsong.piaoai.util.DateUtil;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.PMView;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
public class MonthFragment extends BaseFragment implements OnChartValueSelectedListener, PMView {
    private final static String TAG = MonthFragment.class.getSimpleName();
    @BindView(R.id.month_chart)
    CombinedChart mChart;
    @BindView(R.id.month_msg)
    TextView dyaMsg;
    @BindView(R.id.day_msg_tv)
    TextView dayMsgTv;
    @BindView(R.id.day_unit_tv)
    TextView DayUnitTv;
    private Activity activity;

    private Toastor toastor;
    PMdataPresenterImp pMdataPresenterImp;
    CodataPresenterImp codataPresenterImp;
    MethanalPresenterImp methanalPresenterImp;
    TVOCdataPresenterImp tvoCdataPresenterImp;
    ProgressDialog progressDialog;
    List<String> mList;
    private Map<String, String> map;
    List<String> month;
    private int indext = 0;

    public MonthFragment(Activity activity, int indext) {
        this.activity = activity;
        this.indext = indext;
    }

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        //注册EventBus
        EventBus.getDefault().register(this);
        month = new ArrayList<>();
        mList = new ArrayList<>();
        initMonth();
        initChart();
        String deviceID = getActivity().getIntent().getStringExtra("deviceID");
        toastor = new Toastor(getActivity());
        pMdataPresenterImp = new PMdataPresenterImp(this, getActivity());
        codataPresenterImp = new CodataPresenterImp(this, getActivity());
        methanalPresenterImp = new MethanalPresenterImp(this, getActivity());
        tvoCdataPresenterImp = new TVOCdataPresenterImp(this, getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("数据查询中...");
        Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
        String time = DateUtil.getCurrDate(LONG_DATE_FORMAT);
        String time2 = DateUtil.dateToString(DateUtil.nextDay(cal.getTime(), -29), LONG_DATE_FORMAT);
        map = new HashMap<>();
        map.put("imei", deviceID);
        map.put("type", "3");
        map.put("endDate", time + " 24:00");
        map.put("beginDate", time2 + " 00:00");
        if (indext == 0) {
            //查询pm2.5
            DayUnitTv.setText("μg/m³");
            DayUnitTv.setVisibility(View.VISIBLE);
            pMdataPresenterImp.binding(map);
        } else if (indext == 1) {
            //查询co2
            DayUnitTv.setText("PPM");
            codataPresenterImp.binding(map);
        } else if (indext == 2) {
            //查询TVOC
            DayUnitTv.setVisibility(View.VISIBLE);
            DayUnitTv.setVisibility(View.INVISIBLE);
            tvoCdataPresenterImp.binding(map);
        } else if (indext == 3) {
            //查询甲醛
            DayUnitTv.setText("mg/m³");
            methanalPresenterImp.binding(map);
        } else if (indext == 4) {
            //温度
            DayUnitTv.setVisibility(View.VISIBLE);
            DayUnitTv.setText("℃");
        } else if (indext == 5) {
            // 湿度
            DayUnitTv.setVisibility(View.VISIBLE);
            DayUnitTv.setText("%RH");
        }
        initYLabel(indext);
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
        mChart.setPinchZoom(false);
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
        xAxis.setAxisMinimum(-0.99f);
        xAxis.setGranularity(0.3f);
        xAxis.setAxisMaximum(29);
        xAxis.setLabelCount(6, true);
        xAxis.setTextColor(R.color.spindle);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴在底部
        //不画网格
        xAxis.setDrawGridLines(false);
        mChart.getLegend().setEnabled(false);
        mChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {


            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.e("date", month.get((int) value));
                return month.get((int) value % month.size());
            }


        });

        mChart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e("OnTouchListener", motionEvent.getX() + " " + motionEvent.getY() + " ");
                return false;
            }
        });
        mChart.setOnChartValueSelectedListener(this);

    }

    private LineData getLineData() {


        ArrayList<Entry> values1 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i >= mList.size() ) {
                Log.e(TAG, i + "");
                values1.add(new Entry(i, 0));
            } else {

                values1.add(new Entry(i, Integer.parseInt(mList.get(i))));
            }

        }

        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
        } else {
            set1 = new LineDataSet(values1, "");
            set1.setLineWidth(2f);//设置线宽
            set1.setCircleRadius(3f);//设置焦点圆心的大小
            set1.setHighlightLineWidth(0.5f);//设置点击交点后显示高亮线宽
            set1.setHighlightEnabled(true);//是否禁用点击高亮线
            set1.setDrawHorizontalHighlightIndicator(false);//设置不显示水平高亮线
            set1.setDrawCircles(false);  //设置有圆点
            set1.setDrawValues(false);  //不显示数据
            set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER); //设置为曲线
            set1.setHighLightColor(Color.rgb(51, 51, 51));//设置点击交点后显示交高亮线的颜色
            set1.setColor(Color.rgb(51, 153, 255));    //设置曲线的颜色

        }
        return new LineData(set1);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        dyaMsg.setVisibility(View.VISIBLE);
        dyaMsg.setText((int) e.getY() + "");
        dyaMsg.setX((h.getXPx() - dyaMsg.getWidth() / 2));
        if (indext == 0) {
            //查询pm2.5
            AppUtil.PM2_5(getActivity(), dayMsgTv, (int) e.getY());
        } else if (indext == 1) {
            //查询co2
            AppUtil.CO2(getActivity(), dayMsgTv, (int) e.getY());
        } else if (indext == 2) {
            //TVOC
            AppUtil.TVOC(getActivity(), dayMsgTv, (int) e.getY());
        } else if (indext == 3) {
            //甲醛
            AppUtil.jiaquan(getActivity(), dayMsgTv, (int) e.getY());
        } else if (indext == 4) {
            //温度
            AppUtil.wendu(getActivity(), dayMsgTv, (int) e.getY());
        } else if (indext == 5) {
            // 湿度
            AppUtil.shidu(getActivity(), dayMsgTv, (int) e.getY());
        }
    }

    @Override
    public void onNothingSelected() {

        dyaMsg.setVisibility(View.GONE);
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
            data.setData(getLineData());
            mChart.setData(data);
            mChart.invalidate();
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        Log.e(TAG, throwable.getLocalizedMessage());
        toastor.showSingletonToast("服务器连接异常");
    }

    private void initMonth() {
        String string = "";
        Date data = new Date();
        SimpleDateFormat format2 = new SimpleDateFormat("dd");
        for (int i = 0; i < 30; i++) {
            string = format2.format(DateUtil.nextDay(data, -i)) + "号";

            month.add(0, string);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//反注册EventBus

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FragmentEvent event) {
        dyaMsg.setVisibility(View.INVISIBLE);
        int position = event.getMsg();
        Log.e(TAG, position + "");
        indext = position;
        switch (position) {
            case 0:
                DayUnitTv.setText("μg/m³");
                pMdataPresenterImp.binding(map);
                DayUnitTv.setVisibility(View.VISIBLE);
                break;
            case 1:
                DayUnitTv.setText("PPM");
                codataPresenterImp.binding(map);
                DayUnitTv.setVisibility(View.VISIBLE);

                break;
            case 2:
                DayUnitTv.setVisibility(View.INVISIBLE);
                tvoCdataPresenterImp.binding(map);
                break;
            case 3:
                DayUnitTv.setText("mg/m³");
                methanalPresenterImp.binding(map);
                DayUnitTv.setVisibility(View.VISIBLE);
                break;
            case 4:
                //温度
                DayUnitTv.setText("℃");
                DayUnitTv.setVisibility(View.VISIBLE);
                break;
            case 5:
                // 湿度
                DayUnitTv.setText("%RH");
                DayUnitTv.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        initYLabel(position);
    }
    private void initYLabel(int type){
        switch (type) {
            case 0:
                //PM2.5
                mChart.getAxisLeft().setAxisMaximum(1000);
                mChart.getAxisLeft().setAxisMinimum(0);
                break;
            case 1:
                //CO2
                mChart.getAxisLeft().setAxisMaximum(1500);
                mChart.getAxisLeft().setAxisMinimum(0);
                break;
            case 2:
                //tvoc
                mChart.getAxisLeft().setAxisMaximum(1000);
                mChart.getAxisLeft().setAxisMinimum(0);

                break;
            case 3:
                //甲醛
                mChart.getAxisLeft().setAxisMaximum((float)1.6);
                mChart.getAxisLeft().setAxisMinimum(0);
                break;
            case 4:
                //温度
                mChart.getAxisLeft().setAxisMaximum(40);
                mChart.getAxisLeft().setAxisMinimum(-20);

                break;
            case 5:
                // 湿度

                mChart.getAxisLeft().setAxisMaximum(100);
                mChart.getAxisLeft().setAxisMinimum(0);

                break;
            default:
                break;
        }
        mChart.getAxisLeft().setLabelCount(6,true);
        mChart.notifyDataSetChanged();
        mChart.invalidate();
    }
}
