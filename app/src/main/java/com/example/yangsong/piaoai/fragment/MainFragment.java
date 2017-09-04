package com.example.yangsong.piaoai.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.activity.HistoryActivity;
import com.example.yangsong.piaoai.base.BaseFragment;
import com.example.yangsong.piaoai.bean.Facility;
import com.example.yangsong.piaoai.myview.RoundProgressBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yangsong on 2017/5/14.
 */
@SuppressLint("ValidFragment")
public class MainFragment extends BaseFragment {

    @BindView(R.id.roundProgressBar_TVOC)
    RoundProgressBar roundProgressBarTVOC;
    @BindView(R.id.roundProgressBar_CO2)
    RoundProgressBar roundProgressBarCO2;
    @BindView(R.id.roundProgressBar_PM10)
    RoundProgressBar roundProgressBarPM10;
    @BindView(R.id.roundProgressBar_electric)
    RoundProgressBar roundProgressBarElectric;
    @BindView(R.id.roundProgressBar_humidity)
    RoundProgressBar roundProgressBarHumidity;
    @BindView(R.id.roundProgressBar_methanal)
    RoundProgressBar roundProgressBarMethanal;
    @BindView(R.id.shujv_ll)
    LinearLayout shujv_ll;
    @BindView(R.id.pm25)
    TextView pm25;
    @BindView(R.id.pm_tv)
    TextView pm_tv;


    Facility.ResBodyBean.ListBean facility;
    @BindView(R.id.home_TVOC_tv)
    TextView homeTVOCTv;
    @BindView(R.id.home_CO2_tv)
    TextView homeCO2Tv;
    @BindView(R.id.home_PM10_tv)
    TextView homePM10Tv;
    @BindView(R.id.home_electric_tv)
    TextView homeElectricTv;
    @BindView(R.id.home_humidity_tv)
    TextView homeHumidityTv;
    @BindView(R.id.home_methanal_tv)
    TextView homeMethanalTv;


    public MainFragment(Facility.ResBodyBean.ListBean facility) {
        this.facility = facility;
    }

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        roundProgressBarTVOC.setMax(1000);
        roundProgressBarHumidity.setMax(100);
        roundProgressBarMethanal.setMax(400);
        roundProgressBarElectric.setMax(50);

        roundProgressBarCO2.setMax(1500);
        roundProgressBarPM10.setMax(200);
        //TVOC
        if (TextUtils.isEmpty(facility.getTvoc()))
            roundProgressBarTVOC.setProgress(0);
        else {
            roundProgressBarTVOC.setProgress((int) (Double.parseDouble(facility.getTvoc())*100));
            homeTVOCTv.setText(facility.getTvoc());
        }

        //CO2
        if (TextUtils.isEmpty(facility.getCo2()))
            roundProgressBarCO2.setProgress(0);
        else {
            roundProgressBarCO2.setProgress(Integer.parseInt(facility.getCo2()));
            homeCO2Tv.setText(facility.getCo2());
        }

        //PM10
        if (TextUtils.isEmpty(facility.getPm10()))
            roundProgressBarPM10.setProgress(0);
        else {
            roundProgressBarPM10.setProgress(Integer.parseInt(facility.getPm10()));
            homePM10Tv.setText(facility.getPm10());
        }

        //电量
        if (TextUtils.isEmpty(facility.getWendu()))
            roundProgressBarElectric.setProgress(0);
        else {
            roundProgressBarElectric.setProgress(Integer.parseInt(facility.getWendu()));
            homeElectricTv.setText(facility.getWendu());
        }

        //湿度
        if (TextUtils.isEmpty(facility.getShidu()))
            roundProgressBarHumidity.setProgress(0);
        else {
            roundProgressBarHumidity.setProgress(Integer.parseInt(facility.getShidu()));
            homeHumidityTv.setText(facility.getShidu());
        }

        //甲醛
        if (TextUtils.isEmpty(facility.getJiaquan()))
            roundProgressBarMethanal.setProgress(0);
        else {
            roundProgressBarMethanal.setProgress((int) (Double.parseDouble(facility.getJiaquan())*100));
            //roundProgressBarMethanal.setProgress(Integer.parseInt(facility.getJiaquan()));
            homeMethanalTv.setText(facility.getJiaquan());
        }
        if (!facility.get_$Pm25224().equals("")) {
            pm25.setText(facility.get_$Pm25224());
            int pm = Integer.parseInt(facility.get_$Pm25224());
            if (pm >= 0 && pm <= 35) {
                pm_tv.setText("优");
            } else if (pm > 35 && pm <= 75) {
                pm_tv.setText("良");
                pm_tv.setBackground(getResources().getDrawable(R.drawable.pm_liang));
            } else if (pm > 75 && pm <= 115) {
                pm_tv.setText("轻度污染");
                pm_tv.setBackground(getResources().getDrawable(R.drawable.pm_qingdu));
            } else if (pm > 116 && pm <= 150) {
                pm_tv.setText("中度污染");
                pm_tv.setBackground(getResources().getDrawable(R.drawable.pm_zhongdu));
            } else if (pm > 151 && pm <= 250) {
                pm_tv.setText("重度污染");
                pm_tv.setBackground(getResources().getDrawable(R.drawable.pm_zhong));
            } else if (pm > 251 ) {
                pm_tv.setText("严重污染");
                pm_tv.setBackground(getResources().getDrawable(R.drawable.pm_yanzhong));
            }
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.main_fragment;
    }


    @OnClick({R.id.home_TVOC_rl, R.id.home_CO2_rl, R.id.home_PM10_rl, R.id.home_methanal_rl,R.id.main_iv})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getActivity(), HistoryActivity.class).putExtra("deviceID", facility.getDeviceid()).putExtra("type", facility.getType());
        switch (view.getId()) {
            case R.id.home_TVOC_rl:
                intent.putExtra("indext", 2);
                break;
            case R.id.home_CO2_rl:
                intent.putExtra("indext", 1);
                break;
            case R.id.home_PM10_rl:
                intent.putExtra("indext", 0);
                break;
            case R.id.home_methanal_rl:
                intent.putExtra("indext", 3);
                break;
            case R.id.main_iv:
                intent.putExtra("indext", 0);
                break;
        }
        startActivity(intent);
    }

}
