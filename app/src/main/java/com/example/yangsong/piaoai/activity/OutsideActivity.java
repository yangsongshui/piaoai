package com.example.yangsong.piaoai.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

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

    @Override
    protected int getContentView() {
        return R.layout.activity_outside;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
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

                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };


}
