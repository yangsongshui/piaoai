package com.example.yangsong.piaoai.activity;

import android.Manifest;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.zxing.activity.CaptureActivity;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import butterknife.BindView;
import butterknife.OnClick;

public class BindingActivity extends BaseActivity {
    private final static int REQUECT_CODE_COARSE = 1;
    @BindView(R.id.binding_et)
    EditText bindingEt;
    @BindView(R.id.binding_sn_et)
    EditText bidingSnEt;
    //打开扫描界面请求码
    private int REQUEST_CODE = 0x01;
    //扫描成功返回码
    private int RESULT_OK = 0xA1;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_binding;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        toastor = new Toastor(this);
    }


    @OnClick({R.id.binding_left_iv, R.id.tv_binding_right, R.id.binding_tv, R.id.binding_zxing_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.binding_left_iv:
                finish();
                break;
            case R.id.binding_zxing_iv:
                if (isCameraCanUse()) {
                    Intent intent = new Intent(this, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    MPermissions.requestPermissions(BindingActivity.this, REQUECT_CODE_COARSE, Manifest.permission.CAMERA);
                }
                break;
            default:
                String deviceID = bindingEt.getText().toString().toLowerCase();
                String sn = bidingSnEt.getText().toString();
                if (deviceID.length() == 14 && sn.length() == 8) {
                    String type = deviceID.substring(0, 2);
                    if (type.equals("f1")||type.equals("F1")) {
                        //WiFi设备
                        startActivity(new Intent(this, OneActivity.class).putExtra("deviceID", deviceID).putExtra("sn",sn));
                    } else {
                        //无需配置WiFi直接添加设备
                        startActivity(new Intent(this, ThreeActivity.class).putExtra("deviceID", deviceID).putExtra("sn",sn));
                    }
                } else
                    toastor.showSingletonToast(getString(R.string.binding_msg));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("qr_scan_result");
            String result = scanResult.substring(0, scanResult.indexOf("-")).toLowerCase();
            String sn = scanResult.substring(scanResult.indexOf("-") +1, scanResult.length());
            //将扫描出的信息显示出来
            bindingEt.setText(result);
            bidingSnEt.setText(sn);
            if (result.length() == 14&&sn.length() == 8) {
                String type = result.substring(0, 2);
              //  Log.e("SN",type);
                if (type.equals("f1")||type.equals("F1")) {
                    //WiFi设备
                    startActivity(new Intent(this, OneActivity.class).putExtra("deviceID", result).putExtra("sn",sn));
                } else {
                    //无需配置WiFi直接添加设备
                    startActivity(new Intent(this, ThreeActivity.class).putExtra("deviceID", result).putExtra("sn",sn));
                }
            } else
                toastor.showSingletonToast(getString(R.string.binding_msg));
        }
    }

    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            canUse = false;
        }
        if (canUse) {
            if (mCamera != null)
                mCamera.release();
            mCamera = null;
        }
        return canUse;
    }

    @PermissionGrant(REQUECT_CODE_COARSE)
    public void requestSdcardSuccess() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @PermissionDenied(REQUECT_CODE_COARSE)
    public void requestSdcardFailed() {

        toastor.showSingletonToast(getString(R.string.binding_msg2));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
