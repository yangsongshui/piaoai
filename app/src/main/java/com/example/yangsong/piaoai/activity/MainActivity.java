package com.example.yangsong.piaoai.activity;

import android.app.ProgressDialog;
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
import com.example.yangsong.piaoai.presenter.FacilityPresenerImp;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.FacilityView;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.yangsong.piaoai.util.AppUtil.stringtoBitmap;

public class MainActivity extends BaseActivity implements FacilityView {
    private static final int RESULT = 1;
    private static final int REQUEST_CUT = 2;
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
    List<Facility.ResBodyBean.ListBean> mList;
    private ProgressDialog progressDialog = null;
    private FacilityPresenerImp facilityPresenerImp = null;
    private Toastor toastor;
    Facility facility;
    private String deviceID;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        toastor = new Toastor(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据更新中,请稍后");
        facilityPresenerImp = new FacilityPresenerImp(this, this);
        mAdapter = new TestFragmentAdapter(getSupportFragmentManager(), mList);
        pager.setAdapter(mAdapter);
        indicator.setViewPager(pager);
        String pic = MyApplication.newInstance().getUser().getResBody().getHeadPic();
        if (pic != null)
            if (pic.contains("http:")) {

            } else {
                mePicIv.setImageBitmap(stringtoBitmap(pic));
            }
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                deviceID = mList.get(position).getDeviceid();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @OnClick({R.id.main_left_iv, R.id.tv_main_right, R.id.main_equipment_tv, R.id.main_user_tv, R.id.main_outside_tv, R.id.main_police_tv,
            R.id.main_feedback_tv, R.id.main_shop_tv, R.id.main_about_tv, R.id.main_out_tv, R.id.me_pic_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_left_iv:
                activityMain.openDrawer(GravityCompat.START);
                break;
            case R.id.tv_main_right:
                startActivity(new Intent(this, HistoryActivity.class).putExtra("deviceID", deviceID));
                break;
            case R.id.main_equipment_tv:
                startActivityForResult((new Intent(this, EquipmentActivity.class).putExtra("facility", facility)), RESULT);
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

    @Override
    public void showProgress() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void disimissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public void loadDataSuccess(Facility tData) {
        facility = tData;
        // toastor.showSingletonToast(tData.getResMessage());
        mAdapter.setCount(tData.getResBody().getList());
        indicator.notifyDataSetChanged();
        pager.setCurrentItem(3);
    }

    @Override
    public void loadDataError(Throwable throwable) {
        toastor.showSingletonToast("服务器连接异常");
    }

    @Override
    protected void onResume() {
        super.onResume();
        facilityPresenerImp.findUserDevice(MyApplication.newInstance().getUser().getResBody().getPhoneNumber());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_CUT) {
            Bundle bundle = data.getExtras();
            int position = bundle.getInt("position");
            pager.setCurrentItem(position);
        }
    }
}
