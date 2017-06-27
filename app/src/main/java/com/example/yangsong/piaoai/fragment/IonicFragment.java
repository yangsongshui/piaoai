package com.example.yangsong.piaoai.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseFragment;
import com.example.yangsong.piaoai.bean.FLZDevice;
import com.example.yangsong.piaoai.bean.Facility;
import com.example.yangsong.piaoai.presenter.AddPresenterImp;
import com.example.yangsong.piaoai.presenter.ClosePresenterImp;
import com.example.yangsong.piaoai.presenter.MaxPresenterImp;
import com.example.yangsong.piaoai.presenter.MinPresenterImp;
import com.example.yangsong.piaoai.presenter.OpenPresenterImp;
import com.example.yangsong.piaoai.presenter.ReducePresenterImp;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.FLZView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class IonicFragment extends BaseFragment implements FLZView {
    private final static String TAG = IonicFragment.class.getSimpleName();

    @BindView(R.id.ionic_TVOC_tv)
    TextView homeTVOCTv;
    @BindView(R.id.ionic_ll)
    LinearLayout ionicLl;
    @BindView(R.id.ionic_control_ll)
    LinearLayout ionicControlLl;
    @BindView(R.id.ionic_on_off_ll)
    LinearLayout ionicOnOffLl;
    @BindView(R.id.on_off_control_ll)
    LinearLayout onOffControlLl;

    Facility.ResBodyBean.ListBean facility;
    AddPresenterImp addPresenterImp;   //增加
    ReducePresenterImp reducePresenterImp; //减少
    OpenPresenterImp openPresenterImp;  //开
    ClosePresenterImp closePresenterImp;  //关
    MinPresenterImp minPresenterImp;  //最小
    MaxPresenterImp maxPresenterImp;  //最大
    ProgressDialog progressDialog;
    private Map<String, String> map;
    private Toastor toastor;

    public IonicFragment(Facility.ResBodyBean.ListBean facility) {
        this.facility = facility;
    }

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        initView();
        addPresenterImp = new AddPresenterImp(this, getContext());
        reducePresenterImp = new ReducePresenterImp(this, getContext());
        openPresenterImp = new OpenPresenterImp(this, getContext());
        closePresenterImp = new ClosePresenterImp(this, getContext());
        minPresenterImp = new MinPresenterImp(this, getContext());
        maxPresenterImp = new MaxPresenterImp(this, getContext());
        ionicControlLl.setAlpha(0f);
        onOffControlLl.setAlpha(0f);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("数据查询中...");
        map = new HashMap<>();

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_ionic;
    }

    @OnClick({R.id.ionic_ll, R.id.ionic_on_off_ll, R.id.min_tv, R.id.minus_tv, R.id.plus_tv, R.id.max_tv, R.id.on_tv, R.id.off_tv})
    public void onClick(View view) {
        map.clear();
        map.put("imei", facility.getDeviceid());
        switch (view.getId()) {
            case R.id.ionic_on_off_ll:
                Visibility(true, 1);
                onOffControlLl.setAlpha(0f);
                onOffControlLl.animate()
                        .alpha(1f)
                        .setDuration(1000)
                        .setListener(null);
                break;
            case R.id.ionic_ll:
                Visibility(true, 0);
                ionicControlLl.setAlpha(0f);
                ionicControlLl.animate()
                        .alpha(1f)
                        .setDuration(1000)
                        .setListener(null);
                break;
            case R.id.min_tv:
                map.put("num","1");
                minPresenterImp.loadWeather(map);
                Visibility(false, 0);
                break;
            case R.id.minus_tv:
                Visibility(false, 0);
                reducePresenterImp.loadWeather(map);
                break;
            case R.id.plus_tv:
                Visibility(false, 0);
                addPresenterImp.loadWeather(map);
                break;
            case R.id.max_tv:
                map.put("num","8");
                Visibility(false, 0);
                maxPresenterImp.loadWeather(map);
                break;
            case R.id.on_tv:

                Visibility(false, 1);
                openPresenterImp.loadWeather(map);
                break;
            case R.id.off_tv:

                Visibility(false, 1);
                closePresenterImp.loadWeather(map);
                break;
        }
    }


    private void Visibility(boolean isVisible, int type) {
        if (type == 1) {
            if (isVisible) {
                ionicOnOffLl.setVisibility(View.GONE);
                onOffControlLl.setVisibility(View.VISIBLE);
            } else {
                ionicOnOffLl.setVisibility(View.VISIBLE);
                onOffControlLl.setVisibility(View.GONE);

            }
            ionicLl.setVisibility(View.VISIBLE);
            ionicControlLl.setVisibility(View.GONE);
        } else {
            ionicOnOffLl.setVisibility(View.VISIBLE);
            onOffControlLl.setVisibility(View.GONE);
            if (isVisible) {
                ionicLl.setVisibility(View.GONE);
                ionicControlLl.setVisibility(View.VISIBLE);
            } else {

                ionicLl.setVisibility(View.VISIBLE);
                ionicControlLl.setVisibility(View.GONE);

            }
        }

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void disimissProgress() {

    }

    @Override
    public void loadDataSuccess(FLZDevice tData) {
            if (tData.getResBody().getNum()==0){
                homeTVOCTv.setText("关");
            }else {
                homeTVOCTv.setText(tData.getResBody().getNum() + "组");
            }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        Log.e(TAG, throwable.getLocalizedMessage());
        toastor.showSingletonToast("服务器连接异常");
    }

    private void initView() {
        if (facility.getSwitchX().equals("0")) {
            homeTVOCTv.setText("关");
        } else {
            homeTVOCTv.setText(facility.getNum() + "组");
        }

    }
}

