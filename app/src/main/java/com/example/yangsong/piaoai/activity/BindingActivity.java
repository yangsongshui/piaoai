package com.example.yangsong.piaoai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.bean.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class BindingActivity extends BaseActivity {


    @BindView(R.id.binding_et)
    EditText bindingEt;

    @Override
    protected int getContentView() {
        return R.layout.activity_binding;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }


    @OnClick({R.id.binding_left_iv, R.id.tv_binding_right, R.id.binding_tv, R.id.binding_zxing_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.binding_left_iv:
                finish();
                break;
            case R.id.tv_binding_right:
                break;
            case R.id.binding_tv:
                startActivity(new Intent(this, DeployActivity.class));
                break;
            case R.id.binding_zxing_iv:
                break;
        }
    }
}
