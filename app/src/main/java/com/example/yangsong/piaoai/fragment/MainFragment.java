package com.example.yangsong.piaoai.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.bean.BaseFragment;
import com.example.yangsong.piaoai.model.Facility;
import com.example.yangsong.piaoai.view.RoundProgressBar;

import butterknife.BindView;

/**
 * Created by yangsong on 2017/5/14.
 */

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
    Facility facility;


    public MainFragment(Facility facility) {
        this.facility = facility;
    }

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        roundProgressBarTVOC.setProgress(10);
        roundProgressBarCO2.setProgress(30);
        roundProgressBarPM10.setProgress(50);
        roundProgressBarElectric.setProgress(20);
        roundProgressBarHumidity.setProgress(60);
        roundProgressBarMethanal.setProgress(80);
    }

    @Override
    protected int getContentView() {
        return R.layout.main_fragment;
    }


}
