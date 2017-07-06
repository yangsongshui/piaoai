package com.example.yangsong.piaoai.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.api.ServiceApi;
import com.example.yangsong.piaoai.app.MyApplication;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.bean.Weather;
import com.example.yangsong.piaoai.util.Toastor;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.yangsong.piaoai.util.Constan.WEATHER_URL;
import static com.example.yangsong.piaoai.util.DateUtil.dayNames;

public class OutsideActivity extends BaseActivity {


    @BindView(R.id.location_tv)
    TextView locationTv;   //当前城市
    @BindView(R.id.history_tianqi_tv)
    TextView historyTianqiTv;
    @BindView(R.id.history_tianqi_iv)
    ImageView historyTianqiIv;
    @BindView(R.id.history_wendu_tv)
    TextView historyWenduTv;
    @BindView(R.id.outside_pm)
    TextView outsidePm;
    @BindView(R.id.pm_jibie)
    TextView pmJibie;
    @BindView(R.id.shidu_tv)
    TextView shiduTv;
    @BindView(R.id.nengjiandu_tv)
    TextView nengjianduTv;
    @BindView(R.id.ziwaixian_tv)
    TextView ziwaixianTv;
    @BindView(R.id.fengsu_tv)
    TextView fengsuTv;
    @BindView(R.id.tomorrow_tv)
    TextView tomorrowTv;
    @BindView(R.id.tomorrow_iv)
    ImageView tomorrowIv;
    @BindView(R.id.tomorrow_wendu_tv)
    TextView tomorrowWenduTv;
    @BindView(R.id.tomorrow2_tv)
    TextView tomorrow2Tv;
    @BindView(R.id.tomorrow2_iv)
    ImageView tomorrow2Iv;
    @BindView(R.id.tomorrow2_wendu_tv)
    TextView tomorrow2WenduTv;
    @BindView(R.id.tomorrow3_tv)
    TextView tomorrow3Tv;
    @BindView(R.id.tomorrow3_iv)
    ImageView tomorrow3Iv;
    @BindView(R.id.tomorrow3_wendu_tv)
    TextView tomorrow3WenduTv;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    ProgressDialog progressDialog;
    Retrofit retrofit;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_outside;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据查询中...");
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //获取一次定位结果
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(WEATHER_URL)
                .build();
        toastor = new Toastor(this);
        progressDialog.show();

    }


    @OnClick(R.id.outside_left_iv)
    public void onViewClicked() {
        finish();
    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    Log.e("定位数据", aMapLocation.getCity());
                    locationTv.setText(aMapLocation.getCity());
                    ServiceApi service = retrofit.create(ServiceApi.class);
                    Call<Weather> call = service.getWeather(aMapLocation.getCity(), "1");
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
        MyApplication.newInstance().getGlide().load(weather.getShowapi_res_body().getNow().getWeather_pic()).into(historyTianqiIv);
        MyApplication.newInstance().getGlide().load(weather.getShowapi_res_body().getF2().getDay_weather_pic()).into(tomorrowIv);
        MyApplication.newInstance().getGlide().load(weather.getShowapi_res_body().getF3().getDay_weather_pic()).into(tomorrow2Iv);
        MyApplication.newInstance().getGlide().load(weather.getShowapi_res_body().getF4().getDay_weather_pic()).into(tomorrow3Iv);
        tomorrow2Tv.setText(dayNames[weather.getShowapi_res_body().getF3().getWeekday() - 1]);
        tomorrow3Tv.setText(dayNames[weather.getShowapi_res_body().getF4().getWeekday() -1]);
        tomorrowWenduTv.setText(weather.getShowapi_res_body().getF2().getNight_air_temperature() + "/" + weather.getShowapi_res_body().getF2().getDay_air_temperature() + "℃");
        tomorrow2WenduTv.setText(weather.getShowapi_res_body().getF3().getNight_air_temperature() + "/" + weather.getShowapi_res_body().getF3().getDay_air_temperature() + "℃");
        tomorrow3WenduTv.setText(weather.getShowapi_res_body().getF4().getNight_air_temperature() + "/" + weather.getShowapi_res_body().getF4().getDay_air_temperature() + "℃");
        historyTianqiTv.setText(weather.getShowapi_res_body().getNow().getWeather());
        historyWenduTv.setText(weather.getShowapi_res_body().getNow().getTemperature() + "℃");
        outsidePm.setText(weather.getShowapi_res_body().getNow().getAqiDetail().getPm2_5());
        shiduTv.setText(weather.getShowapi_res_body().getNow().getSd());
        ziwaixianTv.setText(weather.getShowapi_res_body().getF1().getZiwaixian());
        fengsuTv.setText(weather.getShowapi_res_body().getNow().getWind_power());
        //nengjianduTv.setText(weather.getShowapi_res_body().getNow().getSd());
        int pm = Integer.parseInt(weather.getShowapi_res_body().getNow().getAqiDetail().getPm2_5());
        if (pm >= 0 || pm <= 35) {
            pmJibie.setText("优");
        } else if (pm > 35 || pm <= 75) {
            pmJibie.setText("良");
            pmJibie.setBackground(getResources().getDrawable(R.drawable.pm_liang));
        } else if (pm > 75 || pm <= 115) {
            pmJibie.setText("轻度污染");
            pmJibie.setBackground(getResources().getDrawable(R.drawable.pm_qingdu));
        } else if (pm > 116 || pm <= 150) {
            pmJibie.setText("中度污染");
            pmJibie.setBackground(getResources().getDrawable(R.drawable.pm_zhongdu));
        } else if (pm > 151 || pm <= 250) {
            pmJibie.setText("重度污染");
            pmJibie.setBackground(getResources().getDrawable(R.drawable.pm_zhong));
        } else if (pm > 251 || pm <= 500) {
            pmJibie.setText("严重污染");
            pmJibie.setBackground(getResources().getDrawable(R.drawable.pm_yanzhong));
        }
        progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
