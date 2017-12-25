package com.example.yangsong.piaoai.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.adapter.EquipmentAdapter;
import com.example.yangsong.piaoai.app.MyApplication;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.bean.Facility;
import com.example.yangsong.piaoai.bean.Msg;
import com.example.yangsong.piaoai.presenter.DeleteDevicePresenterImp;
import com.example.yangsong.piaoai.util.Log;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.MsgView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class EquipmentActivity extends BaseActivity implements SwipeMenuListView.OnMenuItemClickListener, AdapterView.OnItemClickListener, MsgView {
    private static final int RESULT = 1;
    private static final int REQUEST_CUT = 2;
    //打开地图请求码
    private int REQUEST_CODE = 0x01;
    //获取地址成功返回码
    private int RESULT_OK = 0xA1;
    private static final String TAG = EquipmentActivity.class.getName();
    @BindView(R.id.listView)
    SwipeMenuListView listView;
    EquipmentAdapter adapter;
    List<Facility.ResBodyBean.ListBean> mList;
    int position = 0;
    ProgressDialog progressDialog;
    DeleteDevicePresenterImp deleteDevicePresenterImp;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_equipment;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.equipment_msg));
        deleteDevicePresenterImp = new DeleteDevicePresenterImp(this, this);
        toastor = new Toastor(this);
        Facility facility = (Facility) getIntent().getSerializableExtra("facility");
        if (facility!= null)
            mList = facility.getResBody().getList();
        else
            mList = new ArrayList<>();
        adapter = new EquipmentAdapter(mList, this);
        listView.setAdapter(adapter);
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(this);
        listView.setOnItemClickListener(this);
    }


    @OnClick({R.id.equipment_left_iv, R.id.tv_equipment_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.equipment_left_iv:
                finish();
                break;
            case R.id.tv_equipment_right:
                startActivity(new Intent(this, BindingActivity.class));
                break;
        }
    }


    /**
     * 侧滑监听
     */
    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
        Log.e(TAG, index + "");
        switch (index) {
            case 0:
                //更多
                this.position = position;
                Intent intent = new Intent(this, ModificationActivity.class);
                intent.putExtra("deviceID", mList.get(position).getDeviceid());
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case 1:
                //删除
                this.position = position;
                String phoneNumber = MyApplication.newInstance().getUser().getResBody().getPhoneNumber();
                String deviceID = mList.get(position).getDeviceid();
                Map<String, String> map = new HashMap<>();
                map.put("phoneNumber", phoneNumber);
                map.put("deviceID", deviceID);
                deleteDevicePresenterImp.binding(map);
                break;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("position", i);
        resultIntent.putExtras(bundle);
        this.setResult(REQUEST_CUT, resultIntent);
        finish();
    }

    /*添加侧滑菜单*/
    SwipeMenuCreator creator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            //改名菜单
            SwipeMenuItem alterItem = new SwipeMenuItem(
                    getApplicationContext());
            // 设置背景颜色
            alterItem.setBackground(new ColorDrawable(getResources().getColor(R.color.tan_hide)));
            // 设置宽度
            alterItem.setWidth(dp2px(70));
            // 设置内容
            alterItem.setTitle(getString(R.string.equipment_msg2));
            // 设置字体大小
            alterItem.setTitleSize(14);
            // 字体颜色
            alterItem.setTitleColor(getResources().getColor(R.color.white));
            // 添加菜单
            menu.addMenuItem(alterItem);

            // 删除菜单
            SwipeMenuItem deleteItem = new SwipeMenuItem(
                    getApplicationContext());
            // set item background
            deleteItem.setBackground(new ColorDrawable(getResources().getColor(R.color.carmine_pink)));
            // set item width
            deleteItem.setWidth(dp2px(70));
            // 设置内容
            deleteItem.setTitle(getString(R.string.equipment_msg3));
            // 设置字体大小
            deleteItem.setTitleSize(14);
            // 字体颜色
            deleteItem.setTitleColor(getResources().getColor(R.color.white));
            // add to menu
            menu.addMenuItem(deleteItem);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("name");
            mList.get(position).setDeviceName(scanResult);
            adapter.notifyDataSetChanged();
        }
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
    public void loadDataSuccess(Msg tData) {

        if (tData.getResCode().equals("0")) {
            toastor.showSingletonToast(getString(R.string.dialog_msg3));
            mList.remove(position);
            adapter.notifyDataSetChanged();

        }else {
            toastor.showSingletonToast(getString(R.string.dialog_msg4));
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        toastor.showSingletonToast(getString(R.string.dialog_msg5));
    }
}
