package com.example.yangsong.piaoai.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.bean.Identify;
import com.example.yangsong.piaoai.bean.Msg;
import com.example.yangsong.piaoai.presenter.GetCodePresenterImp;
import com.example.yangsong.piaoai.presenter.PswPresenterImp;
import com.example.yangsong.piaoai.util.MD5;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.GetCodeView;
import com.example.yangsong.piaoai.view.MsgView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity implements MsgView {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.psw_et)
    EditText pswEt;
    @BindView(R.id.psw2_et)
    EditText psw2Et;
    @BindView(R.id.get_code_tv)
    TextView get_code_tv;
    private PswPresenterImp pswPresenterImp = null;
    private GetCodePresenterImp getCodePresenterImp = null;
    private ProgressDialog progressDialog = null;
    private Toastor toastor;
    String code;
    private CountDownTimer timer;

    @Override
    protected int getContentView() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        pswPresenterImp = new PswPresenterImp(this, this);
        toastor = new Toastor(this);
        getCodePresenterImp = new GetCodePresenterImp(new GetCodeView() {
            @Override
            public void showProgress() {
                toastor.showSingletonToast(getString(R.string.dialog_msg10));
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
                toastor.showSingletonToast(getString(R.string.dialog_msg5));
            }
        }, this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.dialog_msg6));
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //每隔countDownInterval秒会回调一次onTick()方法
                String msg=millisUntilFinished / 1000 +getString(R.string.forget_msg);
                get_code_tv.setText(msg);
            }

            @Override
            public void onFinish() {
                get_code_tv.setText(getString(R.string.forget_msg2));
                get_code_tv.setEnabled(true);
            }
        };
    }


    @OnClick({R.id.pws_left_iv, R.id.get_code_tv, R.id.confirm_tv})
    public void onClick(View view) {
        String phone = phoneEt.getText().toString().trim();
        switch (view.getId()) {
            case R.id.pws_left_iv:
                finish();
                break;
            case R.id.get_code_tv:
                if (phone.length() == 11) {
                    get_code_tv.setEnabled(false);
                    timer.start();// 开始计时
                    getCodePresenterImp.GetCode(phone, "2");
                } else
                    toastor.showSingletonToast(getString(R.string.dialog_msg7));
                break;
            case R.id.confirm_tv:
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
                        pswPresenterImp.updatePwd(map);
                    } else
                        toastor.showSingletonToast(getString(R.string.dialog_msg8));
                else
                    toastor.showSingletonToast(getString(R.string.dialog_msg9));
                break;
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void disimissProgress() {

    }

    @Override
    public void loadDataSuccess(Msg tData) {
        toastor.showSingletonToast(tData.getResMessage());
        if (tData.getResCode().equals("0")) {
            finish();
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        toastor.showSingletonToast(getString(R.string.dialog_msg5));
    }
}
