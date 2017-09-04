package com.example.yangsong.piaoai.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseFragment;
import com.example.yangsong.piaoai.bean.PMBean;
import com.example.yangsong.piaoai.bean.TVOC;
import com.example.yangsong.piaoai.inter.FragmentEvent;
import com.example.yangsong.piaoai.presenter.CodataPresenterImp;
import com.example.yangsong.piaoai.presenter.MethanalPresenterImp;
import com.example.yangsong.piaoai.presenter.PMdataPresenterImp;
import com.example.yangsong.piaoai.presenter.TVOCdataPresenterImp;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.example.yangsong.piaoai.util.DateUtil.LONG_DATE_FORMAT;


/**
 * Created by Administrator on 2016/5/28.
 */
@SuppressLint("ValidFragment")
public class DayFragment extends BaseFragment implements TVOCView {
    private final static String TAG = DayFragment.class.getSimpleName();
    @BindView(R.id.day_chart)
    CombinedChart mChart;
    @BindView(R.id.day_unit_tv)
    TextView DayUnitTv;
    private int TYPE = 0;
    private Toastor toastor;
    PMdataPresenterImp pMdataPresenterImp;
    CodataPresenterImp codataPresenterImp;
    MethanalPresenterImp methanalPresenterImp;
    TVOCdataPresenterImp tvoCdataPresenterImp;
    ProgressDialog progressDialog;
    private Map<String, String> map;
    private Activity activity;
    List<String> mList;
    private int indext = 0;
    double max = 0;

    public DayFragment() {
    }

    public DayFragment(Activity activity, int i) {
        this.activity = activity;
        this.indext = i;
    }

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        //注册EventBus
        EventBus.getDefault().register(this);
        String deviceID = activity.getIntent().getStringExtra("deviceID");
        mList = new ArrayList<>();
        toastor = new Toastor(getActivity());
        initChart();
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
            }

            @Override
            public void loadDataSuccess(PMBean tData) {
                toastor.showSingletonToast(tData.getResMessage());
                if (tData.getResCode().equals("0")) {
                    if (tData.getResBody().getList().size() > 0) {
                        mList = tData.getResBody().getList();

                    }
                    CombinedData data = new CombinedData();
                    data.setData(getdayData());
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
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("数据查询中...");
        map = new HashMap<>();
        map.put("imei", deviceID);
        map.put("type", "1");
        //通过格式化输出日期
        String time = DateUtil.getCurrDate(LONG_DATE_FORMAT);
        map.put("endDate", time);
        map.put("beginDate", time);
   /* map.put("beginDate", "2017-08-23");
        map.put("endDate", "2017-08-23");*/
        if (indext == 0) {
            //查询pm2.5
            max = 500;
            DayUnitTv.setText("PM2.5(μg/m³)");
            pMdataPresenterImp.binding(map);
            DayUnitTv.setVisibility(View.VISIBLE);

        } else if (indext == 1) {
            //查询co2
            max = 1500;
            DayUnitTv.setText("CO2(mg/m³)");
            codataPresenterImp.binding(map);
            DayUnitTv.setVisibility(View.VISIBLE);

        } else if (indext == 2) {
            //查询TVOC
            max = 1.6;
            DayUnitTv.setVisibility(View.INVISIBLE);
            tvoCdataPresenterImp.binding(map);

        } else if (indext == 3) {
            //查询甲醛
            max = 0.8;
            DayUnitTv.setText("甲醛(μg/m³)");
            methanalPresenterImp.binding(map);
            DayUnitTv.setVisibility(View.VISIBLE);

        } else if (indext == 4) {
            //pm10
            max = 200;
            DayUnitTv.setText("PM10(μg/m³)");
            DayUnitTv.setVisibility(View.VISIBLE);

        }
        //  initYLabel(indext);
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
        mChart.getAxisLeft().setAxisMaximum(500);
        mChart.getAxisLeft().setAxisMinimum(0);
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
        xAxis.setAxisMaximum(23);
        xAxis.setLabelCount(7, true);

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            String[] day = new String[]{"01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00"
                    , "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return day[(int) value % day.length];
            }


        };
        xAxis.setValueFormatter(formatter);
        mChart.getLegend().setEnabled(false);

    }


    private LineData getdayData() {

        ArrayList<Entry> values1 = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            // Log.e(TAG, mList.get(i)+" " + i );
            if (i >= (mList.size())) {
                values1.add(new Entry(i, 0));
            } else {
                if (Double.parseDouble(mList.get(i)) <= max)
                    values1.add(new Entry(i, Integer.parseInt(mList.get(i))));
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
        //  toastor.showSingletonToast(tData.getResMessage());
        if (tData.getResCode().equals("0")) {
            if (tData.getResBody().getList().size() > 0) {
                mList = tData.getResBody().getList();

            }
            CombinedData data = new CombinedData();
            data.setData(getdayData());
            mChart.setData(data);
            mChart.invalidate();
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

        Log.e(TAG, throwable.getLocalizedMessage());
        toastor.showSingletonToast("服务器连接异常");
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
        switch (position) {
            case 0:
                max = 500;
                DayUnitTv.setText("PM2.5(μg/m³)");
                pMdataPresenterImp.binding(map);
                DayUnitTv.setVisibility(View.VISIBLE);
                mChart.getAxisLeft().setAxisMaximum(500);
                mChart.getAxisLeft().setAxisMinimum(0);

                break;
            case 1:
                max = 1500;
                DayUnitTv.setText("CO2(mg/m³)");
                codataPresenterImp.binding(map);
                DayUnitTv.setVisibility(View.VISIBLE);
                mChart.getAxisLeft().setAxisMaximum(1500);
                mChart.getAxisLeft().setAxisMinimum(0);

                break;
            case 2:
                DayUnitTv.setVisibility(View.INVISIBLE);
                tvoCdataPresenterImp.binding(map);
                mChart.getAxisLeft().setAxisMaximum((float) 1.6);
                mChart.getAxisLeft().setAxisMinimum(0);
                max = 1.6;
                break;
            case 3:
                max = 0.8;
                DayUnitTv.setText("甲醛(μg/m³)");
                methanalPresenterImp.binding(map);
                DayUnitTv.setVisibility(View.VISIBLE);
                mChart.getAxisLeft().setAxisMaximum((float) 0.8);
                mChart.getAxisLeft().setAxisMinimum(0);

                break;
            case 4:
                //PM10
                max = 200;
                DayUnitTv.setText("PM10(μg/m³)");
                DayUnitTv.setVisibility(View.VISIBLE);
                mChart.getAxisLeft().setAxisMaximum(200);
                mChart.getAxisLeft().setAxisMinimum(0);

                break;

            default:
                break;
        }
        mChart.getAxisLeft().setLabelCount(6, true);
        mChart.notifyDataSetChanged();
        mChart.invalidate();
    }


}
