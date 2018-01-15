package com.example.yangsong.piaoai.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.activity.HistoryActivity;
import com.example.yangsong.piaoai.base.BaseFragment;
import com.example.yangsong.piaoai.bean.Facility;
import com.example.yangsong.piaoai.bean.YC;
import com.example.yangsong.piaoai.myview.RoundProgressBar;
import com.example.yangsong.piaoai.presenter.YCHomePresenterImp;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.YCView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yangsong on 2017/5/14.
 */
@SuppressLint("ValidFragment")
public class DustFragment extends BaseFragment implements YCView {


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

    YCHomePresenterImp ycHomePresenterImp;
    private Toastor toastor;
    private Handler handler;
    private Runnable myRunnable;

    public DustFragment(Facility.ResBodyBean.ListBean facility) {
        this.facility = facility;
    }

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        toastor = new Toastor(getActivity());
        roundProgressBarPm2.setMax(1000);
        roundProgressBarHumidity.setMax(100);
        roundProgressBarQiya.setMax(100);
        roundProgressBarElectric.setMax(60);
        roundProgressBarZaosheng.setMax(130);
        roundProgressBarFengsu.setMax(33);
        roundProgressBarPM10.setMax(250);
        roundProgressBarFengxiang.setMax(7);
        roundProgressBarPm100.setMax(200);
        ycHomePresenterImp = new YCHomePresenterImp(this, getActivity());


        ycHomePresenterImp.binding(facility.getDeviceid());
    }

    @Override
    protected int getContentView() {
        return R.layout.dust_fragment;
    }


    @OnClick({R.id.home_PM10_rl, R.id.home_pm1_rl, R.id.home_pm100_rl,
            R.id.home_electric_rl, R.id.home_humidity_rl, R.id.home_qiya_rl,
            R.id.home_zaosheng_rl, R.id.home_fengxiang_rl, R.id.home_fengsu_rl})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getActivity(), HistoryActivity.class).putExtra("deviceID", facility.getDeviceid()).putExtra("type", facility.getType());
        switch (view.getId()) {
            case R.id.home_PM10_rl:
                intent.putExtra("indext", 5);
                break;
            case R.id.home_pm1_rl:
                intent.putExtra("indext", 6);
                break;
            case R.id.home_pm100_rl:
                intent.putExtra("indext", 7);
                break;
            case R.id.home_electric_rl:
                intent.putExtra("indext", 8);
                break;
            case R.id.home_humidity_rl:
                intent.putExtra("indext", 9);
                break;
            case R.id.home_qiya_rl:
                intent.putExtra("indext", 10);
                break;
            case R.id.home_zaosheng_rl:
                intent.putExtra("indext", 11);
                break;
            case R.id.home_fengxiang_rl:
                intent.putExtra("indext", 12);
                break;
            case R.id.home_fengsu_rl:
                intent.putExtra("indext", 13);
                break;
        }
        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ycHomePresenterImp.unSubscribe();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void disimissProgress() {

    }

    @Override
    public void loadDataSuccess(YC tData) {
        if (tData.getResCode().equals("0"))
            initdata(tData.getResBody().getMap());
        else
            toastor.showSingletonToast(tData.getResMessage());
    }

    @Override
    public void loadDataError(Throwable throwable) {
        toastor.showSingletonToast(getString(R.string.dialog_msg5));
        Log.e("loadDataError", throwable.toString());
    }

    private void initdata(YC.ResBodyBean.MapBean tData) {
        if (!tData.getPm2P5().equals("")) {
            pm25.setText(tData.getPm2P5());
            int pm = Integer.parseInt(tData.getPm2P5());
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
        //PM10
        if (TextUtils.isEmpty(tData.getPM10()))
            roundProgressBarPM10.setProgress(0);
        else {
            roundProgressBarPM10.setProgress(Integer.parseInt(tData.getPM10()));
            homePM10Tv.setText(tData.getPM10());
        }
        //湿度
        if (TextUtils.isEmpty(tData.getShidu()))
            roundProgressBarHumidity.setProgress(0);
        else {
            roundProgressBarHumidity.setProgress((int)(Double.parseDouble(tData.getShidu())));
            homeHumidityTv.setText(tData.getPM10());
        }
        if (TextUtils.isDigitsOnly(tData.getWendu()))
            roundProgressBarElectric.setProgress(0);
        else {
            roundProgressBarElectric.setProgress((int)(Double.parseDouble(tData.getWendu())));
            homeElectricTv.setText(tData.getWendu());
        }
        if (TextUtils.isDigitsOnly(tData.getPm1P0()))
            roundProgressBarPm2.setProgress(0);
        else {
            roundProgressBarPm2.setProgress(Integer.parseInt(tData.getPm1P0()));
            homePm2Tv.setText(tData.getPm1P0());
        }
        if (TextUtils.isDigitsOnly(tData.getPm2P5()))
            roundProgressBarPm100.setProgress(0);
        else {
            roundProgressBarPm100.setProgress(Integer.parseInt(tData.getPm2P5()));
            homePm100Tv.setText(tData.getPm2P5());
        }
        if (TextUtils.isDigitsOnly(tData.getZaosheng()))
            roundProgressBarZaosheng.setProgress(0);
        else {
            roundProgressBarZaosheng.setProgress((int)(Double.parseDouble(tData.getZaosheng())));
            homeZaoshengTv.setText(tData.getZaosheng());
        }
        if (TextUtils.isDigitsOnly(tData.getDaqiya()))
            roundProgressBarQiya.setProgress(0);
        else {
            roundProgressBarQiya.setProgress((int)(Double.parseDouble(tData.getDaqiya())));
            homeQiyaTv.setText(tData.getDaqiya());
        }
        if (TextUtils.isDigitsOnly(tData.getWind()))
            roundProgressBarFengsu.setProgress(0);
        else {
            roundProgressBarFengsu.setProgress((int)(Double.parseDouble(tData.getWind())));
            homeFengsuTv.setText(tData.getWind());
        }
        if (TextUtils.isDigitsOnly(tData.getWindDire())) {
            homeFengxiangTv.setText("北风");
            roundProgressBarFengxiang.setProgress(0);
        } else {
            int wind = Integer.parseInt(tData.getWindDire());
            roundProgressBarFengxiang.setProgress(wind);
            switch (wind) {
                case 0:
                    homeFengxiangTv.setText("北风");
                    break;
                case 1:
                    homeFengxiangTv.setText("东北风");
                    break;
                case 2:
                    homeFengxiangTv.setText("东风");
                    break;
                case 3:
                    homeFengxiangTv.setText("东南风");
                    break;
                case 4:
                    homeFengxiangTv.setText("南风");
                    break;
                case 5:
                    homeFengxiangTv.setText("西南风");
                    break;
                case 6:
                    homeFengxiangTv.setText("西风");
                    break;
                case 7:
                    homeFengxiangTv.setText("西北风");
                    break;
            }
        }
    }
}
