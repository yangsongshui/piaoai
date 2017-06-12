package com.example.yangsong.piaoai.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.bean.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.psw_et)
    EditText pswEt;
    @BindView(R.id.psw2_et)
    EditText psw2Et;

    @Override
    protected int getContentView() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }


    @OnClick({R.id.pws_left_iv, R.id.get_code_tv, R.id.psw_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pws_left_iv:
                finish();
                break;
            case R.id.get_code_tv:
                break;
            case R.id.psw_tv:
                break;
        }
    }
}
