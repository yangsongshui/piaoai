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
import com.example.yangsong.piaoai.bean.TVOC;
import com.example.yangsong.piaoai.inter.FragmentEvent;
import com.example.yangsong.piaoai.presenter.CodataPresenterImp;
import com.example.yangsong.piaoai.presenter.FSPresenterImp;
import com.example.yangsong.piaoai.presenter.FXPresenterImp;
import com.example.yangsong.piaoai.presenter.MethanalPresenterImp;
import com.example.yangsong.piaoai.presenter.PM1dataPresenterImp;
import com.example.yangsong.piaoai.presenter.PMPresenterImp;
import com.example.yangsong.piaoai.presenter.PMYCPresenterImp;
import com.example.yangsong.piaoai.presenter.PMdataPresenterImp;
import com.example.yangsong.piaoai.presenter.QYPresenterImp;
import com.example.yangsong.piaoai.presenter.SDPresenterImp;
import com.example.yangsong.piaoai.presenter.TVOCdataPresenterImp;
import com.example.yangsong.piaoai.presenter.WDPresenterImp;
import com.example.yangsong.piaoai.presenter.ZSPresenterImp;
import com.example.yangsong.piaoai.util.DateUtil;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.PMView;
import com.example.yangsong.piaoai.view.TVOCView;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

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
public class MonthFragment extends BaseFragment implements TVOCView {
    private final static String TAG = MonthFragment.class.getSimpleName();
    @BindView(R.id.month_chart)
    CombinedChart mChart;
    @BindView(R.id.day_unit_tv)
    TextView DayUnitTv;
    private Activity activity;

    private Toastor toastor;
    PMdataPresenterImp pMdataPresenterImp;
    CodataPresenterImp codataPresenterImp;
    MethanalPresenterImp methanalPresenterImp;
    TVOCdataPresenterImp tvoCdataPresenterImp;
    PMPresenterImp pmPresenterImp;
    ProgressDialog progressDialog;
    List<String> mList;
    private Map<String, String> map;
    List<String> month;
    private int indext = 0;
    double max = 0;

