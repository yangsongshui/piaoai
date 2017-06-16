package com.example.yangsong.piaoai.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.app.MyApplication;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.bean.Msg;
import com.example.yangsong.piaoai.presenter.FeedbackPresenterImp;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.MsgView;

import butterknife.BindView;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity implements MsgView {

    @BindView(R.id.suggest_input_et)
    EditText suggestInputEt;
    private Toastor toastor;
    private ProgressDialog progressDialog = null;
    private FeedbackPresenterImp feedbackPresenterImp = null;

    @Override
    protected int getContentView() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        toastor = new Toastor(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据提交中,请稍后");
        feedbackPresenterImp = new FeedbackPresenterImp(this, this);
    }


    @OnClick({R.id.iv_toolbar_left, R.id.submit_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                finish();
                break;
            case R.id.submit_tv:
                if (suggestInputEt.getText().toString().trim().length() >1)
                    feedbackPresenterImp.addRemark(MyApplication.newInstance().getUser().getResBody().getPhoneNumber(), suggestInputEt.getText().toString());
                break;
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
    public void loadDataSuccess(Msg tData) {
        toastor.showSingletonToast(tData.getResMessage());
        if (tData.getResCode().equals("0")) {
            finish();
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        toastor.showSingletonToast("服务器连接失败");
    }
}
