package com.example.yangsong.piaoai.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.util.Log;
import com.example.yangsong.piaoai.util.Toastor;

import butterknife.BindView;
import butterknife.OnClick;

public class DeployActivity extends BaseActivity {


    @BindView(R.id.deploy_et)
    EditText deployEt;
    @BindView(R.id.wifi_name)
    TextView wifiName;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_deploy;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        toastor = new Toastor(this);

    }


    @OnClick({R.id.deploy_left_iv, R.id.binding_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.deploy_left_iv:
                finish();
                break;
            case R.id.binding_tv:
                startActivity(new Intent(this, ThreeActivity.class));
                break;
        }
    }

    private void showSexDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("无WiFi连接");
        builder.setMessage("是否跳转到WiFI设置界面连接WiFi");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toastor.showSingletonToast("无WiFi连接 无法继续配置该设备");
                finish();

            }
        });
        builder.create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        @SuppressLint("WifiManagerLeak") WifiManager wifiManager = (WifiManager) this.getSystemService(WIFI_SERVICE);
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED && mWifi.isConnected()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            Log.e("wifi信息：" + wifiInfo.toString());
            Log.e("wifi名称：" + wifiInfo.getSSID());
            wifiName.setText(wifiInfo.getSSID());
        } else {
            showSexDialog();
        }
    }
}
