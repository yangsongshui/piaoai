package com.example.yangsong.piaoai.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.api.ServiceApi;
import com.example.yangsong.piaoai.app.MyApplication;
import com.example.yangsong.piaoai.bean.Weather;
import com.example.yangsong.piaoai.util.Toastor;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.yangsong.piaoai.util.Constan.WEATHER_URL;


public class WeatherFragment extends Fragment {
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    ProgressDialog progressDialog;
    Retrofit retrofit;
    Toastor toastor;
    @BindView(R.id.weather_iv)
    ImageView weatherIv;
    @BindView(R.id.weatherTv)
    TextView weatherTv;
    @BindView(R.id.temperature_tv)
    TextView temperatureTv;
    @BindView(R.id.city_tv)
    TextView cityTv;
    @BindView(R.id.weather_pm2_tv)
    TextView weatherPm2Tv;
    @BindView(R.id.weather_pm10_tv)
    TextView weatherPm10Tv;
    @BindView(R.id.no2)
    TextView no2;
    @BindView(R.id.weather_no2_tv)
    TextView weatherNo2Tv;
    @BindView(R.id.weather_so2_tv)
    TextView weatherSo2Tv;
    @BindView(R.id.weather_o3_tv)
    TextView weatherO3Tv;
    @BindView(R.id.weather_co_tv)
    TextView weatherCoTv;
    @BindView(R.id.line_chart)
    CombinedChart lineChart;
    Unbinder unbinder;
    List<String> mList;
    List<String> time;


    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    Log.e("定位数据", aMapLocation.getCity());
                    cityTv.setText(aMapLocation.getCity());
                    ServiceApi service = retrofit.create(ServiceApi.class);
                    Call<Weather> call = service.getWeather(aMapLocation.getCity(), "1");
                    call.enqueue(new Callback<Weather>() {
                        @Override
                        public void onResponse(Call<Weather> call, Response<Weather> response) {
                            //请求成功操作
                            Weather weather = response.body();
                            if (weather.getShowapi_res_code() == 0) {
                                initWeather(weather);
                            } else {
                                toastor.showSingletonToast("天气查询失败");
                                progressDialog.dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<Weather> call, Throwable t) {
                            //请求失败操作
                            progressDialog.dismiss();
                            toastor.showSingletonToast("天气查询失败");
                        }
                    });
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                    toastor.showSingletonToast("定位失败:" + aMapLocation.getErrorInfo());
                    progressDialog.dismiss();
                }
            }
        }
    };

    private void initWeather(Weather weather) {
        if (weather.getShowapi_res_body().getNow() != null) {
            MyApplication.newInstance().getGlide().load(weather.getShowapi_res_body().getNow().getWeather_pic()).into(weatherIv);
            weatherPm2Tv.setText(weather.getShowapi_res_body().getNow().getSd());

            BigDecimal b = new BigDecimal(weather.getShowapi_res_body().getNow().getAqiDetail().getCo());
            double f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            weatherCoTv.setText(f1 + "");
            weatherNo2Tv.setText(weather.getShowapi_res_body().getNow().getAqiDetail().getNo2());
            weatherPm10Tv.setText(weather.getShowapi_res_body().getNow().getAqiDetail().getPm10());
            weatherSo2Tv.setText(weather.getShowapi_res_body().getNow().getAqiDetail().getSo2());
            weatherO3Tv.setText(weather.getShowapi_res_body().getNow().getAqiDetail().getO3());
            temperatureTv.setText(weather.getShowapi_res_body().getNow().getTemperature() + "℃");
            weatherTv.setText(weather.getShowapi_res_body().getNow().getWeather());
        }
        progressDialog.dismiss();
    }

    private void initChart() {

        /**
         * ====================1.初始化-自由配置===========================
         */
        // 是否在折线图上添加边框
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        // 设置右下角描述
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);
        lineChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE,
        });
        //设置透明度
        // lineChart.setAlpha(0.8f);
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        lineChart.setTouchEnabled(true);
        //设置是否可以拖拽
        lineChart.setDragEnabled(false);
        //设置是否可以缩放
        lineChart.setScaleEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        //设置是否能扩大扩小
        lineChart.setPinchZoom(false);
        //设置四个边的间距
        // lineChart.setViewPortOffsets(10, 0, 0, 10);
        //隐藏Y轴
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setEnabled(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setAxisMinimum(-0.1f);
        xAxis.setGranularity(0.3f);
        xAxis.setAxisMaximum(6);
        xAxis.setTextColor(Color.rgb(255, 255, 255));
        xAxis.setAxisLineColor(Color.argb(148, 255, 255, 255));
        xAxis.setTextSize(10);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴在底部
        //不画网格
        xAxis.setDrawGridLines(true);

        lineChart.getLegend().setEnabled(false);
    }

    private LineData getLineData() {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            // Log.e(TAG, mList.get(i)+" " + i );
            if (i >= (mList.size())) {
                values1.add(new Entry(i, 0));
            } else
                values1.add(new Entry(i, Integer.parseInt(mList.get(i))));
        }
        IAxisValueFormatter formatter = new IAxisValueFormatter() {


            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return time.get((int) value % time.size());
            }


        };
        lineChart.getXAxis().setValueFormatter(formatter);
        LineDataSet set1;
        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
        } else {
            set1 = new LineDataSet(values1, "");
        }
        set1.setLineWidth(1f);//设置线宽
        set1.setCircleRadius(3f);//设置焦点圆心的大小
        set1.enableDashedHighlightLine(5f, 2f, 1f);//点击后的高亮线的显示样式
        set1.setHighlightLineWidth(0.5f);//设置点击交点后显示高亮线宽
        set1.setHighlightEnabled(true);//是否禁用点击高亮线

        set1.setDrawHighlightIndicators(false);//设置不显示水平高亮线
        set1.setDrawCircles(false);  //设置有圆点
        set1.setDrawValues(false);  //不显示数据
        set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER); //设置为曲线

        set1.setColor(Color.rgb(255, 255, 255));    //设置曲线的颜色

        return new LineData(set1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(getContentView(), null);
        unbinder = ButterKnife.bind(this, layout);
        init();
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected int getContentView() {
        return R.layout.fragment_weather;
    }

    private void init() {
        mList = new ArrayList<>();
        time = new ArrayList<>();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("数据查询中...");
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //获取一次定位结果
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setInterval(600000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(WEATHER_URL)
                .build();
        toastor = new Toastor(getActivity());
        progressDialog.show();
        initChart();
        ServiceApi service = retrofit.create(ServiceApi.class);
        Call<Weather> call = service.getWeather("深圳", "1");
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                //请求成功操作
                Weather weather = response.body();
                Log.e("weather", weather.toString());
                if (weather.getShowapi_res_code() == 0) {
                    initWeather(weather);
                } else {
                    toastor.showSingletonToast("天气查询失败");
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                //请求失败操作
                progressDialog.dismiss();
                toastor.showSingletonToast("天气查询失败");
            }
        });
    }

}
