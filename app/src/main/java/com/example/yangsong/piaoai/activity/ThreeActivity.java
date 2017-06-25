package com.example.yangsong.piaoai.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.app.MyApplication;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.bean.Msg;
import com.example.yangsong.piaoai.presenter.BindingPresenterImp;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.MsgView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ThreeActivity extends BaseActivity implements MsgView {
    //打开地图请求码
    private int REQUEST_CODE = 0x01;
    //获取地址成功返回码
    private int RESULT_OK = 0xA1;
    @BindView(R.id.three_name_et)
    EditText threeNameEt;
    @BindView(R.id.three_address_et)
    TextView threeAddressEt;
    @BindView(R.id.three_deviceID)
    TextView threeDeviceID;
    String deviceID;
    ProgressDialog progressDialog;
    BindingPresenterImp bindingPresenterImp;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_three;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        deviceID = getIntent().getStringExtra("deviceID");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("设备绑定中...");
        bindingPresenterImp = new BindingPresenterImp(this, this);
        toastor = new Toastor(this);
        threeDeviceID.setText(deviceID);
    }


    @OnClick({R.id.three_left_iv, R.id.three_tv, R.id.three_address_et})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.three_left_iv:
                finish();
                break;
            case R.id.three_tv:
                String name = threeNameEt.getText().toString().trim();
                String address = threeAddressEt.getText().toString().trim();
                if (!address.equals("请选择设备所在地址")&&!address.equals("")) {
                    if (name.length() > 0) {
                        String phoneNumber = MyApplication.newInstance().getUser().getResBody().getPhoneNumber();
                        Map<String, String> map = new HashMap<>();
                        map.put("phoneNumber", phoneNumber);
                        map.put("deviceID", deviceID);
                        map.put("deviceName", name);
                        map.put("devicePosition", address);
                        bindingPresenterImp.binding(map);
                    } else {
                        toastor.showSingletonToast("设备名字不能为空");
                    }
                } else {
                    toastor.showSingletonToast("设备地址不能为空");
                }
                break;
            case R.id.three_address_et:
                Intent intent = new Intent(this, MapActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
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
        toastor.showSingletonToast(tData.getResMessage());
        if (tData.getResCode().equals("0")) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        toastor.showSingletonToast("服务器连接异常");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("address");
            threeAddressEt.setText(scanResult);
        }
    }
}
