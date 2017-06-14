package com.example.yangsong.piaoai.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;


public class LoadingActivity extends BaseActivity {
    private Handler handler;
    private Runnable myRunnable;
    private final static int REQUECT_CODE_COARSE = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_loading;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        handler = new Handler(Looper.getMainLooper());
        myRunnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                finish();
            }
        };

        MPermissions.requestPermissions(LoadingActivity.this, REQUECT_CODE_COARSE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(REQUECT_CODE_COARSE)
    public void requestSdcardSuccess() {
        handler.postDelayed(myRunnable, 1500);
        Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(REQUECT_CODE_COARSE)
    public void requestSdcardFailed() {
        Toast.makeText(this, "定位权限获取失败", Toast.LENGTH_SHORT).show();


    }
}
