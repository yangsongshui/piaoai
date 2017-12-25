package com.example.yangsong.piaoai.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.bean.Msg;
import com.example.yangsong.piaoai.presenter.AddServicePresenterImp;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.MsgView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MaintainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, MsgView {
    //打开地图请求码
    private int REQUEST_CODE = 0x01;
    //获取地址成功返回码
    private int RESULT_OK = 0xA1;
    @BindView(R.id.maintain_name_et)
    EditText maintainNameEt;
    @BindView(R.id.maintain_phone_et)
    EditText maintainPhoneEt;
    @BindView(R.id.maintain_id_et)
    EditText maintainIdEt;
    @BindView(R.id.maintain_rg)
    RadioGroup maintainRg;
    @BindView(R.id.maintain_et)
    EditText maintainEt;
    @BindView(R.id.maintain_address_et)
    TextView addressTv;
    String type = "1";
    String address = "";
    Map<String, String> map;
    AddServicePresenterImp addServicePresenterImp;
    ProgressDialog progressDialog;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_maintain;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        map = new HashMap<>();
        addServicePresenterImp = new AddServicePresenterImp(this, this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.maintain_msg));
        toastor = new Toastor(this);
        maintainRg.check(R.id.device_1);
        maintainRg.setOnCheckedChangeListener(this);
    }


    @OnClick({R.id.iv_toolbar_left, R.id.maintain_address_et, R.id.submit_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.maintain_address_et:
                Intent intent = new Intent(this, MapActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.submit_tv:
                commit();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.device_1:
                type = "1";
                break;
            case R.id.device_2:
                type = "2";
                break;
            case R.id.device_3:
                type = "3";
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("address");
            address = scanResult;
            addressTv.setText(scanResult);
        }
    }

    private void commit() {
        String name = maintainNameEt.getText().toString();
        String phone = maintainPhoneEt.getText().toString();
        String id = maintainIdEt.getText().toString();
        String maintain = maintainEt.getText().toString();
        if (!name.trim().equals("") && !id.trim().equals("") && !maintain.trim().equals("") && !address.trim().equals("") && phone.length() == 11) {
            map.put("deviceID", id);
            map.put("maintainPerson", name);
            map.put("maintainPhone", phone);
            map.put("faultDesc", maintain);
            map.put("faultLevl", type);
            map.put("address", address);
            addServicePresenterImp.loadWeather(map);
        } else {
            toastor.showSingletonToast(getString(R.string.maintain_msg2));
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
         finish();
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        toastor.showSingletonToast(getString(R.string.dialog_msg5));
    }
}
