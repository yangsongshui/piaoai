package com.example.yangsong.piaoai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.bean.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.login_jizhu_cb)
    CheckBox loginJizhuCb;
    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        loginJizhuCb.setOnCheckedChangeListener(this);
    }


    @OnClick({R.id.login_jizhu_iv, R.id.login_tv, R.id.login_wechat_iv, R.id.login_register_tv, R.id.login_forget_password_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_jizhu_iv:
                break;
            case R.id.login_tv:
                startActivity(new Intent(this, MainActivity.class));
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

    }


}
