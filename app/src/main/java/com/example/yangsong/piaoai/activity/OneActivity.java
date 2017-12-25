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

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.util.Log;
import com.example.yangsong.piaoai.util.Toastor;

import butterknife.OnClick;

public class OneActivity extends BaseActivity {
    String deviceID;
    Toastor toastor;
    String sn;
    @Override
    protected int getContentView() {
        return R.layout.activity_one;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        deviceID = getIntent().getStringExtra("deviceID");
        sn = getIntent().getStringExtra("sn");
        toastor = new Toastor(this);
    }


    @OnClick({R.id.deploy_left_iv, R.id.one_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.deploy_left_iv:
                finish();
                break;
            case R.id.one_tv:
                startActivity(new Intent(this, DeployActivity.class).putExtra("deviceID", deviceID).putExtra("sn",sn));
                break;
        }
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

        } else {
            showSexDialog();
        }
    }

    private void showSexDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.deploy_msg2));
        builder.setMessage(getString(R.string.deploy_msg3));
        builder.setPositiveButton(getString(R.string.dialog_bt), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
                startActivity(intent);
            }
        });
        builder.setNegativeButton(getString(R.string.dialog_bt2), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toastor.showSingletonToast(getString(R.string.dialog_msg2));
                finish();

            }
        });
        builder.create().show();
    }

}
