package com.example.yangsong.piaoai.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.app.MyApplication;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.bean.User;
import com.example.yangsong.piaoai.presenter.LoginPresenterImp;
import com.example.yangsong.piaoai.util.DateUtil;
import com.example.yangsong.piaoai.util.MD5;
import com.example.yangsong.piaoai.util.SpUtils;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.LoginView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.yangsong.piaoai.util.DateUtil.LONG_DATE_FORMAT;

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
    private Toastor toastor;
    Boolean IsRemember;
    String psw;

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
        Boolean IsRemember = SpUtils.getBoolean("remember", true);
        loginJizhuCb.setChecked(IsRemember);


    }


    @OnClick({R.id.login_jizhu_iv, R.id.login_tv, R.id.login_wechat_iv, R.id.login_register_tv, R.id.login_forget_password_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_jizhu_iv:
                break;
            case R.id.login_tv:
                String phone = loginPhoneEt.getText().toString().trim();
                psw = MD5.getMD5(loginPswEt.getText().toString().trim());
                if (phone.length() == 11 && psw.length() >= 6)
                    loginPresenterImp.loadLogin(phone, psw);
                else
                    toastor.showSingletonToast("登陆信息有误");
                break;
            case R.id.login_wechat_iv:
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
        if (MyApplication.newInstance().getUser() != null) {
            User user = MyApplication.newInstance().getUser();
            psw = user.getResBody().getPsw();
            loginPresenterImp.loadLogin(user.getResBody().getPhoneNumber(),psw);
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

            tData.getResBody().setPsw(psw);
            MyApplication.newInstance().setUser(tData);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        toastor.showSingletonToast("服务器连接失败");
    }
}
