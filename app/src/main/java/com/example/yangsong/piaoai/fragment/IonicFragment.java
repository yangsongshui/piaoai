package com.example.yangsong.piaoai.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseFragment;
import com.example.yangsong.piaoai.bean.Facility;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class IonicFragment extends BaseFragment {


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

    public IonicFragment(Facility.ResBodyBean.ListBean facility) {
        this.facility = facility;
    }

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        ionicControlLl.setAlpha(0f);
        onOffControlLl.setAlpha(0f);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_ionic;
    }

    @OnClick({R.id.ionic_ll, R.id.ionic_on_off_ll, R.id.min_tv, R.id.minus_tv, R.id.plus_tv, R.id.max_tv, R.id.on_tv, R.id.off_tv})
    public void onClick(View view) {
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
                Visibility(false, 0);
                break;
            case R.id.minus_tv:
                Visibility(false, 0);
                break;
            case R.id.plus_tv:
                Visibility(false, 0);
                break;
            case R.id.max_tv:
                Visibility(false, 0);
                break;
            case R.id.on_tv:
                homeTVOCTv.setText(6 + "组");
                Visibility(false, 1);
                break;
            case R.id.off_tv:
                homeTVOCTv.setText("关");
                Visibility(false, 1);
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
}
