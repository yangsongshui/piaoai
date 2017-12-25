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
import com.example.yangsong.piaoai.presenter.ModificationPresenterImp;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.MsgView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ModificationActivity extends BaseActivity implements MsgView {
    //打开地图请求码
    private int REQUEST_CODE = 0x01;
    //获取地址成功返回码
    private int RESULT_OK = 0xA1;
    @BindView(R.id.mod_name_et)
    EditText modNameEt;
    @BindView(R.id.mod_address_et)
    TextView modAddressEt;
    String deviceID;
    ProgressDialog progressDialog;
    ModificationPresenterImp modificationPresenterImp;
    Toastor toastor;
    String name;
    @Override
    protected int getContentView() {
        return R.layout.activity_modification;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        deviceID=getIntent().getStringExtra("deviceID");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.modification_msg));
        modificationPresenterImp = new ModificationPresenterImp(this, this);
        toastor = new Toastor(this);
    }


    @OnClick({R.id.iv_toolbar_left, R.id.mod_address_et, R.id.mod_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.mod_address_et:
                Intent intent = new Intent(this, MapActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.mod_tv:
                 name = modNameEt.getText().toString().trim();
                String address = modAddressEt.getText().toString().trim();
                if (address.equals(getString(R.string.modification_msg2)))
                    address = "";
                if (name.length() > 0) {
                    String phoneNumber = MyApplication.newInstance().getUser().getResBody().getPhoneNumber();
                    Map<String, String> map = new HashMap<>();
                    map.put("phoneNumber", phoneNumber);
                    map.put("deviceID", deviceID);
                    map.put("deviceName", name);
                    map.put("devicePosition", address);
                    modificationPresenterImp.binding(map);
                } else {
                    toastor.showSingletonToast(getString(R.string.modification_msg3));
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("address");
            modAddressEt.setText(scanResult);
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
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            resultIntent.putExtras(bundle);
            this.setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        toastor.showSingletonToast(getString(R.string.dialog_msg5));
    }
}
