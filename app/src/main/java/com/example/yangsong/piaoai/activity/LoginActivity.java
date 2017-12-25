package com.example.yangsong.piaoai.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.app.MyApplication;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.bean.Msg;
import com.example.yangsong.piaoai.bean.User;
import com.example.yangsong.piaoai.presenter.LoginPresenterImp;
import com.example.yangsong.piaoai.presenter.MsgPresenterImp;
import com.example.yangsong.piaoai.presenter.ThirdLoginPresenterImp;
import com.example.yangsong.piaoai.util.Log;
import com.example.yangsong.piaoai.util.MD5;
import com.example.yangsong.piaoai.util.SpUtils;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.LoginView;
import com.example.yangsong.piaoai.view.MsgView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, LoginView {


    @BindView(R.id.login_jizhu_cb)
    CheckBox loginJizhuCb;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.login_phone_et)
    EditText loginPhoneEt;
    @BindView(R.id.login_psw_et)
    EditText loginPswEt;
    private ProgressDialog progressDialog = null;
    private LoginPresenterImp loginPresenterImp = null;
    private MsgPresenterImp msgPresenterImp = null;
    private ThirdLoginPresenterImp thirdLoginPresenterImp = null;
    private Toastor toastor;
    Boolean IsRemember;
    String psw;
    UMShareAPI mShareAPI;
    String openid;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        toastor = new Toastor(this);
        loginJizhuCb.setOnCheckedChangeListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("登陆中,请稍后");
        loginPresenterImp = new LoginPresenterImp(this, this);
        msgPresenterImp = new MsgPresenterImp(new MsgView() {
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
                if (tData.getResCode().equals("0")){
                    MyApplication.newInstance().getUser().getResBody().setPhoneNumber(openid);
                    MyApplication.newInstance().getUser().getResBody().setPassWord(MD5.getMD5("123456"));
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

            }

            @Override
            public void loadDataError(Throwable throwable) {

            }
        }, this);
        thirdLoginPresenterImp = new ThirdLoginPresenterImp(new LoginView() {
            @Override
            public void showProgress() {

            }

            @Override
            public void disimissProgress() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void loadDataSuccess(User tData) {

                if (tData.getResCode().equals("0")) {
                    toastor.showSingletonToast(tData.getResMessage());
                    // tData.getResBody().setPsw(psw);
                    // tData.getResBody().setPsw(MD5.getMD5("123456"));
                    MyApplication.newInstance().setUser(tData);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else if (tData.getResMessage().equals("不存在此用户！")) {
                    Map<String, String> map = new HashMap<>();
                    map.put("phoneNumber", openid);
                    map.put("passWord", MD5.getMD5("123456"));
                    map.put("isThird", "1");
                    map.put("role", "0");
                    map.put("openID", openid);
                    msgPresenterImp.loadWeather(map);
                    //  startActivity(new Intent(LoginActivity.this, RegisterActivity.class).putExtra("openid", openid));
                }
            }

            @Override
            public void loadDataError(Throwable throwable) {
                toastor.showSingletonToast("服务器连接失败");
            }
        }, this);
        Boolean IsRemember = SpUtils.getBoolean("remember", true);
        loginJizhuCb.setChecked(IsRemember);
        mShareAPI = UMShareAPI.get(this);

    }


    @OnClick({R.id.login_jizhu_iv, R.id.login_tv, R.id.login_wechat_iv, R.id.login_register_tv, R.id.login_forget_password_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_jizhu_iv:
                break;
            case R.id.login_tv:
                String phone = loginPhoneEt.getText().toString().trim();
                psw = MD5.getMD5(loginPswEt.getText().toString().trim());
                if ( psw.length() >= 6)
                    loginPresenterImp.loadLogin(phone, psw);
                else
                    toastor.showSingletonToast("登陆信息有误");
                break;
            case R.id.login_wechat_iv:
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.login_register_tv:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login_forget_password_tv:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        SpUtils.putBoolean("remember", b);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Boolean IsRemember = SpUtils.getBoolean("remember", true);
        if (MyApplication.newInstance().getUser() != null&&IsRemember) {
            User user = MyApplication.newInstance().getUser();
            psw = user.getResBody().getPassWord();
            loginPresenterImp.loadLogin(user.getResBody().getPhoneNumber(), psw);
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
    public void loadDataSuccess(User tData) {
        toastor.showSingletonToast(tData.getResMessage());
        if (tData.getResCode().equals("0")) {
            tData.getResBody().setPassWord(psw);
            MyApplication.newInstance().setUser(tData);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        toastor.showSingletonToast("服务器连接失败");
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调

            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Log.e("onComplete", "openid:" + data.get("openid") + "/n gender: " + data.get("gender") + "/niconurl:" + data.get("iconurl"));
            thirdLoginPresenterImp.loadLogin(data.get("openid"));
            openid = data.get("openid");
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            toastor.showSingletonToast("第三方登陆失败");
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Log.e("onError", t.getLocalizedMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            toastor.showSingletonToast("第三方登陆取消");
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }
}
