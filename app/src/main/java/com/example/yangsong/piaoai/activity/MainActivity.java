package com.example.yangsong.piaoai.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
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
    private final static String TAG = MainActivity.class.getSimpleName();
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
    @BindView(R.id.tv_main_right)
    ImageView tvMainRight;
    TestFragmentAdapter mAdapter;
    List<Facility.ResBodyBean.ListBean> mList;
    @BindView(R.id.main_fragment)
    LinearLayout mainFragment;
    @BindView(R.id.main_maintain_tv)
    TextView main_maintain_tv;
    private ProgressDialog progressDialog = null;
    private FacilityPresenerImp facilityPresenerImp = null;
    private Toastor toastor;
    Facility facility;
    Facility.ResBodyBean.ListBean device;
    private Handler handler;
    private Runnable myRunnable;
    private String type = "0";
    private int position = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        handler = new Handler();
        mList = new ArrayList<>();
        toastor = new Toastor(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.dialog_msg6));
        facilityPresenerImp = new FacilityPresenerImp(this, this);
        mAdapter = new TestFragmentAdapter(getSupportFragmentManager(), mList);
        pager.setAdapter(mAdapter);
        indicator.setViewPager(pager);
        if (!MyApplication.newInstance().getUser().getResBody().getRole().equals("0")) {
            main_maintain_tv.setVisibility(View.VISIBLE);
        }

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                device = mList.get(position);
                mainTitleTv.setText(device.getDeviceName());
                // Log.e(TAG, device.getDeviceid());
                type = device.getType();
                if (type.equals("3") || type.equals("2")) {
                    tvMainRight.setVisibility(View.GONE);
                } else {
                    tvMainRight.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        myRunnable = new Runnable() {
            @Override
            public void run() {
                facilityPresenerImp.findUserDevice(MyApplication.newInstance().getUser().getResBody().getPhoneNumber());
                handler.postDelayed(myRunnable, 60000);
            }
        };

    }


    @OnClick({R.id.main_left_iv, R.id.tv_main_right, R.id.main_equipment_tv, R.id.main_user_tv, R.id.main_outside_tv, R.id.main_police_tv,
            R.id.main_feedback_tv, R.id.main_shop_tv, R.id.main_about_tv, R.id.main_out_tv, R.id.me_pic_iv, R.id.main_maintain_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_left_iv:
                activityMain.openDrawer(GravityCompat.START);
                break;
            case R.id.tv_main_right:
                //  Log.e("init", device.getDeviceid() + " " + type);
                if (mList.size() > 0)
                    startActivity(new Intent(this, HistoryActivity.class).putExtra("deviceID", device.getDeviceid()).putExtra("type", device.getType()));
                else
                    toastor.showSingletonToast(getString(R.string.main_msg));
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
            case R.id.main_maintain_tv:
                startActivity(new Intent(this, MaintainActivity.class));
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityMain.closeDrawer(Gravity.LEFT);
        handler.removeCallbacks(myRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(myRunnable);
        handler = null;
        facilityPresenerImp = null;
    }

    @Override
    public void showProgress() {
        if (progressDialog != null && !progressDialog.isShowing()&&!isOne) {
            progressDialog.show();
        }
    }

    @Override
    public void disimissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    boolean isOne = false;

    @Override
    public void loadDataSuccess(Facility tData) {
        // Log.e(TAG, tData.toString());
        if (tData.getResCode().equals("0")) {
            mList = tData.getResBody().getList();
            if (mList.size() > 0) {
                mAdapter.setCount(tData.getResBody().getList());
                indicator.notifyDataSetChanged();
                facility = tData;
                if (device == null) {
                    isOne = true;
                    device = mList.get(0);
                } else {
                    if (mList.size() < position)
                        position = mList.size();
                    device = mList.get(position);
                }
                mainTitleTv.setText(device.getDeviceName());
                if (device.getType().equals("3") || type.equals("3") || device.getType().equals("2") || type.equals("2")) {
                    tvMainRight.setVisibility(View.GONE);
                } else {
                    tvMainRight.setVisibility(View.VISIBLE);
                }
                mainFragment.setVisibility(View.GONE);
            } else if (!isOne) {
                isOne = true;
                mainFragment.setVisibility(View.VISIBLE);
                toastor.showSingletonToast(getString(R.string.main_msg));
                startActivity(new Intent(this, BindingActivity.class));
            } else {
                toastor.showSingletonToast(getString(R.string.main_msg));
                mAdapter.setCount(tData.getResBody().getList());
                indicator.notifyDataSetChanged();
                mainFragment.setVisibility(View.VISIBLE);
            }

        } else {
            toastor.showSingletonToast(tData.getResMessage());
        }

    }

    @Override
    public void loadDataError(Throwable throwable) {
        Log.e(TAG, throwable.toString());
        toastor.showSingletonToast(getString(R.string.dialog_msg5));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //facilityPresenerImp.findUserDevice(MyApplication.newInstance().getUser().getResBody().getPhoneNumber());
        handler.postDelayed(myRunnable, 300);
        String pic = MyApplication.newInstance().getUser().getResBody().getHeadPic();
        if (pic != null && pic.length() > 5) {
            Log.e("pic", pic);
            if (pic.contains("http:")) {
                MyApplication.newInstance().getGlide().load(pic).into(mePicIv);
            } else {
                mePicIv.setImageBitmap(stringtoBitmap(pic));
            }
        }
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
