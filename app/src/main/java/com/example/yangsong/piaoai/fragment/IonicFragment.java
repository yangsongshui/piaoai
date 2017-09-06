package com.example.yangsong.piaoai.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
        toastor = new Toastor(getActivity());
        initView();
        addPresenterImp = new AddPresenterImp(this, getContext());
        reducePresenterImp = new ReducePresenterImp(this, getContext());
        openPresenterImp = new OpenPresenterImp(this, getContext());
        closePresenterImp = new ClosePresenterImp(this, getContext());
        minPresenterImp = new MinPresenterImp(this, getContext());
        maxPresenterImp = new MaxPresenterImp(this, getContext());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("数据查询中...");
        map = new HashMap<>();

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_ionic;
    }

    @OnClick({ R.id.min_tv, R.id.minus_tv, R.id.plus_tv, R.id.max_tv, R.id.on_tv, R.id.off_tv})
    public void onClick(View view) {
        map.clear();
        map.put("imei", facility.getDeviceid());
        switch (view.getId()) {
            case R.id.min_tv:
                map.put("num", "1");
                minPresenterImp.loadWeather(map);
                break;
            case R.id.minus_tv:
                reducePresenterImp.loadWeather(map);
                break;
            case R.id.plus_tv:
                addPresenterImp.loadWeather(map);
                break;
            case R.id.max_tv:
                map.put("num", "8");
                maxPresenterImp.loadWeather(map);
                break;
            case R.id.on_tv:
                openPresenterImp.loadWeather(map);
                break;
            case R.id.off_tv:
                closePresenterImp.loadWeather(map);
                break;
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
        toastor.showSingletonToast(tData.getResMessage());
        if (tData.getResBody().getNum() == 0) {
            homeTVOCTv.setText("关");
        } else {
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

