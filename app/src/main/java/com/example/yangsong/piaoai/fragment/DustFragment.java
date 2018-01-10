package com.example.yangsong.piaoai.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseFragment;
import com.example.yangsong.piaoai.bean.Facility;
import com.example.yangsong.piaoai.myview.RoundProgressBar;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by yangsong on 2017/5/14.
 */
@SuppressLint("ValidFragment")
public class DustFragment extends BaseFragment {


    @BindView(R.id.shujv_ll)
    LinearLayout shujv_ll;
    @BindView(R.id.pm25)
    TextView pm25;
    @BindView(R.id.pm_tv)
    TextView pm_tv;

    Facility.ResBodyBean.ListBean facility;
    @BindView(R.id.main_iv)
    ImageView mainIv;
    @BindView(R.id.roundProgressBar_PM10)
    RoundProgressBar roundProgressBarPM10;
    @BindView(R.id.home_PM10_tv)
    TextView homePM10Tv;
    @BindView(R.id.roundProgressBar_pm2)
    RoundProgressBar roundProgressBarPm2;
    @BindView(R.id.home_pm2_tv)
    TextView homePm2Tv;
    @BindView(R.id.home_pm100_tv)
    TextView homePm100Tv;
    @BindView(R.id.roundProgressBar_pm100)
    RoundProgressBar roundProgressBarPm100;
    @BindView(R.id.home_electric_tv)
    TextView homeElectricTv;
    @BindView(R.id.roundProgressBar_electric)
    RoundProgressBar roundProgressBarElectric;
    @BindView(R.id.roundProgressBar_humidity)
    RoundProgressBar roundProgressBarHumidity;
    @BindView(R.id.home_humidity_tv)
    TextView homeHumidityTv;
    @BindView(R.id.roundProgressBar_qiya)
    RoundProgressBar roundProgressBarQiya;
    @BindView(R.id.home_qiya_tv)
    TextView homeQiyaTv;
    @BindView(R.id.home_zaosheng_tv)
    TextView homeZaoshengTv;
    @BindView(R.id.roundProgressBar_zaosheng)
    RoundProgressBar roundProgressBarZaosheng;
    @BindView(R.id.roundProgressBar_fengxiang)
    RoundProgressBar roundProgressBarFengxiang;
    @BindView(R.id.home_fengxiang_tv)
    TextView homeFengxiangTv;
    @BindView(R.id.roundProgressBar_fengsu)
    RoundProgressBar roundProgressBarFengsu;
    @BindView(R.id.home_fengsu_tv)
    TextView homeFengsuTv;
    @BindView(R.id.main_fragment)
    ScrollView mainFragment;
    Unbinder unbinder;


    public DustFragment(Facility.ResBodyBean.ListBean facility) {
        this.facility = facility;
    }

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        roundProgressBarPm2.setMax(1000);
        roundProgressBarHumidity.setMax(100);
        roundProgressBarQiya.setMax(400);
        roundProgressBarElectric.setMax(50);
        roundProgressBarZaosheng.setMax(1500);
        roundProgressBarFengsu.setMax(1500);
        roundProgressBarPM10.setMax(200);
        roundProgressBarFengxiang.setMax(200);
        roundProgressBarPm100.setMax(200);

        //PM10
        if (TextUtils.isEmpty(facility.getPm10()))
            roundProgressBarPM10.setProgress(0);
        else {
            roundProgressBarPM10.setProgress(Integer.parseInt(facility.getPm10()));
            homePM10Tv.setText(facility.getPm10());
        }


        //湿度
        if (TextUtils.isEmpty(facility.getShidu()))
            roundProgressBarHumidity.setProgress(0);
        else {
            roundProgressBarHumidity.setProgress(Integer.parseInt(facility.getShidu()));
            homeHumidityTv.setText(facility.getShidu());
        }


        if (!facility.get_$Pm25224().equals("")) {
            pm25.setText(facility.get_$Pm25224());
            int pm = Integer.parseInt(facility.get_$Pm25224());
            if (pm >= 0 && pm <= 35) {
                pm_tv.setText(getString(R.string.out_msg));
            } else if (pm > 35 && pm <= 75) {
                pm_tv.setText(getString(R.string.out_msg2));
                pm_tv.setBackground(getResources().getDrawable(R.drawable.pm_liang));
            } else if (pm > 75 && pm <= 115) {
                pm_tv.setText(getString(R.string.out_msg3));
                pm_tv.setBackground(getResources().getDrawable(R.drawable.pm_qingdu));
            } else if (pm > 115 && pm <= 150) {
                pm_tv.setText(getString(R.string.out_msg4));
                pm_tv.setBackground(getResources().getDrawable(R.drawable.pm_zhongdu));
            } else if (pm > 150 && pm <= 250) {
                pm_tv.setText(getString(R.string.out_msg5));
                pm_tv.setBackground(getResources().getDrawable(R.drawable.pm_zhong));
            } else if (pm > 250) {
                pm_tv.setText(getString(R.string.out_msg6));
                pm_tv.setBackground(getResources().getDrawable(R.drawable.pm_yanzhong));
            }
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.dust_fragment;
    }


/*    @OnClick({ R.id.home_PM10_rl, R.id.home_methanal_rl, R.id.main_iv})
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
                intent.putExtra("indext", 4);
                break;
            case R.id.home_methanal_rl:
                intent.putExtra("indext", 3);
                break;
            case R.id.main_iv:
                intent.putExtra("indext", 0);
                break;
        }
        startActivity(intent);
    }*/



    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
