package com.example.yangsong.piaoai.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.app.MyApplication;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.bean.Identify;
import com.example.yangsong.piaoai.bean.Msg;
import com.example.yangsong.piaoai.bean.User;
import com.example.yangsong.piaoai.presenter.GetCodePresenterImp;
import com.example.yangsong.piaoai.presenter.MsgPresenterImp;
import com.example.yangsong.piaoai.util.MD5;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.GetCodeView;
import com.example.yangsong.piaoai.view.MsgView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements MsgView {


    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.psw_et)
    EditText pswEt;
    @BindView(R.id.psw2_et)
    EditText psw2Et;
    /**
     * 对象定义
     **/
    private MsgPresenterImp loginPresenterImp = null;
    private GetCodePresenterImp getCodePresenterImp = null;
    private ProgressDialog progressDialog = null;
    private Toastor toastor;
    String code;
    private User user;

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        loginPresenterImp = new MsgPresenterImp(this, this);
        toastor = new Toastor(this);
        user = new User();
        getCodePresenterImp = new GetCodePresenterImp(new GetCodeView() {
            @Override
            public void showProgress() {
                toastor.showSingletonToast("获取中");
            }

            @Override
            public void disimissProgress() {

            }

            @Override
            public void loadDataSuccess(Identify identify) {
                toastor.showSingletonToast(identify.getResMessage());
                if (identify.getResCode().equals("0"))
                    code = identify.getResBody().getIdentify();
            }

            @Override
            public void loadDataError(Throwable throwable) {
                toastor.showSingletonToast("连接服务器失败");
            }
        }, this);
        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle();
        progressDialog.setMessage("注册中,请稍后");
    }


    @OnClick({R.id.register_left_iv, R.id.get_code_tv, R.id.register_tv})
    public void onClick(View view) {
        String phone = phoneEt.getText().toString().trim();
        switch (view.getId()) {
            case R.id.register_left_iv:
                finish();
                break;
            case R.id.get_code_tv:
                if (phone.length() == 11)
                    getCodePresenterImp.GetCode(phone, "0");
                else
                    toastor.showSingletonToast("手机号输入不正确");
                break;
            case R.id.register_tv:
                String psw = pswEt.getText().toString().trim();
                String psw2 = psw2Et.getText().toString().trim();
                String identify = codeEt.getText().toString().trim();
                if (identify.equals(code))
                    if (phone.length() == 11 && psw.equals(psw2)) {
                        Map<String, String> map = new HashMap<>();
                        map.put("phoneNumber", phone);
                        map.put("passWord", MD5.getMD5(psw));
                        map.put("isThird", "0");
                        map.put("role", "0");
                        user.getResBody().setPhoneNumber(phone);
                        user.getResBody().setPsw(MD5.getMD5(psw));
                        loginPresenterImp.loadWeather(map);
                    } else
                        toastor.showSingletonToast("手机号输入不正确或密码输入不一致");
                else
                    toastor.showSingletonToast("验证码有误");
                break;

        }
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void disimissProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void loadDataSuccess(Msg tData) {
        toastor.showSingletonToast(tData.getResMessage());
        if (tData.getResCode().equals("0")) {
            MyApplication.newInstance().setUser(user);
            finish();
        }

    }

    @Override
    public void loadDataError(Throwable throwable) {
        toastor.showSingletonToast("连接服务器失败");
    }


}
