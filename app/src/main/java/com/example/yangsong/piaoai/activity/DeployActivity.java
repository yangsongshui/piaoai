package com.example.yangsong.piaoai.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.hiflying.smartlink.ISmartLinker;
import com.hiflying.smartlink.OnSmartLinkListener;
import com.hiflying.smartlink.SmartLinkedModule;
import com.hiflying.smartlink.v7.MulticastSmartLinker;

import butterknife.BindView;
import butterknife.OnClick;

public class DeployActivity extends BaseActivity implements OnSmartLinkListener {


    @BindView(R.id.deploy_et)
    EditText deployEt;
    @BindView(R.id.wifi_name)
    TextView wifiName;
    Toastor toastor;
    String  deviceID;
    String sn;
    public static ISmartLinker mSnifferSmartLinker;
    ProgressDialog progressDialog;
    @Override
    protected int getContentView() {
        return R.layout.activity_deploy;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        toastor = new Toastor(this);
        deviceID=getIntent().getStringExtra("deviceID");
        sn=getIntent().getStringExtra("sn");
        progressDialog = new ProgressDialog(this);
        //progressDialog.setTitle();
        progressDialog.setMessage(getString(R.string.dialog_msg));
        progressDialog.setCanceledOnTouchOutside(false);
        mSnifferSmartLinker = MulticastSmartLinker.getInstance();
        mSnifferSmartLinker.setOnSmartLinkListener(this);
    }


    @OnClick({R.id.deploy_left_iv, R.id.binding_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.deploy_left_iv:
                finish();
                break;
            case R.id.binding_tv:
               //startActivity(new Intent(this, ThreeActivity.class).putExtra("deviceID",deviceID).putExtra("sn",sn));
                //配置WIFI
                start();
                break;
        }
    }
    private void start() {
        String Wifiname = wifiName.getText().toString();
        String Wifipwd = deployEt.getText().toString();
        if (Wifiname.equals(getString(R.string.deploy_msg))) {
            return;
        } else {
            try {
                progressDialog.show();
                mSnifferSmartLinker.start(this, Wifipwd.trim(),
                        Wifiname.trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    @Override
    protected void onResume() {
        super.onResume();
        @SuppressLint("WifiManagerLeak")
        WifiManager wifiManager = (WifiManager) this.getSystemService(WIFI_SERVICE);
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

    @Override
    public void onLinked(SmartLinkedModule smartLinkedModule) {
        //配对中的设备MAC
        Log.e("----------", smartLinkedModule.getMac() + "");
        toastor.showSingletonToast(getString(R.string.deploy_msg4));
    }

    @Override
    public void onCompleted() {
        //连接成功
        Log.e("----------", "连接成功");
        toastor.showSingletonToast(getString(R.string.deploy_msg5));
        startActivity(new Intent(this, ThreeActivity.class).putExtra("deviceID",deviceID).putExtra("sn",sn));
        progressDialog.dismiss();
    }

    @Override
    public void onTimeOut() {
        //连接超时
        toastor.showSingletonToast(getString(R.string.deploy_msg6));
        progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSnifferSmartLinker.setOnSmartLinkListener(null);
    }
}
