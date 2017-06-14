package com.example.yangsong.piaoai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.adapter.TestFragmentAdapter;
import com.example.yangsong.piaoai.app.MyApplication;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.bean.Facility;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_title_tv)
    TextView mainTitleTv;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;
    @BindView(R.id.me_pic_iv)
    CircleImageView mePicIv;
    @BindView(R.id.left_menu_ll)
    LinearLayout leftMenuLl;
    @BindView(R.id.activity_main)
    DrawerLayout activityMain;

    TestFragmentAdapter mAdapter;
    List<Facility> mList;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        mList.add(new Facility());
        mList.add(new Facility());
        mList.add(new Facility());
        mAdapter = new TestFragmentAdapter(getSupportFragmentManager(), mList);
        pager.setAdapter(mAdapter);
        indicator.setViewPager(pager);


    }


    @OnClick({R.id.main_left_iv, R.id.tv_main_right, R.id.main_equipment_tv, R.id.main_user_tv, R.id.main_outside_tv, R.id.main_police_tv,
            R.id.main_feedback_tv, R.id.main_shop_tv, R.id.main_about_tv, R.id.main_out_tv, R.id.me_pic_iv})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.main_left_iv:
                activityMain.openDrawer(GravityCompat.START);
                break;
            case R.id.tv_main_right:
                startActivity(new Intent(this, HistoryActivity.class));
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
                MyApplication.newInstance().outLogin();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.me_pic_iv:
                startActivity(new Intent(this, MyInfoActivity.class));

                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityMain.closeDrawer(Gravity.LEFT);
    }
}
