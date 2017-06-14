package com.example.yangsong.piaoai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class IonicActivity extends BaseActivity {
    @BindView(R.id.me_pic_iv)
    CircleImageView mePicIv;

    @BindView(R.id.activity_ionic)
    DrawerLayout activityMain;
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

    @Override
    protected int getContentView() {
        return R.layout.activity_ionic;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ionicControlLl.setAlpha(0f);
        onOffControlLl.setAlpha(0f);
    }

    @OnClick({R.id.main_left_iv, R.id.main_equipment_tv, R.id.main_user_tv, R.id.main_outside_tv, R.id.main_police_tv,
            R.id.main_feedback_tv, R.id.main_shop_tv, R.id.main_about_tv, R.id.main_out_tv, R.id.ionic_ll, R.id.ionic_on_off_ll, R.id.me_pic_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_left_iv:
                activityMain.openDrawer(GravityCompat.START);
                break;
            case R.id.main_equipment_tv:
                startActivity(new Intent(this, EquipmentActivity.class));
                break;
            case R.id.main_user_tv:
                startActivity(new Intent(this, UserActivity.class));
                break;
            case R.id.main_outside_tv:
                startActivity(new Intent(this, OutsideActivity.class));
                break;
            case R.id.main_police_tv:
                startActivity(new Intent(this, PoliceActivity.class));
                break;
            case R.id.main_feedback_tv:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.main_shop_tv:
                break;
            case R.id.main_about_tv:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.main_out_tv:
                break;

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
            case R.id.me_pic_iv:
                startActivity(new Intent(this, MyInfoActivity.class));
                break;
        }
    }


    @OnClick({R.id.min_tv, R.id.minus_tv, R.id.plus_tv, R.id.max_tv, R.id.on_tv, R.id.off_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
                Visibility(false, 1);
                break;
            case R.id.off_tv:
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