    FXPresenterImp fxPresenterImp;
    FSPresenterImp fsPresenterImp;
    PM1dataPresenterImp pm1dataPresenterImp;
    PMYCPresenterImp pmycPresenterImp;
    QYPresenterImp qyPresenterImp;
    SDPresenterImp sdPresenterImp;
    WDPresenterImp wdPresenterImp;
    ZSPresenterImp zsPresenterImp;
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
        pMdataPresenterImp = new PMdataPresenterImp(new PMView() {
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
                max = 1.6;
            }

            @Override
            public void loadDataSuccess(PMBean tData) {
                //  toastor.showSingletonToast(tData.getResMessage());
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
        }, getActivity());
        codataPresenterImp = new CodataPresenterImp(this, getActivity());
        methanalPresenterImp = new MethanalPresenterImp(this, getActivity());
        tvoCdataPresenterImp = new TVOCdataPresenterImp(this, getActivity());
        pmPresenterImp = new PMPresenterImp(this, getActivity());
        fxPresenterImp = new FXPresenterImp(this, getActivity());
        fsPresenterImp = new FSPresenterImp(this, getActivity());
        pm1dataPresenterImp = new PM1dataPresenterImp(this, getActivity());
        pmycPresenterImp = new PMYCPresenterImp(this, getActivity());
        qyPresenterImp = new QYPresenterImp(this, getActivity());
        sdPresenterImp = new SDPresenterImp(this, getActivity());
        wdPresenterImp = new WDPresenterImp(this, getActivity());
        zsPresenterImp = new ZSPresenterImp(this, getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.dialog_msg6));
        Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
        String time = DateUtil.getCurrDate(LONG_DATE_FORMAT);
        String time2 = DateUtil.dateToString(DateUtil.nextDay(cal.getTime(), -29), LONG_DATE_FORMAT);
        map = new HashMap<>();
        map.put("imei", deviceID);
        map.put("type", "3");
        map.put("endDate", time);
        map.put("beginDate", time2);
        getData(indext);
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
        mChart.setDrawBorders(false);
        // 设置右下角描述
        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.LINE,
        });
        //设置透明度
        // mChart.setAlpha(0.8f);
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mChart.setTouchEnabled(false);
        //设置是否可以拖拽
        mChart.setDragEnabled(false);
        //设置是否可以缩放
        mChart.setScaleEnabled(false);
        mChart.setDoubleTapToZoomEnabled(false);
        //设置是否能扩大扩小
        mChart.setPinchZoom(false);
        //隐藏Y轴
        mChart.getAxisRight().setEnabled(false);
        //不画网格
        initY();
        mChart.getAxisLeft().setTextColor(Color.WHITE);
        mChart.getAxisLeft().setAxisLineColor(Color.WHITE);
        mChart.getAxisLeft().setGridColor(Color.WHITE);
        mChart.getAxisLeft().enableGridDashedLine(5f, 3f, 0);
        mChart.getAxisLeft().setAxisLineWidth(1);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        xAxis.setGridColor(Color.WHITE);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.enableGridDashedLine(5f, 3f, 0);
        xAxis.setAxisLineWidth(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴在底部
        xAxis.setAxisMaximum(29);
        xAxis.setLabelCount(6, true);
        mChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {


            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                return month.get((int) value % month.size());
            }


        });

        mChart.getLegend().setEnabled(false);


    }

    private LineData getLineData() {


        ArrayList<Entry> values1 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i >= mList.size()) {
                Log.e(TAG, i + "");
                values1.add(new Entry(i, 0));
            } else {
                if (Double.parseDouble(mList.get(i)) <= max)
                    values1.add(new Entry(i,  Float.parseFloat(mList.get(i))));
                else
                    values1.add(new Entry(i, (float) max));
            }

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
            set1.setDrawCircles(false);  //设置有圆点
            set1.setDrawValues(false);  //不显示数据
            set1.setDrawFilled(true);  //设置包括的范围区域填充颜色
            set1.setFillColor(Color.argb(96, 57, 144, 233));
            set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER); //设置为曲线
            set1.setColor(Color.rgb(255, 255, 255));    //设置曲线的颜色
        }
        return new LineData(set1);
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
    public void loadDataSuccess(TVOC tData) {
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
        toastor.showSingletonToast(getString(R.string.dialog_msg5));
    }

    private void initMonth() {
        String string = "";
        Date data = new Date();
        SimpleDateFormat format2 = new SimpleDateFormat("dd");
        for (int i = 0; i < 30; i++) {
            string = format2.format(DateUtil.nextDay(data, -i)) + getString(R.string.ionic_msg3);

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
        int position = event.getMsg();
        Log.e(TAG, position + "");
        indext = position;
        getData(position);
        initY();
    }

    private void initY() {
        switch (indext) {
            case 0:
                //查询pm2.5
                max = 500;
                mChart.getAxisLeft().setAxisMaximum(500);
                mChart.getAxisLeft().setAxisMinimum(0);
                break;
            case 1:
                //查询co2
                max = 1500;
                mChart.getAxisLeft().setAxisMaximum(2000);
                mChart.getAxisLeft().setAxisMinimum(0);
                break;
            case 2:
                //查询TVOC
                max = 1.6;
                mChart.getAxisLeft().setAxisMaximum((float) 1.6);
                mChart.getAxisLeft().setAxisMinimum(0);
                break;
            case 3:
                //查询甲醛
                max = 0.8;
                mChart.getAxisLeft().setAxisMaximum((float) 0.8);
                mChart.getAxisLeft().setAxisMinimum(0);
                break;
            case 4:
                //pm10
            case 5:
                //pm10 扬尘
            case 6:
                //pm1.0
            case 7:
                //pm100
                max = 500;
                mChart.getAxisLeft().setAxisMaximum(500);
                mChart.getAxisLeft().setAxisMinimum(0);
                break;
            case 8:
                //温度
                max = 60;
                mChart.getAxisLeft().setAxisMaximum(60);
                mChart.getAxisLeft().setAxisMinimum(0);
                break;
            case 9:
                //湿度
                max = 100;
                mChart.getAxisLeft().setAxisMaximum(100);
                mChart.getAxisLeft().setAxisMinimum(0);
                break;
            case 10:
                //气压
                max = 200;
                mChart.getAxisLeft().setAxisMaximum(200);
                mChart.getAxisLeft().setAxisMinimum(0);
                break;
            case 11:
                //噪声
                break;
            case 12:
                //风向
            case 13:
                //风速
                max = 50;
                mChart.getAxisLeft().setAxisMaximum(50);
                mChart.getAxisLeft().setAxisMinimum(0);
                break;
        }
        mChart.getAxisLeft().setLabelCount(6, true);
        mChart.notifyDataSetChanged();
        mChart.invalidate();
    }
    private void getData(int indext){
        switch (indext) {
            case 0:
                //查询pm2.5
                max = 500;
                DayUnitTv.setText(getString(R.string.data_msg));
                pMdataPresenterImp.binding(map);
                break;
            case 1:
                //查询co2
                max = 1500;
                DayUnitTv.setText(getString(R.string.data_msg2));
                codataPresenterImp.binding(map);
                break;
            case 2:
                //查询TVOC
                max = 1.6;
                DayUnitTv.setText(getString(R.string.data_msg3));
                tvoCdataPresenterImp.binding(map);
                break;
            case 3:
                //查询甲醛
                max = 0.8;
                DayUnitTv.setText(getString(R.string.data_msg4));
                methanalPresenterImp.binding(map);
                break;
            case 4:
                //pm10
                max = 200;
                DayUnitTv.setText(getString(R.string.data_msg5));
                pmPresenterImp.binding(map);
                break;
            case 5:
                //pm10 扬尘
                max = 200;
                DayUnitTv.setText(getString(R.string.data_msg5));
                pmPresenterImp.binding(map);
                break;
            case 6:
                //pm1.0
                max = 500;
                DayUnitTv.setText(getString(R.string.data_msg));
                pMdataPresenterImp.binding(map);
                break;
            case 7:
                //pm100
                max = 200;
                DayUnitTv.setText(getString(R.string.data_msg5));
                pmycPresenterImp.binding(map);
                break;
            case 8:
                //温度
                max = 60;
                DayUnitTv.setText(getString(R.string.data_msg7));
                wdPresenterImp.binding(map);
                break;
            case 9:
                //湿度
                max = 100;
                sdPresenterImp.binding(map);
                DayUnitTv.setText(getString(R.string.data_msg8));
                break;
            case 10:
                //气压
                max = 500;
                DayUnitTv.setText("");
                qyPresenterImp.binding(map);
                break;
            case 11:
                //噪声
                max = 50;
                DayUnitTv.setText("");
                zsPresenterImp.binding(map);
                break;
            case 12:
                //风向
                max = 50;
                DayUnitTv.setText("");
                fxPresenterImp.binding(map);
                break;
            case 13:
                //风速
                max = 50;
                DayUnitTv.setText("");
                fsPresenterImp.binding(map);
                break;

        }
    }
}
